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
    private TextView gameMode,botLetter,defaultLabel,oWinLabel, defaultTotal, oWinTotal;
    private Button playerSwitch, diffSwitch;
    private List<TextView> winTexts = new ArrayList<>();
    private boolean isHardDiff = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        PlayActivity.reset();
        gameMode = findViewById(R.id.gameModeText);
        gameMode.setText(R.string.singlePlayer);
        botLetter = findViewById(R.id.botPlayer);
        botLetter.setVisibility(View.VISIBLE);
        playerSwitch = findViewById(R.id.letterSwitchBtn);
        playerSwitch.setVisibility(View.VISIBLE);
        diffSwitch = findViewById(R.id.diffSwitchBtn);
        diffSwitch.setVisibility(View.VISIBLE);

        defaultLabel = findViewById(R.id.defaultWinText);
        defaultLabel.setText(R.string.defaultSingle);
        oWinLabel = findViewById(R.id.oWinText);
        defaultTotal = findViewById(R.id.totalDefault);
        oWinTotal = findViewById(R.id.totalOwin);
        winTexts.addAll(Arrays.asList(oWinTotal, oWinLabel));
        for (TextView text : winTexts)
            text.setVisibility(View.GONE);

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
//        for (int i = 0; i < BUTTONS.size(); i++) {
//            if (BUTTONS.get(i).getId() == v.getId()) {
//                index = i;
//                break;
//            }
//        }
        if(index != -1 && PlayActivity.grid[index] == 0) {
            PlayActivity.grid[index] = PlayActivity.isXTurn ? 1: -1;
            ((Button)v).setText(PlayActivity.isXTurn ? "X": "O");
            PlayActivity.isXTurn ^= true;
            defaultLabel.setText("Player "+(PlayActivity.isXTurn? "X's": "O's"));
            PlayActivity.turnNum++;
//            if(turnNum >= 4 && winCheck(checkDiagonalSum(index), checkColumnSum(index), checkRowSum(index)))
//                resetButtons();
        }
        else { //toast message indicating you cannot place there
            CharSequence text = "Spot's Taken";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
    public void openHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    } //starts the home page activity
    public void letterChangeClick(View v)
    {
        if(PlayActivity.turnNum == 0)
            PlayActivity.isXTurn^=true;
        //then run robot's turn
    }
    public void diffChangeClick(View v)
    {
        isHardDiff^=true;
        //need add some indicator that it is on a hard mode difficulty
    }
}