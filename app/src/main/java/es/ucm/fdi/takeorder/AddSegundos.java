package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.PrimerosAdapter;
import es.ucm.fdi.takeorder.adapter.SegundosAdapter;
import es.ucm.fdi.takeorder.model.MenuElement;

public class AddSegundos extends AppCompatActivity {

    RecyclerView recyclerViewAddPrimeros;
    SegundosAdapter segundosAdapter;
    FirebaseFirestore dbFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_segundos);

        dbFirestore = FirebaseFirestore.getInstance();
        recyclerViewAddPrimeros = findViewById(R.id.segundosRecyclerView);
        recyclerViewAddPrimeros.setLayoutManager(new LinearLayoutManager(this));

        Query query = dbFirestore.collection("plates")
                .whereEqualTo("in_menu", true)
                .whereEqualTo("category", "Segundo plato")
                .whereGreaterThan("quantity_menu", 0);


        FirestoreRecyclerOptions<MenuElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<MenuElement>().setQuery(query, MenuElement.class).build();



        segundosAdapter = new SegundosAdapter(firestoreRecyclerOptions, this);
        segundosAdapter.notifyDataSetChanged();
        recyclerViewAddPrimeros.setAdapter(segundosAdapter);


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
        segundosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        segundosAdapter.stopListening();
    }
}