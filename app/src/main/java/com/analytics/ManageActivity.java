package com.analytics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.analytics.utils.Constants;

public class ManageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSharedPreferences();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initCheckBoxes();

    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void initCheckBoxes(){
        CheckBox eventCheckBox = (CheckBox) findViewById(R.id.eventCheckBox);
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("eventCheckBox", false);
        eventCheckBox.setChecked(checked);

        CheckBox categoryCheckBox = (CheckBox) findViewById(R.id.categoryCheckBox);
        boolean checked1 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("categoryCheckBox", false);
        categoryCheckBox.setChecked(checked1);

        CheckBox actionCheckBox = (CheckBox) findViewById(R.id.actionCheckBox);
        boolean checked2 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("actionCheckBox", false);
        actionCheckBox.setChecked(checked2);

        CheckBox eventChartCheckBox = (CheckBox) findViewById(R.id.eventChartCheckBox);
        boolean checked3 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("eventChartCheckBox", false);
        eventChartCheckBox.setChecked(checked3);

        CheckBox categoryChartCheckBox = (CheckBox) findViewById(R.id.categoryChartCheckBox);
        boolean checked4 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("categoryChartCheckBox", false);
        categoryChartCheckBox.setChecked(checked4);

        CheckBox actionChartCheckBox = (CheckBox) findViewById(R.id.actionChartCheckBox);
        boolean checked5 = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("actionChartCheckBox", false);
        actionChartCheckBox.setChecked(checked5);
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
            Intent intent = new Intent(this, ReportingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

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

    public void onSave(View view) {
        CheckBox eventCheckBox = (CheckBox) findViewById(R.id.eventCheckBox);
        CheckBox categoryCheckBox = (CheckBox) findViewById(R.id.categoryCheckBox);
        CheckBox actionCheckBox = (CheckBox) findViewById(R.id.actionCheckBox);
        CheckBox eventChartCheckBox = (CheckBox) findViewById(R.id.eventChartCheckBox);
        CheckBox categoryChartCheckBox = (CheckBox) findViewById(R.id.categoryChartCheckBox);
        CheckBox actionChartCheckBox = (CheckBox) findViewById(R.id.actionChartCheckBox);
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("eventCheckBox",eventCheckBox.isChecked()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("categoryCheckBox",categoryCheckBox.isChecked()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("actionCheckBox",actionCheckBox.isChecked()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("eventChartCheckBox",eventChartCheckBox.isChecked()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("categoryChartCheckBox",categoryChartCheckBox.isChecked()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("actionChartCheckBox",actionChartCheckBox.isChecked()).commit();


        Toast.makeText(this, "Saved",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ReportingActivity.class);
        startActivity(intent);
    }
}
