package com.tesseract.DoctorSaheb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Recommend3 extends AppCompatActivity {
    TextView tips, specialisttxt;
    Toolbar toolbar;
    Button search;
    String specialist = "General Physician";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend3);
        int id = getIntent().getIntExtra("id", 1);
        List<String> list = getIntent().getStringArrayListExtra("list");
        tips = findViewById(R.id.tips);
        search = findViewById(R.id.search);
        specialisttxt = findViewById(R.id.specialisttxt);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String one = "Tips:-\n1.If you have a migraine, place a cold pack on your forehead.\n2.Bright or flickering light, even from your computer screen, can cause migraine headaches.\n3.sleeping in an uncomfortable position can cause neck pain\n4.Have some tea, coffee, or something with a little caffeine in it. If you get it early enough, it could ease your headache pain";

        String two = "Tips:-\n1.Cold compresses can help reduce swelling in the shoulder. Cooling also helps to numb sharp pain.\n2.Heat helps to relax tense muscles and soothe a stiff shoulder\n3.Warm up, even before deep stretching.\n4.Stop or change the activity that may have caused the shoulder pain. It’s important to move the shoulder gently. This helps to keep the shoulder muscles strong and flexible.\n5.If you’re experiencing aches and pains, try avoiding smoking. Smoking can affect blood circulation to the shoulder and body. This can slow recovery.";
        String three = "Tips:-\n1.Popular recommendation for heart pain is to add baking soda to warm or cool water.\n2.Garlic is claimed to be a remedy for chest pain, although there is no science to back this up.\n3.When heart pain strikes, lying down immediately with the head elevated above the body may bring some relief\n4.A hot drink may help to eliminate gas when a person’s pain is due to gas or bloating. The hot liquid can also help boost digestion.\n5.Turmeric has anti-inflammatory properties that may alleviate pain symptoms in the chest.";
        String four = "Tips:-\n1.Being dehydrated makes digestion more difficult and less effective,Drink Water.\n2.When the body is horizontal, the acid in the stomach is more likely to travel backward and move upward, which can cause heartburn.\n3.Ginger is a common natural remedy for an upset stomach and indigestion.\n4.Raw and cooked mint leaves are both suitable for consumption.Sucking on mint candies might be another way to help reduce the pain and discomfort of heartburn.";
        String five = "Tips:-\n1.Pain near your navel can be a sign of appendicitis or something wrong in your small intestine\n2.Make sure that your meals are well-balanced and high in fiber.\n3.Drink Plenty of Water\n4.Rectal pain cause by  hemorrhoids usually cause mild or moderate discomfort, while anal fissures can cause a tearing or knife-like sharp pain.";
        String six = "Tips:-\n1.Too much rest can weaken your muscles, which can worsen joint pain.\n2.Cardio exercises strengthen the muscles that support your knee and increase flexibility.\n3.If you're overweight, losing weight reduces the stress on your knee\n4.Athlete's foot is caused by the same type of fungus that causes ringworm and jock itch. Damp socks and shoes and warm, humid conditions favor the organisms' growth.\n5.In a Fracture, prevent future injuries. This includes building muscle strength around the bone and starting new activities slowly";

        String[] spec = {"ENT Specialist", "Orthopedist", "Pulmonologist", "General Sergeon", "Gastroenterologist", "Colon and Rectal Surgeon", "Podiatrist"};


        if (id == 1) {

            tips.setText(one);
            specialist = spec[0];


        } else if (id == 2 || id == 4) {

            tips.setText(two);
            specialist = spec[1];

        } else if (id == 3) {

            tips.setText(three);
            specialist = spec[2];

        } else if (id == 5 || id == 7 || id == 8 || id == 10) {

            tips.setText(two);
            specialist = spec[3];

        } else if (id == 6) {

            tips.setText(four);
            specialist = spec[4];

        } else if (id == 9) {

            tips.setText(five);
            specialist = spec[5];


        } else {

            tips.setText(six);
            specialist = spec[6];

        }

        specialisttxt.setText(specialist);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Recommend3.this, HomeActivity.class);

                i.putExtra("rflag", 1);
                i.putExtra("specialist", specialist);
                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}
