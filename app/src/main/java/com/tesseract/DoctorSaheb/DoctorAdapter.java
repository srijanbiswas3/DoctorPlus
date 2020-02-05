package com.tesseract.DoctorSaheb;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

public class DoctorAdapter extends FirebaseRecyclerAdapter<Doctors, DoctorAdapter.DoctorsViewHolder> {


    public DoctorAdapter(@NonNull FirebaseRecyclerOptions<Doctors> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull DoctorsViewHolder holder, int i, @NonNull Doctors doc) {
        holder.tname.setText(doc.getName());
        holder.temail.setText(doc.getEmail());
        holder.ttype.setText(doc.getType());
        Picasso.get().load(doc.getProfileimg()).into(holder.timg);
    }

    @NonNull
    @Override
    public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_card, parent, false);
        return new DoctorsViewHolder(view);
    }

    class DoctorsViewHolder extends RecyclerView.ViewHolder{

        TextView tname,temail,ttype;
        ImageView timg;

        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.docname);
            temail = itemView.findViewById(R.id.docemail);
            ttype = itemView.findViewById(R.id.doctype);
            timg=itemView.findViewById(R.id.docimg);

        }
    }
}