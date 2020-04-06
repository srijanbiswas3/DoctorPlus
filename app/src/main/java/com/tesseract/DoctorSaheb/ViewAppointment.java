package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewAppointment extends AppCompatActivity {
    private List<Appointment> appointmentList;
    RecyclerView recyclerView;
    AppointmentAdapter adapter;
    TextView dataloadingtxt, uname;
    ProgressBar progressBar;
    DatabaseReference apref;
    int hflag = 0;
    ImageView datanf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataloadingtxt = findViewById(R.id.dataloadingtxt);
        progressBar = findViewById(R.id.progress);
        uname = findViewById(R.id.uname);
        datanf = findViewById(R.id.datanf);
        hflag = getIntent().getIntExtra("hflag", hflag);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (hflag == 1) {
            toolbar.setTitle("Appointment History");
        }

        appointmentList = new ArrayList<>();
        adapter = new AppointmentAdapter(this, appointmentList);
        recyclerView.setAdapter(adapter);
        apref = FirebaseDatabase.getInstance().getReference().child("Appointment");

        String name = getIntent().getStringExtra("name");
        apref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Calendar now = Calendar.getInstance();
                    int year = now.get(Calendar.YEAR);
                    int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
                    int day = now.get(Calendar.DAY_OF_MONTH);
                    String curdate = year + "" + month + "" + day;
                    long cur = Long.parseLong(curdate);


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (hflag == 1) {
                            if (ds.child("username").getValue().toString().equals(name) && cur > ScheduledDate(ds.child("time").getValue().toString())) {
                                Appointment appointment = ds.getValue(Appointment.class);
                                appointment.setStatus("Completed");
                                appointmentList.add(appointment);

                            }
                        } else {
                            if (ds.child("username").getValue().toString().equals(name) && cur <= ScheduledDate(ds.child("time").getValue().toString())) {
                                Appointment appointment = ds.getValue(Appointment.class);
                                appointmentList.add(appointment);

                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    dataloadingtxt.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    dataloadingtxt.setVisibility(View.GONE);
                }
                if (appointmentList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
                    datanf.setVisibility(View.VISIBLE);
                } else {
                    datanf.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //     }

        uname.setText("Appointments For: " + name);

    }

    private long ScheduledDate(String time) {
        int scheduledate = 0;
        int f = 0, l = time.length();
        String da = "", res = "";
        for (int i = time.length() - 1; i >= 0; i--) {
            if (time.charAt(i) == ',') {
                l = i;
            }
            if (time.charAt(i) == 'y') {
                f = i;
                break;
            }


        }
        da = time.substring(f + 1, l);
        int m = da.length();
        for (int i = da.length() - 1; i >= 0; i--) {
            if (da.charAt(i) == '/') {
                for (int j = i + 1; j < m; j++) {
                    res = res + da.charAt(j);
                }
                m = i;
            }
        }
        for (int i = 1; i < m; i++) {
            res = res + da.charAt(i);
        }

        return Long.parseLong(res);
    }


}
