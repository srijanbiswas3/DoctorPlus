package com.tesseract.DoctorSaheb;

import android.content.Context;
import android.content.Intent;
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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private Context mCtx;
    private List<Event> eventlist;
    DatabaseReference eventref = FirebaseDatabase.getInstance().getReference().child("Event");
    Event event;

    public EventAdapter() {

    }

    public EventAdapter(Context mCtx, List<Event> eventlist) {
        this.mCtx = mCtx;
        this.eventlist = eventlist;
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.event_card, parent, false);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventAdapter.EventViewHolder holder, int position) {
         event = eventlist.get(position);
        Picasso.get().load(event.getImage()).into(holder.image);
        Picasso.get().load(event.getImage2()).into(holder.image2);
        holder.date.setText(event.getDate());
        holder.heading.setText(event.getHeading());
        holder.title.setText(event.getTitle());
        holder.recent.setText(event.getRecent());
        holder.author.setText(event.getAuthor());
        holder.desc.setText(event.getDescription());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event=eventlist.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.card.getContext(), WebView.class);
                intent.putExtra("site", event.getLink());
                intent.putExtra("title", event.getTitle());
                holder.card.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView heading, title, date, desc, author, recent;
        ImageView image, image2;

        RelativeLayout card;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.heading);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            recent = itemView.findViewById(R.id.recent);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.image);
            image2 = itemView.findViewById(R.id.image2);


        }
    }
}