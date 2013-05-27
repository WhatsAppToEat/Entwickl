package com.example.whatsapptoeat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.wate.android.sqlite.first.FoodsDataSource;
import de.wate.android.sqlite.first.Food;
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

public class MainActivity extends Activity {

	FoodsDataSource datasource = new FoodsDataSource(this);
	List<Food> allFood;
	
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
    	startActivityForResult(myIntent, 1);
    }
    
    public void Click_Exit(View v) {
    finish();	
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	  if (requestCode == 1) {

    	     if(resultCode == 1){      
    	         String name = data.getStringExtra("name");
    	         String menge = data.getStringExtra("menge");
    	         String masseinheit = data.getStringExtra("masseinheit");
    	         AddNewFoodToTable(name, menge, masseinheit);
    	    	 String[] values = new String[4];
    	    	 values[0] = data.getStringExtra("name");
    	    	 values[1] = data.getStringExtra("menge");
    	    	 values[2] = data.getStringExtra("masseinheit");
    	    	 values[3] = "5.5.2013";   	    	 
    	    	 datasource.createFood(values);
    	    	 FillTableWithDatabaseValues();
    	     }
    	     if (resultCode == 2) {    
    	    	 DeleteFoodFromDatabase(data.getStringExtra("name").toString());
    	     }
    	     
    	     if (resultCode == 3) {
    	     }
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
    	
    	////
    	
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
    	
    	TableRow tr_masseinheit = new TableRow(this);
    	tr_masseinheit.addView(tv_masseinheit);
    	
    	////
    	
    	TableLayout tl_name = (TableLayout)findViewById(R.id.tableLayout1);
    	tl_name.addView(tr_name);
    	
    	TableLayout tl_menge = (TableLayout)findViewById(R.id.tableLayout2);
    	tl_menge.addView(tr_menge);
    	
    	TableLayout tl_mass = (TableLayout)findViewById(R.id.tableLayout3);
    	tl_mass.addView(tr_masseinheit);
    	
    }
    
    public void FillTableWithDatabaseValues() {
    	TableLayout tl_name = (TableLayout)findViewById(R.id.tableLayout1);
    	tl_name.removeAllViews();
    	TableLayout tl_menge = (TableLayout)findViewById(R.id.tableLayout2);
    	tl_menge.removeAllViews();
    	TableLayout tl_mass = (TableLayout)findViewById(R.id.tableLayout3);
    	
    	tl_mass.removeAllViews();
    	
        FillListWithFoodFromDB();
        for(int i = 0; i < allFood.size(); i++)
        {
        	AddNewFoodToTable(allFood.get(i).getName(), String.valueOf(allFood.get(i).getMenge()), allFood.get(i).getMasseinheit());
        }
    }
    
    public void FillListWithFoodFromDB() {
    	datasource.open();
        allFood = datasource.getAllFood();
    }
 
    public void DeleteFoodFromDatabase(String name_to_delete) {
     FillListWithFoodFromDB();
   	 for(int i = 0; i < allFood.size(); i++)
   	 {
   		 String temp = allFood.get(i).getName();
   		 
   		 if(temp.equals(name_to_delete))
   		 {    	    			 
   			 datasource.deleteFood(allFood.get(i));
   			 break;
   		 }
   	 }
   	 FillTableWithDatabaseValues();
    }

    public void Click_FoodInTable(View v) {
    	Intent myIntent = new Intent(this, NewFoodActivity.class);
    	TextView tv = (TextView)((TableRow)v).getChildAt(0);
    	String name = tv.getText().toString();
    	Food food = GetFoodByName(name);
    	myIntent.putExtra("name", food.getName());
    	myIntent.putExtra("menge", String.valueOf(food.getMenge()));
    	myIntent.putExtra("masseinheit", food.getMasseinheit());	
    	startActivityForResult(myIntent, 1);
    }

    public Food GetFoodByName(String name) {
    	Food food = new Food();
    	FillListWithFoodFromDB();
    	String menge = "";
    	String masseinheit = "";
    	for (int i = 0; i < allFood.size(); i++) {
    		String temp = allFood.get(i).getName();
    		if (temp.equals(name)) {
    			menge = String.valueOf(allFood.get(i).getMenge());
    			masseinheit = allFood.get(i).getMasseinheit().toString();
    		}
    	}
    	food.setName(name);
    	food.setMasseinheit(masseinheit);
    	food.setMenge(Double.valueOf(menge));
    	return food;
    }
}


