package es.ucm.fdi.takeorder;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.takeorder.adapter.DrinksOrderAdapter;
import es.ucm.fdi.takeorder.adapter.ListDrinksAdapter;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;

public class ListDrinksOrder extends AppCompatActivity {

    RecyclerView RecyclerViewListDrinksOrder;
    ListDrinksAdapter listDrinksAdapter;
    FirebaseFirestore dbFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_drinks_order);

        this.setTitle("Lista de Bebidas Pedidas");
        String id = getIntent().getStringExtra("id_mesa");

        dbFirestore = FirebaseFirestore.getInstance();
        RecyclerViewListDrinksOrder = findViewById(R.id.listDrinksRecyclerView);
        RecyclerViewListDrinksOrder.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("mesas").document(id).collection("drinks");

        FirestoreRecyclerOptions<DrinksOrderElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<DrinksOrderElement>().setQuery(query, DrinksOrderElement.class).build();

        listDrinksAdapter = new ListDrinksAdapter(firestoreRecyclerOptions, this);
        listDrinksAdapter.notifyDataSetChanged();
        RecyclerViewListDrinksOrder.setAdapter(listDrinksAdapter);

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
        listDrinksAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listDrinksAdapter.stopListening();
    }


}