package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class DoctorDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener{
    ImageView docimg;
    TextView docname, docemail, doctype, docabout, docqualification, docworkplace, time, canceltxt, location, age, yoe;
    Button map, appointbtn, cancel, pay, chtime;

    LinearLayout linear, botsheettime;
    DatabaseReference userref, docref, appref;
    Doctors doc, doc2;
    String mobile, tim;
    FirebaseAuth auth;
    String nam;
    Appointment appointment;
    DataSnapshot ds2;
    BottomSheetBehavior bottomSheetBehavior;
    RadioGroup rg;
    RadioButton rb;
    //String[] names = {"Monday         8:30AM", "Monday         7:30PM", "Wednesday  10:30AM", "Wednesday    6:30PM", "Thursday         7:00AM", "Thursday         9:30AM", "Friday              7:00AM", "Friday              6:30PM"};
    int flag = 0;
    String name, type, about, qualification, workplace, img, email,dateapp,timeapp;


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
        pay = findViewById(R.id.pay);
        time = findViewById(R.id.time);
        chtime = findViewById(R.id.chtime);
        canceltxt = findViewById(R.id.canceltxt);
        location = findViewById(R.id.locationtxt);
        yoe = findViewById(R.id.yoetxt);
        age = findViewById(R.id.age);
        bottomSheetBehavior = BottomSheetBehavior.from(botsheettime);
        chtime.setVisibility(View.GONE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        auth = FirebaseAuth.getInstance();
        appointment = new Appointment();
        doc = new Doctors();
        appref = FirebaseDatabase.getInstance().getReference().child("Appointment");
        docref = FirebaseDatabase.getInstance().getReference().child("Doctors");
        userref = FirebaseDatabase.getInstance().getReference().child("Member").child("" + auth.getUid());
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        type = getIntent().getStringExtra("type");
        about = getIntent().getStringExtra("about");
        qualification = getIntent().getStringExtra("qualification");
        workplace = getIntent().getStringExtra("workplace");
        img = getIntent().getStringExtra("img");
        doc.setProfileimg(img);
        Picasso.get().load(doc.getProfileimg()).into(docimg);
        docname.setText(name);
        docemail.setText(email);
        doctype.setText(type);
        docabout.setText(about);
        docqualification.setText(qualification);
        docworkplace.setText(workplace);
        mobile = auth.getCurrentUser().getPhoneNumber().toString();
        doc2 = doc;


        /*rg = new RadioGroup(this);
        rg.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < names.length; i++) {
            rb = new RadioButton(this);
            rb.setText(names[i]);
            rg.addView(rb);
            rb.setTextColor(getResources().getColor(R.color.background));
            rb.setTextSize(18);
            Typeface face = Typeface.createFromAsset(getAssets(),
                    "font/muli.ttf");
            rb.setTypeface(face);
        }
        linear.addView(rg);*/

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int selectedId = rg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                tim = radioButton.getText().toString();


                time.setText("on " + tim);*/
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
               tim=dateapp+", "+timeapp;
                appointment.setTime(tim);
                if (ds2 == null) {
                    appref.push().setValue(appointment);
                } else {
                    ds2.getRef().removeValue();
                    appref.push().setValue(appointment);
                    // appref.child(ds2.getRef().toString()).child("time").setValue(tim);

                }


                chtime.setVisibility(View.VISIBLE);
                if (flag == 1) {
                    Toast.makeText(getApplicationContext(), "Appointment day and time Changed", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Appointment Requested", Toast.LENGTH_SHORT).show();
                }


            }
        });
        chtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                showDatePicker();
                flag = 1;

            }
        });


        docref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("name").getValue().toString().equals(name)) {
                            Picasso.get().load(ds.child("profileimg").getValue().toString()).into(docimg);
                            docemail.setText(ds.child("email").getValue().toString());
                            doctype.setText(ds.child("type").getValue().toString());
                            docabout.setText(ds.child("about").getValue().toString());
                            docqualification.setText(ds.child("qualifications").getValue().toString());
                            docworkplace.setText(ds.child("workplace").getValue().toString());
                            location.setText(ds.child("location").getValue().toString());
                            age.setText("Age: " + ds.child("age").getValue().toString());
                            yoe.setText(ds.child("yoe").getValue().toString());

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                            if (ds.child("status").getValue().toString().equals("Requested")) {
                                appointbtn.setBackgroundResource(R.drawable.btn_background3);
                                appointbtn.setClickable(false);
                                appointbtn.setText("Appointment Requested");

                                chtime.setVisibility(View.VISIBLE);
                            } else if (ds.child("status").getValue().toString().equals("Confirmed")) {
                                appointbtn.setBackgroundResource(R.drawable.status_color_green);
                                appointbtn.setClickable(false);
                                appointbtn.setText("Appointment Confirmed");
                            }
                            time.setText("-on " + ds2.child("time").getValue().toString());
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

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+docworkplace.getText()+" "+location.getText()));
                startActivity(intent);
            }
        });

        appointbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                showDatePicker();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ds2.getRef().removeValue();
                appointbtn.setBackgroundResource(R.drawable.btn_background);
                appointbtn.setText("Request Appointment");
                Toast.makeText(getApplicationContext(), "Appointment request Cancelled", Toast.LENGTH_SHORT).show();
                appointbtn.setClickable(true);
                cancel.setVisibility(View.GONE);
                chtime.setVisibility(View.GONE);
                time.setText("");

            }
        });


    }
    private void showDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                DoctorDetails.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );

        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        Calendar late = Calendar.getInstance();
        late.add(Calendar.MONTH, 1);
       /* Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.add(Calendar.DAY_OF_MONTH,1);
        Calendar date3 = Calendar.getInstance();
        date3.add(Calendar.DAY_OF_MONTH,4);
        Calendar[] days={date1,date2,date3};
        dpd.setDisabledDays(days);
        dpd.setHighlightedDays(days);*/
       dpd.setAccentColor(getResources().getColor(R.color.background));
        dpd.setMinDate(now);
        dpd.setMaxDate(late);
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");

    }
    private void showTimePicker()
    {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setAccentColor(getResources().getColor(R.color.background));
        tpd.show(getSupportFragmentManager(),"Choose Time");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         dateapp =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
         showTimePicker();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
         timeapp =hourString+":"+minuteString;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }
}
