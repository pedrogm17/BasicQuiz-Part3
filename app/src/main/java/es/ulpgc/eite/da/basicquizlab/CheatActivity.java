package es.ulpgc.eite.da.basicquizlab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CheatActivity extends AppCompatActivity {

  public static final String TAG = "Quiz.CheatActivity";

  public final static String EXTRA_ANSWER = "EXTRA_ANSWER";
  public final static String EXTRA_CHEATED = "EXTRA_CHEATED";

  public final static String KEY_ANSWER_CHEATED = "ANSWER_CHEATED";

  private Button noButton, yesButton;
  private TextView answerText;

  private int currentAnswer;
  private boolean answerCheated;
  private boolean buttonPressed;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);

    getSupportActionBar().setTitle(R.string.cheat_title);

    initLayoutData();

    linkLayoutComponents();
    updateLayoutContent();
    enableLayoutButtons();
  }

  private void initLayoutData() {
    currentAnswer = getIntent().getExtras().getInt(EXTRA_ANSWER);
  }

  private void linkLayoutComponents() {
    noButton = findViewById(R.id.noButton);
    yesButton = findViewById(R.id.yesButton);

    answerText = findViewById(R.id.answerText);
  }

  private void enableLayoutButtons() {

    noButton.setOnClickListener(v -> onNoButtonClicked());
    yesButton.setOnClickListener(v -> onYesButtonClicked());
  }

  private void returnCheatedStatus() {
    Log.d(TAG, "returnCheatedStatus()");
    Log.d(TAG, "answerCheated: " + answerCheated);

    Intent intent = new Intent();
    intent.putExtra(EXTRA_CHEATED, answerCheated);
    setResult(RESULT_OK, intent);

    finish();
  }

  @Override
  public void onBackPressed() {
    Log.d(TAG, "onBackPressed()");

    returnCheatedStatus();
  }


  private void onYesButtonClicked() {
    answerCheated = true;
    buttonPressed = true;

    updateLayoutContent();
  }

  private void onNoButtonClicked() {
    buttonPressed = true;

    updateLayoutContent();
  }

  private void updateLayoutContent() {
    if (buttonPressed){
      yesButton.setEnabled(false);
      noButton.setEnabled(false);
      if(answerCheated){
        if(currentAnswer == 0) {
          answerText.setText(R.string.false_text);
        } else {
          answerText.setText(R.string.true_text);
        }
      }else{
        returnCheatedStatus();
      }
    }
  }


  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putBoolean(KEY_ANSWER_CHEATED, answerCheated);
  }

}
