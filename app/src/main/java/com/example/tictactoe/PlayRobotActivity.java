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
    private boolean hasTied = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        PlayActivity.reset();
        //title
        gameMode = findViewById(R.id.gameModeText);
        gameMode.setText(R.string.singlePlayer);
        //options buttons
        playerSwitch = findViewById(R.id.letterSwitchBtn);
        playerSwitch.setVisibility(View.VISIBLE);
        diffSwitch = findViewById(R.id.diffSwitchBtn);
        diffSwitch.setVisibility(View.VISIBLE);
        //turn indicators textViews
        botLetter = findViewById(R.id.botPlayer);
        botLetter.setVisibility(View.VISIBLE);
        playerLetter = findViewById(R.id.currentPlayer);
        //score textViews
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
                    PlayActivity.checkColumnSum(index), PlayActivity.checkRowSum(index))) {
                nextGame();
            }
            else botTurn(index);
        }
        else { //toast message indicating you cannot place there
            CharSequence text = "Spot's Taken";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
    private boolean winCheck(int a, int b, int c) //a, b, and c are the possible winning sums
    { //return boolean if game is finished it's true

        //NOTE: change turn indicators when game finishes,
        // should keep letters but swap player and bot turn precedence

        //(on wincheck) if they tie the game should flip letter starts
        //            if someone wins they take next first

        int win = Math.abs(a) == 3? a:
                Math.abs(b) == 3 ? b:
                Math.abs(c) == 3? c: -1;
        if(win != -1) {
            CharSequence text = win < 0 ? "O's Win!": "X's Win!";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(PlayActivity.turnNum >= 9) {
            Toast.makeText(this, "Tie Game", Toast.LENGTH_LONG).show();
            hasTied = true;
            return true;
        }
        else return false;
    }
    private void nextGame()
    {
        if(!hasTied) {
            if (PlayActivity.turnNum %2 != 0 == PlayActivity.isXTurn)
                defaultTotal.setText((Integer.parseInt(defaultTotal.getText().toString()) + 1) + "");
            else
                botTotal.setText((Integer.parseInt(botTotal.getText().toString()) + 1) + "");
        }
        hasTied = false;
        PlayActivity.grid = new int[9];
        PlayActivity.turnNum = 0;
        for (Button btn : BUTTONS)
            btn.setText("~");
    }
    private void botTurn(int index)
    {
        PlayActivity.turnNum++;
        int botIndex = -1;
        if(isHardDiff)
        {
            if(PlayActivity.turnNum > 2) {
                if(PlayActivity.checkDiagonalSum(index) == 2) {
                    if(index == 4) {
                        int nonBlank = 0;
                        //check corners
                        while (PlayActivity.grid[nonBlank] == 0) nonBlank+= nonBlank == 2 ? 4: 2;
                        botIndex= 8 -nonBlank;
                    }
                    else if(PlayActivity.grid[4] == 0)
                    {
                        botIndex = 4;
                    }
                    else {
                        int endD = (index % 4 == 0) ? 9 : 7;
                        int addD = (endD == 9) ? 4 : 2;
                        for (int i = index % 4; i < endD; endD += addD) {
                            if (PlayActivity.grid[i] == 0) {
                                botIndex = i;
                                break;
                            }
                        }
                    }
                    //this leaves a hole in the hard diff bot,
                    //only if you take the center and play a diagonal
                } //end of partical digaonal case
                else if(PlayActivity.checkRowSum(index) == 2) {
                    int startR = index<3? 0: index<6?3:6;
                    for(int j = startR; j< startR+3; j++) {
                        if (PlayActivity.grid[j] == 0) {
                            botIndex = j;
                            break;
                        }
                    }
                }
                else if(PlayActivity.checkColumnSum(index) == 2) {
                    int startC = index%3;
                    for(int k = startC; k< startC+7; k+=3) {
                        if (PlayActivity.grid[k] == 0) {
                            botIndex = k;
                            break;
                        }
                    }
                }
            } //end of turn num if
        } //end of hard diff
        if(botIndex == -1) botIndex = nextRandom();
        //NOTE: make sure the botIndex you set is not one that already exists,
        // otherwise you will override existing tile

        PlayActivity.grid[botIndex] = PlayActivity.isXTurn ? -1: 1;
        BUTTONS.get(botIndex).setText(PlayActivity.isXTurn? "O": "X");

//        uncomment these lines after adding both difficulties
//        adds the winCheck after botTurn
        if(PlayActivity.turnNum >= 4 && winCheck(PlayActivity.checkDiagonalSum(botIndex),
                PlayActivity.checkColumnSum(botIndex), PlayActivity.checkRowSum(botIndex))) {
            nextGame();
        }
    }
    private void botStart()
    {
        int addIndex = (Math.random()*9 <= 4)? (int)(Math.random() * 8): 4;
        PlayActivity.grid[addIndex] = PlayActivity.isXTurn? -1: 1;
        BUTTONS.get(addIndex).setText(PlayActivity.isXTurn? "O": "X");
        PlayActivity.turnNum++;
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
            if(!PlayActivity.isXTurn) {
                botLetter.setText("Bot X's START");
                botStart();
            }
            else {
                botLetter.setText("Bot O's");
            }
        }
        else {
            Toast.makeText(this, "Finish Current Game First.", Toast.LENGTH_SHORT).show();
        }
    }
    public void diffChangeClick(View v)
    {
        isHardDiff^=true;
        CharSequence text = isHardDiff ? "Hard Mode Enabled": "Hard Mode Disabled";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        //optionally add some other indicator that game is in hard mode eg. UI changes color
    }
    public int nextRandom()
    {
        int i = (int)(Math.random()*8);
        while(PlayActivity.grid[i]!= 0) i = (int)(Math.random()*8);
        return i;
    }
}