package ge.tsu.android.firsttask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ge.tsu.android.firsttask.data.QuizQuestion;
import ge.tsu.android.firsttask.data.QuizStorage;
import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.StorageImpl;

public class ManageQuestionsActivity extends AppCompatActivity {
    private ListView mQuestions;
    private QuestionArrayAdapter questionArrayAdapter;
    private String[] mAnswers;
    private ArrayList<QuizQuestion> quizQuestions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_questions);
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
            Button button = new Button(this.mContext);
            button.setText("delete question");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteQuestion(current);
                }
            });
            ((LinearLayout) view).addView(button);
            return view;
        }
    }

    public void deleteQuestion(QuizQuestion quizQuestion) {
        Storage storage = new StorageImpl();
        Object storageAsObject = storage
                .getObject(this, QuizStorage.QUIZ_STORAGE_KEY, QuizStorage.class);

        QuizStorage quizStorage = (QuizStorage) storageAsObject;

        quizStorage.getQuestions().remove(quizQuestion);
        storage.add(this, QuizStorage.QUIZ_STORAGE_KEY, quizStorage);

        Intent intentReload = new Intent(this, ManageQuestionsActivity.class);
        finish();
        startActivity(intentReload);
    }
}
