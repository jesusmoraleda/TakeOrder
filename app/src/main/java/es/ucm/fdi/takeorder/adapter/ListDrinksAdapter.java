package es.ucm.fdi.takeorder.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.ucm.fdi.takeorder.R;
import es.ucm.fdi.takeorder.model.DrinksOrderElement;

public class ListDrinksAdapter extends FirestoreRecyclerAdapter<DrinksOrderElement,ListDrinksAdapter.ViewHolder> {

    private FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListDrinksAdapter(@NonNull FirestoreRecyclerOptions<DrinksOrderElement> options, Activity activity) {
        super(options);
        this.activity= activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull DrinksOrderElement model) {

        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        String id_mesa = activity.getIntent().getStringExtra("id_mesa");

        viewHolder.name_drink.setText(model.getName());
        viewHolder.amount_drink.setText(model.getAmount());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_drinks_order,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_drink;
        TextView amount_drink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            name_drink = itemView.findViewById(R.id.listTitleDrink);
            amount_drink = itemView.findViewById(R.id.numAmountDrink);

        }

    }
}
