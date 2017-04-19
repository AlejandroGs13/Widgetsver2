package com.example.dianags.widgetsver2;


import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfiguradorPersonal01 extends AppCompatActivity {

    EditText texto;
    TextInputLayout inpulayout;
    Button btn_configurrar,cerrar;
    private int widgetID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurador_personal01);

        Intent in = getIntent();
        Bundle parametros = in.getExtras();

        widgetID = parametros.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        setResult(RESULT_CANCELED);
        inpulayout = (TextInputLayout)findViewById(R.id.Edi_layout);
        texto = (EditText)findViewById(R.id.Edi_texto);
        btn_configurrar = (Button)findViewById(R.id.btn_configurar);
        cerrar = (Button)findViewById(R.id.btn_cancelar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_configurrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferencias = getSharedPreferences("preferenciaswidget", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("dato",texto.getText().toString());
                editor.commit();

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ConfiguradorPersonal01.this);

                ControlWidget.actualizarAppWidget(getApplicationContext(),appWidgetManager,widgetID);

                Intent in = new Intent();
                in.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetID);
                setResult(RESULT_OK, in);
                finish();
            }
        });


    }
}
