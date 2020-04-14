package ge.tsu.android.firsttask.data;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizStorageSharePreferenceImpl implements Storage {

  private static final String QUIZ_PREVIOUS_ANSWER = "quiz_previous_answer";

  @Override
  public void save(Context context, String key, String value) {
    SharedPreferences sharedPref = getInstance(context);
    SharedPreferences.Editor editor = sharedPref.edit();
    editor.putString(key, value);
    editor.commit();
  }

  @Override
  public String get(Context context, String key) {
    SharedPreferences sharedPref = getInstance(context);
    return sharedPref.getString(key, null);
  }

  @Override
  public boolean exists(Context context, String key) {
    return get(context, key) != null;
  }

  private SharedPreferences getInstance(Context context) {
    SharedPreferences sharedPref = context.getSharedPreferences(QUIZ_PREVIOUS_ANSWER, Context.MODE_PRIVATE);
    return sharedPref;
  }

}
