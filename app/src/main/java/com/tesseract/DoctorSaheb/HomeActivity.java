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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.tesseract.DoctorSaheb.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
TextView welcome;
Button logout;
BottomSheetBehavior bottomSheetBehavior;
LinearLayout linearLayout;
Spinner location;
Spinner ptype;
private RecyclerView doclist;
private DatabaseReference databaseReference;
private DoctorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcome=findViewById(R.id.welcome);
        logout=findViewById(R.id.logout);
        linearLayout=findViewById(R.id.botsheet);
        bottomSheetBehavior=BottomSheetBehavior.from(linearLayout);
        location=findViewById(R.id.location);
        ptype=findViewById(R.id.ptype);
        doclist=findViewById(R.id.recyclerview);
        doclist.setLayoutManager(new LinearLayoutManager(this));

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors");
        databaseReference.keepSynced(true);

        FirebaseRecyclerOptions<Doctors> options =
                new FirebaseRecyclerOptions.Builder<Doctors>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors"), Doctors.class)
                        .build();
        adapter = new DoctorAdapter(options);
        doclist.setAdapter(adapter);



        String[] loca={"Ghaziabad","Agra","Firozabad","Gazipur","Meerut","Hapur","Mirzapur","Varanasi","Sitapur","Etawah"};
        String[] type={"ENT Specialist","Orthopadist","gynaecologist","Dermatologists","Allergists","Cardiologists","Gastroenterologists"};
        List<String> loc=new ArrayList<String >();
        loc.addAll(Arrays.asList(loca));
        List<String> typep=new ArrayList<>();
        typep.addAll(Arrays.asList(type));


        ArrayAdapter<String > arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,loc);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(arrayAdapter);
        location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),item+" Setected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String > arrayAdapter2=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,typep);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ptype.setAdapter(arrayAdapter2);
        ptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item=parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),item+" Setected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        String name=getIntent().getStringExtra("name");
        welcome.setText(name);

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
    @Override public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();

                linearLayout.getGlobalVisibleRect(outRect);

                if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    protected void onStart() {
        super.onStart();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        adapter.startListening();



    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }





}
