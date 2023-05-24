package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ucm.fdi.takeorder.adapter.ListPrimerosAdapter;
import es.ucm.fdi.takeorder.adapter.ListSegundosAdapter;
import es.ucm.fdi.takeorder.model.MenuElement;

public class ListSegundos extends AppCompatActivity {

    RecyclerView RecyclerViewListSegundos;
    ListSegundosAdapter listSegundosAdapter;
    FirebaseFirestore dbFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_segundos);

        this.setTitle("Lista de Segundos Pedidos");
        String id = getIntent().getStringExtra("id_mesa");

        dbFirestore = FirebaseFirestore.getInstance();
        RecyclerViewListSegundos = findViewById(R.id.listSegundosRecyclerView);
        RecyclerViewListSegundos.setLayoutManager(new LinearLayoutManager(this));
        Query query = dbFirestore.collection("mesas").document(id).collection("menu_segundos");

        FirestoreRecyclerOptions<MenuElement> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<MenuElement>().setQuery(query, MenuElement.class).build();

        listSegundosAdapter = new ListSegundosAdapter(firestoreRecyclerOptions, this);
        listSegundosAdapter.notifyDataSetChanged();
        RecyclerViewListSegundos.setAdapter(listSegundosAdapter);

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
        listSegundosAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listSegundosAdapter.stopListening();
    }

}