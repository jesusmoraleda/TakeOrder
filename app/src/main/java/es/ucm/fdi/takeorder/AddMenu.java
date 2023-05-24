package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddMenu extends AppCompatActivity {

    Context context;
    Button btn_add_primeros, btn_list_primeros, btn_add_segundos, btn_list_segundos, btn_add_postres, btn_list_postres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        this.setTitle("Añadir Menu");

        btn_add_primeros = findViewById(R.id.btn_add_Primeros);
        btn_list_primeros = findViewById(R.id.btn_show_Primeros);
        btn_add_segundos = findViewById(R.id.btn_add_Segundos);
        btn_list_segundos = findViewById(R.id.btn_show_Segundos);
        btn_add_postres = findViewById(R.id.btn_add_Postre);
        btn_list_postres = findViewById(R.id.btn_show_Postre);

        context = this;
        String id_mesa = getIntent().getStringExtra("id_mesa");

        btn_add_primeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddPrimeros.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_list_primeros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListPrimeros.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_add_segundos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddSegundos.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_list_segundos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListSegundos.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_add_postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddPostres.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_list_postres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListPostres.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        //para poder retroceder haciendo click a atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //para poder hacer la accion de selec click y retoceder
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}