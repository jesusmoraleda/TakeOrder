package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.DrinksOrderAdapter;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;
import es.ucm.fdi.takeorder.model.MesaElement;

public class AddDrinksOrder extends AppCompatActivity {

    RecyclerView recyclerViewAddDrinksOrder;
    DrinksOrderAdapter drinksAdapter;
    FirebaseFirestore dbFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drinks_order);

        dbFirestore = FirebaseFirestore.getInstance();
        recyclerViewAddDrinksOrder = findViewById(R.id.drinksRecyclerView);
        recyclerViewAddDrinksOrder.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("all_drinks");

        FirestoreRecyclerOptions<DrinksOrderElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<DrinksOrderElement>().setQuery(query, DrinksOrderElement.class).build();

        drinksAdapter = new DrinksOrderAdapter(firestoreRecyclerOptions, this);
        drinksAdapter.notifyDataSetChanged();
        recyclerViewAddDrinksOrder.setAdapter(drinksAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        drinksAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        drinksAdapter.stopListening();
    }
}
