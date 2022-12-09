package es.ucm.fdi.takeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class Mesas extends AppCompatActivity {
    BottomNavigationView navigation;
    List<MesaElement> elements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        init();

    }


    private void init(){
        Context context = this;
        navigation = findViewById(R.id.menu_bottom_navigation);
        navigation.setSelectedItemId(R.id.menu_mesas);
        FloatingActionButton addMesas = findViewById(R.id.botonFLotante);

        addMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agregar lista Compra nueva
                startActivity(new Intent(context, AddMesas.class));
            }
        });
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_mesas:
                        return true;
                    case R.id.menu_platos:
                        startActivity(new Intent(getApplicationContext(), Platos.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }
}

