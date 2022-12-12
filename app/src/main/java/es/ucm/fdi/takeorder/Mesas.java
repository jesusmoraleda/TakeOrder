package es.ucm.fdi.takeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Mesas extends AppCompatActivity {
    BottomNavigationView navigation;
    List<MesaElement> elements;
    RecyclerView recyclerViewMesas;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "Firebase";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        init();
        //carga las mesas de la base de datos
        cargarMesas();

    }

    private void init(){
        context = this;
        elements = new ArrayList<>();
        navigation = findViewById(R.id.menu_bottom_navigation);
        navigation.setSelectedItemId(R.id.menu_mesas);
        FloatingActionButton addMesas = findViewById(R.id.botonFLotante);
        recyclerViewMesas = findViewById(R.id.mesasRecyclerview);

        addMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agregar lista Compra nueva
                Intent i = new Intent(context, AddMesas.class);
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

    private void cargarMesas(){
        //seleccionamos la coleccion mesas y obtenemos sus documentos
        db.collection("mesas").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            //se leen los diferentes documentos secuencialmente
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                //creamos objetos mesa y asignamos los valores

                                MesaElement mesa = new MesaElement();
                                mesa.setName(document.get("nombre").toString());

                                //en cada iteracion se agrega un plato a la lista
                                elements.add(mesa);
                                //usamos el adapter para crear un item de lista con el objeto creado
                                MesaAdapter mesaAdapter = new MesaAdapter(elements, context );
                                //agregamos el item a la vista de la lista
                                RecyclerView recyclerView = findViewById(R.id.mesasRecyclerview);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                recyclerView.setAdapter(mesaAdapter);
                            }
                        }
                    }
                });
    }
}

