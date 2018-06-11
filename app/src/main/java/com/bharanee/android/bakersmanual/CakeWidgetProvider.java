package com.bharanee.android.bakersmanual;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class CakeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cake_widget_provider);
        Intent intent=new Intent(context,ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_listView,intent);
        Intent activityIntent=new Intent(context,DetailsPage.class);
        PendingIntent pendingIntentTemplate= TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(activityIntent).getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_listView,pendingIntentTemplate);

        views.setEmptyView(R.id.widget_listView,R.id.empty_view);

        Intent appOpenIntent=new Intent(context,HomeScreen.class);
        PendingIntent homeScreenPIntent=PendingIntent.getActivity(context,0,appOpenIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.empty_view,homeScreenPIntent);
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

