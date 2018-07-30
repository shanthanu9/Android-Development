package com.example.admin.ghost;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText textInput;
    private TextView output, cGhost, pGhost;
    private final String ghost = "GHOST";
    private WordDictionary dictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("english3.txt");
            dictionary = new WordDictionary(new InputStreamReader(inputStream));
        } catch(IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        textInput = findViewById(R.id.editText);
        output =  findViewById(R.id.textView3);
        cGhost =  findViewById(R.id.textView);
        pGhost =  findViewById(R.id.textView2);

    }

    public void compMove(View view) {
        String input = textInput.getText().toString();
        textInput.setText("");
        if(input.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter the letter in the given block...", Toast.LENGTH_SHORT).show();
        }
        else {
            String newText = output.getText().toString() + input;
            output.setText(newText);

            //to check if user entered a word
            if(!(dictionary.isWord(newText))) {
                if(dictionary.isBeginOFSomeWord(newText)) {
                    String compLetter =  dictionary.bestLetterChoice(newText);
                    output.setText(newText + compLetter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Computer catches your bluff!! No word begins with " + newText + ".", Toast.LENGTH_SHORT).show();
                    playerLose();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Computer catches your bluff!! " + newText + " is a word.", Toast.LENGTH_SHORT).show();
                playerLose();
            }

        }
    }

    public void onPlayerChallenge(View view) {
        String value = output.getText().toString();
        if(dictionary.isWord(value)) {
            Toast.makeText(getApplicationContext(), "Well done!! " + value  + " is a word.", Toast.LENGTH_SHORT).show();
            compLose();
        }
        else if(!dictionary.isBeginOFSomeWord(value)) {
            Toast.makeText(getApplicationContext(), "Well done!! No word begins with " + value + ".", Toast.LENGTH_SHORT).show();
            compLose();
        }
        else {
            String someWord = dictionary.someWordBeginWith(value);
            Toast.makeText(getApplicationContext(), "Wrong Challenge!! " + someWord + " is a word beginning with " + value + ".", Toast.LENGTH_SHORT).show();
            playerLose();
        }
    }

    private int itC = 0, itP = 0;

    private void compLose() {
        if(itC == ghost.length() - 1) {
            Toast.makeText(getApplicationContext(), "Congratulations!!! You Win!", Toast.LENGTH_SHORT).show();
        }
        String value = cGhost.getText().toString() + ghost.charAt(itC);
        cGhost.setText(value);
        if(itC != ghost.length() - 1) {
            itC++;
        }
        reset();
    }

    private void playerLose() {
        if(itP == ghost.length() - 1) {
            Toast.makeText(getApplicationContext(), "GHOST!!! You Lose!", Toast.LENGTH_SHORT).show();
        }
        String value = pGhost.getText().toString() + ghost.charAt(itP);
        pGhost.setText(value);
        if(itP != ghost.length() - 1) {
            itP++;
        }
        reset();
    }

    private void reset() {
        output.setText("");
    }
}
