package es.ucm.fdi.takeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddMesas extends AppCompatActivity {
    private FirebaseFirestore db;
    EditText nombreMesa, numeroComensales;
    Button anadir;// boton añadir
    Context context;

    List<MesaElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mesas);
        this.setTitle("Añadir Mesa");
        Init();
        //para poder retroceder haciendo click a atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void Init() {

        db = FirebaseFirestore.getInstance();
        elements = new ArrayList<>();

        //instanciamos cada uno de los datos
        nombreMesa = findViewById(R.id.nombreMesaNueva);
        numeroComensales = findViewById(R.id.numeroComensales);
        anadir = findViewById(R.id.AñadirMesa);
        context = this;
        //HashMap hp = new HashMap();

        //En el momento de hacer clik en anadir querremos guardar los datos
        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //codigo para añadir a la base de datos
                /*hp.put("comensales", Integer.parseInt(numeroComensales.getText().toString()));
                hp.put("test", 1234);
                db.collection("mesas").document(nombreMesa.getText().toString()).set(
                        hp
                );*/

                //tenemos que obtener la informacion que ha introducido el usuario
                String nombre = nombreMesa.getText().toString().trim();
                String numero = numeroComensales.getText().toString().trim();

                //Comporbamos si se han introducido bien los campos o hay alguno vacio

                if(nombre.isEmpty() || numero.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Error al añadir, ingrese bien todos los campos", Toast.LENGTH_SHORT).show();

                }else if(!esNumero(numero)) {
                    Toast.makeText(getApplicationContext(), "Error en numero de comensales, introducir un numero", Toast.LENGTH_SHORT).show();

                }else{
                    //guardaremos la mesa con sus respectivos campos
                    postMesa(nombre,numero);
                }

                //Toast.makeText(context, "Lista añadida",Toast.LENGTH_LONG).show();
                startActivity(new Intent(context, Mesas.class));
            }
        });

    }
    //este metodo se encarga de enviar estos campos a la base de datos
    private void postMesa(String nombre, String numero) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre",nombre);
        map.put("numero",numero);
        //añdimos en la coleccion de "mesas" de la base de datos, el map generado con los campos
        db.collection("mesas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Creado con exito", Toast.LENGTH_SHORT).show();
                finish();
            }
        //caso de que exista un error al acceder a la base de datos
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean esNumero(String valor) {
        try {
            Float.parseFloat(valor);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //para poder hacer la accion de selec click y retoceder
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}