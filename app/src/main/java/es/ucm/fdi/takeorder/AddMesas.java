package es.ucm.fdi.takeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*import es.ucm.fdi.takeorder.dataBase.AppDataBase;
import es.ucm.fdi.takeorder.dataBase.DAO.ListaCompraDAO;
import es.ucm.fdi.takeorder.dataBase.Entities.ListaCompraEntity;*/

public class AddMesas extends AppCompatActivity {
    TextView nombreLista;
    Button guardar;
    //AppDataBase dataBase;
    //ListaCompraDAO listaCompraDAO;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mesas);

        Init();
    }

    private void Init() {
        nombreLista = findViewById(R.id.nombreMesaNueva);
        guardar = findViewById(R.id.AñadirMesa);
        //dataBase = AppDataBase.getInstanceDataBase(this.getApplicationContext());
        //listaCompraDAO = dataBase.listaCompraDao();
        context = this;
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ListaCompraEntity list = new ListaCompraEntity(nombreLista.getText().toString());
                //listaCompraDAO.insert(list);
                Toast.makeText(context, "Lista añadida",Toast.LENGTH_LONG).show();
                startActivity(new Intent(context, Mesas.class));
            }
        });

    }
}