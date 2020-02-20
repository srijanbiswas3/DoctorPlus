package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button login;
    TextView register;
    EditText phone,password;
    DatabaseReference user;
    String ph,pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        phone=findViewById(R.id.phone);
        register=findViewById(R.id.register);
        user=FirebaseDatabase.getInstance().getReference().child("Member");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ph=phone.getText().toString();
                 pa=password.getText().toString();

                if(ph.equals("") || pa.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter the details",Toast.LENGTH_SHORT).show();
                }
                else {

                   /* user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                for(DataSnapshot ds : dataSnapshot.getChildren())
                                {
                                    if(ds.child("mobile").getValue().toString().equals(ph) && ds.child("password").getValue().toString().equals(pa))
                                    {
                                        String nam = ds.child("name").getValue().toString();
                                        String id=ds.getRef().toString();
                                        Intent intent = new Intent(Login.this, HomeActivity.class);
                                        intent.putExtra("name", nam);
                                        intent.putExtra("id", id);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else if(ds.child("mobile").getValue().toString().equals(ph))
                                    {
                                        Toast.makeText(getApplicationContext(),"Wrong Password!",Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"No data found! Please Register first before you log in",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/



                   user.orderByChild("mobile").equalTo(ph).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if (dataSnapshot.getValue() != null){
                               String nam = dataSnapshot.child("name").getValue().toString();
                               String id=dataSnapshot.getRef().toString();
                               Intent intent = new Intent(Login.this, HomeActivity.class);
                               intent.putExtra("name", nam);
                               intent.putExtra("id", id);
                               intent.putExtra("ph", ph);

                               startActivity(intent);
                               finish();

                           }else{
                               //It is new users
                               //write an entry to your user table
                               //writeUserEntryToDB();
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });


                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });






    }
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(Login.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
