package com.example.androidprojectcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn1; // object that will hold the button in our xml — Layout
    Button btn2; // Button Exercise
    Button btn3; // Calculator Exercise
    Button btnPlayTicTacToe; // Midterm Exam TIC TAC TOE
    Button btnMatch3; // Match 3
    Button btnPassingIntents;
    Button btnMenuExercise;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btnLayoutExercise);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(
                        MainActivity.this, // this activity
                        LayoutExercise.class ); // destination class

                startActivity(intent1);
            }
        });

        btn2 = (Button) findViewById(R.id.btnButtonExercise);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(
                        MainActivity.this,
                        ButtonExercise.class);

                startActivity(intent2);
            }
        });

        btn3 = findViewById(R.id.btnCalculatorExercise);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(
                        MainActivity.this,
                        CalculatorExercise.class);
                startActivity(intent3);
            }
        });

        btnPlayTicTacToe = (findViewById(R.id.btnPlayTicTacToe));
        btnPlayTicTacToe.setOnClickListener(e->{
            Intent intent1 = new Intent(
                    MainActivity.this,
                    TicTacToe.class );

            Toast moveToast = Toast.makeText(this, "Charlene Repuesto — TicTacToe", Toast.LENGTH_SHORT);
            moveToast.show();
            startActivity(intent1);
        });

        btnMatch3 = findViewById(R.id.btnMatch3);
        btnMatch3.setOnClickListener(e->{
            Intent intent1 = new Intent(
                    MainActivity.this,
                    Match3.class );

            startActivity(intent1);
        });

        btnPassingIntents = findViewById(R.id.btnPassingIntents);
        btnPassingIntents.setOnClickListener(e -> {
            Intent intent1 = new Intent(
                    MainActivity.this,
                    PassingIntentsExercise.class );

            startActivity(intent1);
        });

        btnMenuExercise = findViewById(R.id.btnMenuExercise);
        btnMenuExercise.setOnClickListener(e -> {
            Intent intent1 = new Intent(
                    MainActivity.this,
                    MenuExercise.class);

            startActivity(intent1);
        });
    }
}