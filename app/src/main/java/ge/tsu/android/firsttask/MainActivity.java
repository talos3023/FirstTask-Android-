package ge.tsu.android.firsttask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ge.tsu.android.firsttask.data.QuizHistory;
import ge.tsu.android.firsttask.data.QuizScore;
import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.StorageImpl;

public class MainActivity extends AppCompatActivity {

    public ListView mScoreHistory;
    private QuestionHistoryArrayAdapter questionArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScoreHistory = findViewById(R.id.score_history);
        questionArrayAdapter = new QuestionHistoryArrayAdapter(this, 0, new ArrayList<QuizScore>());
        mScoreHistory.setAdapter(questionArrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Storage storage = new StorageImpl();
        Object quizStorageAsObject = storage
                .getObject(this, QuizHistory.QUIZ_HISTORY_KEY, QuizHistory.class);

        if (quizStorageAsObject != null) {
            QuizHistory quizHistory = (QuizHistory) quizStorageAsObject;
            questionArrayAdapter.clear();
            questionArrayAdapter.addAll(quizHistory.getQuizScoreList());
        }
    }

    public void addQuestion(View view) {
        Intent intent = new Intent(this, AddQuestionActivity.class);
        startActivity(intent);
    }

    public void manageQuestions(View view) {
        Intent intent = new Intent(this, ManageQuestionsActivity.class);
        startActivity(intent);
    }

    public void takeQuiz(View view) {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    class QuestionHistoryArrayAdapter extends ArrayAdapter<QuizScore> {

        private Context mContext;

        public QuestionHistoryArrayAdapter(@NonNull Context context,
                                           int resource,
                                           @NonNull List<QuizScore> objects) {
            super(context, resource, objects);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final QuizScore current = getItem(position);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.view_quiz_history_item, parent, false);
            TextView scoreText = view.findViewById(R.id.score);
            scoreText.setText(Integer.toString(current.getScore()));
            TextView dateText = view.findViewById(R.id.date);

            dateText.setText(new SimpleDateFormat("yyyy-MM-dd").format(current.getDate().getTime()));
            return view;
        }
    }

}
