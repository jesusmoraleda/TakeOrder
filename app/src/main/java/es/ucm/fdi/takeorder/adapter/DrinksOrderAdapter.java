package es.ucm.fdi.takeorder.adapter;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.takeorder.AddMesas;
import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;
import es.ucm.fdi.takeorder.model.MesaElement;

public class DrinksOrderAdapter extends FirestoreRecyclerAdapter<DrinksOrderElement,DrinksOrderAdapter.ViewHolder> {

    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DrinksOrderAdapter(@NonNull FirestoreRecyclerOptions<DrinksOrderElement> options, Activity activity) {
        super(options);
        this.activity = activity;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull DrinksOrderElement model) {
        //referenciamos el id del elemento
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        String id_mesa = activity.getIntent().getStringExtra("id_mesa");


        viewHolder.name_drink.setText(model.getName());

        viewHolder.btn_add_drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para poder enviar datos o parametros a traves de activity
                String name = viewHolder.name_drink.getText().toString().trim();
                Map<String, Object> map = new HashMap<>();
                map.put("nombre",name);

                dbFirestore.collection("mesas").document(id_mesa).collection("drinks").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(activity, "Añadido con exito", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Error al Añadir", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_drinks_order,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_drink;
        Button btn_add_drinks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            name_drink = itemView.findViewById(R.id.titleDrink);
            //y lo mismo con los botones
            btn_add_drinks = itemView.findViewById(R.id.btn_add_Drinks_Order);

        }
    }
}
