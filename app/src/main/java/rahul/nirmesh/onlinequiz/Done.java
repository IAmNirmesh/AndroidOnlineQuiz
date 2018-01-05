package rahul.nirmesh.onlinequiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rahul.nirmesh.onlinequiz.common.Common;
import rahul.nirmesh.onlinequiz.model.QuestionScore;

public class Done extends AppCompatActivity {

    Button btnTryAgain;
    TextView textTotalScore, textTotalQuestions;
    ProgressBar doneProgressBar;

    FirebaseDatabase database;
    DatabaseReference question_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        btnTryAgain = findViewById(R.id.btnTryAgain);
        textTotalScore = findViewById(R.id.textTotalScore);
        textTotalQuestions = findViewById(R.id.textTotalQuestions);
        doneProgressBar = findViewById(R.id.doneProgressBar);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doneIntent = new Intent(Done.this, Home.class);
                startActivity(doneIntent);
                finish();
            }
        });

        // Get Data from Bundle and set to this View
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int score = extra.getInt("SCORE");
            int totalQuestions = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");

            textTotalScore.setText(String.format("SCORE: %d", score));
            textTotalQuestions.setText(String.format("PASSED: %d / %d", correctAnswer, totalQuestions));

            doneProgressBar.setMax(totalQuestions);
            doneProgressBar.setProgress(correctAnswer);

            // Upload to DB
            question_score.child(String.format("%s_%s", Common.currentUser.getUsername(), Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getUsername(), Common.categoryId),
                            Common.currentUser.getUsername(),
                            String.valueOf(score),
                            Common.categoryId,
                            Common.categoryName));
        }
    }
}
