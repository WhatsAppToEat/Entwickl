package com.example.whatsapptoeat;

import data.Food;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
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
		
		EditText et_name = (EditText)findViewById(R.id.EditText01);
		EditText et_menge = (EditText)findViewById(R.id.EditText02);
		EditText et_mass = (EditText)findViewById(R.id.EditText03);
		
		TextView tv_name = new TextView(this);
		tv_name.setText(et_name.getText().toString());
		
		TextView tv_menge = new TextView(this);
		tv_menge.setText(et_menge.getText().toString());
		
		TextView tv_mass = new TextView(this);
		tv_mass.setText(et_mass.getText().toString());
		
		TableRow tr_name = new TableRow(this);
		TableRow tr_menge = new TableRow(this);
		TableRow tr_mass = new TableRow(this);
		
		tr_name.addView(tv_name);
		tr_menge.addView(tv_menge);
		tr_mass.addView(tv_mass);
		
		TableLayout tl_name = (TableLayout)findViewById(R.id.tableLayout1);
		TableLayout tl_menge = (TableLayout)findViewById(R.id.tableLayout2);
		TableLayout tl_mass = (TableLayout)findViewById(R.id.tableLayout3);
		
		tl_name.addView(tr_name);
		tl_menge.addView(tr_menge);
		tl_mass.addView(tr_mass);
		
		finish();
		
	}

}
