package com.analytics;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.analytics.model.Project;
import com.analytics.model.ProjectList;
import com.analytics.model.Stats;
import com.analytics.model.StatsList;
import com.analytics.network.NetworkUtil;
import com.analytics.utils.Constants;
import com.analytics.utils.StatsCount;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.learn2crack.R;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;*/




public class ReportingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;
    private StatsList statsList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSharedPreferences();
        mSubscriptions = new CompositeSubscription();
        initialisationProcess();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Updating....", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /**TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText("MEDEROS Manuel");
        TextView userId = (TextView) findViewById(R.id.userid);
        userId.setText("manuel@gmail.com");*/



        /**GraphView graph = (GraphView) findViewById(R.id.graph1);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);*/


        ConstraintLayout eventLayout = (ConstraintLayout) findViewById(R.id.eventLayout);
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("eventCheckBox", false);
        if(checked == false) {
            eventLayout.setVisibility(View.GONE);
        }
        else{
            eventLayout.setVisibility(View.VISIBLE);
            /**int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
            TextView eventTodayNb = (TextView) findViewById(R.id.eventTodayNb);
            TextView eventWeekNb = (TextView) findViewById(R.id.eventWeekNb);
            TextView eventMonthNb = (TextView) findViewById(R.id.eventMonthNb);
            TextView eventAllNb = (TextView) findViewById(R.id.eventAllNb);
            eventTodayNb.setText(randomNum + "");
            eventWeekNb.setText(randomNum + "");
            eventMonthNb.setText(randomNum + "");
            eventAllNb.setText(randomNum + "");*/
        }

        ConstraintLayout categoryLayout = (ConstraintLayout) findViewById(R.id.categoryLayout);
        boolean checked1 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("categoryCheckBox", false);
        if(checked1 == false) {
            categoryLayout.setVisibility(View.GONE);
        }
        else{
            categoryLayout.setVisibility(View.VISIBLE);
        }

        ConstraintLayout actionLayout = (ConstraintLayout) findViewById(R.id.actionLayout);
        boolean checked2 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("actionCheckBox", false);
        if(checked2 == false) {
            actionLayout.setVisibility(View.GONE);
        }
        else{
            actionLayout.setVisibility(View.VISIBLE);
        }

    }



    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void initialisationProcess() {

        String idSave = mSharedPreferences.getString(Constants.ID, "0");
        BigInteger id = BigInteger.valueOf(Integer.parseInt(idSave));
        mSubscriptions.add(NetworkUtil.getRetrofit(Constants.REPORTING_URL).getProjects(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }



    @TargetApi(Build.VERSION_CODES.M)
    private void handleResponse(ProjectList projects) {

        Log.println(Log.INFO,"PROJECTS_OWN", "*****" + projects.getProjectOwn() + "**************************");
        Log.println(Log.INFO,"PROJECTS_COLLABORATIONS", "*****" + projects.getCollaborations() + "**************************");

        List <Project> projectOwn = projects.getProjectOwn();
        List <Project> projectCollaborations = projects.getCollaborations();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Set<String> projectNames = new ArraySet<String>();
        if(projectOwn.size() != 0) {
            for (Project project: projectOwn) {
                Log.println(Log.INFO,"ID:", String.valueOf(project.getId()));
                Log.println(Log.INFO,"NAME:", project.getName());
                Log.println(Log.INFO,"URL_FOLLOWED:", project.getUrlfollowed());
                projectNames.add(project.getName());
            }
        }
        if(projectCollaborations.size() != 0) {
            for (Project project: projectCollaborations) {
                Log.println(Log.INFO,"ID:", String.valueOf(project.getId()));
                Log.println(Log.INFO,"NAME:", project.getName());
                Log.println(Log.INFO,"URL_FOLLOWED:", project.getUrlfollowed());
                projectNames.add(project.getName());
            }
        }

        editor.putStringSet(Constants.PROJECTSNAME, projectNames);
        editor.apply();

        //Project projectSelected = selectedProject(projects);
        //initialiseStats(projectSelected);
        initialiseStats(projectOwn.get(0));

    }

    public Project selectedProject(ProjectList projects) {
        String selected = mSharedPreferences.getString(Constants.PROJECTSELECTED, "null");
        List <Project> projectOwn = projects.getProjectOwn();
        List <Project> projectCollaborations = projects.getCollaborations();
        if(selected.equals(null)) {
            if(projectOwn.size() != 0) {
                mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, projectOwn.get(0).getName());
                return projectOwn.get(0);
            }

            else {
                if(projectCollaborations.size() != 0) {
                    mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, projectOwn.get(0).getName());
                    return projectCollaborations.get(0);
                }
            }
            }
            else {
            for (Project project: projectOwn) {
                if(project.getName().equals(selected)) return project;
            }
            for (Project project: projectCollaborations) {
                if(project.getName().equals(selected)) return project;
            }
        }
        return null;
    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                //Response response = gson.fromJson(errorBody,Response.class);
                //showSnackBarMessage(response.getMessage());
                //showSnackBarMessage("Response Error !");
                Log.println(Log.ERROR,"ERROR1",errorBody );

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.println(Log.ERROR,"ERROR2",error.toString() );
        }
    }

    private void initialiseStats(Project project) {
        Log.println(Log.INFO, "STAT", "*******************************************");
        Log.println(Log.INFO, "STATID", String.valueOf(project.getId()));
        Log.println(Log.INFO, "STATNAME", String.valueOf(project.getName()));
        mSubscriptions.add(NetworkUtil.getRetrofit(Constants.PROJECTS_URL).getAllStats(project.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseStats, this::handleErrorStats));

    }


    private void handleResponseStats(List<Stats> stats) {

        for (Stats stat: stats) {
            Log.println(Log.INFO,"ID:", String.valueOf(stat.getId()));
            Log.println(Log.INFO,"BASEURL:", stat.getBaseurl());
            Log.println(Log.INFO,"ROUTE:", stat.getRoute());
            Log.println(Log.INFO,"EVENTNAME:", stat.getEventname());
            Log.println(Log.INFO,"CATEGORY:", stat.getCategory());
            Log.println(Log.INFO,"ACTION:", stat.getAction());
            Log.println(Log.INFO,"DATE:", stat.getCreatedAt());
        }

        updateEventStats(stats, "index");
        updateCategoryStats(stats, "page");
    }




    private void updateEventStats(List<Stats> stats, String eventName) {


        long allEvents = StatsCount.countAllEventsByEventName(stats,eventName);
        long todayEvents = StatsCount.countTodayEventsByEventName(stats,eventName);
        long thisWeekEvents = StatsCount.countWeekEventsByEventName(stats,eventName);
        long thisMonthEvents = StatsCount.countMonthEventsByEventName(stats,eventName);
        TextView eventLayoutTitle = (TextView) findViewById(R.id.eventLayoutTitle);
        eventLayoutTitle.setText("Event Name:" + eventName);
        TextView eventAllNb = (TextView) findViewById(R.id.eventAllNb);
        TextView eventTodayNb = (TextView) findViewById(R.id.eventTodayNb);
        TextView eventWeekNb = (TextView) findViewById(R.id.eventWeekNb);
        TextView eventMontNb = (TextView) findViewById(R.id.eventMonthNb);

        eventAllNb.setText(String.valueOf(allEvents));
        eventTodayNb.setText(String.valueOf(todayEvents));
        eventWeekNb.setText(String.valueOf(thisWeekEvents));
        eventMontNb.setText(String.valueOf(thisMonthEvents));
    }

    private void updateCategoryStats(List<Stats> stats, String categoryName) {
        long allEvents = StatsCount.countAllCategoriesByCategory(stats,categoryName);
        long todayEvents = StatsCount.countTodayCategoriesByCategory(stats,categoryName);
        long thisWeekEvents = StatsCount.countWeekCategoriesByCategory(stats,categoryName);
        long thisMonthEvents = StatsCount.countMonthCategoriesByCategory(stats,categoryName);

        TextView categoryLayoutTitle = (TextView) findViewById(R.id.categoryLayoutTitle);
        categoryLayoutTitle.setText("Event Name:" + categoryName);
        TextView categoryAllNb = (TextView) findViewById(R.id.categoryAllNb);
        TextView categoryTodayNb = (TextView) findViewById(R.id.categoryTodayNb);
        TextView categoryWeekNb = (TextView) findViewById(R.id.categoryWeekNb);
        TextView categoryMontNb = (TextView) findViewById(R.id.categoryMonthNb);

        categoryAllNb.setText(String.valueOf(allEvents));
        categoryTodayNb.setText(String.valueOf(todayEvents));
        categoryWeekNb.setText(String.valueOf(thisWeekEvents));
        categoryMontNb.setText(String.valueOf(thisMonthEvents));
    }







    private void handleErrorStats(Throwable error){

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Log.println(Log.ERROR,"ERRORSTAT1","\n" + errorBody );

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.println(Log.ERROR,"ERRORSTAT2",error.toString() );
        }
    }


    public void onSnapGraph(View view){
        /**GraphView graph = (GraphView) findViewById(R.id.graph1);
        graph.takeSnapshotAndShare(this, "exampleGraph", "GraphViewSnapshot");*/
    }

    public void onUpdate(){
        TextView numberofUser = (TextView) findViewById(R.id.eventTodayNb);
        String nbofUser = (String) numberofUser.getText();
        Integer nbfortest  = Integer.parseInt(nbofUser);
        nbfortest++;
        numberofUser.setText(nbfortest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        TextView userName = (TextView) findViewById(R.id.userName);
        userName.setText(mSharedPreferences.getString(Constants.LASTNAME, null)
                + " " + mSharedPreferences.getString(Constants.FIRSTNAME, null));
        TextView userId = (TextView) findViewById(R.id.userid);
        userId.setText(mSharedPreferences.getString(Constants.EMAIL, null));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_menu) {
            /**Intent intent = new Intent(this, ReportingActivity.class);
            startActivity(intent);*/
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, ManageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
