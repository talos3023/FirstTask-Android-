package ge.tsu.android.firsttask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.LoginStorageSharePreferenceImpl;

public class HomeActivity extends AppCompatActivity {

  private TextView mUsernameText;
  private TextView mPreviousScore;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    mUsernameText = findViewById(R.id.username_text);
    mPreviousScore = findViewById(R.id.score_text);
    Intent intent = getIntent();
    if (intent.hasExtra("username")) {
      mUsernameText.setText(intent.getStringExtra("username"));
    }
    if (intent.hasExtra("score")) {
      mPreviousScore.setText(intent.getStringExtra("score"));
    }
  }

  public void logout(View view) {
    Storage storage = new LoginStorageSharePreferenceImpl();
    storage.save(this, "logged_user", null);
    Intent intent = new Intent(this, LauncherActivity.class);
    startActivity(intent);
    finish();
  }

  public void startQuiz(View view) {
    Intent intent = new Intent(this, QuizActivity.class);
    intent.putExtra("username", mUsernameText.getText().toString().trim());
    startActivityForResult(intent, 10001);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mPreviousScore.setText(data.getStringExtra("score"));
  }
}
