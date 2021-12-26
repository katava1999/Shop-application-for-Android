package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat_android.DBHelper;
import com.example.projekat_android.R;

import org.w3c.dom.Text;

import java.util.List;

import model.Artikal;

public class ArtikalAdapterProdavac extends RecyclerView.Adapter<ArtikalAdapterProdavac.ViewHolder> {
    Context context;
    List<Artikal> artikalList;
    RecyclerView recyclerView;
    DBHelper DB;

    final View.OnClickListener onClickListener = new MyOnClickListener();

    public static class ViewHolder extends RecyclerView.ViewHolder {
            EditText naziv;
            EditText opis;
            EditText cena;
            Button izmena;
            Button obrisi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            naziv = itemView.findViewById(R.id.nazivProdavac);
            opis = itemView.findViewById(R.id.opisProdavac);
            cena = itemView.findViewById(R.id.cenaProdavac);
            izmena = itemView.findViewById(R.id.editArtikal);
            obrisi = itemView.findViewById(R.id.deleteArtikal);
        }
    }

    public ArtikalAdapterProdavac(Context context, List<Artikal> artikalList, RecyclerView recyclerView){
        this.context = context;
        this.artikalList = artikalList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ArtikalAdapterProdavac.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_prodavac, parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Artikal artikal = artikalList.get(position);
        DB = new DBHelper(context);
        holder.naziv.setText(artikal.getNaziv());
        holder.opis.setText(artikal.getOpis());
        holder.cena.setText(String.valueOf(artikal.getCena()));

        holder.izmena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String naziv = holder.naziv.getText().toString();
                String opis = holder.opis.getText().toString();
                Double cena = Double.valueOf(holder.cena.getText().toString());
                String cenaZaProveru = holder.cena.getText().toString();


                 if (naziv.equals("") || opis.equals("") || cena<=0){
                     CharSequence text = "Unesite sva polja";
                     int duration = Toast.LENGTH_SHORT;
                     Toast toast = Toast.makeText(context, text, duration);
                     toast.show();
                 }else{
                     DB.updateArtikal(new Artikal(artikal.getId(), naziv, opis, cena));
                     notifyDataSetChanged();
                     ((Activity) context).finish();
                     context.startActivity(((Activity) context).getIntent());
                 }

            }
        });

        holder.obrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.deleteArtikal(artikal.getId());
                artikalList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return artikalList.size();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildLayoutPosition(v);
        }
    }
}
