package io.github.craciuncezar.infobac.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.google.android.material.textfield.TextInputEditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.craciuncezar.infobac.R;
import io.github.craciuncezar.infobac.models.DiscussionThread;

public class AddDiscussionActivity extends AppCompatActivity {

  @BindView(R.id.toolbar_add_discussion)
  Toolbar toolbarAddDiscussion;
  @BindView(R.id.input_message_discussion)
  TextInputEditText inputMessage;
  @BindView(R.id.input_title_discussion)
  TextInputEditText inputTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_discussion);
    ButterKnife.bind(this);

    setupToolbar();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @OnClick(R.id.btn_add_discussion)
  public void addNewDiscussion(View view){
    String title = inputTitle.getText().toString().trim();
    String message = inputMessage.getText().toString().trim();
    if(validateInputs()){
      DiscussionThread newDiscussionThread = new DiscussionThread(title,message,"Cezar");
      Intent intent = new Intent();
      intent.putExtra("newThread",newDiscussionThread);
      InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      setResult(Activity.RESULT_OK, intent);
      finish();
    }
  }

  public boolean validateInputs(){
    boolean isValid = true;
    if(inputTitle.getText().toString().trim().length()<12) {
      inputTitle.setError("Titlul trebuie sa contina minim 12 caractere!");
      isValid = false;
    }
    if(inputMessage.getText().toString().trim().length()<12){
      inputMessage.setError("Postarea ta trebuie sa contina minim 12 caractere!");
      isValid = false;
    }
    return isValid;
  }

  public void setupToolbar(){
    setSupportActionBar(toolbarAddDiscussion);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("");
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
