package com.tesseract.DoctorSaheb;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DrugListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DrugAdapter recyclerAdapter;

    List<String> drugsList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        drugsList = new ArrayList<>();

        drugsList.add("Abilify");
        drugsList.add("Acetaminophen");
        drugsList.add("Acyclovir");
        drugsList.add("Acetaminophen and Hydrocodone");
        drugsList.add("Actos");
        drugsList.add("Belsomra");
        drugsList.add("Benadryl");
        drugsList.add("Benazepril");
        drugsList.add("Benicar");
        drugsList.add("Carbamazepine");
        drugsList.add("Cardizem");
        drugsList.add("Carvedilol");
        drugsList.add("Demerol");
        drugsList.add("Desloratadine");
        drugsList.add("Darvocet");
        drugsList.add("Demerol");
        drugsList.add("Eliquis");
        drugsList.add("Ecotrin and MDMA");

        drugsList.add("Fentanyl");
        drugsList.add("Famotidine");
        drugsList.add("Farxiga");
        drugsList.add("Gabapentin");
        drugsList.add("Galantamine");
        drugsList.add("Herceptin");
        drugsList.add("Hiprex");
        drugsList.add("Ibrance");
        drugsList.add("Imbruvica");
        drugsList.add("Jakafi");
        drugsList.add("Jalyn");
        drugsList.add("Jantoven");
        drugsList.add("Kadcyla");
        drugsList.add("Kadian");
        drugsList.add("Labetalol");
        drugsList.add("Lactulose");
        drugsList.add("Magnesium citrate");
        drugsList.add("Magnesium oxide");
        drugsList.add("Naproxen");
        drugsList.add("Odefsey");
        drugsList.add("Paracetamol");
        drugsList.add("Paroxetine");
        drugsList.add("Penicillin");
        drugsList.add("Phenazopyridine");
        drugsList.add("Rabeprazole");
        drugsList.add("Salmeterol");
        drugsList.add("Tacrolimus");



        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new DrugAdapter(drugsList);
        setSupportActionBar(toolbar);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerAdapter);

     /*   DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drug_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recyclerAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

