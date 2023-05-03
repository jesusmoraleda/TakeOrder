package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOption extends AppCompatActivity {

    Context context;
    Button btn_add_carta, btn_add_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        this.setTitle("Añadir Comanda");

        btn_add_carta = findViewById(R.id.btn_add_Order);
        btn_add_menu = findViewById(R.id.btn_add_Menu);

        context = this;
        String id_mesa = getIntent().getStringExtra("id_mesa");

        btn_add_carta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddOrder.class);
                i.putExtra("id_mesa",id_mesa);
                startActivity(i);
            }
        });

        btn_add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddMenu.class);
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