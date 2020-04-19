package com.tesseract.DoctorSaheb;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> implements Filterable {

    List<String> drugsList;
    List<String> drugsListAll;

    public DrugAdapter(List<String> drugsList) {
        this.drugsList = drugsList;
        this.drugsListAll = new ArrayList<>(drugsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.drug_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.slno.setText(String.valueOf(position));
        holder.drugname.setText(drugsList.get(position));
        //holder.drugimg.setImageIcon();
        //holder.drugimg.setImageDrawable();

    }

    @Override
    public int getItemCount() {
        return drugsList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // runs on a background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<String> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(drugsListAll);
            } else {
                for (String movie : drugsListAll) {
                    if (movie.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            drugsList.clear();
            drugsList.addAll((Collection<? extends String>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView drugimg;
        TextView drugname, slno;
        RelativeLayout card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drugimg = itemView.findViewById(R.id.drugimg);
            drugname = itemView.findViewById(R.id.drugname);
            slno = itemView.findViewById(R.id.slno);
            card = itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            String message = drugsList.get(getAdapterPosition());
            Intent intent = new Intent(view.getContext(), DrugDetailsActivity.class);
            intent.putExtra("EXTRA_MESSAGE", message);
            view.getContext().startActivity(intent);

        }
    }

}
