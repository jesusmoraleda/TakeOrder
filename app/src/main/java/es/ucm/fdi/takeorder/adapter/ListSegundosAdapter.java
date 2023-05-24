package es.ucm.fdi.takeorder.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.model.MenuElement;

public class ListSegundosAdapter extends FirestoreRecyclerAdapter<MenuElement,ListSegundosAdapter.ViewHolder> {

    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListSegundosAdapter(@NonNull FirestoreRecyclerOptions<MenuElement> options, Activity activity) {
        super(options);
        this.activity= activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull MenuElement model) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        String id_mesa = activity.getIntent().getStringExtra("id_mesa");

        viewHolder.name_plate.setText(model.getName());
        viewHolder.quantity_plate.setText(String.valueOf(model.getQuantity()));

        // Si el campo "entregado" de la base de datos es true, mostrar el checkbox entregado como marcado,
        // y ocultar el checkbox pendiente
        boolean entregado = model.isEntregado();

        if (entregado) {
            viewHolder.entregadoCheckBox.setChecked(true);
            viewHolder.entregadoCheckBox.setEnabled(false);
            viewHolder.pendienteCheckBox.setButtonDrawable(android.R.color.transparent);
        }
        // Si el campo "entregado" de la base de datos es false, mostrar el checkbox pendiente como marcado,
        // y mostrar el checkbox entregado sin marcar
        else {
            viewHolder.entregadoCheckBox.setChecked(false);
            viewHolder.pendienteCheckBox.setChecked(true);
            viewHolder.pendienteCheckBox.setEnabled(false);
        }

        viewHolder.entregadoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("¿Seguro que desea marcar como entregado este pedido?")
                            .setCancelable(false)
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id0) {

                                    dbFirestore.collection("mesas").document(id_mesa)
                                            .collection("menu_segundos").document(id)
                                            .update("entregado", true)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // La cantidad se ha actualizado correctamente
                                                    Toast.makeText(activity, "Actulizado el campo entregado", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Ha ocurrido un error al actualizar la cantidad
                                                    Toast.makeText(activity, "Error al actulizar campo entregado", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    viewHolder.pendienteCheckBox.setButtonDrawable(android.R.color.transparent);
                                    viewHolder.entregadoCheckBox.setEnabled(false);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    viewHolder.entregadoCheckBox.setChecked(false);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_plates_menu,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_plate;
        TextView quantity_plate;

        CheckBox entregadoCheckBox;
        CheckBox pendienteCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            name_plate = itemView.findViewById(R.id.listTitlePlateMenu);
            quantity_plate = itemView.findViewById(R.id.numAmountPlateMenu);


            entregadoCheckBox = itemView.findViewById(R.id.checkBoxPlateMenuEntregado);
            pendienteCheckBox = itemView.findViewById(R.id.checkBoxPlateMenuPendiente);
        }

    }
}