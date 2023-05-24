package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.ListPostresAdapter;
import es.ucm.fdi.takeorder.adapter.ListPrimerosAdapter;
import es.ucm.fdi.takeorder.model.MenuElement;

public class ListPostres extends AppCompatActivity {

    RecyclerView RecyclerViewListPostres;
    ListPostresAdapter listPostresAdapter;
    FirebaseFirestore dbFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_postres);

        this.setTitle("Lista de Postres Pedidos");
        String id = getIntent().getStringExtra("id_mesa");

        dbFirestore = FirebaseFirestore.getInstance();
        RecyclerViewListPostres = findViewById(R.id.listPostresRecyclerView);
        RecyclerViewListPostres.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("mesas").document(id).collection("menu_postres");

        FirestoreRecyclerOptions<MenuElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<MenuElement>().setQuery(query, MenuElement.class).build();

        listPostresAdapter = new ListPostresAdapter(firestoreRecyclerOptions, this);
        listPostresAdapter.notifyDataSetChanged();
        RecyclerViewListPostres.setAdapter(listPostresAdapter);

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
        listPostresAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listPostresAdapter.stopListening();
    }
}