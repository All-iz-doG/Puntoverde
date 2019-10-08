package com.example.punto_verde;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductActivity extends AppCompatActivity {

    private ImageView imgPrincipal;
    private TextView nombre;
    private TextView category;
    private TextView descripcion;
    private TextView horario;
    private TextView contacto;
    private Button ubication;
    private Double lat;
    private Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_product);
        imgPrincipal = findViewById(R.id.imagen_lugar);
        nombre = findViewById(R.id.name_place);
        category = findViewById(R.id.type_place);
        descripcion = findViewById(R.id.description_place);
        horario = findViewById(R.id.timetable_place);
        contacto = findViewById(R.id.contact_place);

        Intent intent = getIntent();
        if(intent != null){
            nombre.setText(intent.getStringExtra("name"));
            category.setText(intent.getStringExtra("category"));
            descripcion.setText(intent.getStringExtra("description"));
            horario.setText(intent.getStringExtra("horario"));
            contacto.setText("Tel: "+intent.getStringExtra("contacto"));
            lat = intent.getDoubleExtra("lat",0);
            lng = intent.getDoubleExtra("lng",0);
        }
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_mapa:
                Intent intent = new Intent(this, UbicationActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng",lng);
                intent.putExtra("name",nombre.getText().toString());
                startActivity(intent);
                break;
        }

    }
}
