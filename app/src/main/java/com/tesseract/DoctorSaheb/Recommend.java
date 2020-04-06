package com.tesseract.DoctorSaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class Recommend extends AppCompatActivity implements View.OnClickListener {
    //TextView selected;
    Button next;
    Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
      //  selected = findViewById(R.id.selected);
        next = findViewById(R.id.next);
        next.setOnClickListener(this);
     /*   b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b10 = findViewById(R.id.b10);
        b11 = findViewById(R.id.b11);
        b12 = findViewById(R.id.b12);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b10.setOnClickListener(this);
        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
*/
        buttons = new Button[12];


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (int i = 0; i < buttons.length; i++) {
            {
                String buttonID = "b" + (i + 1);

                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i] = ((Button) findViewById(resID));
                buttons[i].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {

        int j = 0;
        int index = 1;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getId() == v.getId()) {
                index = i + 1;


                break;
            }
        }
        buttons[index - 1].setBackgroundResource(R.drawable.circlebutton2);
        for(int i = 0; i < buttons.length; i++)
        {
            if(buttons[i].getId()==v.getId())
            {
                continue;
            }
            else {
                buttons[i].setBackgroundResource(R.drawable.circlebutton);
            }
        }
     if(next.getId()==v.getId())
     {
         Intent intent=new Intent(Recommend.this,Recommend2.class);
         intent.putExtra("id",index);
         intent.putExtra("problem",buttons[index-1].getHint());
         startActivity(intent);
     }
    }
}



