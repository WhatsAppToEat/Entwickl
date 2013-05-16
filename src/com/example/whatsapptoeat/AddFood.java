package com.example.whatsapptoeat;

import data.Food;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AddFood extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_food);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_food, menu);
		return true;
	}
	
	public void Click_Save(View v) {
		
		EditText et_name = (EditText)findViewById(R.id.editText1);
		EditText et_menge = (EditText)findViewById(R.id.editText2);
		Spinner sp_mass = (Spinner)findViewById(R.id.spinner1);
		
		// HALLO
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("name", et_name.getText().toString());
		returnIntent.putExtra("menge", et_menge.getText().toString());
		returnIntent.putExtra("masseinheit", sp_mass.getSelectedItem().toString());
		setResult(RESULT_OK, returnIntent);
		finish();
		
	}

}
