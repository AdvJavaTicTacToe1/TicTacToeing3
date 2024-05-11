package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayAIActivity extends AppCompatActivity
{
//    private final List<Button> BUTTONS = new ArrayList<>();
//    private final int[] grid = new int[9];
    private TextView gameMode,botLetter,defaultLabel,oWinLabel, defaultTotal, oWinTotal;
    private Button playerSwitch, diffSwitch;
    private List<TextView> winTexts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
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
    }
    public void openHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    } //starts the home page activity
}