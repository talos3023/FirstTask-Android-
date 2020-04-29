package ge.tsu.android.firsttask.data;

import java.util.ArrayList;

public class QuizStorage {

  public static String QUIZ_STORAGE_KEY = "quiz_storage";

  private ArrayList<QuizQuestion> questions = new ArrayList<>();


  public ArrayList<QuizQuestion> getQuestions() {
    return questions;
  }

  public void setQuestions(ArrayList<QuizQuestion> questions) {
    this.questions = questions;
  }
}
