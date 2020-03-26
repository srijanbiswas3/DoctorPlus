package com.tesseract.DoctorSaheb;

import android.content.Context;
import android.content.Intent;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorsViewHolder> {
    private Context mCtx;
    private List<Doctors> doctorsList;
    Float rate;
    DatabaseReference docreff= FirebaseDatabase.getInstance().getReference().child("Doctors");

    public DoctorAdapter(ViewAppointment mCtx, List<Appointment> appointmentList) {

    }

    public DoctorAdapter(Context mCtx, List<Doctors> doctorsList) {
        this.mCtx = mCtx;
        this.doctorsList = doctorsList;
    }

    @NonNull
    @Override
    public DoctorAdapter.DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.doctor_card, parent, false);
        return new DoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DoctorAdapter.DoctorsViewHolder holder, int position) {
        final Doctors doc = doctorsList.get(holder.getAdapterPosition());
        holder.tname.setText(doc.getName());
        holder.temail.setText(doc.getEmail());
        holder.ttype.setText(doc.getType());
        Picasso.get().load(doc.getProfileimg()).into(holder.timg);
        holder.ratingBar.setRating(doc.getRating());


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.card.getContext(), DoctorDetails.class);
                intent.putExtra("name", doc.getName());
                intent.putExtra("email", doc.getEmail());
                intent.putExtra("type", doc.getType());
                intent.putExtra("about", doc.getAbout());
                intent.putExtra("qualification", doc.getQualifications());
                intent.putExtra("workplace", doc.getWorkplace());
                intent.putExtra("img", doc.getProfileimg());
                intent.putExtra("rate", doc.getRating());
                holder.card.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    class DoctorsViewHolder extends RecyclerView.ViewHolder {

        TextView tname, temail, ttype;
        ImageView timg;
        RelativeLayout card;
        RatingBar ratingBar;

        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.docname);
            temail = itemView.findViewById(R.id.docemail);
            ttype = itemView.findViewById(R.id.doctype);
            timg = itemView.findViewById(R.id.docimg);
            card = itemView.findViewById(R.id.card);
            ratingBar = itemView.findViewById(R.id.rating);


        }
    }
}