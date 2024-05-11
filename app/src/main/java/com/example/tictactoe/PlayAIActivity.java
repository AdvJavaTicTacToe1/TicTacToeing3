package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlayAIActivity extends AppCompatActivity
{
//    private final List<Button> BUTTONS = new ArrayList<>();
//    private final int[] grid = new int[9];
    private TextView gameMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        gameMode = findViewById(R.id.gameModeText);
        gameMode.setText(R.string.singlePlayer);
    }
    public void openHome(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    } //starts the home page activity
}