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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
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
    TextView docname, docemail, doctype, docabout, docqualification, docworkplace, time;
    Button map, appointbtn, cancel, confirm, chtime;
    LinearLayout linear, botsheettime;
    DatabaseReference userref, docref, appref;
    Doctors doc, doc2;
    Member member;
    String mobile, tim;
    FirebaseAuth auth;
    String nam;
    Appointment appointment;
    DataSnapshot ds2;
    BottomSheetBehavior bottomSheetBehavior;
    RadioGroup rg;
    RadioButton rb;
    String[] names = {"Monday    8:30AM", "Monday    7:30PM", "Wednesday 10:30AM", "Wednesday 6:30PM", "Friday    8:30AM", "Friday    6:30PM"};


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
        cancel = findViewById(R.id.cancel);
        appointbtn = findViewById(R.id.appointbtn);
        botsheettime = findViewById(R.id.botsheettime);
        linear = findViewById(R.id.linear);
        confirm = findViewById(R.id.confirm);
        time = findViewById(R.id.time);
        chtime = findViewById(R.id.chtime);
        bottomSheetBehavior = BottomSheetBehavior.from(botsheettime);
        chtime.setVisibility(View.GONE);


        auth = FirebaseAuth.getInstance();
        appointment = new Appointment();
        doc = new Doctors();
        appref = FirebaseDatabase.getInstance().getReference().child("Appointment");
        userref = FirebaseDatabase.getInstance().getReference().child("Member").child("" + auth.getUid());
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

        rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);
        RadioGroup.LayoutParams r1;
        for (int i = 0; i < names.length; i++) {
            rb = new RadioButton(this);
            rb.setText(names[i]);
            // r1=new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.MATCH_PARENT);
            rg.addView(rb);

        }
        linear.addView(rg);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                tim = radioButton.getText().toString();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                time.setText("on " + tim);
                appointment.setTime(tim);
                if(ds2==null)
                {
                    appref.push().setValue(appointment);
                }
                else {
                    ds2.getRef().removeValue();
                    appref.push().setValue(appointment);
                   // appref.child(ds2.getRef().toString()).child("time").setValue(tim);
                }



                chtime.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Appointment Requested",Toast.LENGTH_SHORT).show();



            }
        });
        chtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            }
        });



        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    nam = dataSnapshot.child("name").getValue().toString();
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
                        if (ds.child("doctorname").getValue().toString().equals(name) && ds.child("username").getValue().toString().equals(nam)) {
                            ds2 = ds;

                            appointbtn.setBackgroundResource(R.drawable.btn_background3);
                            appointbtn.setClickable(false);
                            appointbtn.setText("Appointment Requested");
                            chtime.setVisibility(View.VISIBLE);
                            time.setText("-on "+ds2.child("time").getValue().toString());
                            if (appointment.getTime() == null) {

                            } else {
                                time.setText("-on " + tim);
                            }
                            cancel.setVisibility(View.VISIBLE);
                        } else {
                            appointment.setTime(tim);
                            appointment.setDoctorname(name);
                            appointment.setUsername(nam);
                            appointment.setStatus("Requested");
                        }
                    }
                } else {
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


                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds2.getRef().removeValue();
                appointbtn.setBackgroundResource(R.drawable.btn_background);
                appointbtn.setText("REQUEST APPOINTMENT");
                Toast.makeText(getApplicationContext(), "Appointment request Cancelled", Toast.LENGTH_SHORT).show();
                appointbtn.setClickable(true);
                cancel.setVisibility(View.GONE);
                chtime.setVisibility(View.GONE);
                time.setText("");

            }
        });


    }
}
