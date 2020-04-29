package ge.tsu.android.firsttask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ge.tsu.android.firsttask.data.QuizQuestion;
import ge.tsu.android.firsttask.data.QuizStorage;
import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.StorageImpl;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText mQuestion;
    private List<EditText> mAnswersOptions;
    private EditText mCorrectAnswerNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        mQuestion = findViewById(R.id.question);
        mAnswersOptions = new ArrayList<>();
        mAnswersOptions.add((EditText) findViewById(R.id.answer_option1));
        mAnswersOptions.add((EditText) findViewById(R.id.answer_option2));
        mAnswersOptions.add((EditText) findViewById(R.id.answer_option3));
        mAnswersOptions.add((EditText) findViewById(R.id.answer_option4));
        mCorrectAnswerNum = findViewById(R.id.correct_answer_number);
    }

    public void addQuestion(View view) {
        String question = mQuestion.getText().toString();
        List<String> answerOptions = new ArrayList<>();
        for (EditText answer : mAnswersOptions) {
            answerOptions.add(answer.getText().toString());
        }
        String correctAnswerNum = mCorrectAnswerNum.getText().toString();

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestion(question);
        quizQuestion.setOptions(answerOptions);
        quizQuestion.setCorrectAnswerNumber(correctAnswerNum);

        Storage storage = new StorageImpl();
        Object storageAsObject = storage
                .getObject(this, QuizStorage.QUIZ_STORAGE_KEY, QuizStorage.class);

        QuizStorage quizStorage;
        if (storageAsObject != null) {
            quizStorage = (QuizStorage) storageAsObject;
        } else {
            quizStorage = new QuizStorage();
        }

        quizStorage.getQuestions().add(quizQuestion);
        storage.add(this, QuizStorage.QUIZ_STORAGE_KEY, quizStorage);

        finish();
    }

}
