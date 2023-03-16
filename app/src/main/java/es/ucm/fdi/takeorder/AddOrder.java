package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class AddOrder extends AppCompatActivity {

    Context context;
    Button btn_add_drinks, btn_list_drinks,btn_add_plates,btn_list_plates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        this.setTitle("Añadir Comanda");

        btn_add_drinks = findViewById(R.id.btn_add_Drinks);
        btn_list_drinks = findViewById(R.id.btn_show_Drinks);
        btn_add_plates = findViewById(R.id.btn_add_Plates);
        btn_list_plates = findViewById(R.id.btn_show_Plates);

        context = this;
        String id_mesa = getIntent().getStringExtra("id_mesa");

        btn_add_drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddDrinksOrder.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_list_drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListDrinksOrder.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_add_plates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddPlatesOrder.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_list_plates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ListPlatesOrder.class);
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
