package com.example.punto_verde;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.punto_verde.Config.Config;
import com.example.punto_verde.Interface.Apiservice;
import com.example.punto_verde.model.Place;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormProductActivity extends AppCompatActivity {

    private Apiservice service;
    private TextView txtname;
    private TextView txtdes;
    private Spinner txtcate;
    private TextView txtsche;
    private TextView txtcontact;
    private TextView txtweb;
    private Spinner txtprovince;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_form_product);

        txtname = findViewById(R.id.text_lugar);
        txtdes = findViewById(R.id.text_descripcion);
        txtcate = findViewById(R.id.category);
        txtsche = findViewById(R.id.txt_horario);
        txtprovince = findViewById(R.id.provincias);
        service = Config.getRetrofit().create(Apiservice.class);
        txtcontact = findViewById(R.id.txt_contacto);
        txtweb = findViewById(R.id.txt_web);

        intent = getIntent();

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onClick: ", "OK");
                if (txtname.getText().toString().isEmpty() ||
                        txtprovince.getSelectedItem().toString().equals("---Seleccione Provincia---")){

                    Config.Mensaje(getApplicationContext(), "Llene El espacio de nombre");
                }else if(txtdes.getText().toString().isEmpty()){
                    Config.Mensaje(getApplicationContext(), "Llene El espacio de descripcion");
                }else if(txtcate.getSelectedItem().toString().equals("---Seleccione Categoria---")){
                    Config.Mensaje(getApplicationContext(), "Seleccione una categoria");
                }else if(txtsche.getText().toString().isEmpty()){
                    txtsche.setText("No Disponible");
                }else if(txtcontact.getText().toString().isEmpty()){
                    txtcontact.setText("No Disponible");
                }
                else {
                    Log.i("onClick: ", "else OK");
                    Call<Place> call = service.savePlaces(
                            txtname.getText().toString(),
                            txtdes.getText().toString(),
                            txtcate.getSelectedItem().toString(),
                            txtsche.getText().toString(),
                            txtprovince.getSelectedItem().toString(),
                            txtcontact.getText().toString(),
                            txtweb.getText().toString(),
                            0,
                            intent.getDoubleExtra("lng",0),
                            intent.getDoubleExtra("lat",0)
                    );

                    call.enqueue(new Callback<Place>() {
                        @Override
                        public void onResponse(Call<Place> call, Response<Place> response) {
                            Log.i("onResponse: ", "OK");
                            if (response.isSuccessful()){
                                Config.Mensaje(getApplicationContext(), "Punto Verde Creado Con Exito");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Config.Mensaje(getApplicationContext(), "error");
                            }
                        }

                        @Override
                        public void onFailure(Call<Place> call, Throwable t) {
                            Log.d("onFailure", t.getMessage());
                        }
                    });
                }
            }
        });

    }

}
