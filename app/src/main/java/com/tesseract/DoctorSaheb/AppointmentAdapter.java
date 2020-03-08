package com.tesseract.DoctorSaheb;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private Context mCtx;
    private List<Appointment> appointmentList;
    Doctors doc;
    String docemail, doctype, docabout, docquali, docwork, location;
    ImageView docprofile;
    DatabaseReference docref = FirebaseDatabase.getInstance().getReference().child("Doctors");
    Appointment app;

    public AppointmentAdapter() {

    }

    public AppointmentAdapter(Context mCtx, List<Appointment> appointmentList) {
        this.mCtx = mCtx;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.appointment_card, parent, false);

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentAdapter.AppointmentViewHolder holder, int position) {


        docref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                app = appointmentList.get(holder.getAdapterPosition());

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("name").getValue().toString().equals(app.getDoctorname())) {
                        Picasso.get().load(ds.child("profileimg").getValue().toString()).into(holder.img);
                        docemail = ds.child("email").getValue().toString();
                        doctype = ds.child("type").getValue().toString();
                        docabout = ds.child("about").getValue().toString();
                        docquali = ds.child("qualifications").getValue().toString();
                        docwork = ds.child("workplace").getValue().toString();
                        location = ds.child("location").getValue().toString();


                        if (app.getStatus().equals("Requested")) {
                            holder.statustxt.setBackgroundResource(R.drawable.status_color_blue);
                        } else if (app.getStatus().equals("Confirmed")) {
                            holder.statustxt.setBackgroundResource(R.drawable.status_color_green);
                        } else if (app.getStatus().equals("Cancelled")) {
                            holder.statustxt.setBackgroundResource(R.drawable.status_color_red);
                        }
                        holder.slno.setText((holder.getAdapterPosition() + 1) + "");
                        holder.docname.setText(app.getDoctorname());
                        holder.time.setText(app.getTime());
                        holder.status.setText(app.getStatus());
                        holder.type.setText(doctype);
                        holder.workplace.setText(docwork);
                        holder.email.setText(docemail);
                        holder.about.setText(docabout);
                        holder.quali.setText(docquali);
                        holder.location.setText(location);


                        holder.card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Intent intent = new Intent(holder.card.getContext(), DoctorDetails.class);
                                intent.putExtra("name", holder.docname.getText());
                                intent.putExtra("email", holder.email.getText());
                                intent.putExtra("type", holder.type.getText());
                                intent.putExtra("about", holder.about.getText());
                                intent.putExtra("qualification", holder.quali.getText());
                                intent.putExtra("workplace", holder.workplace.getText());
                                holder.card.getContext().startActivity(intent);

                            }
                        });

                    } else {
                        //Toast.makeText(mCtx, "data not found", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView slno, time, docname, status, type, email, workplace, location, about, quali, statustxt;
        ImageView img;

        RelativeLayout card;


        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            slno = itemView.findViewById(R.id.slno);
            docname = itemView.findViewById(R.id.docname);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            type = itemView.findViewById(R.id.doctype);
            email = itemView.findViewById(R.id.docemail);
            workplace = itemView.findViewById(R.id.workplace);
            location = itemView.findViewById(R.id.location);
            card = itemView.findViewById(R.id.card);
            img = itemView.findViewById(R.id.docimg);
            about = itemView.findViewById(R.id.docabout);
            quali = itemView.findViewById(R.id.docquali);
            statustxt = itemView.findViewById(R.id.statustxt);


        }
    }
}