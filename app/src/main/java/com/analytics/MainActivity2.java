package com.analytics;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.analytics.utils.Constants;
import com.learn2crack.R;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;*/




public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mSharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSharedPreferences();
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
            int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
            TextView eventTodayNb = (TextView) findViewById(R.id.eventTodayNb);
            TextView eventWeekNb = (TextView) findViewById(R.id.eventWeekNb);
            TextView eventMonthNb = (TextView) findViewById(R.id.eventMonthNb);
            TextView eventAllNb = (TextView) findViewById(R.id.eventAllNb);
            eventTodayNb.setText(randomNum + "");
            eventWeekNb.setText(randomNum + "");
            eventMonthNb.setText(randomNum + "");
            eventAllNb.setText(randomNum + "");
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
            /**Intent intent = new Intent(this, MainActivity2.class);
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
