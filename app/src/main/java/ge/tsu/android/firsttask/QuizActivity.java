package ge.tsu.android.firsttask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ge.tsu.android.firsttask.data.QuizStorageSharePreferenceImpl;
import ge.tsu.android.firsttask.data.Storage;

public class QuizActivity extends AppCompatActivity {

    private EditText question1;
    private EditText question2;
    private EditText question3;
    private EditText question4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
    }

    public void submit(View view) {
        String questionAnswer1 = question1.getText().toString().trim();
        String questionAnswer2 = question2.getText().toString().trim();
        String questionAnswer3 = question3.getText().toString().trim();
        String questionAnswer4 = question4.getText().toString().trim();
        int countCorrect = 0;
        if (questionAnswer1.equals(getString(R.string.question1_answer))) {
            countCorrect++;
        }
        if (questionAnswer2.equals(getString(R.string.question2_answer))) {
            countCorrect++;
        }
        if (questionAnswer3.equals(getString(R.string.question3_answer))) {
            countCorrect++;
        }
        if (questionAnswer4.equals(getString(R.string.question4_answer))) {
            countCorrect++;
        }
        Storage storage = new QuizStorageSharePreferenceImpl();
        String user = getIntent().getStringExtra("username");
        storage.save(this, user, Integer.toString(countCorrect));
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("score", Integer.toString(countCorrect));
        setResult(10001, intent);
        finish();
    }
}
