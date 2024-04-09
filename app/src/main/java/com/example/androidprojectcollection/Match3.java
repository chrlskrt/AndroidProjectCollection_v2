package com.example.androidprojectcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Match3 extends AppCompatActivity {
    int[] colors = new int[]{Color.CYAN, Color.RED, Color.YELLOW, Color.MAGENTA};
    Button[][] matchTiles;
    int[][] tileColors = new int[5][5];
    Button btnRestart;
    TextView tv_score;
    int score;
    ArrayList<Integer> btnClickedRow, btnClickedCol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_match3);

        matchTiles = new Button[][]{
                {findViewById(R.id.btnM00), findViewById(R.id.btnM01), findViewById(R.id.btnM02), findViewById(R.id.btnM03), findViewById(R.id.btnM04)},
                {findViewById(R.id.btnM10), findViewById(R.id.btnM11), findViewById(R.id.btnM12), findViewById(R.id.btnM13), findViewById(R.id.btnM14)},
                {findViewById(R.id.btnM20), findViewById(R.id.btnM21), findViewById(R.id.btnM22), findViewById(R.id.btnM23), findViewById(R.id.btnM24)},
                {findViewById(R.id.btnM30), findViewById(R.id.btnM31), findViewById(R.id.btnM32), findViewById(R.id.btnM33), findViewById(R.id.btnM34)},
                {findViewById(R.id.btnM40), findViewById(R.id.btnM41), findViewById(R.id.btnM42), findViewById(R.id.btnM43), findViewById(R.id.btnM44)}
        };

        btnRestart = findViewById(R.id.btnMRestart);
        btnRestart.setOnClickListener(e -> {
            startGame();
        });

        btnClickedRow = new ArrayList<>();
        btnClickedCol = new ArrayList<>();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                int row = i, col = j;

                matchTiles[row][col].setOnClickListener(e -> {
                    if (btnClickedRow.size() < 2){
                        btnClickedRow.add(row);
                        btnClickedCol.add(col);
                    }

                    if (btnClickedRow.size() == 2){
                        colorSwap();
                    }
                });
            }
        }

        tv_score = findViewById(R.id.tv_MScore);

        startGame();
    }

    private void colorSwap() {
        System.out.println("rows clicked: " + btnClickedRow);
        System.out.println("cols clicked: " + btnClickedCol);
        int row1 = btnClickedRow.get(0), row2 = btnClickedRow.get(1);
        int col1 = btnClickedCol.get(0), col2 = btnClickedCol.get(1);

        if (((Math.abs(row1 - row2) == 1) && (col1 == col2)) || ((Math.abs(col1 - col2) == 1) && (row1 == row2))) {
            if (tileColors[row1][col1] != tileColors[row2][col2]){
                int tempColorIndex = tileColors[row1][col1];
                tileColors[row1][col1] = tileColors[row2][col2];
                tileColors[row2][col2] = tempColorIndex;

                updateTileColors();
            }
        }

        btnClickedRow.clear();
        btnClickedCol.clear();
    }

    void startGame(){
        tv_score.setText("0");
        score = 0;

        btnClickedRow.clear();
        btnClickedCol.clear();

        randomizeColors();
        updateTileColors();
    }

    private void updateTileColors() {
        for (int i= 0; i < 5; i++){
            for (int j = 0; j <5; j++){
                int color = colors[tileColors[i][j]];
                matchTiles[i][j].setBackgroundColor(color);
            }
        }

        checkBoard();
    }

    private void checkBoard() {
        // check rows
        for (int j = 0; j < 5; j++){
            for (int i = 0; i < 3;  i++){
                while (tileColors[j][i] == tileColors[j][i+1] && tileColors[j][i+1] == tileColors[j][i+2]){
                    matchHorizontal(j, i);

                    if (btnClickedRow.size() != 0){
                        score++;
                        tv_score.setText(String.valueOf(score));
                    }
                }
            }
        }

        // check columns
        for (int j = 0; j < 5; j++){
            for (int i = 0; i < 3;  i++){
                while (tileColors[i][j] == tileColors[i+1][j] && tileColors[i+1][j] == tileColors[i+2][j]){
                    matchVertical(i, j);

                    if (btnClickedRow.size() != 0){
                        score++;
                        tv_score.setText(String.valueOf(score));
                    }
                }
            }
        }
    }
    private void matchHorizontal(int row, int col) {
        printBoard();
        Random r = new Random();

        tileColors[row][col] = r.nextInt(4);
        tileColors[row][col+1] = r.nextInt(4);
        tileColors[row][col+2] = r.nextInt(4);

        updateTileColors();
    }
    private void matchVertical(int row, int col) {
        printBoard();
        Random r = new Random();

        tileColors[row][col] = r.nextInt(4);
        tileColors[row+1][col] = r.nextInt(4);
        tileColors[row+2][col] = r.nextInt(4);

        updateTileColors();
    }
    private void randomizeColors() {
        Random r = new Random();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                tileColors[i][j] = r.nextInt(4);
            }
        }
    }

    void printBoard(){
        System.out.println("BOARD NOW:");
        for (int i = 0; i < 5; i++){
            System.out.print("\t");
            for (int j = 0; j < 5; j++){
                System.out.print(tileColors[i][j] + " ");
            }

            System.out.println("");
        }
    }

}