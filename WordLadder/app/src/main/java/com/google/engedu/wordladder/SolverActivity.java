package com.google.engedu.wordladder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.google.engedu.worldladder.R;

public class SolverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_solver);

        Bundle b = this.getIntent().getExtras();
        String[] words = b.getStringArray("hello");

        TextView startWord = (TextView)findViewById(R.id.startTextView);
        startWord.setText(words[0]);
        TextView endWord = (TextView)findViewById(R.id.endTextView);
        endWord.setText(words[words.length - 1]);

        LinearLayout lv = (LinearLayout)findViewById(R.id.linearLayout);

        for(int i = 1; i <= words.length - 2; i++) {
            EditText wordsInPath = new EditText(this);
            /*LinearLayout.LayoutParams editTextDetails = new LinearLayout.LayoutParams(
               LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ) ;*/
            wordsInPath.setId(i);
            lv.addView(wordsInPath);
        }
    }

    public void onSolve(View v) {
        Bundle b = this.getIntent().getExtras();
        String[] words = b.getStringArray("hello");

        for(int i = 1; i <= words.length - 2; i++) {
            EditText wordsInPath = (EditText)findViewById(i);
            wordsInPath.setText(words[i]);
        }

    }

    /*public void onRetry(View view) {
        Intent intent = new Intent(this, WordSelectionActivity.class);
        startActivity(intent);
    }*/
}
