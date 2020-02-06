package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
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
    TextView welcome, drag;
    Button logout, viewbtn, search;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout linearLayout;
    Spinner location;
    Spinner ptype;
    private List<Doctors> doctorsList;
    private RecyclerView recyclerview;
    private DatabaseReference databaseReference;
    private DoctorAdapter adapter;
    String spinner1item, spinner2item;
    FirebaseRecyclerOptions<Doctors> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcome = findViewById(R.id.welcome);
        logout = findViewById(R.id.logout);
        linearLayout = findViewById(R.id.botsheet);
        drag = findViewById(R.id.drag);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        location = findViewById(R.id.location);
        ptype = findViewById(R.id.ptype);
        viewbtn = findViewById(R.id.viewbtn);
        search = findViewById(R.id.search);
        recyclerview = findViewById(R.id.recyclerview);
        doctorsList = new ArrayList<>();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorAdapter(this, doctorsList);
        recyclerview.setAdapter(adapter);
        recyclerview.hasFixedSize();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
        databaseReference.keepSynced(true);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        drag.setText("Drag down to hide");
        String[] loca = {"Location", "Ghaziabad", "Agra", "Firozabad", "Gazipur", "Meerut", "Hapur", "Mirzapur", "Varanasi", "Sitapur", "Etawah"};
        String[] type = {"Type", "ENT Specialist", "Orthopadist", "gynaecologist", "Dermatologists", "Allergists", "Cardiologists", "Gastroenterologists"};
        List<String> loc = new ArrayList<String>();
        loc.addAll(Arrays.asList(loca));
        List<String> typep = new ArrayList<>();
        typep.addAll(Arrays.asList(type));


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, loc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typep);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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


        String name = getIntent().getStringExtra("name");
        welcome.setText("Welcome " + name);

       /* viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(getApplicationContext(),"click me",Toast.LENGTH_SHORT).show();
            }
        });*/
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                        .orderByChild("type")
                        .startAt(spinner2item).endAt(spinner2item);


                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                query.addListenerForSingleValueEvent(valueEventListener);


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
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
            } else {
                Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
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
                    drag.setText("^Drag up to show^");
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    protected void onStart() {
        super.onStart();

        //  adapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        //  adapter.stopListening();
    }


}
