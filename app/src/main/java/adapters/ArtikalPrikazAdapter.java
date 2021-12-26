package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekat_android.DBHelper;
import com.example.projekat_android.R;

import java.util.List;

import model.Artikal;
import model.Porudzbina;
import model.Stavka;

public class ArtikalPrikazAdapter extends RecyclerView.Adapter<ArtikalPrikazAdapter.ViewHolder> {
    Context context;
    DBHelper DB;
    RecyclerView recyclerV;
    List<Artikal> artikalList;

    final View.OnClickListener onClickListener = new ArtikalPrikazAdapter.MyOnClickListner();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rowartikal;
        TextView rowkolicina;
        TextView rowukupnaCena;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowartikal = itemView.findViewById(R.id.itemArtikal);
            rowkolicina = itemView.findViewById(R.id.ItemKolicina);
            rowukupnaCena = itemView.findViewById(R.id.itemUkupnaCena);
        }
    }

    public ArtikalPrikazAdapter(Context context, List<Artikal> artikalList, RecyclerView recyclerV){
        this.context = context;
        this.artikalList = artikalList;
        this.recyclerV = recyclerV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_item_kupljeni_artikli, parent, false);
        view.setOnClickListener(onClickListener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Artikal artikal = artikalList.get(position);
        DB = new DBHelper(context);

        //  PRAVICU NOVU TABELU GDE CU CUVATI NAZIV ARTIKLA, KOLICINU I UKUPNU CENU
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class MyOnClickListner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = recyclerV.getChildLayoutPosition(v);

        }
    }
}