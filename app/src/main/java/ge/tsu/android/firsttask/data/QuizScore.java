package ge.tsu.android.firsttask.data;

import java.util.Calendar;

public class QuizScore {
    int score;
    Calendar date;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
