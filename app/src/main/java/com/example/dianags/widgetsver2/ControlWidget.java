package com.example.dianags.widgetsver2;



import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.widget.RemoteViews;

import java.util.Date;

public class ControlWidget extends AppWidgetProvider{

    static AppWidgetManager appWidgetManager;
    int id_widget;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i<appWidgetIds.length; i++){
            id_widget = appWidgetIds[i];
        }

        actualizarAppWidget(context, appWidgetManager,id_widget);
    }


    static void actualizarAppWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {

        SharedPreferences preferences = context.getSharedPreferences("preferenciaswidget", Context.MODE_PRIVATE);
        String dato = preferences.getString("dato","dato");

        Date Dato = new Date();
        int hora = Dato.getHours();
        int minutos = Dato.getMinutes();
        int segundos = Dato.getSeconds();

        RemoteViews controles = new RemoteViews(context.getPackageName(), R.layout.primerwidget);
        controles.setTextViewText(R.id.lblhora, hora+":"+minutos+":"+segundos);
        controles.setTextViewText(R.id.mostrar, dato);

        Intent intent = new Intent(context,ControlWidget.class);
        intent.setAction("ACTUALIZAR");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,widgetId,intent,0);
        controles.setOnClickPendingIntent(R.id.bactualizar,pendingIntent);




        appWidgetManager.updateAppWidget(widgetId, controles);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("ACTUALIZAR")){
            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);

            AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

            if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID){

                actualizarAppWidget(context,widgetManager,widgetId);
            }
        }
    }



}
