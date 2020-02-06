package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DoctorDetails extends AppCompatActivity {
    ImageView docimg;
    TextView docname, docemail, doctype, docabout, docqualification, docworkplace;
    Button map, appointbtn;
    DatabaseReference databaseReference;
    Doctors doc;

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
        doc = new Doctors();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctors");


       /* databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        docemail.setText(data.child("email").getValue().toString());
                        docname.setText(data.child("name").getValue().toString());
                        doctype.setText(data.child("name").getValue().toString());
                        docabout.setText(data.child("name").getValue().toString());
                        docqualification.setText(data.child("name").getValue().toString());
                        docworkplace.setText(data.child("name").getValue().toString());

                        Picasso.get().load(doc.getProfileimg()).into(docimg);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), " Please Make sure your are connected to Internet...", Toast.LENGTH_LONG).show();

            }
        });*/
      /* String name=getIntent().getStringExtra("name");
       docname.setText(name);
*/

        String name=getIntent().getStringExtra("name");
        String email=getIntent().getStringExtra("email");
        String type=getIntent().getStringExtra("type");
        String about=getIntent().getStringExtra("about");
        String qualification=getIntent().getStringExtra("qualification");
        String workplace=getIntent().getStringExtra("workplace");
        String img=getIntent().getStringExtra("img");
        doc.setProfileimg(img);
        Picasso.get().load(doc.getProfileimg()).into(docimg);


        docname.setText(name);
        docemail.setText(email);
        doctype.setText(type);
        docabout.setText(about);
        docqualification.setText(qualification);
        docworkplace.setText(workplace);


    }
}
