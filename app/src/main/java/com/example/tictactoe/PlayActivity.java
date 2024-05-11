package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener
{
    private final List<Button> BUTTONS = new ArrayList<>();
    public static int[] grid = new int[9];
    private TextView oWinsText;
    private TextView xWinsText;
    private TextView playerText;
    private TextView gameMode;
    private boolean isXTurn;
    public static int turnNum = 0;
    public static int winSum = 0;
    public static int startIndex = 0;
    public static int endIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        gameMode = findViewById(R.id.gameModeText);
        gameMode.setText(R.string.twoPlayer);

        playerText = findViewById(R.id.currentPlayer);
        oWinsText = findViewById(R.id.totalOwin);
        xWinsText = findViewById(R.id.totalDefault);
        oWinsText.setText(0 + "");
        xWinsText.setText(0 + "");
        grid = new int[9];
        turnNum = 0;
        isXTurn = true;

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
        int index = -1; // Default to -1 if not found
        for (int i = 0; i < BUTTONS.size(); i++) {
            if (BUTTONS.get(i).getId() == v.getId()) {
                index = i;
                break;
            }
        }
        if(grid[index] == 0) { //set the "grid" to -1 (for O's) or 1 (for X's)
            grid[index] = isXTurn? 1: -1;
            BUTTONS.get(index).setText(isXTurn ? "X": "O");
            isXTurn ^= true;
            playerText.setText("Player "+(isXTurn? "X's": "O's"));
            turnNum++;
            if(turnNum >= 4) {
                if(winCheck(checkDiagonalSum(index), checkColumnSum(index), checkRowSum(index))) resetButtons();
            }
        }
        else { //toast message indicating you cannot place there
            CharSequence text = "Spot's Taken";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }
    private boolean winCheck(int sum1, int sum2, int sum3)
    {
        int win = Math.abs(sum1) == 3? sum1:(Math.abs(sum2) == 3 ? sum2:(Math.abs(sum3) == 3? sum3: -1));
        if(Math.abs(win) == 3) {
            CharSequence text = "";
            if(win < 0) {
                text+= "O's Wins!";
                oWinsText.setText((Integer.parseInt(oWinsText.getText().toString())+1)+"");//I did this instead of having global int lol
            }
            else {
                text+= "X's Wins!";
                xWinsText.setText((Integer.parseInt(xWinsText.getText().toString())+1)+"");
            }
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
            return true;
        }
        else if(turnNum >= 9) {
            Toast.makeText(this, "Tie Game", Toast.LENGTH_LONG).show();
            resetButtons();
            return true;
        }
        else return false;
    }
    private void resetButtons()
    {
        grid = new int[9];
        if(isXTurn) playerText.setText("Player O's START");
        else playerText.setText("Player X's START");
        isXTurn ^= true;
        turnNum = 0;
        for (Button btn : BUTTONS) {
            btn.setText("~");
        }
    }
    public void openHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    } //starts the home page activity
    private static int checkDiagonalSum(int gid) //grid index
    {
        winSum = 0;
        if(gid%2 ==0) {
            startIndex = gid%4;
            endIndex = startIndex == 0 ? 8: 6;
            int add = startIndex == 0 ? 4: 2;
            for(int i = startIndex; i <= endIndex; i+=add)
                winSum+=grid[i];
        }
        if(gid == 4) { //check the second case for center
            int temp = 0;
            for(int i = 2; i < 7; i+=2)
                temp+=grid[i];
            if(Math.abs(winSum)!=3) winSum = temp;
        }
        return winSum;
    }
    public static int checkColumnSum(int gid) //grid index
    {
        winSum = 0;
        startIndex = gid %3;
        for(int i = startIndex; i <= startIndex+6; i+=3)
            winSum += grid[i];
        return winSum;
    }
    private static int checkRowSum(int gid) //grid index
    {
        winSum = 0;
        startIndex = gid<3? 0: gid<6?3:6;
        for(int i = startIndex; i <= startIndex+2; i++)
            winSum += grid[i];
        return winSum;
    }
}