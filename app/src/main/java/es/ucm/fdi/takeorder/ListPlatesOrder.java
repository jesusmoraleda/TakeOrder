package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.ListDrinksAdapter;
import es.ucm.fdi.takeorder.adapter.ListPlatesAdapter;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;
import es.ucm.fdi.takeorder.model.PlatesOrderElement;

public class ListPlatesOrder extends AppCompatActivity {

    RecyclerView RecyclerViewListPlatesOrder;
    ListPlatesAdapter listPlatesAdapter;
    FirebaseFirestore dbFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_plates_order);

        this.setTitle("Lista de Platos Pedidos");
        String id = getIntent().getStringExtra("id_mesa");

        dbFirestore = FirebaseFirestore.getInstance();
        RecyclerViewListPlatesOrder = findViewById(R.id.listPlatesRecyclerView);
        RecyclerViewListPlatesOrder.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("mesas").document(id).collection("plates");

        FirestoreRecyclerOptions<PlatesOrderElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<PlatesOrderElement>().setQuery(query, PlatesOrderElement.class).build();

        listPlatesAdapter = new ListPlatesAdapter(firestoreRecyclerOptions, this);
        listPlatesAdapter.notifyDataSetChanged();
        RecyclerViewListPlatesOrder.setAdapter(listPlatesAdapter);

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
        listPlatesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listPlatesAdapter.stopListening();
    }

}