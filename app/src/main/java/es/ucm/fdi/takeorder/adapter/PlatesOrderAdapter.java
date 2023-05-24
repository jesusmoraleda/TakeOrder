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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.model.Ingredient;
import es.ucm.fdi.takeorder.model.PlatesOrderElement;

public class PlatesOrderAdapter extends FirestoreRecyclerAdapter<PlatesOrderElement,PlatesOrderAdapter.ViewHolder> {


    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    private static final String TAG = "IngredientChecker";


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
        viewHolder.category_plate.setText(model.getCategory());

        viewHolder.btn_add_plates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = viewHolder.amount_plates.getText().toString().trim();
                String name = viewHolder.name_plate.getText().toString().trim();
                List<Ingredient> listIngredients = model.getIngredients();

                // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Confirme añadir " + amount + " de " + name)
                        .setTitle("Añadir Platos");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id2) {
                        // User clicked OK button

                        if (amount.isEmpty() || !esNumero(amount)) {
                            Toast.makeText(activity, "Error al añadir, ingrese bien todos los campos", Toast.LENGTH_SHORT).show();
                            /*
                        } else if (!checkIngredientsAvailability(listIngredients, Integer.parseInt(amount))) {
                            Toast.makeText(activity, "No hay suficiente cantidad de ingredientes para este plato", Toast.LENGTH_SHORT).show();
                             */
                        } else {

                            AtomicBoolean allIngredientsAvailable = new AtomicBoolean(true);

                            for (Ingredient ingredient : listIngredients) {
                                String id = ingredient.getId();
                                dbFirestore.collection("ingredients").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if (documentSnapshot.exists()) {

                                            long quantityTotal = documentSnapshot.getLong("quantity");
                                            double quantityNeeded = Integer.parseInt(amount) * ingredient.getQuantity();
                                            if (quantityTotal < quantityNeeded) {
                                                allIngredientsAvailable.set(false);
                                                Toast.makeText(activity, "Error al añadir plato, el ingrediente " + ingredient.getName() + " no esta disponible para " + amount + " cantidades", Toast.LENGTH_SHORT).show();
                                            }
                                            if (listIngredients.indexOf(ingredient) == listIngredients.size() - 1) {
                                                if (allIngredientsAvailable.get()) {

                                                    Map<String, Object> map = new HashMap<>();
                                                    map.put("name", name);
                                                    map.put("amount", Integer.parseInt(amount));
                                                    map.put("entregado", false);

                                                    dbFirestore.collection("mesas").document(id_mesa).collection("plates").add(map)
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Toast.makeText(activity, "Plato añadido", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(activity, "Error al añadir plato", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                    for (Ingredient ingredient2 : listIngredients) {

                                                        String id_ingredient = ingredient2.getId();
                                                        DocumentReference ingredientsRef = dbFirestore.collection("ingredients").document(id_ingredient);
                                                        ingredientsRef.update("quantity", FieldValue.increment(-(Integer.parseInt(amount) * ingredient2.getQuantity())))
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        // La cantidad se ha actualizado correctamente
                                                                        Toast.makeText(activity, "Actulizados los ingredientes totales", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        // Ha ocurrido un error al actualizar la cantidad
                                                                        Toast.makeText(activity, "Error al actulizar los ingredientes totales", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                    }


                                                }
                                            }

                                        }
                                    }
                                });
                            }
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id2) {
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
        TextView name_plate,category_plate;
        EditText amount_plates;
        Button btn_add_plates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            name_plate = itemView.findViewById(R.id.addTitlePlate);
            category_plate = itemView.findViewById(R.id.nameCategoryPlates);
            //editText
            amount_plates = itemView.findViewById(R.id.addAmountPlates);
            //y lo mismo con los botones
            btn_add_plates = itemView.findViewById(R.id.btn_add_Plates_Order);
            //btn_view_ingredients = itemView.findViewById(R.id.btn_Ingredients_Plate);

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
