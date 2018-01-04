package rahul.nirmesh.onlinequiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import rahul.nirmesh.onlinequiz.common.Common;

public class Playing extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; // 1 sec
    final static long TIMEOUT = 7000; // 7 sec
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestions, correctAnswer;

    // Firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar progressBar;
    ImageView question_image;
    Button btnAnswerA, btnAnswerB, btnAnswerC, btnAnswerD;
    TextView textScore, textQuestionNum, question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        // Firebase
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        // Views
        btnAnswerA = findViewById(R.id.btnAnswerA);
        btnAnswerB = findViewById(R.id.btnAnswerB);
        btnAnswerC = findViewById(R.id.btnAnswerC);
        btnAnswerD = findViewById(R.id.btnAnswerD);

        btnAnswerA.setOnClickListener(this);
        btnAnswerB.setOnClickListener(this);
        btnAnswerC.setOnClickListener(this);
        btnAnswerD.setOnClickListener(this);

        textScore = findViewById(R.id.textScore);
        textQuestionNum = findViewById(R.id.textTotalQuestions);
        question_text = findViewById(R.id.question_text);
        question_image = findViewById(R.id.question_image);

        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View view) {
        mCountDown.cancel();
        if (index < totalQuestions) {
            Button clickedButton = (Button)view;
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())) {
                // Choose Correct Answer
                score += 10;
                correctAnswer++;
                showQuestion(++index);
            }
            else {
                // Choose Wrong Answer
                Intent intentDone = new Intent(this, Done.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE", score);
                dataSend.putInt("TOTAL", totalQuestions);
                dataSend.putInt("CORRECT", correctAnswer);
                intentDone.putExtras(dataSend);
                startActivity(intentDone);
                finish();
            }

            textScore.setText(String.format("%d", score));
        }
    }

    private void showQuestion(int index) {
        if (index < totalQuestions) {
            thisQuestion++;
            textQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestions));
            progressBar.setProgress(0);
            progressValue = 0;

            if (Common.questionList.get(index).getIsImageQuestion().equals("true")) {
                // If it is Image Question
                Picasso.with(getBaseContext())
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            } else {
                // If it is Text Question
                question_text.setText(Common.questionList.get(index).getQuestion());
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);
            }

            btnAnswerA.setText(Common.questionList.get(index).getAnswerA());
            btnAnswerB.setText(Common.questionList.get(index).getAnswerB());
            btnAnswerC.setText(Common.questionList.get(index).getAnswerC());
            btnAnswerD.setText(Common.questionList.get(index).getAnswerD());

            mCountDown.start(); // Start the Timer
        } else {
            // If it is the Final Question
            Intent intentDone = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestions);
            dataSend.putInt("CORRECT", correctAnswer);
            intentDone.putExtras(dataSend);
            startActivity(intentDone);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestions = Common.questionList.size();

        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long miniSec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };

        showQuestion(index);
    }
}
