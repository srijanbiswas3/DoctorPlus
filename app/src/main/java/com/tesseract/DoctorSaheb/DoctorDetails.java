package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DoctorDetails extends AppCompatActivity {
    ImageView docimg;
    TextView docname, docemail, doctype, docabout, docqualification, docworkplace;
    Button map, appointbtn;
    DatabaseReference userref, docref,appref;
    Doctors doc, doc2;
    Member member;
    String mobile;
    FirebaseAuth auth;
    String nam;
    int apno=0;
    Appointment appointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        docimg = findViewById(R.id.docimg);
        docname = findViewById(R.id.docnametxt);
        docemail = findViewById(R.id.docemailtxt);
        doctype = findViewById(R.id.doctypetxt);
        docabout = findViewById(R.id.abouttxt);
        docqualification = findViewById(R.id.qualificationtxt);
        docworkplace = findViewById(R.id.workplacetxt);
        map = findViewById(R.id.map);
        appointbtn = findViewById(R.id.appointbtn);
        auth = FirebaseAuth.getInstance();
        appointment=new Appointment();
        doc=new Doctors();
        appref=FirebaseDatabase.getInstance().getReference().child("Appointment");
        userref = FirebaseDatabase.getInstance().getReference().child("Member").child(""+auth.getUid());
        final String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String type = getIntent().getStringExtra("type");
        String about = getIntent().getStringExtra("about");
        String qualification = getIntent().getStringExtra("qualification");
        String workplace = getIntent().getStringExtra("workplace");
        String img = getIntent().getStringExtra("img");
        doc.setProfileimg(img);
        Picasso.get().load(doc.getProfileimg()).into(docimg);
        docname.setText(name);
        docemail.setText(email);
        doctype.setText(type);
        docabout.setText(about);
        docqualification.setText(qualification);
        docworkplace.setText(workplace);
        mobile = auth.getCurrentUser().getPhoneNumber().toString();

        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    nam=dataSnapshot.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        appref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(ds.child("doctorname").getValue().toString().equals(name) && ds.child("username").getValue().toString().equals(nam))
                        {
                            appointbtn.setBackgroundColor(Color.BLUE);
                            appointbtn.setClickable(false);
                            appointbtn.setText("Appointment Requested");
                        }
                    }
                }
                else {
                    apno = (int) dataSnapshot.getChildrenCount();
                    appointment.setDoctorname(name);
                    appointment.setUsername(nam);
                    appointment.setStatus("Requested");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        appointbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointbtn.setBackgroundColor(Color.BLUE);
                appref.child("appointment"+(apno+1)).setValue(appointment);
                appointbtn.setClickable(false);
                appointbtn.setText("Appointment Requested");

            }
        });


    }
}
