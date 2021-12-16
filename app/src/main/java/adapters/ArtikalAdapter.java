package adapters;

        import android.content.Context;
        import android.content.Intent;
        import android.content.LocusId;
        import android.content.SharedPreferences;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.projekat_android.DBHelper;
        import com.example.projekat_android.LoginActivity;
        import com.example.projekat_android.MainActivityKupac;
        import com.example.projekat_android.OpisArtiklaActivity;
        import com.example.projekat_android.R;

        import java.util.ArrayList;
        import java.util.List;

        import model.Artikal;
        import model.Korisnik;
        import model.Kupac;
        import model.Porudzbina;
        import model.Stavka;

public class ArtikalAdapter extends RecyclerView.Adapter<ArtikalAdapter.ViewHolder> {
    Context context;
    List<Artikal> artikalList;
    RecyclerView recyclerV;
    DBHelper DB;
    TextView prikaz;

    final View.OnClickListener onClickListener = new MyOnClickListner();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rowId;
        TextView rowNaziv;
        TextView rowOpis;
        TextView rowCena;
        EditText rowKolicina;
        Button rowNaruci;
        TextView prikaz;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //rowId = itemView.findViewById(R.id.item_id);
            rowNaziv = itemView.findViewById(R.id.item_name);
            rowOpis = itemView.findViewById(R.id.item_description);
            rowCena = itemView.findViewById(R.id.item_price);
            rowKolicina = itemView.findViewById(R.id.inputKolicina);
            rowNaruci = itemView.findViewById(R.id.btnNaruci);
            prikaz = itemView.findViewById(R.id.zaPrikazCeneKupcu);
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
        DB = new DBHelper(context);
        //holder.rowId.setText(""+artikal.getId());
        holder.rowNaziv.setText("Naziv: "+artikal.getNaziv());
        holder.rowOpis.setText("Opis: " +artikal.getOpis());
        //String pr = artikal.getOpis()
        holder.rowCena.setText("Cena: "+  String.valueOf(artikal.getCena()));

        holder.rowNaruci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int artikal_id = artikal.getId();
                if (holder.rowKolicina.getText().toString().equals("")){
                    Toast.makeText(v.getContext(), "Morate uneti kolicinu", Toast.LENGTH_SHORT).show();
                }
                else{
                    int kolicina = Integer.valueOf(holder.rowKolicina.getText().toString());

                    int idStavke = hashCode();
                    Stavka stavka = new Stavka(idStavke, kolicina, artikal_id);
                    int stavkaId = stavka.getId();
                    DB.insertStavke(stavka);

                    SharedPreferences sharedPref = context.getSharedPreferences("My pref", Context.MODE_PRIVATE);
                    String usernameKupca = sharedPref.getString("userName", "No name defined");
                    Korisnik korisnik = DB.findKorisnik(usernameKupca);
                    //Kupac kupac = DB.findKupca(usernameKupca);
                    int idKupca = korisnik.getId();

                    double cena = artikal.getCena() * kolicina;
                    Porudzbina porudzbina = new Porudzbina(idKupca, idStavke, cena);
                    DB.insertPorudzbinu(porudzbina);

                    Toast.makeText(v.getContext(), "Uspesno ste izvrsili kupovinu za "+ artikal.getNaziv(), Toast.LENGTH_SHORT).show();
                    holder.rowKolicina.setText("");
                    holder.prikaz.setText("");

                }
            }
        });

        holder.rowKolicina.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!holder.rowKolicina.getText().toString().equalsIgnoreCase("")){
                    int kolicina = Integer.valueOf(holder.rowKolicina.getText().toString());
                    double cena = artikal.getCena() * kolicina;
                    String pr = String.valueOf(cena);
                    holder.prikaz.setText("Ukupna cena nakon unete kolicine: " + pr + " " + "RSD");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return artikalList.size();
    }

    public void filteredList(ArrayList<Artikal> filterList) {
        artikalList = filterList;
        notifyDataSetChanged();

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
