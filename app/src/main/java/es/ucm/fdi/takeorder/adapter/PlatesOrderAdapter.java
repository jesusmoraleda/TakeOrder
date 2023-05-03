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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;
import es.ucm.fdi.takeorder.model.PlatesOrderElement;

public class PlatesOrderAdapter extends FirestoreRecyclerAdapter<PlatesOrderElement,PlatesOrderAdapter.ViewHolder> {


    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    AlertDialog mDialog;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PlatesOrderAdapter(@NonNull FirestoreRecyclerOptions<PlatesOrderElement> options, Activity activity) {
        super(options);
        this.activity = activity;

    }

    @Override
    protected void onBindViewHolder(@NonNull PlatesOrderAdapter.ViewHolder viewHolder, int position, @NonNull PlatesOrderElement model) {
        //referenciamos el id del elemento , Obtén el documento actual del snapshot
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id_drink = documentSnapshot.getId();

        String id_mesa = activity.getIntent().getStringExtra("id_mesa");




        viewHolder.name_plate.setText(model.getName());
        viewHolder.num_total_plates.setText(model.getAmount());

        viewHolder.btn_add_plates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //para poder enviar datos o parametros a traves de activity
                String amount = viewHolder.amount_plates.getText().toString().trim();
                String name = viewHolder.name_plate.getText().toString().trim();
                String amountTotal = viewHolder.num_total_plates.getText().toString().trim();


                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Confirme añadir " + amount + " de " + name)
                        .setTitle("Añadir Platos");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        if (amount.isEmpty()) {
                            Toast.makeText(activity, "Error al añadir, ingrese bien todos los campos", Toast.LENGTH_SHORT).show();

                        } else if (!esNumero(amount)) {
                            Toast.makeText(activity, "Error en la cantidad, introducir un numero", Toast.LENGTH_SHORT).show();

                        } else if (Integer.parseInt(amount) > Integer.parseInt(amountTotal)) {
                            Toast.makeText(activity, "Error, a superado la cantidad total disponible", Toast.LENGTH_SHORT).show();

                        } else {
                            //guardaremos la mesa con sus respectivos campos

                            Map<String, Object> map = new HashMap<>();
                            map.put("name", name);
                            map.put("amount", amount);


                            Task<DocumentSnapshot> task;
                            task = dbFirestore.collection("mesas").document(id_mesa).collection("plates").document(name).get();

                            task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    //Si no existe, se añade a la lista
                                    if (!documentSnapshot.exists()) {

                                        dbFirestore.collection("mesas").document(id_mesa).collection("plates").document(name).set(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(activity, "Platos añadidos correctamente", Toast.LENGTH_SHORT).show();

                                                        mDialog.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(activity, "Error al añadir platos", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        //Si existe, se actualiza la cantidad
                                        DocumentReference documentReference = dbFirestore.collection("mesas").document(id_mesa).collection("plates").document(name);
                                        documentReference.update("amount", String.valueOf(Integer.parseInt(task.getResult().getString("amount")) + Integer.parseInt(amount)))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(activity, "Platos añadidas correctamente", Toast.LENGTH_SHORT).show();
                                                        mDialog.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(activity, "Error al añadir platos", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });

                            // Actualizar la cantidad en la colección "all_drinks"
                            DocumentReference allPlatesRef = dbFirestore.collection("plates").document(id_drink);
                            allPlatesRef.update("amount", String.valueOf(Integer.parseInt(amountTotal) - Integer.parseInt(amount)))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // La cantidad se ha actualizado correctamente
                                            Toast.makeText(activity, "Actulizadas los platos totales", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Ha ocurrido un error al actualizar la cantidad
                                            Toast.makeText(activity, "Error al actulizar los platos totales", Toast.LENGTH_SHORT).show();
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
    public PlatesOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_plates_order,parent,false);
        return new PlatesOrderAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_plate,num_total_plates;
        EditText amount_plates;
        Button btn_add_plates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            name_plate = itemView.findViewById(R.id.addTitlePlate);
            num_total_plates = itemView.findViewById(R.id.numTotalPlates);
            //editText
            amount_plates = itemView.findViewById(R.id.addAmountPlates);
            //y lo mismo con los botones
            btn_add_plates = itemView.findViewById(R.id.btn_add_Plates_Order);

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
