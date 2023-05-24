package es.ucm.fdi.takeorder.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.model.MenuElement;

public class PostresAdapter extends FirestoreRecyclerAdapter<MenuElement,PostresAdapter.ViewHolder> {
    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    AlertDialog mDialog;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PostresAdapter(@NonNull FirestoreRecyclerOptions<MenuElement> options, Activity activity) {
        super(options);
        this.activity = activity;

    }

    @Override
    protected void onBindViewHolder(@NonNull PostresAdapter.ViewHolder viewHolder, int position, @NonNull MenuElement model) {
        //referenciamos el id del elemento , Obtén el documento actual del snapshot
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id_postre = documentSnapshot.getId();

        String id_mesa = activity.getIntent().getStringExtra("id_mesa");




        viewHolder.name_postre.setText(model.getName());
        viewHolder.num_total_plates.setText(String.valueOf(model.getQuantity_menu()));

        viewHolder.btn_add_plates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //para poder enviar datos o parametros a traves de activity
                String quantity = viewHolder.quantity_plates.getText().toString().trim();
                String name = viewHolder.name_postre.getText().toString().trim();
                String quantityTotal = viewHolder.num_total_plates.getText().toString().trim();


                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Confirme añadir " + quantity + " de " + name)
                        .setTitle("Añadir postre");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        if (quantity.isEmpty()) {
                            Toast.makeText(activity, "Error al añadir, ingrese bien todos los campos", Toast.LENGTH_SHORT).show();

                        } else if (!esNumero(quantity)) {
                            Toast.makeText(activity, "Error en la cantidad, introducir un numero", Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(quantity) > Integer.parseInt(quantityTotal)) {
                            Toast.makeText(activity, "Error, a superado la cantidad total disponible", Toast.LENGTH_SHORT).show();

                        } else {
                            //guardaremos la mesa con sus respectivos campos

                            Map<String, Object> map = new HashMap<>();
                            map.put("name", name);
                            map.put("quantity", Integer.parseInt(quantity));
                            // map.put("ultAmount",Integer.parseInt(amount));
                            map.put("entregado",false);


                            dbFirestore.collection("mesas").document(id_mesa).collection("menu_postres").add(map)
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(activity, "Postres añadidos correctamente", Toast.LENGTH_SHORT).show();
                                        mDialog.dismiss();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(activity, "Error al añadir los postres", Toast.LENGTH_SHORT).show());


                            // Actualizar la cantidad en la colección "plates"
                            DocumentReference allPrimerosRef = dbFirestore.collection("plates").document(id_postre);
                            allPrimerosRef.update("quantity_menu", (Integer.parseInt(quantityTotal) - Integer.parseInt(quantity)))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // La cantidad se ha actualizado correctamente
                                            Toast.makeText(activity, "Actulizados los postres totales", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Ha ocurrido un error al actualizar la cantidad
                                            Toast.makeText(activity, "Error al actulizar los primeros platos totales", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }


                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

                // 3. Get the AlertDialog from create()
                mDialog = builder.create();
                mDialog.show();
            }
        });
    }


    @NonNull
    @Override
    public PostresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_plates_menu,parent,false);
        return new PostresAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_postre,num_total_plates;
        EditText quantity_plates;
        Button btn_add_plates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            name_postre = itemView.findViewById(R.id.addTitlePlatoMenu);
            num_total_plates = itemView.findViewById(R.id.numTotalPlateMenu);
            //editText
            quantity_plates = itemView.findViewById(R.id.addAmountPlateMenu);
            //y lo mismo con los botones
            btn_add_plates = itemView.findViewById(R.id.btn_add_Plates_Menu);

        }
    }

    private boolean esNumero(String valor) {
        try {
            Float.parseFloat(valor);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}