package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.DrinksOrderAdapter;
import es.ucm.fdi.takeorder.adapter.PlatesOrderAdapter;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;
import es.ucm.fdi.takeorder.model.PlatesOrderElement;

public class AddPlatesOrder extends AppCompatActivity {

    RecyclerView recyclerViewAddPlatesOrder;
    PlatesOrderAdapter platesAdapter;
    FirebaseFirestore dbFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plates_order);

        dbFirestore = FirebaseFirestore.getInstance();
        recyclerViewAddPlatesOrder = findViewById(R.id.platesRecyclerView);
        recyclerViewAddPlatesOrder.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("plates");//whereGreaterThan("amount","0");

        FirestoreRecyclerOptions<PlatesOrderElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<PlatesOrderElement>().setQuery(query, PlatesOrderElement.class).build();


        platesAdapter = new PlatesOrderAdapter(firestoreRecyclerOptions, this);
        platesAdapter.notifyDataSetChanged();
        recyclerViewAddPlatesOrder.setAdapter(platesAdapter);


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
        platesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        platesAdapter.stopListening();
    }
}
