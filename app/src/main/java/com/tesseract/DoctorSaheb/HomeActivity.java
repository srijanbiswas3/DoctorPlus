package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tesseract.DoctorSaheb.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextView welcome, dataloadingtxt, filter;
    Button logout, search, clear;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout linearLayout;
    Spinner location;
    Spinner ptype;
    private List<Doctors> doctorsList;
    private RecyclerView recyclerview;
    private DatabaseReference databaseReference, reff;
    private DoctorAdapter adapter;
    String spinner1item, spinner2item;
    FirebaseAuth auth;
    ProgressBar progressBar;
    String nam;
    String mobile;
    Toolbar toolbar;
    NavigationView nav;
    String gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcome = findViewById(R.id.welcome);
        logout = findViewById(R.id.logout);
        linearLayout = findViewById(R.id.botsheet);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        location = findViewById(R.id.location);
        ptype = findViewById(R.id.ptype);
        search = findViewById(R.id.search);
        clear = findViewById(R.id.clear);
        dataloadingtxt = findViewById(R.id.dataloadingtxt);
        filter = findViewById(R.id.filter);
        progressBar = findViewById(R.id.progress);
        recyclerview = findViewById(R.id.recyclerview);
        auth = FirebaseAuth.getInstance();
        doctorsList = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorAdapter(this, doctorsList);
        recyclerview.setAdapter(adapter);
        recyclerview.hasFixedSize();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
        databaseReference.keepSynced(true);
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.menu1);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DrawerLayout navDrawer = findViewById(R.id.draw);
                        // If the navigation drawer is not open then open it, if its already open then close it.
                        if (!navDrawer.isDrawerOpen(Gravity.LEFT))
                            navDrawer.openDrawer(Gravity.LEFT);
                        else navDrawer.closeDrawer(Gravity.LEFT);
                    }
                }
        );
        nav = (NavigationView) findViewById(R.id.nav);
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) {
                            case R.id.viewapp:
                                Intent intent = new Intent(HomeActivity.this, ViewAppointment.class);
                                intent.putExtra("name", nam);
                                startActivity(intent);
                                break;
                            case R.id.edprofile:
                                Intent intent1 = new Intent(HomeActivity.this, ProfileActivity.class);
                                intent1.putExtra("flag", 1);
                                startActivity(intent1);
                                break;
                            case R.id.apphistory:
                                Toast.makeText(HomeActivity.this, "clicked History", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.events:
                                Intent intent2 = new Intent(HomeActivity.this,ViewEvents.class);
                                startActivity(intent2);
                                break;
                        }
                        return false;
                    }
                }
        );

        String[] loca = {"Location", "Ghaziabad", "Agra", "Firozabad", "Gazipur", "Meerut", "Hapur", "Mirzapur", "Varanasi", "Sitapur", "Etawah"};
        String[] type = {"Type", "ENT Specialist", "Orthopedist", "gynaecologist", "Dermatologists", "Allergist", "Cardiologist", "Gastroenterologist", "General Physician"};
        List<String> loc = new ArrayList<String>();
        loc.addAll(Arrays.asList(loca));
        List<String> typep = new ArrayList<>();
        typep.addAll(Arrays.asList(type));
        progressBar.setVisibility(View.VISIBLE);
        dataloadingtxt.setVisibility(View.VISIBLE);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, loc);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        location.setAdapter(arrayAdapter);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                } else {
                    spinner1item = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, typep);
        arrayAdapter2.setDropDownViewResource(R.layout.spinner_item);
        ptype.setAdapter(arrayAdapter2);
        ptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                } else {
                    spinner2item = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mobile = auth.getCurrentUser().getPhoneNumber().toString();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if (data.child("mobile").getValue().toString().equals(mobile)) {
                            nam = data.child("name").getValue().toString();
                            filter.setText("Showing Result for: ALL");
                            if (data.child("gender").getValue().toString().equals("Male")) {
                                gen = "Mr.";
                            } else {
                                gen = "Mrs.";
                            }
                            welcome.setText("Welcome " + gen + nam);
                            Toast.makeText(getApplicationContext(), "Welcome " + nam, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            dataloadingtxt.setVisibility(View.GONE);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), " Please Make sure your are connected to Internet...", Toast.LENGTH_LONG).show();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (location.getSelectedItemPosition() == 0 && ptype.getSelectedItemPosition() == 0) {
                    filter.setText("Showing Result for: ALL");
                    databaseReference.addListenerForSingleValueEvent(valueEventListener);
                } else if(location.getSelectedItemPosition() == 0) {
                    Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                            .orderByChild("type")
                            .equalTo(spinner2item);
                    filter.setText("Showing Result for: "+ spinner2item);
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
                else if(ptype.getSelectedItemPosition() == 0) {
                    Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                            .orderByChild("location")
                            .equalTo(spinner1item);
                    filter.setText("Showing Result for: " + spinner1item);
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
                else {

                    filter.setText("Showing Result for: " + spinner2item + " in " + spinner1item);

                    Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                            .orderByChild("type_location")
                            .equalTo(spinner2item+"_"+spinner1item);
                    query.addListenerForSingleValueEvent(valueEventListener);
                }


            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Doctors");
                query.addListenerForSingleValueEvent(valueEventListener);
                filter.setText("Showing Result for: ALL");
                location.setSelection(0);
                ptype.setSelection(0);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), " Logged out", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            doctorsList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctors doctor = snapshot.getValue(Doctors.class);
                    doctorsList.add(doctor);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                dataloadingtxt.setVisibility(View.GONE);

            } else {
                Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                Rect outRect = new Rect();
                linearLayout.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}
