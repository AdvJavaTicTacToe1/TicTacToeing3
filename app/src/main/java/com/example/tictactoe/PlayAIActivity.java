package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayAIActivity extends AppCompatActivity implements View.OnClickListener
{
    private final List<Button> BUTTONS = new ArrayList<>();
    private final int[] grid = new int[9];
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
        for (Button button : BUTTONS) button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        CharSequence text = "Button Clicked";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
    public void openHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    } //starts the home page activity
    public void letterChangeClick(View v)
    {
        CharSequence text = "Change Letter Clicked";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
    public void diffChangeClick(View v)
    {
        CharSequence text = "Hard Mode Clicked";
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}