package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayRobotActivity extends AppCompatActivity implements View.OnClickListener
{
    private final List<Button> BUTTONS = new ArrayList<>();
    private TextView gameMode,botLetter,defaultLabel,botLabel, defaultTotal, botTotal, playerLetter;
    private Button playerSwitch, diffSwitch;
    private List<TextView> winTexts = new ArrayList<>();
    private boolean isHardDiff = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        PlayActivity.reset();
        //title
        gameMode = findViewById(R.id.gameModeText);
        gameMode.setText(R.string.singlePlayer);
        //buttons
        playerSwitch = findViewById(R.id.letterSwitchBtn);
        playerSwitch.setVisibility(View.VISIBLE);
        diffSwitch = findViewById(R.id.diffSwitchBtn);
        diffSwitch.setVisibility(View.VISIBLE);
        //turn indicators
        botLetter = findViewById(R.id.botPlayer);
        botLetter.setVisibility(View.VISIBLE);
        playerLetter = findViewById(R.id.currentPlayer);

        defaultLabel = findViewById(R.id.defaultWinText);
        defaultLabel.setText(R.string.defaultSingle);
        botLabel = findViewById(R.id.oWinText);
        botLabel.setText(R.string.botWinLabel);
        defaultTotal = findViewById(R.id.totalDefault);
        botTotal = findViewById(R.id.totalOwin);

        Button buttonTL = findViewById(R.id.btn0); //TopLeft
        Button buttonTC = findViewById(R.id.btn1); //TopCenter
        Button buttonTR = findViewById(R.id.btn2); //TopRight
        Button buttonML = findViewById(R.id.btn3); //MidLeft
        Button buttonMC = findViewById(R.id.btn4); //MidCenter
        Button buttonMR = findViewById(R.id.btn5); //MidRight
        Button buttonBL = findViewById(R.id.btn6); //BotLeft
        Button buttonBC = findViewById(R.id.btn7); //BotCenter
        Button buttonBR = findViewById(R.id.btn8); //BotRight
        BUTTONS.addAll(Arrays.asList(buttonTL, buttonTC, buttonTR, buttonML, buttonMC, buttonMR, buttonBL, buttonBC, buttonBR));
        for (Button button : BUTTONS)
            button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        int index = BUTTONS.indexOf(v); // Default to -1 if not found
        if(index != -1 && PlayActivity.grid[index] == 0) {
            if(PlayActivity.turnNum == 0)
                playerLetter.setText("Player " + (PlayActivity.isXTurn ? "X's": "O's"));
            PlayActivity.grid[index] = PlayActivity.isXTurn ? 1: -1;
            ((Button)v).setText(PlayActivity.isXTurn ? "X": "O");
            PlayActivity.turnNum++;
            if(PlayActivity.turnNum >= 4 && winCheck(PlayActivity.checkDiagonalSum(index),
                    PlayActivity.checkColumnSum(index), PlayActivity.checkRowSum(index)))
                nextGame();
            else botTurn();
        }
        else { //toast message indicating you cannot place there
            CharSequence text = "Spot's Taken";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
    private boolean winCheck(int a, int b, int c) //a, b, and c are the possible winning sums
    { //return boolean if game is finished it's true
        int win = Math.abs(a) == 3? a:
                Math.abs(b) == 3 ? b:
                Math.abs(c) == 3? c: -1;
        if(win != -1) {
            CharSequence text = win < 0 ? "O's Win!": "X's Win!";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            //need to set the turn indicators to correct turn
            return true;
        }
        else if(PlayActivity.turnNum >= 9) {
            Toast.makeText(this, "Tie Game", Toast.LENGTH_LONG).show();
            nextGame();
            return true;
        }
        else return false;
    }
    private void nextGame()
    {
        if(PlayActivity.turnNum%2 !=0 == PlayActivity.isXTurn) {
            defaultTotal.setText((Integer.parseInt(defaultTotal.getText().toString())+1)+"");
        }
        else {
            botTotal.setText((Integer.parseInt(botTotal.getText().toString())+1)+"");
        }
        PlayActivity.grid = new int[9];
        PlayActivity.turnNum = 0;
        for (Button btn : BUTTONS)
            btn.setText("~");
    }
    private void botTurn()
    {
        PlayActivity.turnNum++;
        int botIndex;
        if(isHardDiff)
        {
            CharSequence text = "Hard Bot Turn";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();

        }
        else {
            //add the easy diff code in here I guess
        }
//        if(PlayActivity.turnNum >= 4 && winCheck(PlayActivity.checkDiagonalSum(botIndex),
//                PlayActivity.checkColumnSum(botIndex), PlayActivity.checkRowSum(botIndex)))
//            nextGame();
        //add the winCheck after botTurn is able to find an index
    }
    public void openHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    } //starts the home page activity
    public void letterChangeClick(View v)
    {
        if(PlayActivity.turnNum == 0) {
            PlayActivity.isXTurn ^= true;
            playerLetter.setText("Player " + (PlayActivity.isXTurn ? "X's START": "O's"));
            if(!PlayActivity.isXTurn)
            {
                botLetter.setText("Bot X's START");
                botTurn();
            }
            else {
                botLetter.setText("Bot O's");
            }
        }
        else {
            CharSequence text = "Finish Game First.";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
    public void diffChangeClick(View v)
    {
        isHardDiff^=true;
        if(isHardDiff) Toast.makeText(this, "Hard Mode Enabled", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Hard Mode Disabled", Toast.LENGTH_SHORT).show();
        //need add some indicator that it is on a hard mode difficulty
        // eg. colors on screen change, sound, or like idk a toast is prolly fine
    }
}