package es.ucm.fdi.takeorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;

import java.util.List;


import es.ucm.fdi.takeorder.AddMesas;
import es.ucm.fdi.takeorder.AddOrder;

import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.SelectOption;
import es.ucm.fdi.takeorder.model.MesaElement;


public class MesaAdapter extends FirestoreRecyclerAdapter<MesaElement,MesaAdapter.ViewHolder> {

    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MesaAdapter(@NonNull FirestoreRecyclerOptions<MesaElement> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull MesaElement model) {
        //referenciamos el id del elemento
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.nombre.setText(model.getNombre());
        viewHolder.numero.setText(model.getNumero());

        viewHolder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para poder enviar datos o parametros a traves de activity
                Intent i = new Intent(activity, AddMesas.class);
                i.putExtra("id_mesa",id);
                activity.startActivity(i);
            }
        });

        viewHolder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMesa(id);
            }
        });

        viewHolder.btn_comanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para poder enviar datos o parametros a traves de activity
                Intent i = new Intent(activity, SelectOption.class);
                i.putExtra("id_mesa",id);
                activity.startActivity(i);


            }
        });
    }

    private void deleteMesa(String id) {
        dbFirestore.collection("mesas").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() { //caso de que haya un error
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //nos muestra los datos de la base de datos, de cada mesa en la view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_mesas,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //pasa los datos atraves de esta instancia
        TextView nombre, numero;
        Button btn_eliminar, btn_editar,btn_comanda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            nombre = itemView.findViewById(R.id.nombreMesa);
            numero = itemView.findViewById(R.id.numeroComensales);
            //y lo mismo con los botones
           btn_eliminar = itemView.findViewById(R.id.btnMesaEliminar);
           btn_editar = itemView.findViewById(R.id.btnMesaEditar);
           btn_comanda = itemView.findViewById(R.id.btnMesaComanda);

        }
    }

}
