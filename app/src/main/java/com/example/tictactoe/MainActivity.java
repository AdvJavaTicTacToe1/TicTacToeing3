package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void openInstructions(View v)
    {
        Intent intent = new Intent(this, InstructionsActivity.class);
        finish();
        this.startActivity(intent);
    }
    public void openPlayScreen(View v)
    {
        Intent intent = new Intent(this, PlayActivity.class);
        finish();
        this.startActivity(intent);
    }
     public void openPlayAIScreen(View v)
     {
         Intent intent = new Intent(this, PlayAIActivity.class);
         finish();
         this.startActivity(intent);
     }
}