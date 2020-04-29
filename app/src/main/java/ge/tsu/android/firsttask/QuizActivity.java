package ge.tsu.android.firsttask;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ge.tsu.android.firsttask.data.QuizQuestion;
import ge.tsu.android.firsttask.data.QuizStorage;
import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.StorageImpl;

public class QuizActivity extends AppCompatActivity {

  private ListView mQuestions;
  private QuestionArrayAdapter questionArrayAdapter;
  private String[] mAnswers;
  private ArrayList<QuizQuestion> quizQuestions;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
    mQuestions = findViewById(R.id.questions);
    questionArrayAdapter = new QuestionArrayAdapter(this, 0, new ArrayList<QuizQuestion>());
    mQuestions.setAdapter(questionArrayAdapter);

    Storage storage = new StorageImpl();
    Object quizStorageAsObject = storage
      .getObject(this, QuizStorage.QUIZ_STORAGE_KEY, QuizStorage.class);

    if (quizStorageAsObject != null) {
      QuizStorage quizStorage = (QuizStorage) quizStorageAsObject;
      quizQuestions = quizStorage.getQuestions();
      questionArrayAdapter.addAll(quizQuestions);
      mAnswers = new String[quizStorage.getQuestions().size()];
    }

    findViewById(R.id.complete_quiz).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        completeQuiz();
      }
    });
  }

  public void completeQuiz() {
    int score = 0;
    for (int i = 0; i < quizQuestions.size(); i++) {
      if (quizQuestions.get(i).getAnswer().equals(mAnswers[i])) {
        score++;
      }
    }
    Log.i("Score", score + "");
    Toast.makeText(this, String.format("Score is %d", score), Toast.LENGTH_SHORT).show();
  }

  class QuestionArrayAdapter extends ArrayAdapter<QuizQuestion> {

    private Context mContext;

    public QuestionArrayAdapter(@NonNull Context context,
      int resource,
      @NonNull List<QuizQuestion> objects) {
      super(context, resource, objects);
      mContext = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      QuizQuestion current = getItem(position);
      LayoutInflater inflater = (LayoutInflater) mContext
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.view_question_item, parent, false);
      TextView textView = view.findViewById(R.id.question);
      textView.setText(current.getQuestion());
      EditText answer = view.findViewById(R.id.answer);

      //კოდის ეს ნაწილი მნიშვნელოვანია, გამომდინარე იქიდან რომ , თუ ListVIew-ს რომელიმე Item არ ჩანს ეკრანზე,
      // ის იშლება, და თავიდან გამოჩენის შემთხვევაში იქმნება თავიდან, ამიტომ უნდა ჩავსვათ ის მნიშვნელობა რაც იყო მანამდე
      if (mAnswers[position] != null) {
        answer.setText(mAnswers[position]);
      }
      answer.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          Log.i("Text Change", s.toString());
          mAnswers[position] = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
      });
      return view;
    }
  }

}
