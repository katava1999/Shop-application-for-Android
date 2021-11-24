package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat_android.R;

import java.util.List;

import model.Artikal;

public class ArtikalAdapter extends RecyclerView.Adapter<ArtikalAdapter.ViewHolder> {
    Context context;
    List<Artikal> artikalList;
    RecyclerView recyclerV;

    final View.OnClickListener onClickListener = new MyOnClickListner();
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowId;
        TextView rowNaziv;
        TextView rowOpis;
        TextView rowCena;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowId = itemView.findViewById(R.id.item_id);
            rowNaziv = itemView.findViewById(R.id.item_name);
            rowOpis = itemView.findViewById(R.id.item_description);
            rowCena = itemView.findViewById(R.id.item_price);
        }
    }

    public ArtikalAdapter(Context context, List<Artikal> artikalList, RecyclerView recyclerV){
            this.context = context;
            this.artikalList = artikalList;
            this.recyclerV = recyclerV;
    }

    @NonNull
    @Override
    public ArtikalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_artikli, parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtikalAdapter.ViewHolder holder, int position) {
        Artikal artikal = artikalList.get(position);
        holder.rowId.setText(""+artikal.getId());
        holder.rowNaziv.setText(""+artikal.getNaziv());
        holder.rowOpis.setText(""+artikal.getOpis());
        holder.rowCena.setText(""+artikal.getCena());

    }

    @Override
    public int getItemCount() {
        return artikalList.size();
    }

    private class MyOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int vrednost = 300;
            int itemPosition = recyclerV.getChildLayoutPosition(v);
            String item = artikalList.get(itemPosition).getNaziv();
            double cena = artikalList.get(itemPosition).getCena();
            if (cena < vrednost){
                Toast.makeText(context, "Ne mozete kupiti ovaj proizvod" , Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(context, "Mozete kupiti ovaj proizvod" + item, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
