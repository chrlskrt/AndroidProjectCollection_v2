package com.example.androidprojectcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class TicTacToe extends AppCompatActivity {
    boolean endGame;
    boolean isPlayer1;
    Button[][] ticTacTiles;
    Button btnRestart;
    int[][] ticTacEquivalent;
    char[] ticTacLetters = new char[]{'O', 'X'};
    TextView tv_player_turn;
    TableLayout tb_TicTac;
    int row1 = 0, row2 = 0, row3 = 0, col1 = 0, col2 = 0, col3 = 0, diagToRight = 0, diagToLeft = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_tic_tac_toe);

        ticTacTiles = new Button[][]{
                {findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3)},
                {findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6)},
                {findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9)}
        };

        tv_player_turn = findViewById(R.id.tv_player_turn);
        tb_TicTac = findViewById(R.id.tb_ticTac);

        btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(e -> {
            start();
        });

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                int row = i, col = j;
                ticTacTiles[i][j].setOnClickListener(e -> {
                    boolean firstClick = ticTacEquivalent[row][col] == -1;
                    if (firstClick && !endGame){
                        int ticTac_index;
                        if (isPlayer1){
                            ticTac_index = 0;
                        } else {
                            ticTac_index = 1;
                        }

                        ticTacEquivalent[row][col] = ticTac_index;
                        updateTicTacTiles();
                        updateGameStat(row, col, ticTac_index);

                        int checkEnd = checkGame(); // 1 - win, 0 - draw, -1 - not end;

                        if (checkEnd == 1){
                            String text;
                            if (isPlayer1){
                                text = "Player O WON !!!";
                            } else {
                                text = "Player X WON !!!";
                            }

                            Toast winToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
                            winToast.show();
                        } else if (checkEnd == 0){
                            Toast drawToast = Toast.makeText(this, "IT\'S A DRAW", Toast.LENGTH_SHORT);
                            drawToast.show();
                        } else {
                            isPlayer1 = !isPlayer1;

                            if (isPlayer1){
                                tv_player_turn.setText("Player O's turn");
                                tb_TicTac.setBackgroundColor(Color.BLUE);
                            } else {
                                tv_player_turn.setText("Player X's turn");
                                tb_TicTac.setBackgroundColor(Color.RED);
                            }
                        }
                    }
                });
            }
        }

        start();
    }

    private void updateGameStat(int row, int col, int ticTac_index) {
        int toAdd = (ticTac_index == 0) ? -1 : 1;
        if (row == 0){
            row1 += toAdd;
        } else if (row == 1){
            row2 += toAdd;
        } else {
            row3 += toAdd;
        }

        if (col == 0){
            col1 += toAdd;
        } else if (col == 1){
            col2 += toAdd;
        } else {
            col3 += toAdd;
        }

        if (row == 0 && col == 0){
            diagToRight += toAdd;
        } else if (row == 1 && col == 1){
            diagToRight += toAdd;
            diagToLeft += toAdd;
        } else if (row == 0 && col == 2){
            diagToLeft += toAdd;
        } else if (row == 2 && col == 0){
            diagToLeft += toAdd;
        } else if (row == 2 && col == 2){
            diagToRight += toAdd;
        }
    }

    private int checkGame() {
        boolean isDraw = true;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (ticTacEquivalent[i][j] == -1){
                    isDraw = false;
                }
            }
        }

        // 1 - win, 0 - draw, -1 - not end;
        boolean checkWin = false;

        if (row1 == 3 || row1 == -3 || row2 == 3 || row2 == -3 || row3 == 3 || row3 == -3 || col1 == 3 || col1 == -3 || col2 == 3 || col2 == -3 || col3 == 3 || col3 == -3
                || diagToRight == 3 || diagToRight == -3 || diagToLeft == 3 || diagToLeft == -3){
            checkWin = true;
        }

        if (checkWin){
            endGame = true;
            return 1;
        }

        if (isDraw){
            endGame = true;
            return 0;
        }

        return -1;
    }

    public void start(){
        endGame = false;
        ticTacEquivalent = new int[][]{{-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1}};
        isPlayer1 = true;

        updateTicTacTiles();
        tb_TicTac.setBackgroundColor(Color.BLUE);
        tv_player_turn.setText("Player O's turn");

        row1 = row2 = row3 = col1 = col2 = col3 = diagToRight = diagToLeft = 0;
    }

    private void updateTicTacTiles() {
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                int index_letter = ticTacEquivalent[i][j];
                if (index_letter != -1){
                    String ch = String.valueOf(ticTacLetters[index_letter]);
                    ticTacTiles[i][j].setText(ch);
                } else {
                    ticTacTiles[i][j].setText(" ");
                }
            }
        }
    }
}