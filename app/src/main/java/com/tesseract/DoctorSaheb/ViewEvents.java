package com.tesseract.DoctorSaheb;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewEvents extends AppCompatActivity {
    private List<Event> eventList;
    RecyclerView recyclerView;
    EventAdapter adapter;
    TextView dataloadingtxt;
    ProgressBar progressBar;
    DatabaseReference eventref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataloadingtxt = findViewById(R.id.dataloadingtxt);
        progressBar = findViewById(R.id.progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eventList = new ArrayList<>();
        adapter = new EventAdapter(this, eventList);
        recyclerView.setAdapter(adapter);
        eventref = FirebaseDatabase.getInstance().getReference().child("Event");


        eventref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Event events = snapshot.getValue(Event.class);
                        eventList.add(events);
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    dataloadingtxt.setVisibility(View.GONE);

                } else {
                    Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    dataloadingtxt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
