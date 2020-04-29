package ge.tsu.android.firsttask;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ge.tsu.android.firsttask.data.QuizHistory;
import ge.tsu.android.firsttask.data.QuizQuestion;
import ge.tsu.android.firsttask.data.QuizScore;
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

        for (QuizQuestion quizQuestion : quizQuestions) {
            if (quizQuestion.getCorrectAnswerNumber().equals(quizQuestion.getUserAnswerNumber())) {
                score++;
            }
        }
        Log.i("Score", score + "");
        saveScoreInHistory(score);
        Toast.makeText(this, String.format("Score is %d", score), Toast.LENGTH_SHORT).show();
        finish();
    }

    // TODO move all storage related code in QuizHistoryStorage implementation
    public void saveScoreInHistory(int score) {
        QuizScore quizScore = new QuizScore();
        quizScore.setScore(score);
        quizScore.setDate(Calendar.getInstance());
        Storage storage = new StorageImpl();
        Object storageAsObject = storage.getObject(this, QuizHistory.QUIZ_HISTORY_KEY, QuizHistory.class);

        QuizHistory quizHistory;
        if (storageAsObject != null) {
            quizHistory = (QuizHistory) storageAsObject;
        } else {
            quizHistory = new QuizHistory();
        }

        quizHistory.getQuizScoreList().add(quizScore);
        storage.add(this, QuizHistory.QUIZ_HISTORY_KEY, quizHistory);

        finish();
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
            final QuizQuestion current = getItem(position);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_question_item, parent, false);
            TextView textView = view.findViewById(R.id.question);
            textView.setText(current.getQuestion());
            List<String> options = current.getOptions();
            RadioGroup radioGroup = view.findViewById(R.id.radio_group);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton radioButton = group.findViewById(checkedId);
                    current.setUserAnswerNumber(Integer.toString(group.indexOfChild(radioButton)));
                }
            });
            RadioButton option1 = view.findViewById(R.id.answer_option1);
            option1.setText(options.get(0));
            RadioButton option2 = view.findViewById(R.id.answer_option2);
            option2.setText(options.get(1));
            RadioButton option3 = view.findViewById(R.id.answer_option3);
            option3.setText(options.get(2));
            RadioButton option4 = view.findViewById(R.id.answer_option4);
            option4.setText(options.get(3));
            return view;
        }
    }
}
