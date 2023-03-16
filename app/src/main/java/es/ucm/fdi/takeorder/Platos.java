package es.ucm.fdi.takeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Platos extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private BottomNavigationView navigation;

    private int cantidad;
    private String ingrediente;
    private String nombre;
    private boolean disponible;

    private List<Platos> listPlatos = new ArrayList<>();
    ArrayAdapter<Platos> arrayAdapterPlatos;
    String TAG = "Firebase";
    ListView listPer;


    //region Constructors
    public Platos(int cantidad, String ingrediente, boolean disponible) {
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
        this.disponible = disponible;
    }

    public Platos() {
    }
    //endregion

    //region Get and Set
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //endregion

    //region Public methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platos);
        listPer = findViewById(R.id.lv_datosPlatos);


        cargarPlatos();

        navigation = findViewById(R.id.menu_bottom_navigation);
        navigation.setSelectedItemId(R.id.menu_platos);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_mesas:
                        startActivity(new Intent(getApplicationContext(), Mesas.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.menu_platos:
                        return true;

                }
                return false;
            }
        });

    }

    @Override
    public String toString() { return nombre; }
    //endregion


    private void cargarPlatos(){
        //seleccionamos la coleccion platos y obtenemos sus documentos
        db.collection("platos").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            //se leen los diferentes documentos secuencialmente
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d(TAG, document.getId() + "=>" + document.getData());
                                //creamos objetos plato y asignamos los valores
                                Platos plato = new Platos();
                                plato.setNombre(document.get("nombre").toString());
                                plato.setCantidad(Integer.parseInt(document.get("cantidad").toString()));
                                plato.setDisponible((Boolean) document.get("disponible"));
                                plato.setIngrediente(document.get("ingrediente").toString());

                                //en cada iteracion se agrega un plato a la lista
                                listPlatos.add(plato);
                                //usamos el adapter para crear un item de lista con el objeto creado
                                arrayAdapterPlatos = new ArrayAdapter<Platos>(Platos.this, android.R.layout.simple_list_item_1,listPlatos);
                                //agregamos el item a la vista de la lista
                                listPer.setAdapter(arrayAdapterPlatos);
                            }
                        }
                    }
                });
    }

    //endregion
}

