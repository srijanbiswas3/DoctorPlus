package com.tesseract.DoctorSaheb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Recommend2 extends AppCompatActivity {
    TextView name, tips;
    Toolbar toolbar;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend2);
        name = findViewById(R.id.name);
        tips = findViewById(R.id.tips);
        next = findViewById(R.id.next);
        int id = getIntent().getIntExtra("id", 1);
        String pname = getIntent().getStringExtra("pname");
        name.setText(pname);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(pname);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int n;
        String[] head = {"Headache", "Migraine", "Fever", "Neck", "Nausea", "Vomiting", "Pain in eyes", "Dizziness", "Stroke", "Blackout", "Not Sure"};
        String[] shoulder = {"Reduced Movement", "Pain", "Sensations of pins and needles", "Burning sensation", "Socket related Problem", "Not Sure"};
        String[] chest = {"Lightheadedness", "Short of Breath", "nausea", "Stroke", "Compresson fracture", "Broken Ribs", "Not Sure"};
        String[] hand = {"Bleeding or Bruising", "Bone Fracture", "Difficultu in moving hands", "Swelling,Redness,Warmth", "Joint Dislocation", "Not Sure"};
        String[] stomach = {"Stomach ache", "Food Poisining", "Abdominal pain", "Vomiting", "Nausea", "Tarry Stool", "Appetite loss", "Indigestion", "Not Sure"};
        String[] abdomen = {"Nausea", "Cramping", "Bloating", "Gas", "Vomiting", "Constipation", "Menstual pain", "Ulcer", "Not sure"};
        String[] leg = {"Weakness", "Numbness", "Throbbing", "Cramps", "Aching", "Joint Pain", "Knee pain", "Infection", "Rupture of Ligaments or tendons", "Tears in Cartilage", "Not Sure"};


        String one = "Tips:-\n1.If you have a migraine, place a cold pack on your forehead.\n2.Bright or flickering light, even from your computer screen, can cause migraine headaches.\n3.sleeping in an uncomfortable position can cause neck pain";

        String two = "Tips:-\n1.Cold compresses can help reduce swelling in the shoulder. Cooling also helps to numb sharp pain.\n2.Heat helps to relax tense muscles and soothe a stiff shoulder\n3.Warm up, even before deep stretching.";
        String three = "Tips:-\n1.Popular recommendation for heart pain is to add baking soda to warm or cool water.\n2.Garlic is claimed to be a remedy for chest pain, although there is no science to back this up.\n3.When heart pain strikes, lying down immediately with the head elevated above the body may bring some relief";
        String four = "Tips:-\n1.Being dehydrated makes digestion more difficult and less effective,Drink Water.\n2.When the body is horizontal, the acid in the stomach is more likely to travel backward and move upward, which can cause heartburn.\n3.Ginger is a common natural remedy for an upset stomach and indigestion.";
        String five = "Tips:-\n1.Pain near your navel can be a sign of appendicitis or something wrong in your small intestine\n2.Make sure that your meals are well-balanced and high in fiber.\n3.Drink Plenty of Water";
        String six="Tips:-\n1.Too much rest can weaken your muscles, which can worsen joint pain.\n2.Cardio exercises strengthen the muscles that support your knee and increase flexibility.\n3.If you're overweight, losing weight reduces the stress on your knee";
        if (id == 1) {
            n = head.length;
            tips.setText(one);

        } else if (id == 2 || id == 4) {
            n = shoulder.length;
            tips.setText(two);
        } else if (id == 3) {
            n = chest.length;
            tips.setText(three);
        } else if (id == 5 || id == 7 || id == 8 || id == 10) {
            n = hand.length;
            tips.setText(two);
        } else if (id == 6) {
            n = stomach.length;
            tips.setText(four);
        } else if (id == 9) {
            n = abdomen.length;
            tips.setText(five);

        } else {
            n = leg.length;
            tips.setText(six);
        }


        LinearLayout linear = findViewById(R.id.linear);


        for (int i = 0; i < n; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setTextSize(17);
            cb.setTextColor(Color.DKGRAY);
            cb.setHighlightColor(Color.BLUE);
            if (id == 1) {
                cb.setText(head[i]);
            } else if (id == 2 || id == 4) {
                cb.setText(shoulder[i]);
            } else if (id == 3) {
                cb.setText(chest[i]);
            } else if (id == 5 || id == 7 || id == 8 || id == 10) {
                cb.setText(hand[i]);
            } else if (id == 6) {
                cb.setText(stomach[i]);
            } else if (id == 9) {
                cb.setText(abdomen[i]);
            } else {
                cb.setText(leg[i]);
            }


            linear.addView(cb);
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Recommend2.this,Recommend3.class);
               // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });


    }
}
