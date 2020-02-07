package com.tesseract.DoctorSaheb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter< DoctorAdapter.DoctorsViewHolder> {
    private Context mCtx;
    private List<Doctors> doctorsList;
    public DoctorAdapter()
    {

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
        final Doctors doc = doctorsList.get(position);
        holder.tname.setText(doc.getName());
        holder.temail.setText(doc.getEmail());
        holder.ttype.setText(doc.getType());
        Picasso.get().load(doc.getProfileimg()).into(holder.timg);
        holder.viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.viewbtn.getContext(),DoctorDetails.class);
                intent.putExtra("name",doc.getName());
                intent.putExtra("email",doc.getEmail());
                intent.putExtra("type",doc.getType());
                intent.putExtra("about",doc.getAbout());
                intent.putExtra("qualification",doc.getQualifications());
                intent.putExtra("workplace",doc.getWorkplace());
                intent.putExtra("img",doc.getProfileimg());
                holder.viewbtn.getContext().startActivity(intent);

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
        Button viewbtn;

        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.docname);
            temail = itemView.findViewById(R.id.docemail);
            ttype = itemView.findViewById(R.id.doctype);
            timg = itemView.findViewById(R.id.docimg);
            viewbtn=itemView.findViewById(R.id.viewbtn);

        }
    }
}