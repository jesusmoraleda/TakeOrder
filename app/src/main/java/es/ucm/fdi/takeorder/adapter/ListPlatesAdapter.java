package es.ucm.fdi.takeorder.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;
import es.ucm.fdi.takeorder.model.PlatesOrderElement;

public class ListPlatesAdapter extends FirestoreRecyclerAdapter<PlatesOrderElement,ListPlatesAdapter.ViewHolder> {

    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListPlatesAdapter(@NonNull FirestoreRecyclerOptions<PlatesOrderElement> options, Activity activity) {
        super(options);
        this.activity= activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ListPlatesAdapter.ViewHolder viewHolder, int position, @NonNull PlatesOrderElement model) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        String id_mesa = activity.getIntent().getStringExtra("id_mesa");

        viewHolder.name_plate.setText(model.getName());
        viewHolder.amount_plate.setText(model.getAmount());

    }

    @NonNull
    @Override
    public ListPlatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_plates_order,parent,false);
        return new ListPlatesAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_plate;
        TextView amount_plate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            name_plate = itemView.findViewById(R.id.listTitlePlates);
            amount_plate = itemView.findViewById(R.id.numAmountPlates);

        }

    }
}
