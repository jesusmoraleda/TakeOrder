package es.ucm.fdi.takeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.MesaAdapter;
import es.ucm.fdi.takeorder.model.MesaElement;

public class Mesas extends AppCompatActivity {
    BottomNavigationView navigation;

    RecyclerView recyclerViewMesas;
    MesaAdapter mesaAdapter;
    FirebaseFirestore dbFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        navigation = findViewById(R.id.menu_bottom_navigation);
        navigation.setSelectedItemId(R.id.menu_mesas);


        dbFirestore = FirebaseFirestore.getInstance();
        recyclerViewMesas = findViewById(R.id.mesasRecyclerview);
        recyclerViewMesas.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("mesas");

        FirestoreRecyclerOptions<MesaElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<MesaElement>().setQuery(query,MesaElement.class).build();

        mesaAdapter = new MesaAdapter(firestoreRecyclerOptions,this);
        mesaAdapter.notifyDataSetChanged();
        recyclerViewMesas.setAdapter(mesaAdapter);

        FloatingActionButton buttonAddMesas = findViewById(R.id.btn_add);


        buttonAddMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agregar lista Compra nueva
                Intent i = new Intent(Mesas.this, AddMesas.class);
                startActivity(new Intent(Mesas.this, AddMesas.class));
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

    @Override
    protected void onStart() {
        super.onStart();
        mesaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mesaAdapter.stopListening();
    }

}

