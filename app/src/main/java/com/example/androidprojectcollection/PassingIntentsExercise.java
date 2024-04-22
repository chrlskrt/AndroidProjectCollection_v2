package com.example.androidprojectcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class PassingIntentsExercise extends AppCompatActivity {
    Button btnClear, btnSubmit;
    EditText etxtFName, etxtLName, etxtBDate, etxtBirthPlace, etxtHomeAdd, etxtPNUmber, etxtEmailAdd;
    RadioGroup radgroupGender, radgroupCivilStatus, radgroupYearLevel;
    RadioButton[] rgGender, rgCivilStat, rgYLevel;
    Spinner spnr_program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_passing_intents_exercise);

        etxtFName = findViewById(R.id.etxtFName);
        etxtLName = findViewById(R.id.etxtLName);
        etxtBDate = findViewById(R.id.etxtBDate);
        etxtBirthPlace = findViewById(R.id.etxtBirthPlace);
        etxtHomeAdd = findViewById(R.id.etxtHomeAdd);
        etxtPNUmber = findViewById(R.id.etxtPNumber);
        etxtEmailAdd = findViewById(R.id.etxtEmailAdd);

        radgroupGender = findViewById(R.id.radgroupGender);
        radgroupCivilStatus = findViewById(R.id.radgroupCivilStat);
        radgroupYearLevel = findViewById(R.id.radgroupYearLevel);

        rgGender = new RadioButton[]{
                findViewById(R.id.radMale), findViewById(R.id.radFemale), findViewById(R.id.radOthers)
        };

        rgCivilStat = new RadioButton[]{
                findViewById(R.id.radSingle), findViewById(R.id.radMarried), findViewById(R.id.radDivorced), findViewById(R.id.radWidowed)
        };

        rgYLevel  = new RadioButton[]{
                findViewById(R.id.rad1), findViewById(R.id.rad2), findViewById(R.id.rad3), findViewById(R.id.rad4), findViewById(R.id.rad5)
        };

        spnr_program = findViewById(R.id.spnr_program);

        btnClear = findViewById(R.id.btnPIClear);
        btnClear.setOnClickListener(e -> {
            start();
        });

        btnSubmit = findViewById(R.id.btnPISubmit);
        btnSubmit.setOnClickListener(e -> {
            String fName = etxtFName.getText().toString();
            String lName = etxtLName.getText().toString();
            String gender;
            int rIndex = radgroupGender.getCheckedRadioButtonId();
            if (rIndex == -1){
                gender = "Unknown";
            } else {
                gender = ((RadioButton) findViewById(rIndex)).getText().toString();
            }

            String cStatus;
            rIndex = radgroupCivilStatus.getCheckedRadioButtonId();
            if (rIndex == -1){
                cStatus = "Unknown";
            } else {
                cStatus = ((RadioButton) findViewById(rIndex)).getText().toString();
            }

            String bDate = etxtBDate.getText().toString();
            String bPlace = etxtBirthPlace.getText().toString();
            String homeAdd = etxtHomeAdd.getText().toString();
            String PNum = etxtPNUmber.getText().toString();
            String emailAdd = etxtEmailAdd.getText().toString();
            String program = spnr_program.getSelectedItem().toString();
            String yearLevel;
            rIndex = radgroupYearLevel.getCheckedRadioButtonId();
            if (rIndex == -1){
                yearLevel = "";
            } else {
                yearLevel = ((RadioButton) findViewById(rIndex)).getText().toString();
            }

            Intent intent = new Intent(PassingIntentsExercise.this, PassingIntentsExercise2.class);
            intent.putExtra("fName", fName );
            intent.putExtra("lName", lName);;
            intent.putExtra("gender", gender);
            intent.putExtra("civilStat", cStatus);
            intent.putExtra("bDate", bDate);
            intent.putExtra("bPlace", bPlace);
            intent.putExtra("homeAdd", homeAdd);
            intent.putExtra("pNumber", PNum);
            intent.putExtra("emailAdd", emailAdd);
            intent.putExtra("program", program);
            intent.putExtra("yearLevel", yearLevel);

            startActivity(intent);
        });

        start();
    }

    private void start() {
        etxtFName.setText("");
        etxtLName.setText("");
        etxtBDate.setText("");
        etxtBirthPlace.setText("");
        etxtHomeAdd.setText("");
        etxtPNUmber.setText("");
        etxtEmailAdd.setText("");

        radgroupGender.clearCheck();
        radgroupCivilStatus.clearCheck();
        radgroupYearLevel.clearCheck();

        spnr_program.setSelection(0);
    }
}