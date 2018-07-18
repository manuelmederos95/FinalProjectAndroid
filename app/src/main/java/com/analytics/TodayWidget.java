package com.analytics;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.analytics.R;
import com.analytics.model.Stats;
import com.analytics.network.NetworkUtil;
import com.analytics.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Implementation of App Widget functionality.
 */
public class TodayWidget extends AppWidgetProvider {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        String projectView = mSharedPreferences.getString(Constants.PROJECTSELECTED, "");
        String event = mSharedPreferences.getString(Constants.EVENTSELECTED, "Event");
        String category = mSharedPreferences.getString(Constants.CATEGORYSELECTED, "Category");
        String action = mSharedPreferences.getString(Constants.ACTIONSELECTED, "Action");
        String eventToday = mSharedPreferences.getString(Constants.EVENTTODAY,"O");
        String categoryToday = mSharedPreferences.getString(Constants.CATEGORYTODAY,"O");
        String actionToday = mSharedPreferences.getString(Constants.ACTIONTODAY,"O");
        views.setTextViewText(R.id.todayWidgetProject, "Project: " + projectView);
        views.setTextViewText(R.id.todayWidgetStats, event + " - " + category + " - " +  action);
        views.setTextViewText(R.id.statsFromToday, eventToday + " - " + categoryToday + " - " +  actionToday);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

