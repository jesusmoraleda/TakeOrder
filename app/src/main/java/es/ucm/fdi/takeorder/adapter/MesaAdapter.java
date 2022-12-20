package es.ucm.fdi.takeorder.adapter;

import android.app.Activity;
import android.content.Context;
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

import es.ucm.fdi.takeorder.R;
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

        viewHolder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMesa(id);
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
        Button btn_eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //referenciamos los valores atraves de cada setText de onBindViewHolder
            nombre = itemView.findViewById(R.id.nombreMesa);
            numero = itemView.findViewById(R.id.numeroComensales);
            //y lo mismo con los botones
           btn_eliminar = itemView.findViewById(R.id.btnMesaEliminar);

        }
    }

    /*
    private List<MesaElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public MesaAdapter(List<MesaElement> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }


    //region Public methods
    @Override
    public int getItemCount(){return mData.size();}

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Object model) {

    }

    @Override
    public MesaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.item_adapter_mesas, null);
        return new MesaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MesaAdapter.ViewHolder holder, int position){
        holder.bindData(mData.get(position));
    }

    public void setItemList(List<MesaElement> items) {mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, status;
        Button delete, favoritos;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nombreMesa);
            status = itemView.findViewById(R.id.statusTextView);
            delete = itemView.findViewById(R.id.MesaEliminar);
            favoritos = itemView.findViewById(R.id.MesaFavorito);
        }

        void bindData(final MesaElement item){
            //iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            //status.setText(item.getStatus());
        }

    }
    */
    //endregion
}
