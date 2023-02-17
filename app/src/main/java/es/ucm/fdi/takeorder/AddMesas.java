package es.ucm.fdi.takeorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.ucm.fdi.takeorder.model.MesaElement;

public class AddMesas extends AppCompatActivity {
    private FirebaseFirestore dbFirestore;
    EditText nombreMesa, numeroComensales;
    Button btn_anadir;// boton añadir
    Context context;

    List<MesaElement> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mesas);
        this.setTitle("Añadir Mesa");

        dbFirestore = FirebaseFirestore.getInstance();
        String id = getIntent().getStringExtra("id_mesa");
        elements = new ArrayList<>();

        //instanciamos cada uno de los datos
        nombreMesa = findViewById(R.id.nombreMesaNueva);
        numeroComensales = findViewById(R.id.numeroComensales);
        btn_anadir = findViewById(R.id.AñadirMesa);
        context = this;
        //HashMap hp = new HashMap();

        //validacion del id de "id_mesa"
        if(id == null || id ==""){
            //en caso de que sea vacio quiere decir que lo estamos creando

            //En el momento de hacer clik en anadir querremos guardar los datos
            btn_anadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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

        }else{
            //en el caso de que este se hara igual, pero actualizando los datos

            //si estamos en actuslizar, cambiaremos el nombre al boton
            btn_anadir.setText("Actualizar");

            //vamos a obtener toda la informacion antes del momento de actualizar
            getMesa(id);

            btn_anadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                        updateMesa(nombre,numero,id);
                    }

                    startActivity(new Intent(context, Mesas.class));
                }
            });

        }


        //para poder retroceder haciendo click a atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void updateMesa(String nombre, String numero, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre",nombre);
        map.put("numero",numero);

        dbFirestore.collection("mesas").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Actualizado con exito", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {//en caso de error
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //este metodo se encarga de enviar estos campos a la base de datos
    private void postMesa(String nombre, String numero) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre",nombre);
        map.put("numero",numero);
        //añdimos en la coleccion de "mesas" de la base de datos, el map generado con los campos
        dbFirestore.collection("mesas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    //vamos a obtener toda la informacion antes del momento de actualizar
    private void getMesa(String id){
        dbFirestore.collection("mesas").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nombre = documentSnapshot.getString("nombre");
                String numero = documentSnapshot.getString("numero");
                // vamos a setear los datos
                nombreMesa.setText(nombre);
                numeroComensales.setText(numero);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
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