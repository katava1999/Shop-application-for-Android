package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat_android.DBHelper;
import com.example.projekat_android.R;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            naziv = itemView.findViewById(R.id.nazivProdavac);
            opis = itemView.findViewById(R.id.opisProdavac);
            cena = itemView.findViewById(R.id.cenaProdavac);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Artikal artikal = artikalList.get(position);
        DB = new DBHelper(context);
        holder.naziv.setText(artikal.getNaziv());
        holder.opis.setText(artikal.getOpis());
        holder.cena.setText(Double.toString(artikal.getCena()));


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
