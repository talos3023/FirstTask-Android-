package ge.tsu.android.firsttask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ge.tsu.android.firsttask.data.Storage;
import ge.tsu.android.firsttask.data.LoginStorageSharePreferenceImpl;

public class RegistrationActivity extends AppCompatActivity {

  private EditText mUsername;
  private EditText mPassword;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    Log.i("RegistrationActivity", "onCreate Called");
    mUsername = findViewById(R.id.username);
    mPassword = findViewById(R.id.password);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.i("RegistrationActivity", "onDestroy Called");
  }

  public void register(View view) {
    String username = mUsername.getText().toString().trim();
    String password = mPassword.getText().toString().trim();

    if (username.isEmpty() || password.isEmpty()) {
      Toast.makeText(this, "Please enter credentials", Toast.LENGTH_LONG).show();
      return;
    }
    Storage storage = new LoginStorageSharePreferenceImpl();
    if (storage.exists(this, username)) {
      Toast.makeText(this, "User already exists, please enter another", Toast.LENGTH_LONG).show();
      return;
    }
    storage.save(this, username, password);

    Toast.makeText(this, "User Successfully Inserted", Toast.LENGTH_LONG).show();
    mUsername.setText("");
    mPassword.setText("");
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();
  }
}
