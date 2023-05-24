package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.ListDrinksAdapter;
import es.ucm.fdi.takeorder.adapter.ListPrimerosAdapter;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;
import es.ucm.fdi.takeorder.model.MenuElement;

public class ListPrimeros extends AppCompatActivity {

    RecyclerView RecyclerViewListPrimeros;
    ListPrimerosAdapter listPrimerosAdapter;
    FirebaseFirestore dbFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_primeros);

        this.setTitle("Lista de Primeros Pedidos");
        String id = getIntent().getStringExtra("id_mesa");

        dbFirestore = FirebaseFirestore.getInstance();
        RecyclerViewListPrimeros = findViewById(R.id.listPrimerosRecyclerView);
        RecyclerViewListPrimeros.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("mesas").document(id).collection("menu_primeros");

        FirestoreRecyclerOptions<MenuElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<MenuElement>().setQuery(query, MenuElement.class).build();

        listPrimerosAdapter = new ListPrimerosAdapter(firestoreRecyclerOptions, this);
        listPrimerosAdapter.notifyDataSetChanged();
        RecyclerViewListPrimeros.setAdapter(listPrimerosAdapter);

        //para poder retroceder haciendo click a atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //para poder hacer la accion de selec click y retoceder
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        listPrimerosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listPrimerosAdapter.stopListening();
    }

}