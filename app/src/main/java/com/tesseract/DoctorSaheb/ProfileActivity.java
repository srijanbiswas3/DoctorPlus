package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tesseract.DoctorSaheb.R;

public class ProfileActivity extends AppCompatActivity {
    Button logout, male, female;
    EditText email, phnumber, password, name;
    Button create;

    DatabaseReference reff, reff2;
    Member member;
    long maxid = 0;
    String mobile;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String Gender = "";
    TextView gendertxt;
    int g = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        email = findViewById(R.id.email);
        phnumber = findViewById(R.id.phnumber);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        create = findViewById(R.id.create);
        gendertxt = findViewById(R.id.gendertxt);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        member = new Member();
        g = getIntent().getIntExtra("flag", g);
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff2 = FirebaseDatabase.getInstance().getReference().child("Member");
        reff2.keepSynced(true);
        reff.keepSynced(true);
        reff2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (data.child("mobile").getValue().toString().equals(mobile)) {

                            String nam = data.child("name").getValue().toString();
                            String em = data.child("email").getValue().toString();
                            String num = data.child("mobile").getValue().toString();
                            String pass = data.child("password").getValue().toString();
                            email.setText(em);
                            phnumber.setText(num);
                            name.setText(nam);
                            password.setText(pass);
                            if (g == 0) {
                                Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                intent.putExtra("name", nam);
                                startActivity(intent);
                                finish();
                            }


                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), " Please Make sure your are connected to Internet...", Toast.LENGTH_LONG).show();

            }
        });
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (g == 1) {
            toolbar.setTitle("Edit Profile");
            create.setText("Done");
            findViewById(R.id.cancel).setVisibility(View.GONE);
        }
        phnumber.setEnabled(false);
        mobile = auth.getCurrentUser().getPhoneNumber().toString();
        phnumber.setText(mobile);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(g==0) {
                    firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Details not saved", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);


                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else
                {
                    finish();
                }
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setText("\u2713" + " Male");
                female.setText("Female");
                Gender = "Male";

            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setText("\u2713" + " Female");
                male.setText("Male");
                Gender = "Female";
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String Mobile = phnumber.getText().toString();
                String Name = name.getText().toString();


                boolean l = validate(Email, Password, Mobile, Name, Gender);
                if (l) {
                    member.setEmail(Email);
                    member.setPassword(Password);
                    member.setMobile(Mobile);
                    member.setName(Name);
                    member.setGender(Gender);
                    reff.child(auth.getUid().toString()).setValue(member);


                    Toast.makeText(getApplicationContext(), "Account Created Succesfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
                    i.putExtra("name", name.getText().toString());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);


                }


            }

            private boolean validate(String Email, String Password, String Mobile, String Name, String Gender) {
                boolean lol=true;
                if (TextUtils.isEmpty(Email) || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Enter correct Email");
                    lol=false;


                }
                else if (Password.isEmpty()) {
                    name.setError("Enter password");
                    lol=false;

                }
                else if (Mobile.isEmpty()) {

                    phnumber.setError("Enter name");
                    lol=false;

                }
                else if (Name.isEmpty() || Name.matches(".*\\d.*")) {
                    name.setError("Enter name");
                    lol=false;
                }
                else if (Gender.equals("")) {
                    gendertxt.setError("Select your Gender");
                    lol=false;
                }
               else{
                    lol=true;
                }
                return lol;
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Details not saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        //FirebaseAuth.getInstance().signOut();
        if (g == 0) {
            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Details not saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        } else {
            super.onBackPressed();
        }

    }
}
