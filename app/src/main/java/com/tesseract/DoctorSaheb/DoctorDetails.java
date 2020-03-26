package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DoctorDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener {
    ImageView docimg, prescription;
    TextView phone,docname, docemail, doctype, docabout, docqualification, docworkplace, time, canceltxt, location, age, yoe;
    Button map, appointbtn, cancel, pay, chtime, uploadproceed, skipproceed;


    LinearLayout botsheetpay, botsheetpres;
    DatabaseReference userref, docref, appref,pres;
    Doctors doc, doc2;
    String mobile, tim;
    FirebaseAuth auth;
    String nam,phnum;
    Appointment appointment;
    DataSnapshot ds2;
    BottomSheetBehavior bottomSheetBehavior, bottomSheetBehavior2;
    int flag = 0;
    String name, type, about, qualification, workplace, img, email, dateapp, timeapp;
    private static final int PICK_IMAGE_REQUEST = 1888;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    Prescription prescrip;
    RatingBar ratingBar;
   float rate= (float) 4.0;

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
        botsheetpay = findViewById(R.id.botsheetpay);
        botsheetpres = findViewById(R.id.botsheetpres);
        pay = findViewById(R.id.pay);
        time = findViewById(R.id.time);
        chtime = findViewById(R.id.chtime);
        canceltxt = findViewById(R.id.canceltxt);
        location = findViewById(R.id.locationtxt);
        yoe = findViewById(R.id.yoetxt);
        age = findViewById(R.id.age);
        phone = findViewById(R.id.phone);
        uploadproceed = findViewById(R.id.uploadproceed);
        skipproceed = findViewById(R.id.skipproceed);
        prescription = findViewById(R.id.prescription);
        ratingBar=findViewById(R.id.rating);

        bottomSheetBehavior = BottomSheetBehavior.from(botsheetpay);
        bottomSheetBehavior2 = BottomSheetBehavior.from(botsheetpres);

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
        prescrip = new Prescription();
        pres = FirebaseDatabase.getInstance().getReference().child("Prescription");
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        appref = FirebaseDatabase.getInstance().getReference().child("Appointment");
        docref = FirebaseDatabase.getInstance().getReference().child("Doctors");
        userref = FirebaseDatabase.getInstance().getReference().child("Member").child("" + auth.getUid());
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        rate= getIntent().getFloatExtra("rate",rate);
        type = getIntent().getStringExtra("type");
        about = getIntent().getStringExtra("about");
        qualification = getIntent().getStringExtra("qualification");
        workplace = getIntent().getStringExtra("workplace");
        img = getIntent().getStringExtra("img");
        doc.setProfileimg(img);
        Picasso.get().load(doc.getProfileimg()).into(docimg);
        docname.setText(name);
        docemail.setText(email);
        ratingBar.setRating(rate);
        doctype.setText(type);
        docabout.setText(about);
        docqualification.setText(qualification);
        docworkplace.setText(workplace);
        mobile = auth.getCurrentUser().getPhoneNumber().toString();
        doc2 = doc;
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                setAppointment();


            }
        });
        chtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
                flag = 1;
                uploadproceed.setText("Upload and Set Appointment");
                skipproceed.setText("Skip and Set Apppointment");

            }
        });
phone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        String phno="tel:"+phone.getText();
        i.setData(Uri.parse(phno));
        startActivity(i);

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
                            phnum=ds.child("mobile").getValue().toString();

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
                                phone.setVisibility(View.VISIBLE);

                                phone.setText(phnum);
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
                        Uri.parse("http://maps.google.com/maps?daddr=" + docworkplace.getText() + " " + location.getText()));
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
                phone.setVisibility(View.GONE);

            }
        });

        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
        uploadproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();


                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (flag == 0) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {

                    setAppointment();
                }
            }
        });

        skipproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (flag == 0) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                } else {
                    setAppointment();
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(prescription);
        }
    }

    private void uploadFile() {
        if (mImageUri != null) {
            mStorageRef.child(auth.getUid()+"prescription").putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return mStorageRef.child(auth.getUid()+"prescription").getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        prescrip.setDoctorname(name);
                        prescrip.setUsername(nam);
                        prescrip.setImage(downloadUri.toString());
                        pres.child(nam+"_"+name.substring(3)).setValue(prescrip);
                    } else {
                        Toast.makeText(DoctorDetails.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void setAppointment() {
        tim = dateapp + ", " + timeapp;
        appointment.setTime(tim);
        if (ds2 == null) {
            appref.push().setValue(appointment);
        } else {
            ds2.getRef().removeValue();
            appref.push().setValue(appointment);

        }


        chtime.setVisibility(View.VISIBLE);
        if (flag == 1) {
            Toast.makeText(getApplicationContext(), "Appointment day and time Changed", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Appointment Requested", Toast.LENGTH_SHORT).show();
        }
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

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setAccentColor(getResources().getColor(R.color.background));
        tpd.setMinTime(6,0,0);
        tpd.setMaxTime(21,0,0);
        tpd.show(getSupportFragmentManager(), "Choose Time");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
        String dayOfWeek = simpledateformat.format(date);
        dateapp = dayOfWeek + "\n" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        showTimePicker();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar datetime = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        datetime.set(Calendar.SECOND, second);
      /*  if(datetime.getTimeInMillis() < c.getTimeInMillis()){
            Toast.makeText(getApplicationContext(),"Selected Time already Passed",Toast.LENGTH_SHORT).show();
        }
        else {*/
            String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
            String minuteString = minute < 10 ? "0" + minute : "" + minute;
            timeapp = hourString + ":" + minuteString;

            bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
     //   }

    }
}
