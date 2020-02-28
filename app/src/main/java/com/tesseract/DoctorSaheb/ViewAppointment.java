package com.tesseract.DoctorSaheb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAppointment extends AppCompatActivity {
    private List<Appointment> appointmentList;
    RecyclerView recyclerView;
    AppointmentAdapter adapter;
    TextView dataloadingtxt,uname;
    ProgressBar progressBar;
    DatabaseReference apref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataloadingtxt = findViewById(R.id.dataloadingtxt);
        progressBar = findViewById(R.id.progress);
        uname = findViewById(R.id.uname);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appointmentList = new ArrayList<>();
        adapter = new AppointmentAdapter(this, appointmentList);
        recyclerView.setAdapter(adapter);
        apref = FirebaseDatabase.getInstance().getReference().child("Appointment");

        String name=getIntent().getStringExtra("name");
        Query query=apref.orderByChild("username").equalTo(name);
        query.addListenerForSingleValueEvent(valueEventListener);

        uname.setText("Appointments For: "+name);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            appointmentList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    appointmentList.add(appointment);
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
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
