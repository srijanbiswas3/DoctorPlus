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

     next.setVisibility(View.GONE);
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
    static int index=1;
    @Override
    public void onClick(View v) {

        int j = 0;

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getId() == v.getId()) {
                index = i + 1;
                break;
            }
        }
        next.setVisibility(View.VISIBLE);
        buttons[index - 1].setBackgroundResource(R.drawable.circlebutton2);
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getId() == v.getId()) {
                continue;
            }

            else {
                buttons[i].setBackgroundResource(R.drawable.circlebutton);
            }
        }
        if (next.getId() == v.getId()) {
            buttons[index - 1].setBackgroundResource(R.drawable.circlebutton2);
            Intent intent = new Intent(Recommend.this, Recommend2.class);
            intent.putExtra("id", index);
            intent.putExtra("pname", buttons[index - 1].getHint());
            startActivity(intent);
        }
    }
}



