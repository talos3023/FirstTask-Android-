package ge.tsu.android.firsttask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ge.tsu.android.firsttask.data.QuizStorageSharePreferenceImpl;
import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.LoginStorageSharePreferenceImpl;

public class LoginActivity extends AppCompatActivity {

  private EditText mUsername;
  private EditText mPassword;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    Log.i("LoginActivity", "onCreate Called");
    mUsername = findViewById(R.id.username);
    mPassword = findViewById(R.id.password);
  }

  public void register(View view) {
    Intent intent = new Intent(this, RegistrationActivity.class);
    startActivity(intent);
  }

  public void login(View view) {
    String username = mUsername.getText().toString().trim();
    String password = mPassword.getText().toString().trim();

    if (username.isEmpty() || password.isEmpty()) {
      Toast.makeText(this, "Please enter credentials", Toast.LENGTH_LONG).show();
      return;
    }
    Storage storage = new LoginStorageSharePreferenceImpl();
    String existingPassword = storage.get(this, username);
    if (existingPassword == null || !existingPassword.equals(password)) {
      Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_LONG).show();
      return;
    }
    Toast.makeText(this, "Successfully login", Toast.LENGTH_LONG).show();
    storage.save(this, "logged_user", username);
    Intent intent = new Intent(this, HomeActivity.class);
    intent.putExtra("username", username);
    Storage quizStorage = new QuizStorageSharePreferenceImpl();
    if (quizStorage.exists(this, username)) {
      intent.putExtra("score", quizStorage.get(this, username));
    }
    startActivity(intent);
    finish();
  }
}
