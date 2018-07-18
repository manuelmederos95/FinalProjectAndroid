package com.analytics;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;

import com.analytics.model.Project;
import com.analytics.model.ProjectList;
import com.analytics.model.Response;
import com.analytics.model.Stats;
import com.analytics.network.NetworkUtil;
import com.analytics.utils.Constants;
import com.analytics.utils.StatsCount;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;



public class ReportingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;
    private BarChart eventChart;
    private BarChart categoryChart;
    private BarChart actionChart;


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
        fab.setOnClickListener(view -> initialisationProcess());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        setLayoutsVisibility();
        setSpinnerListenners();

    }

    private void setLayoutsVisibility() {
        ConstraintLayout eventLayout = (ConstraintLayout) findViewById(R.id.eventLayout);
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("eventCheckBox", false);
        if(checked == false) {
            eventLayout.setVisibility(View.GONE);
        }
        else{
            eventLayout.setVisibility(View.VISIBLE);
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

        eventChart = (BarChart) findViewById(R.id.eventChart);
        boolean checked3 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("eventChartCheckBox", false);
        if(checked3 == false) {
            eventChart.setVisibility(View.GONE);
        }
        else{
            eventChart.setVisibility(View.VISIBLE);
        }

        categoryChart = (BarChart) findViewById(R.id.categoryChart);
        boolean checked4 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("categoryChartCheckBox", false);
        if(checked4 == false) {
            categoryChart.setVisibility(View.GONE);
        }
        else{
            categoryChart.setVisibility(View.VISIBLE);
        }

        actionChart = (BarChart) findViewById(R.id.actionChart);
        boolean checked5 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("actionChartCheckBox", false);
        if(checked5 == false) {
            actionChart.setVisibility(View.GONE);
        }
        else{
            actionChart.setVisibility(View.VISIBLE);
        }

    }


    private void setSpinnerListenners() {
        Spinner spinnerProjects = (Spinner) findViewById(R.id.spinnerProjects);
        spinnerProjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getSelectedItem().toString();
                try {
                    if (!mSharedPreferences.getString(Constants.PROJECTSELECTED, null).equals(selected)) {
                        mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, selected).apply();
                    }
                }catch (NullPointerException exception) {

                }
                initialisationProcess();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinnerEvent = (Spinner) findViewById(R.id.spinnerEvent);
        spinnerEvent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                try {
                    if (!mSharedPreferences.getString(Constants.EVENTSELECTED, null).equals(selected)) {
                        mSharedPreferences.edit().putString(Constants.EVENTSELECTED, selected).apply();
                        initialisationProcess();
                    }
                }catch (NullPointerException exception){
                        mSharedPreferences.edit().putString(Constants.EVENTSELECTED, selected).apply();
                        initialisationProcess();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                try {
                    if (!mSharedPreferences.getString(Constants.CATEGORYSELECTED, null).equals(selected)) {
                        mSharedPreferences.edit().putString(Constants.CATEGORYSELECTED, selected).apply();
                        initialisationProcess();
                    }
                }catch (NullPointerException exception){
                    mSharedPreferences.edit().putString(Constants.CATEGORYSELECTED, selected).apply();
                    initialisationProcess();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinnerAction = (Spinner) findViewById(R.id.spinnerAction);
        spinnerAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                try {
                    if (!mSharedPreferences.getString(Constants.ACTIONSELECTED, null).equals(selected)) {
                        mSharedPreferences.edit().putString(Constants.ACTIONSELECTED, selected).apply();
                        initialisationProcess();
                    }
                }catch (NullPointerException exception){
                    mSharedPreferences.edit().putString(Constants.ACTIONSELECTED, selected).apply();
                    initialisationProcess();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void initialisationProcess() {

        //Update widget
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
        remoteViews.setTextViewText(R.id.widgetProjectTitle, "Project:" + mSharedPreferences.getString(Constants.PROJECTSELECTED,null));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);


        String idSave = mSharedPreferences.getString(Constants.ID, "0");
        BigInteger id = BigInteger.valueOf(Integer.parseInt(idSave));
        mSubscriptions.add(NetworkUtil.getRetrofit(Constants.REPORTING_URL).getProjects(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void initProjectSpinner(List <String> projects) {
        String selected = mSharedPreferences.getString(Constants.PROJECTSELECTED, null);
        if(projects.contains(selected)) {
            projects.remove(selected);
            projects.add(0,selected);
        }
        Spinner spinnerProjects = (Spinner) findViewById(R.id.spinnerProjects);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, projects);
        spinnerProjects.setAdapter(adapter);
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void handleResponse(ProjectList projects) {


        List <Project> projectOwn = projects.getProjectOwn();
        List <Project> projectCollaborations = projects.getCollaborations();
        List<String> projectNames = new ArrayList<>();
        if(projectOwn.size() != 0) {
            for (Project project: projectOwn) {
                projectNames.add(project.getName());
            }
        }
        if(projectCollaborations.size() != 0) {
            for (Project project: projectCollaborations) {
                projectNames.add(project.getName());
            }
        }



        if (mSharedPreferences.getString(Constants.PROJECTSELECTED, null) == null)
            mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, "").apply();
        Spinner spinnerProjects = (Spinner) findViewById(R.id.spinnerProjects);
        if(spinnerProjects.getItemAtPosition(0) == null) initProjectSpinner(projectNames);
        if(mSharedPreferences.getString(Constants.PROJECTSELECTED, null).equals(null)) {
            String selected = spinnerProjects.getSelectedItem().toString();
            mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, selected).apply();
        }

        initialiseStats(selectedProject(projects));

    }


    public Project selectedProject(ProjectList projects) {
        String selected = mSharedPreferences.getString(Constants.PROJECTSELECTED, "null");
        List <Project> projectOwn = projects.getProjectOwn();
        List <Project> projectCollaborations = projects.getCollaborations();
        if(selected.equals(null)) {
            if(projectOwn.size() != 0) {
                mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, projectOwn.get(0).getName());
                mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, String.valueOf(projectOwn.get(0).getId()));
                return projectOwn.get(0);
            }

            else {
                if(projectCollaborations.size() != 0) {
                    mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, projectOwn.get(0).getName());
                    mSharedPreferences.edit().putString(Constants.PROJECTSELECTED, String.valueOf(projectOwn.get(0).getId()));
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
                Response response = gson.fromJson(errorBody,Response.class);
                Log.println(Log.ERROR,"ERROR",response.getError() );

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    private void initialiseStats(Project project) {
        mSubscriptions.add(NetworkUtil.getRetrofit(Constants.PROJECTS_URL).getAllStats(project.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseStats, this::handleErrorStats));

    }


    private void handleResponseStats(List<Stats> stats) {

        List<String> eventNames = new ArrayList<>();
        List<String> categoryNames = new ArrayList<>();
        List<String> actionNames = new ArrayList<>();

        for (Stats stat: stats) {
            if(!eventNames.contains(stat.getEventname())) eventNames.add(stat.getEventname());
            if(!categoryNames.contains(stat.getCategory())) categoryNames.add(stat.getCategory());
            if(!actionNames.contains(stat.getAction())) actionNames.add(stat.getAction());

        }



        Spinner spinnerEvent = (Spinner) findViewById(R.id.spinnerEvent);
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        Spinner spinnerAction = (Spinner) findViewById(R.id.spinnerAction);


        if(mSharedPreferences.getString(Constants.EVENTSELECTED, null) == null)
            mSharedPreferences.edit().putString(Constants.EVENTSELECTED, actionNames.get(0)).apply();
        if(mSharedPreferences.getString(Constants.CATEGORYSELECTED, null) == null)
            mSharedPreferences.edit().putString(Constants.CATEGORYSELECTED, categoryNames.get(0)).apply();
        if(mSharedPreferences.getString(Constants.ACTIONSELECTED, null) == null)
            mSharedPreferences.edit().putString(Constants.ACTIONSELECTED, actionNames.get(0)).apply();


        if(spinnerEvent.getSelectedItem() == null || !eventNames.contains(spinnerEvent.getSelectedItem().toString())) {
            initStatsSpinners(eventNames,categoryNames,actionNames);
        }

        if(spinnerEvent.getSelectedItem() != null ) {
            updateEventStats(stats, spinnerEvent.getSelectedItem().toString());
            updateCategoryStats(stats, spinnerCategory.getSelectedItem().toString());
            updateActionStats(stats, spinnerAction.getSelectedItem().toString());
        }
        else{
            updateEventStats(stats, "");
            updateCategoryStats(stats, "");
            updateActionStats(stats, "");
        }
    }

    private void initStatsSpinners(List <String> eventNames, List <String> categoryNames, List <String> actionNames) {


        Spinner spinnerEvent = (Spinner) findViewById(R.id.spinnerEvent);
        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        Spinner spinnerAction = (Spinner) findViewById(R.id.spinnerAction);




        List<String> events = eventNames;
        List<String> categories = categoryNames;
        List<String> actions = actionNames;
        String selectedevent = mSharedPreferences.getString(Constants.EVENTSELECTED,null);
        String selectedcategory = mSharedPreferences.getString(Constants.CATEGORYSELECTED,null);
        String selectedaction = mSharedPreferences.getString(Constants.ACTIONSELECTED,null);


        if(eventNames.contains(selectedevent) && eventNames.size() >= 1){
            events.remove(selectedevent);
            events.add(0, selectedevent);
        }


        if(categoryNames.contains(selectedcategory) && categoryNames.size() >= 1){
            categories.remove(selectedcategory);
            categories.add(0, selectedcategory);
        }


        if(actionNames.contains(selectedaction) && actionNames.size() >= 1){
            actions.remove(selectedaction);
            actions.add(0, selectedaction);
        }



        if(selectedevent != null) {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, events);
            spinnerEvent.setAdapter(adapter1);
        }else {
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, eventNames);
            spinnerEvent.setAdapter(adapter1);
        }

        if(selectedcategory != null) {
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
            spinnerCategory.setAdapter(adapter2);
        }else  {
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);
            spinnerCategory.setAdapter(adapter2);
        }
        if(selectedaction != null) {
            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, actions);
            spinnerAction.setAdapter(adapter3);

        } else {
            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, actionNames);
            spinnerAction.setAdapter(adapter3);

        }



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

        mSharedPreferences.edit().putString(Constants.EVENTSELECTED, String.valueOf(todayEvents)).apply();

        //Update widget
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
        remoteViews.setTextViewText(R.id.widgetEventTitle, "Event: " + mSharedPreferences.getString(Constants.EVENTSELECTED,null));
        remoteViews.setTextViewText(R.id.widgetEventStats, allEvents + " - " + thisMonthEvents + " - " +
        thisWeekEvents + " - " + todayEvents);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        initEventChart(todayEvents, thisWeekEvents, thisMonthEvents, allEvents, eventName);
    }

    private void initEventChart(long today, long week, long month, long all, String eventName) {
        eventChart = (BarChart) findViewById(R.id.eventChart);
        eventChart.setDrawBarShadow(false);
        eventChart.setDrawValueAboveBar(true);
        eventChart.setMaxVisibleValueCount(50);
        eventChart.setPinchZoom(false);
        eventChart.setDrawGridBackground(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1,today));
        barEntries.add(new BarEntry(2,week));
        barEntries.add(new BarEntry(3,month));
        barEntries.add(new BarEntry(4,all));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Today - This Week - This Month - All\tEvent:" + eventName);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        eventChart.setData(data);

    }

    private void updateCategoryStats(List<Stats> stats, String categoryName) {
        long allEvents = StatsCount.countAllCategoriesByCategory(stats,categoryName);
        long todayEvents = StatsCount.countTodayCategoriesByCategory(stats,categoryName);
        long thisWeekEvents = StatsCount.countWeekCategoriesByCategory(stats,categoryName);
        long thisMonthEvents = StatsCount.countMonthCategoriesByCategory(stats,categoryName);

        TextView categoryLayoutTitle = (TextView) findViewById(R.id.categoryLayoutTitle);
        categoryLayoutTitle.setText("Category Name:" + categoryName);
        TextView categoryAllNb = (TextView) findViewById(R.id.categoryAllNb);
        TextView categoryTodayNb = (TextView) findViewById(R.id.categoryTodayNb);
        TextView categoryWeekNb = (TextView) findViewById(R.id.categoryWeekNb);
        TextView categoryMontNb = (TextView) findViewById(R.id.categoryMonthNb);

        categoryAllNb.setText(String.valueOf(allEvents));
        categoryTodayNb.setText(String.valueOf(todayEvents));
        categoryWeekNb.setText(String.valueOf(thisWeekEvents));
        categoryMontNb.setText(String.valueOf(thisMonthEvents));

        mSharedPreferences.edit().putString(Constants.CATEGORYSELECTED, String.valueOf(todayEvents)).apply();

        //Update widget
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
        remoteViews.setTextViewText(R.id.widgetCategoryTitle, "Category: " + mSharedPreferences.getString(Constants.CATEGORYSELECTED,null));
        remoteViews.setTextViewText(R.id.widgetCategoryStats, allEvents + " - " + thisMonthEvents + " - " +
                thisWeekEvents + " - " + todayEvents);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        initCategoryChart(todayEvents, thisWeekEvents, thisMonthEvents, allEvents, categoryName);


    }

    private void initCategoryChart(long today, long week, long month, long all, String categoryName) {
        categoryChart = (BarChart) findViewById(R.id.categoryChart);
        categoryChart.setDrawBarShadow(false);
        categoryChart.setDrawValueAboveBar(true);
        categoryChart.setMaxVisibleValueCount(50);
        categoryChart.setPinchZoom(false);
        categoryChart.setDrawGridBackground(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1,today));
        barEntries.add(new BarEntry(2,week));
        barEntries.add(new BarEntry(3,month));
        barEntries.add(new BarEntry(4,all));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Today - This Week - This Month - All\tCategory:" + categoryName);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        categoryChart.setData(data);

    }

    private void updateActionStats(List<Stats> stats, String actionName) {
        long allAction = StatsCount.countAllActionsByAction(stats,actionName);
        long todayAction = StatsCount.countTodayActionsByAction(stats,actionName);
        long thisWeekAction = StatsCount.countWeekActionsByAction(stats,actionName);
        long thisMonthAction = StatsCount.countMonthActionsByAction(stats,actionName);

        TextView actionLayoutTitle = (TextView) findViewById(R.id.actionLayoutTitle);
        actionLayoutTitle.setText("Action Name:" + actionName);
        TextView actionAllNb = (TextView) findViewById(R.id.actionAllNb);
        TextView actionTodayNb = (TextView) findViewById(R.id.actionTodayNb);
        TextView actionWeekNb = (TextView) findViewById(R.id.actionWeekNb);
        TextView actionMontNb = (TextView) findViewById(R.id.actionMonthNb);

        actionAllNb.setText(String.valueOf(allAction));
        actionTodayNb.setText(String.valueOf(todayAction));
        actionWeekNb.setText(String.valueOf(thisWeekAction));
        actionMontNb.setText(String.valueOf(thisMonthAction));

        mSharedPreferences.edit().putString(Constants.ACTIONSELECTED, String.valueOf(todayAction)).apply();

        //Update widget
        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        ComponentName thisWidget = new ComponentName(context, NewAppWidget.class);
        remoteViews.setTextViewText(R.id.widgetActionTitle, "Action: " + mSharedPreferences.getString(Constants.ACTIONSELECTED,null));
        remoteViews.setTextViewText(R.id.widgetActionStats, allAction + " - " + thisMonthAction + " - " +
                thisWeekAction + " - " + todayAction);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        initActionChart(todayAction, thisWeekAction, thisMonthAction, allAction, actionName);
    }

    private void initActionChart(long today, long week, long month, long all, String actionName) {
        actionChart = (BarChart) findViewById(R.id.actionChart);
        actionChart.setDrawBarShadow(false);
        actionChart.setDrawValueAboveBar(true);
        actionChart.setMaxVisibleValueCount(50);
        actionChart.setPinchZoom(false);
        actionChart.setDrawGridBackground(false);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1,today));
        barEntries.add(new BarEntry(2,week));
        barEntries.add(new BarEntry(3,month));
        barEntries.add(new BarEntry(4,all));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Today - This Week - This Month - All\tAction:" + actionName);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        actionChart.setData(data);

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

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, ManageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/manuelmederos95/FinalProjectAndroid/tree/master"));
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
