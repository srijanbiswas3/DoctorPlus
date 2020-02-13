package com.tesseract.DoctorSaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
ImageView roll,tape,logo,name;
Animation rightmove,fadein,lefttoright;
    private final int SPLASH_DISPLAY_TIMER = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        rightmove= AnimationUtils.loadAnimation(this,R.anim.right_move);
        lefttoright= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        fadein= AnimationUtils.loadAnimation(this,R.anim.fadein);
        roll=findViewById(R.id.roll);
        tape=findViewById(R.id.tape);
        logo=findViewById(R.id.logo);
        name=findViewById(R.id.name);
        roll.setAnimation(rightmove);
        tape.setAnimation(lefttoright);
        logo.setAnimation(fadein);
        name.setAnimation(fadein);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        },SPLASH_DISPLAY_TIMER);



    }
}
