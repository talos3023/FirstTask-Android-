package ge.tsu.android.firsttask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import ge.tsu.android.firsttask.data.QuizStorageSharePreferenceImpl;
import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.LoginStorageSharePreferenceImpl;

public class LauncherActivity extends Activity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Storage storage = new LoginStorageSharePreferenceImpl();
    if (storage.exists(this, "logged_user")) {
      Intent intent = new Intent(this, HomeActivity.class);
      String loggedUser = storage.get(this, "logged_user");
      intent.putExtra("username", loggedUser);
      Storage quizStorage = new QuizStorageSharePreferenceImpl();
      if (quizStorage.exists(this, loggedUser)) {
        intent.putExtra("score", quizStorage.get(this, loggedUser));
      }
      startActivity(intent);
    } else {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
    }
    finish();
  }
}
