package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.LocusId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat_android.LoginActivity;
import com.example.projekat_android.MainActivityKupac;
import com.example.projekat_android.OpisArtiklaActivity;
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
        EditText rowKolicina;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //rowId = itemView.findViewById(R.id.item_id);
            rowNaziv = itemView.findViewById(R.id.item_name);
            rowOpis = itemView.findViewById(R.id.item_description);
            rowCena = itemView.findViewById(R.id.item_price);
            rowKolicina = itemView.findViewById(R.id.inputKolicina);
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
    public void onBindViewHolder(@NonNull final ArtikalAdapter.ViewHolder holder, int position) {
        final Artikal artikal = artikalList.get(position);
        //holder.rowId.setText(""+artikal.getId());
        holder.rowNaziv.setText("Naziv: "+artikal.getNaziv());
        holder.rowOpis.setText("Kliknite za detalje o artiklu");
        //String pr = artikal.getOpis()
        holder.rowCena.setText("Cena: "+artikal.getCena());

        holder.rowOpis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String opis = String.valueOf(holder.rowOpis.getText().toString());
                String pr = artikal.getOpis();
                Intent intent = new Intent(v.getContext(), OpisArtiklaActivity.class);// ovde cu staviti aktiviti gde cu prikazazi opis proidvoda
                intent.putExtra("opis", pr);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return artikalList.size();
    }



    private class MyOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerV.getChildLayoutPosition(v);
            String item = artikalList.get(itemPosition).getNaziv();
            String opis = artikalList.get(itemPosition).getOpis();

        }
    }
}
