package com.example.alberto.snookerscorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    final int[] buttonsId = new int[7];
    final int[][] statsId = {{R.id.score_1, R.id.score_2}, {R.id.break_1, R.id.break_2}, {R.id.max_break_1, R.id.max_break_2}};

    int[][] stats = new int[3][2];
    int[][] buttonClicks = new int[7][2];
    int playerInUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonsId[0] = R.id.button_red;
        buttonsId[1] = R.id.button_yellow;
        buttonsId[2] = R.id.button_green;
        buttonsId[3] = R.id.button_brown;
        buttonsId[4] = R.id.button_blue;
        buttonsId[5] = R.id.button_pink;
        buttonsId[6] = R.id.button_black;

        display(R.id.score_1,0);
        display(R.id.score_2,0);
    }

    public void changePlayer(View v) {
        if (v.getId() == R.id.score_1) {
            playerInUse = 0;
        } else {
            playerInUse = 1;
        }
        changeButtonPlayer();
    }

    public void changeButtonPlayer(){
        for (int i = 0; i < 7; i++) {
            display(buttonsId[i], buttonClicks[i][playerInUse]);
        }
        stats[1][(playerInUse-1)*-1] = 0;
        display(statsId[1][(playerInUse-1)*-1], 0);
    }

    public void onButtonClick(View v) {
        int buttonColor = 0;
        ToggleButton foulButton = (ToggleButton) findViewById(R.id.toggle_foul);
        boolean foul = foulButton.isChecked();
        while (v.getId() != buttonsId[buttonColor]) {
            buttonColor++;
        }
        if (foul) {
            if (buttonColor >= 3) {
                foulButton.setChecked(false);
                playerInUse = (playerInUse - 1) * - 1;
                RadioGroup groupScore = (RadioGroup) findViewById(R.id.radio_group);
                groupScore.check(statsId[0][playerInUse]);
                changeButtonPlayer();
            }
            else {
                return;
            }
        }
        else {
            buttonClicks[buttonColor][playerInUse]++;
        }
        for (int i = 0; i < 2; i++){
            stats[i][playerInUse] += buttonColor + 1;
        }
        int endDisplay = 2;
        if (stats[1][playerInUse] > stats[2][playerInUse]){
            stats[2][playerInUse] = stats[1][playerInUse];
            endDisplay = 3;
        }
        for (int i = 0; i < endDisplay; i++){
            display(statsId[i][playerInUse], stats[i][playerInUse]);
        }
        display(buttonsId[buttonColor], buttonClicks[buttonColor][playerInUse]);
    }

    public void display(int id, int value) {
        TextView view = (TextView) findViewById(id);
        String s = "";
        if (value > 0 || id == R.id.score_1 || id == R.id.score_2) {
            s = String.valueOf(value);
        }
        view.setText(s);
    }

    public void newGame(View v) {
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                stats[i][j] = 0;
                display(statsId[i][j], 0);
            }
            for (int i = 0; i < 7; i++) {
                buttonClicks[i][j] = 0;
                display(buttonsId[i], 0);
            }
        }
        TextView text = (TextView) findViewById(R.id.player1);
        text.setText(R.string.player_1);
        text = (TextView) findViewById(R.id.player2);
        text.setText(R.string.player_2);
    }
}