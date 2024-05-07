package com.example.androidprojectcollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

public class MenuExercise extends AppCompatActivity {
    Button btnChanger;
    int colors[] = new int[]{Color.RED, Color.BLUE, Color.MAGENTA, Color.YELLOW};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_exercise);

        btnChanger = findViewById(R.id.btnTransformingButton);
        start();
    }

    public void start(){
        btnChanger.setText("Start");
        btnChanger.setBackgroundColor(Color.rgb(244,196,196));
        btnChanger.setTextColor(Color.WHITE);
        btnChanger.setScaleX(1);
        btnChanger.setScaleY(1);
        btnChanger.setVisibility(View.VISIBLE);
        btnChanger.setRotation(0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu_exercise, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.mItemCBtnColor){
            changeBtnColor();
            Toast.makeText(this, "Change Button Color is clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mItemCBtnSize){
            changeBtnSize();
            Toast.makeText(this, "Change Button Size is clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mItemCBtnText){
            changeBtnText();
            Toast.makeText(this, "Change Button Text is clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mItemCBtnTextColor){
            changeBtnTextColor();
            Toast.makeText(this, "Change Button Text Color is clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mItemCBtnRotation){
//            changeBtnVisibility();
            changeBtnRotation();
        }
        else if (item.getItemId() == R.id.mItemReset){
            start();
            Toast.makeText(this, "Reset Object Item is clicked", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.mItemExit){
            finish();
        }

        return true;
    }

    public void changeBtnRotation(){
        Random r = new Random();
        btnChanger.setRotation(btnChanger.getRotation() + r.nextInt(180));
    }
    public void changeBtnVisibility(){
        if (btnChanger.getVisibility() == View.VISIBLE){
            btnChanger.setVisibility(View.INVISIBLE);
        } else {
            btnChanger.setVisibility(View.VISIBLE);
        }
    }
    public void changeBtnTextColor(){
        Random randomColor = new Random();
        btnChanger.setTextColor(Color.argb(255, randomColor.nextInt(255), randomColor.nextInt(255), randomColor.nextInt(255)));
    }
    public void changeBtnText(){
        String texts[] = new String[]{"HI", "HELLO", "How are you?", "BYE"};
        Random r = new Random();
        int new_text = r.nextInt(4);

        btnChanger.setText(texts[new_text]);
    }
    public void changeBtnSize(){
        Random r = new Random();
        int scale = r.nextInt(4) + 1;
        btnChanger.setScaleY(scale);
        btnChanger.setScaleX(scale);
    }
    public void changeBtnColor(){
        Random randomColor = new Random();
        btnChanger.setBackgroundColor(Color.argb(255, randomColor.nextInt(255), randomColor.nextInt(255), randomColor.nextInt(255)));
    }
}