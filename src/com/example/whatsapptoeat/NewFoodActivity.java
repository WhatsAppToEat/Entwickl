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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class NewFoodActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_food);
		Intent myIntent = this.getIntent();
		String name = myIntent.getStringExtra("name");
		String menge = myIntent.getStringExtra("menge");
		String masseinheit = myIntent.getStringExtra("masseinheit");
		EditText et_name = (EditText)findViewById(R.id.editText1);
		EditText et_menge = (EditText)findViewById(R.id.editText2);
		Spinner sp_mass = (Spinner)findViewById(R.id.spinner1);
		ArrayAdapter  myAdap = (ArrayAdapter )sp_mass.getAdapter();
		int position = myAdap.getPosition(masseinheit);
		et_name.setText(name);
		et_menge.setText(menge);
		sp_mass.setSelection(position);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_food, menu);
		return true;
	}
	
	public void Click_Save(View v) {
		
		string[] data = new string[4];
		
		EditText et_name = (EditText)findViewById(R.id.editText1);
		EditText et_menge = (EditText)findViewById(R.id.editText2);
		Spinner sp_mass = (Spinner)findViewById(R.id.spinner1);
		
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
