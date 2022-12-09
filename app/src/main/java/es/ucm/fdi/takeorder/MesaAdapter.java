package es.ucm.fdi.takeorder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesaAdapter extends RecyclerView.Adapter<MesaAdapter.ViewHolder> {
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
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            name.setText(item.getName());
            status.setText(item.getStatus());
        }

    }

    //endregion
}
