package com.example.androidprojectcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PassingIntentsExercise2 extends AppCompatActivity {
    TextView tFName, tLName, tGender, tCivilStat, tBDate, tBPlace, tHomeAdd, tPNumber, tEmailAdd, tProgram, tYear;
    Button btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_passing_intents_exercise2);

        tFName = findViewById(R.id.tvSPFNameRes);
        tLName = findViewById(R.id.tvSPLNameRes);
        tGender = findViewById(R.id.tvSPGenderRes);
        tCivilStat = findViewById(R.id.tvSPCivilStatusRes);
        tBDate = findViewById(R.id.tvSPBDateRes);
        tBPlace = findViewById(R.id.tvSPBirthPlaceRes);
        tHomeAdd = findViewById(R.id.tvSPHomeAddRes);
        tPNumber = findViewById(R.id.tvSPPNumberRes);
        tEmailAdd = findViewById(R.id.tvSPEmailAddRes);
        tProgram = findViewById(R.id.tvSPProgramRes);
        tYear = findViewById(R.id.tvSPYearLevelRes);

        btnReturn = findViewById(R.id.btnPIReturn);

        Intent intent = getIntent();

        String fName = intent.getStringExtra("fName");
        String lName = intent.getStringExtra("lName");
        String gender = intent.getStringExtra("gender");
        String civilStat = intent.getStringExtra("civilStat");
        String bDate = intent.getStringExtra("bDate");
        String bPlace = intent.getStringExtra("bPlace");
        String hAdd = intent.getStringExtra("homeAdd");
        String pNum = intent.getStringExtra("pNumber");
        String eAdd = intent.getStringExtra("emailAdd");
        String program = intent.getStringExtra("program");
        String year = intent.getStringExtra("yearLevel");

        tFName.setText(fName);
        tLName.setText(lName);
        tGender.setText(gender);
        tCivilStat.setText(civilStat);
        tBDate.setText(bDate);
        tBPlace.setText(bPlace);
        tHomeAdd.setText(hAdd);
        tPNumber.setText(pNum);
        tEmailAdd.setText(eAdd);
        tProgram.setText(program);
        tYear.setText(year);

        btnReturn.setOnClickListener(e->{
            clear();
            finish();
        });
    }

    void clear(){
        tFName.setText("");
        tLName.setText("");
        tGender.setText("");
        tCivilStat.setText("");
        tBDate.setText("");
        tBPlace.setText("");
        tHomeAdd.setText("");
        tPNumber.setText("");
        tEmailAdd.setText("");
        tProgram.setText("");
        tYear.setText("");
    }
}