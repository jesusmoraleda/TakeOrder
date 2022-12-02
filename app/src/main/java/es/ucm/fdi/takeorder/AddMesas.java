package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddMesas extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView nombreMesa, numeroComensales, nombreComedor;
    Button guardar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mesas);

        Init();
    }

    private void Init() {
        nombreMesa = findViewById(R.id.nombreMesaNueva);
        numeroComensales = findViewById(R.id.numeroComensales);
        nombreComedor = findViewById(R.id.nombreComedor);
        guardar = findViewById(R.id.AñadirMesa);
        context = this;
        HashMap hp = new HashMap();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hp.put("comensales", Integer.parseInt(numeroComensales.getText().toString()));
                hp.put("comedor", nombreComedor.getText().toString());
                hp.put("test", 1234);
                db.collection("mesas").document(nombreMesa.getText().toString()).set(
                        hp
                );
                Toast.makeText(context, "Lista añadida",Toast.LENGTH_LONG).show();
                startActivity(new Intent(context, Mesas.class));
            }
        });

    }
}