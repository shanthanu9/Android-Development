package com.example.admin.scarnesdice;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int userTotalScore = 0;
    private int userTurnScore = 0;
    private int compTotalScore = 0;
    private int compTurnScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRoll(View view) {
        int valueOnDice = rollDice();
        if(valueOnDice != 1) {
            userTurnScore += valueOnDice;
            updateTextUserTurn();
        }
        else {
            userTurnScore = 0;
            updateTextUserTurn();
        }
    }

    private int rollDice() {
        ImageView image = findViewById(R.id.imageView);
        Random random = new Random();
        int value = random.nextInt(6) + 1;;
        Drawable drawable = null;
        switch(value) {
            case 1:
                drawable = getResources().getDrawable(R.drawable.dice1);
                break;
            case 2:
                drawable = getResources().getDrawable(R.drawable.dice2);
                break;
            case 3:
                drawable = getResources().getDrawable(R.drawable.dice3);
                break;
            case 4:
                drawable = getResources().getDrawable(R.drawable.dice4);
                break;
            case 5:
                drawable = getResources().getDrawable(R.drawable.dice5);
                break;
            case 6:
                drawable = getResources().getDrawable(R.drawable.dice6);
                break;
        }
        image.setImageDrawable(drawable);
        return value;
    }

    public void onReset(View view) {
        userTotalScore = 0;
        compTotalScore = 0;
        userTurnScore = 0;
        compTurnScore = 0;
        updateText();
    }

    public void onHold(View view) {
        userTotalScore += userTurnScore;
        userTurnScore = 0;
        updateText();
    }

    private void updateTextUserTurn() {
        TextView text = findViewById(R.id.textView2);
        text.setText("Your score: " + userTotalScore + " Computer score: " + compTotalScore + " your turn score: " + userTurnScore);
    }

    private void updateText() {
        TextView text = findViewById(R.id.textView2);
        text.setText("Your score: " + userTotalScore + " Computer score: " + compTotalScore);
    }

    private void updateTextCompTurn() {
        TextView text = findViewById(R.id.textView2);
        text.setText("Your score: " + userTotalScore + " Computer score: " + compTotalScore + " computer turn score: " + compTurnScore);
    }

    private void setRollAndHold(boolean state) {
        Button roll = findViewById(R.id.button);
        Button hold = findViewById(R.id.button2);
        roll.setEnabled(state);
        hold.setEnabled(state);
    }

    private void computerTurn() {
        setRollAndHold(false);
        int valueOnDice = rollDice();
        if(valueOnDice != 1) {
            compTurnScore += valueOnDice;
            updateTextCompTurn();
        }
        else {
            compTurnScore = 0;
            updateTextCompTurn();
            Toast.makeText(getApplicationContext(), "Computer roles a one.", Toast.LENGTH_SHORT).show();
            setRollAndHold(true);
            return;
        }
        Handler handler = new Handler();
        handler.postDelayed(this, 500);

    }

}
