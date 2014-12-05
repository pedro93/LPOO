package com.trapShooter.src;
import com.kilobolt.robotgame.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class Input extends Activity{

	final Context context = this;
	EditText inputField;
	String name;
	int score, gameDificulty; 
	boolean mode, audio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		name = new String();
		setContentView(R.layout.activity_input);

		Button done = (Button) findViewById(R.id.button1);
		inputField = (EditText) findViewById(R.id.editText1);
		inputField.requestFocus();

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		score = extras.getInt("playerScore");
		mode = extras.getBoolean("mode");
		audio = extras.getBoolean("audio");
		gameDificulty = extras.getInt("gameDificulty");
		done.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				name = inputField.getText().toString();

				inputField.getEditableText().toString();
				if(name.length() <= 5)
				{
					finish();
				}
				else
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("Wrong Input!"); 
					builder.setMessage("Name is too big"); 
					builder.setPositiveButton("OK", null);
					AlertDialog alertDialog = builder.create();
					alertDialog.show();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		inputField.requestFocus();
		inputField.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputMethodManager keyboard = (InputMethodManager)
						getSystemService(Context.INPUT_METHOD_SERVICE);
				keyboard.showSoftInput(inputField, 0);
			}
		},200);}

	@Override
	public void finish() {
		// Prepare data intent 
		Intent data = new Intent();
		data.putExtra("playerName", name);
		data.putExtra("playerScore", score);
		data.putExtra("mode", mode);
		data.putExtra("audio", audio);
		data.putExtra("gameDificulty", gameDificulty);
		// Activity finished ok, return the data
		setResult(RESULT_OK, data);
		super.finish();
	}
}
