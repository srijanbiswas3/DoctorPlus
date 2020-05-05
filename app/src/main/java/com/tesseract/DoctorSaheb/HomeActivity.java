package com.tesseract.DoctorSaheb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    TextView welcome, dataloadingtxt, filter, logouttext;
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
    String cityname;
    Toolbar toolbar;
    NavigationView nav;
    String gen;
    ImageView datanf;
    View locbtn, refresh;
    Geocoder geocoder;
    List<Address> addresses;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;
    FloatingActionButton fab;

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
        logouttext = findViewById(R.id.logouttext);
        datanf = findViewById(R.id.datanf);
        locbtn = findViewById(R.id.locbtn);
        refresh = findViewById(R.id.refresh);
        fab = findViewById(R.id.fab);
        DrawerLayout navDrawer = findViewById(R.id.draw);
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Animation spin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spin);

        int rflag = getIntent().getIntExtra("rflag", 0);
        String specialist = getIntent().getStringExtra("specialist");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.menu1);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // If the navigation drawer is not open then open it, if its already open then close it.
                        if (!navDrawer.isDrawerOpen(Gravity.LEFT))
                            navDrawer.openDrawer(Gravity.LEFT);
                        else navDrawer.closeDrawer(Gravity.LEFT);
                    }
                }
        );
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!navDrawer.isDrawerOpen(Gravity.LEFT)) {
                    navDrawer.openDrawer(Gravity.LEFT);

                    fab.setRippleColor(getColor(R.color.background2));


                }
                if (navDrawer.isDrawerOpen(Gravity.LEFT)) {
                    navDrawer.closeDrawer(Gravity.LEFT);
                    fab.setBackgroundResource(R.drawable.menu1);

                }

            }
        });

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

                            case R.id.apphistory:
                                Intent intent2 = new Intent(HomeActivity.this, ViewAppointment.class);
                                intent2.putExtra("name", nam);
                                intent2.putExtra("hflag", 1);
                                startActivity(intent2);
                                break;
                            case R.id.edprofile:
                                Intent intent1 = new Intent(HomeActivity.this, ProfileActivity.class);
                                intent1.putExtra("flag", 1);
                                startActivity(intent1);
                                break;
                            case R.id.events:
                                Intent intent3 = new Intent(HomeActivity.this, ViewEvents.class);
                                startActivity(intent3);
                                break;
                            case R.id.symptom:
                                Intent intent4 = new Intent(HomeActivity.this, Recommend.class);
                                startActivity(intent4);
                                break;
                            case R.id.drugdic:
                                Intent intent6 = new Intent(HomeActivity.this, DrugListActivity.class);
                                startActivity(intent6);
                                break;

                        }
                        return false;
                    }
                }
        );


        String[] loca = {"Location", "Ghaziabad", "Agra", "Firozabad", "Gazipur", "Meerut", "Hapur", "Mirzapur", "Varanasi", "Sitapur", "Etawah"};
        String[] type = {"Type", "Allergist", "Anesthesiologist", "Cardiologist", "Colon and Rectal Surgeon", "Medicine Specialist", "ENT Specialist", "Orthopedist", "Gynaecologist", "Dermatologist", "Gastroenterologist", "Neurologist", "Peditritian", "Pulmonologist", "Physiotherapist", "Podiatrist", "General Sergeon", "General Physician"};
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
        if (rflag == 1) {

            int c = 0, index = 0;
            for (int i = 0; i < ptype.getCount(); i++) {
                if (ptype.getItemAtPosition(i).equals(specialist)) {
                    c = 1;
                    index = i;
                    break;
                }

            }
            if (c == 0) {
                //Toast.makeText(getApplicationContext(), "Sorry!No Specialist found ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Found :" + specialist, Toast.LENGTH_SHORT).show();
                ptype.setSelection(index);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                        .orderByChild("type")
                        .equalTo(specialist);
                filter.setText("Showing Result for: " + specialist);
                query.addListenerForSingleValueEvent(valueEventListener);


            }
            return;
        }

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
                } else if (location.getSelectedItemPosition() == 0) {
                    Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                            .orderByChild("type")
                            .equalTo(spinner2item);
                    filter.setText("Showing Result for: " + spinner2item);
                    query.addListenerForSingleValueEvent(valueEventListener);
                } else if (ptype.getSelectedItemPosition() == 0) {
                    Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                            .orderByChild("location")
                            .equalTo(spinner1item);
                    filter.setText("Showing Result for: " + spinner1item);
                    query.addListenerForSingleValueEvent(valueEventListener);
                } else {

                    filter.setText("Showing Result for: " + spinner2item + " in " + spinner1item);

                    Query query = FirebaseDatabase.getInstance().getReference("Doctors")
                            .orderByChild("type_location")
                            .equalTo(spinner2item + "_" + spinner1item);
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

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = FirebaseDatabase.getInstance().getReference().child("Doctors");
                query.addListenerForSingleValueEvent(valueEventListener);
                refresh.startAnimation(spin);
                filter.setText("Showing Result for: ALL");
                location.setSelection(0);
                ptype.setSelection(0);
                Connected();
                Toast.makeText(getApplicationContext(), "Refreshing..", Toast.LENGTH_SHORT).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
                dialog.setMessage("Do you want to log out?");
                dialog.setTitle("Logout?");
                dialog.setIcon(R.drawable.logo2);

                dialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getApplicationContext(), " Logged out", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.edittext_background);
                alertDialog.show();


            }
        });

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locbtn.startAnimation(spin);
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                if (ActivityCompat.checkSelfPermission(
                        HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                } else {
                    getLastLocation();
                }
            }
        });
        Connected();
    }

    private void Connected() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
            Toast.makeText(getApplicationContext(), "Please connect to Internet", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            dataloadingtxt.setVisibility(View.GONE);
            filter.setText("Please Connect to network!");
            if (doctorsList.size() == 0) {
                datanf.setVisibility(View.VISIBLE);
            }


        }

    }


    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Geolocation(location.getLatitude(), location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Geolocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void Geolocation(double latitude, double longitude) {
        geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            // String address = addresses.get(0).getAddressLine(0);
            String area = addresses.get(0).getLocality();
            //  String city = addresses.get(0).getAdminArea();
            // String country = addresses.get(0).getCountryName();
            //  String postalcode = addresses.get(0).getPostalCode();
            //  String fulladdress = address + ", " + area + ", " + city + ", " + country + ", " + postalcode;
            cityname = area;
            Toast.makeText(getApplicationContext(), area, Toast.LENGTH_LONG).show();
            int c = 0, index = 0;

            for (int i = 0; i < location.getCount(); i++) {
                if (location.getItemAtPosition(i).equals(area)) {
                    c = 1;
                    index = i;
                    break;
                }

            }
            if (c == 0) {
                Toast.makeText(getApplicationContext(), "Sorry!No doctors found in: " + area, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Location:" + area, Toast.LENGTH_SHORT).show();

                location.setSelection(index);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            doctorsList.clear();
            if (dataSnapshot.exists()) {
                datanf.setVisibility(View.GONE);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doctors doctor = snapshot.getValue(Doctors.class);
                    doctorsList.add(doctor);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                dataloadingtxt.setVisibility(View.GONE);

            } else {
                Toast.makeText(getApplicationContext(), "data not found", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                datanf.setVisibility(View.VISIBLE);
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
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            Rect outRect = new Rect();
            linearLayout.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

        }


        return super.dispatchTouchEvent(event);
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
