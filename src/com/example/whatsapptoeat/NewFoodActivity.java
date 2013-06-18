package com.example.whatsapptoeat;

import java.util.List;

import de.wate.android.sqlite.first.FoodsDataSource;
import de.wate.android.sqlite.first.Food;
import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NewFoodActivity extends Activity {
	
	String editFood = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_food);

		Intent myIntent = this.getIntent();
		String name = myIntent.getStringExtra("name");
		String menge = myIntent.getStringExtra("menge");
		String masseinheit = myIntent.getStringExtra("masseinheit");
		String ablaufdatum = myIntent.getStringExtra("ablaufdatum");
		
		//editFood = myIntent.getBooleanExtra("getFood", true);
		editFood = myIntent.getStringExtra("editFood");

		EditText et_name = (EditText) findViewById(R.id.editText1);
		EditText et_menge = (EditText) findViewById(R.id.editText2);
		Spinner sp_mass = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter myAdap = (ArrayAdapter) sp_mass.getAdapter();
		int position = myAdap.getPosition(masseinheit);

		et_name.setText(name);
		et_menge.setText(menge);
		sp_mass.setSelection(position);

		DatePicker dp_ablaufdatum = (DatePicker) findViewById(R.id.datePicker1);
		CheckBox cb_speichern = (CheckBox) findViewById(R.id.checkBox1);

		if (ablaufdatum != null) {
			if (ablaufdatum.equals("0")) {
				cb_speichern.setChecked(false);
			} else {

				int day = Integer.valueOf(ablaufdatum.split("\\.")[0]);
				int month = Integer.valueOf(ablaufdatum.split("\\.")[1]) - 1;
				int year = Integer.valueOf(ablaufdatum.split("\\.")[2]);
				dp_ablaufdatum.init(year, month, day, null);
				cb_speichern.setChecked(true);
			}
		}
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
		DatePicker dp_ablaufdatum = (DatePicker)findViewById(R.id.datePicker1);
		CheckBox cb_speichern = (CheckBox)findViewById(R.id.checkBox1);
		
		if (et_name.getText().length() <= 0 || et_menge.getText().length() <= 0) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Fehlende Information");
			builder.setMessage("Bitte füllen Sie das Formular komplett aus, bevor Sie auf \"Save\" klicken!");
			builder.setNegativeButton("OK",new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	            }
	        });
			AlertDialog alert = builder.create();
			alert.show();
		}
		else {		
			Intent returnIntent = new Intent();
			returnIntent.putExtra("name", et_name.getText().toString());
			returnIntent.putExtra("menge", et_menge.getText().toString());
			returnIntent.putExtra("masseinheit", sp_mass.getSelectedItem().toString());
			String datum = "0";
			if (cb_speichern.isChecked()) {
				int year = dp_ablaufdatum.getYear();
				int month = dp_ablaufdatum.getMonth() + 1;
				int day = dp_ablaufdatum.getDayOfMonth();
				datum = String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);
			}
			returnIntent.putExtra("ablaufdatum", datum);
			returnIntent.putExtra("editFood", editFood);
			setResult(1, returnIntent);
			finish();
		}
		
	}
		
	public void Click_Exit(View v){
		Intent returnIntent = new Intent();
		setResult(3, returnIntent);
		finish();
	}
	
	public void Click_Delete(View v) {
		EditText et_name = (EditText)findViewById(R.id.editText1);
		if (et_name.getText().toString().equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Fehler beim Löschen");
			builder.setMessage("Kann Eintrag nicht löschen, da kein bekannter Name angegeben wurde");
			builder.setNegativeButton("OK",new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	            }
	        });
			AlertDialog alert = builder.create();
			alert.show();
		}
		else {
			Intent returnIntent = new Intent();
			returnIntent.putExtra("name", et_name.getText().toString());
			setResult(2, returnIntent);
			finish();
		}
		
	}
}
