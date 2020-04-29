package ge.tsu.android.firsttask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ge.tsu.android.firsttask.data.QuizQuestion;
import ge.tsu.android.firsttask.data.QuizStorage;
import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.StorageImpl;

public class AddQuestionActivity extends AppCompatActivity {

  private EditText mQuestion;
  private EditText mAnswer;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_question);
    mQuestion = findViewById(R.id.question);
    mAnswer = findViewById(R.id.answer);

  }

  public void addQuestion(View view) {
    String question = mQuestion.getText().toString();
    String answer = mAnswer.getText().toString();

    QuizQuestion quizQuestion = new QuizQuestion();
    quizQuestion.setQuestion(question);
    quizQuestion.setAnswer(answer);

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
