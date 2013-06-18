package com.example.whatsapptoeat;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.wate.android.sqlite.first.FoodsDataSource;
import de.wate.android.sqlite.first.Food;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import java.util.Random;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import java.util.Date;

public class MainActivity extends Activity {

	FoodsDataSource datasource = new FoodsDataSource(this);
	List<Food> allFood;
	boolean mergeFood = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FillTableWithDatabaseValues();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Click_Add(View v) {
		Intent myIntent = new Intent(this, NewFoodActivity.class);
		myIntent.putExtra("editFood", "nein");
		startActivityForResult(myIntent, 1);
	}

	public void Click_Exit(View v) {
		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		String editFood = data.getStringExtra("editFood");
		
		if (resultCode == 1) {
			String name = data.getStringExtra("name");
			String menge = data.getStringExtra("menge");
			String masseinheit = data.getStringExtra("masseinheit");
			String ablaufdatum = data.getStringExtra("ablaufdatum");

			Food newFood = new Food();
			newFood.setName(name);
			newFood.setMasseinheit(masseinheit);
			newFood.setMenge(Double.valueOf(menge));
			newFood.setAblaufdatum(ablaufdatum);
			
			if (editFood.equals("ja")) {
				DeleteFoodFromDatabase(newFood.getName());
				String[] values = new String[4];
				values[0] = newFood.getName();
				values[1] = String.valueOf(newFood.getMenge());
				values[2] = newFood.getMasseinheit();
				values[3] = newFood.getAblaufdatum();
				datasource.createFood(values);
				FillTableWithDatabaseValues();
				return;
			}

			// Merge nur versuchen, wenn das Lebensmittel nicht editiert wird
			for (int i = 0; i < allFood.size(); i++) {
				if (allFood.get(i).getName().toString().equals(name)) {
					boolean canMerge = true;
					newFood = AddingSameFood(newFood);
					if (newFood.getMenge() == 0) {
						newFood.setMenge(Double.valueOf(menge));
						canMerge = false;
					} else {
						DeleteFoodFromDatabase(newFood.getName());
					}
					if (canMerge == true) {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								this);
						builder.setTitle("Schon vorhanden");
						builder.setMessage("Das Lebensmittel "
								+ name
								+ " ist schon vorhanden. Ihr neuer Eintrag wurde mit dem alten Eintrag verknüpft.");
						builder.setNegativeButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
						AlertDialog alert = builder.create();
						alert.show();
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								this);
						builder.setTitle("Schon vorhanden");
						builder.setMessage("Das Lebensmittel "
								+ name
								+ " ist schon vorhanden. Leider konnte der alte Eintrag nicht mit dem neuen Eintrag verknüpft werde. Stellen Sie sicher, dass sie bei gleichen Lebensmittel auch die gleiche Mengenbasis angeben. Ihr Eintrag wurde nicht gespeichert!");
						builder.setNegativeButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
						AlertDialog alert = builder.create();
						alert.show();
						return;
					}
				}
			}
			String[] values = new String[4];
			values[0] = newFood.getName();
			values[1] = String.valueOf(newFood.getMenge());
			values[2] = newFood.getMasseinheit();
			values[3] = newFood.getAblaufdatum();
			datasource.createFood(values);
			FillTableWithDatabaseValues();
		}
		if (resultCode == 2) {
			DeleteFoodFromDatabase(data.getStringExtra("name").toString());
		}

		if (resultCode == 3) {
		}
	}

	public void AddNewFoodToTable(String name, String menge, String masseinheit) {

		TextView tv_name = new TextView(this);
		tv_name.setText(name);
		tv_name.setPadding(0, 3, 0, 3);

		TextView tv_menge = new TextView(this);
		tv_menge.setText(menge);
		tv_menge.setPadding(0, 3, 0, 3);

		TextView tv_masseinheit = new TextView(this);
		tv_masseinheit.setText(masseinheit);
		tv_masseinheit.setPadding(0, 3, 0, 3);

		// //

		TableRow tr_name = new TableRow(this);
		tr_name.addView(tv_name);
		tr_name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Click_FoodInTable(v);
			}
		});

		TableRow tr_menge = new TableRow(this);
		tr_menge.addView(tv_menge);
		tr_menge.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Click_FoodInTable(v);
			}
		});

		TableRow tr_masseinheit = new TableRow(this);
		tr_masseinheit.addView(tv_masseinheit);
		tr_masseinheit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Click_FoodInTable(v);
			}
		});
		// //

		TableLayout tl_name = (TableLayout) findViewById(R.id.tableLayout1);
		tl_name.addView(tr_name);

		TableLayout tl_menge = (TableLayout) findViewById(R.id.tableLayout2);
		tl_menge.addView(tr_menge);

		TableLayout tl_mass = (TableLayout) findViewById(R.id.tableLayout3);
		tl_mass.addView(tr_masseinheit);

	}

	public void FillTableWithDatabaseValues() {
		TableLayout tl_name = (TableLayout) findViewById(R.id.tableLayout1);
		tl_name.removeAllViews();
		TableLayout tl_menge = (TableLayout) findViewById(R.id.tableLayout2);
		tl_menge.removeAllViews();
		TableLayout tl_mass = (TableLayout) findViewById(R.id.tableLayout3);

		tl_mass.removeAllViews();

		FillListWithFoodFromDB();
		for (int i = 0; i < allFood.size(); i++) {
			AddNewFoodToTable(allFood.get(i).getName(), String.valueOf(allFood
					.get(i).getMenge()), allFood.get(i).getMasseinheit());
		}
	}

	public void FillListWithFoodFromDB() {
		datasource.open();
		allFood = datasource.getAllFood();
	}

	public void DeleteFoodFromDatabase(String name_to_delete) {
		FillListWithFoodFromDB();
		for (int i = 0; i < allFood.size(); i++) {
			String temp = allFood.get(i).getName();

			if (temp.equals(name_to_delete)) {
				datasource.deleteFood(allFood.get(i));
				break;
			}
		}
		FillTableWithDatabaseValues();
	}

	public void Click_FoodInTable(View v) {
		Intent myIntent = new Intent(this, NewFoodActivity.class);
		TableRow tablerow = (TableRow) v;

		// Herausfinden welche Row angeklickt wurde
		TableLayout tl_name = (TableLayout) findViewById(R.id.tableLayout1);
		TableLayout tl_menge = (TableLayout) findViewById(R.id.tableLayout2);
		TableLayout tl_masseinheit = (TableLayout) findViewById(R.id.tableLayout3);

		int index_name = tl_name.indexOfChild(tablerow);
		int index_menge = tl_menge.indexOfChild(tablerow);
		int index_masseinheit = tl_masseinheit.indexOfChild(tablerow);
		int index = -1;

		if (index_name != -1) {
			index = index_name;
		}
		if (index_menge != -1) {
			index = index_menge;
		}
		if (index_masseinheit != -1) {
			index = index_masseinheit;
		}

		// Name, Menge und Masseinheit bestimmen
		TextView tv_name = (TextView) ((TableRow) tl_name.getChildAt(index))
				.getChildAt(0);
		String name = tv_name.getText().toString();

		Food clickedFood = GetFoodByName(name);
		String menge = String.valueOf(clickedFood.getMenge());
		String masseinheit = clickedFood.getMasseinheit();
		String ablaufdatum = clickedFood.getAblaufdatum();

		myIntent.putExtra("name", name);
		myIntent.putExtra("menge", menge);
		myIntent.putExtra("masseinheit", masseinheit);
		myIntent.putExtra("ablaufdatum", ablaufdatum);
		myIntent.putExtra("editFood", "ja");
		startActivityForResult(myIntent, 1);
	}

	public Food GetFoodByName(String name) {
		Food food = new Food();
		FillListWithFoodFromDB();
		String menge = "";
		String masseinheit = "";
		String ablaufdatum = "";
		for (int i = 0; i < allFood.size(); i++) {
			String temp = allFood.get(i).getName();
			if (temp.equals(name)) {
				menge = String.valueOf(allFood.get(i).getMenge());
				masseinheit = allFood.get(i).getMasseinheit();
				ablaufdatum = allFood.get(i).getAblaufdatum();
			}
		}
		food.setName(name);
		food.setMasseinheit(masseinheit);
		food.setMenge(Double.valueOf(menge));
		food.setAblaufdatum(ablaufdatum);
		return food;
	}

	public Food AddingSameFood(Food newFood) {
		Food food = new Food();
		food.setName(newFood.getName());
		food.setMasseinheit(newFood.getMasseinheit());
		food.setAblaufdatum(newFood.getAblaufdatum());
		food.setMenge(0);

		Food foodFromDB = GetFoodByName(newFood.getName());

		// Wenn unterschiedliche Ablaufdaten, dann kann nicht gemergt werden
		if (!foodFromDB.getAblaufdatum().equals(food.getAblaufdatum())) {
			return food;
		}
		if (foodFromDB.getMasseinheit().equals(newFood.getMasseinheit())) {
			food.setMenge(newFood.getMenge() + foodFromDB.getMenge());
			return food;
		} else {
			if (foodFromDB.getMasseinheit().equals("Gramm")
					&& newFood.getMasseinheit().equals("KGramm")) {
				Double menge1 = foodFromDB.getMenge() / 1000;
				Double menge2 = newFood.getMenge();
				Double newMenge = menge1 + menge2;
				food.setMenge(newMenge);
				food.setMasseinheit("KGramm");
			}
			if (foodFromDB.getMasseinheit().equals("KGramm")
					&& newFood.getMasseinheit().equals("Gramm")) {
				Double menge1 = foodFromDB.getMenge();
				Double menge2 = newFood.getMenge() / 1000;
				Double newMenge = menge1 + menge2;
				food.setMenge(newMenge);
				food.setMasseinheit("KGramm");
			}
			if (foodFromDB.getMasseinheit().equals("Liter")
					&& newFood.getMasseinheit().equals("MLiter")) {
				Double menge1 = foodFromDB.getMenge();
				Double menge2 = newFood.getMenge() / 1000;
				Double newMenge = menge1 + menge2;
				food.setMenge(newMenge);
				food.setMasseinheit("Liter");
			}
			if (foodFromDB.getMasseinheit().equals("MLiter")
					&& newFood.getMasseinheit().equals("Liter")) {
				Double menge1 = foodFromDB.getMenge() / 1000;
				Double menge2 = newFood.getMenge();
				Double newMenge = menge1 + menge2;
				food.setMenge(newMenge);
				food.setMasseinheit("Liter");
			}
		}
		return food;
	}

	public void Click_CheckADatum(View v) {
		FillListWithFoodFromDB();
		String message = "";
		for (int i = 0; i < allFood.size(); i++) {
			if (!allFood.get(i).getAblaufdatum().equals("0")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				String today = dateFormat.format(date);
				String year_today = today.split("/")[0];
				String month_today = today.split("/")[1];
				if (month_today.charAt(0) == '0') {
					month_today = month_today.substring(1);
				}
				String day_today = today.split("/")[2];
				if (day_today.charAt(0) == '0') {
					day_today = day_today.substring(1);
				}
				String day_ablaufdatum = allFood.get(i).getAblaufdatum().split("\\.")[0];
				if (day_ablaufdatum.charAt(0) == '0') {
					day_ablaufdatum = day_ablaufdatum.substring(1);
				}
				String month_ablaufdatum = allFood.get(i).getAblaufdatum().split("\\.")[1];
				if (month_ablaufdatum.charAt(0) == '0') {
					month_ablaufdatum = month_ablaufdatum.substring(1);
				}
				String year_ablaufdatum = allFood.get(i).getAblaufdatum().split("\\.")[2];
				
				
				if (year_today.equals(year_ablaufdatum)) {
					if (month_today.equals(month_ablaufdatum)) {
						int differenz = Integer.parseInt(day_ablaufdatum) -  Integer.parseInt(day_today);
						message = message + "\n" + allFood.get(i).getName() + " (" + allFood.get(i).getMenge() + " " + allFood.get(i).getMasseinheit() + ")" + " in " + String.valueOf(differenz) + " Tagen" + " (am " + day_ablaufdatum + "." + month_ablaufdatum + ")";
					}
				}		
			}
		}
		
		if (message.equals("")) {
			message = "Kein Lebensmittel läuft ab.";
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this);
		builder.setTitle("Ablaufdatum");
		builder.setMessage("Folgende Lebensmittel laufen diesen Monat ab:" + message);
		builder.setNegativeButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
