package com.onfido.toth.xkcd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.onfido.toth.xkcdlib.XkcdDialog;

public class MainActivity extends AppCompatActivity implements XkcdDialog.FeedbackListener {

  private static final String XKCD_DIALOG_TAG = "xkcd-dialog";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button comicButton = (Button) findViewById(R.id.comic_button);
    if (comicButton != null) {
      comicButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          new XkcdDialog().show(getSupportFragmentManager(), XKCD_DIALOG_TAG);
        }
      });
    }
  }

  @Override
  public void positive() {
    Toast.makeText(this, R.string.positive_feedback_message, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void negative() {
    Toast.makeText(this, R.string.negative_feedback_message, Toast.LENGTH_SHORT).show();
  }
}
