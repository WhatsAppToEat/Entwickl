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

	FoodsDataSource datasource;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*AddNewFoodToTable("Apfel", "3", "Stück");
        AddNewFoodToTable("Mehl", "500", "Gramm");
        AddNewFoodToTable("Fleisch", "1", "KGramm");
        AddNewFoodToTable("Milch", "1.5", "Liter");
        AddNewFoodToTable("Ei", "6", "Stück");
        AddNewFoodToTable("Sahne", "100", "MLiter");
        AddNewFoodToTable("Zitrone", "2", "Stück");
        AddNewFoodToTable("Joghurt", "300", "MLiter");
        */
        datasource = new FoodsDataSource(this);
        datasource.open();
        
        //String[] test = new String[]{"Milch", "3", "liter", "5.5.2013"};
        
        //datasource.createFood(test);
        
        List<Food> getFood;
        getFood = datasource.getAllFood();
        
        for(int i = 0; i < getFood.size(); i++)
        {
        	AddNewFoodToTable(getFood.get(i).getName(), String.valueOf(getFood.get(i).getMenge()), getFood.get(i).getMasseinheit());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void Click_Add(View v) { 	
    	Intent myIntent = new Intent(this, AddFood.class);
    	startActivityForResult(myIntent, 1);
    }
    
    public void Click_Exit(View v) {
    finish();	
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	  if (requestCode == 1) {

    	     if(resultCode == RESULT_OK){      
    	         //String name = data.getStringExtra("name");
    	         //String menge = data.getStringExtra("menge");
    	         //String masseinheit = data.getStringExtra("masseinheit");
    	         //AddNewFoodToTable(name, menge, masseinheit);
    	    	 String[] values = new String[4];
    	    	 values[0] = data.getStringExtra("name");
    	    	 values[1] = data.getStringExtra("menge");
    	    	 values[2] = data.getStringExtra("masseinheit");
    	    	 values[3] = "5.5.2013";
    	    	 
    	    	 datasource.createFood(values);
    	     }
    	     if (resultCode == RESULT_CANCELED) {    
    	         //Write your code if there's no result
    	     }
    	  }
    }

    
    public void AddNewFoodToTable(String name, String menge, String masseinheit) {
    	
    	TextView tv_name = new TextView(this);
    	tv_name.setText(name);
    	
    	TextView tv_menge = new TextView(this);
    	tv_menge.setText(menge);
    	
    	TextView tv_masseinheit = new TextView(this);
    	tv_masseinheit.setText(masseinheit);
    	
    	////
    	
    	TableRow tr_name = new TableRow(this);
    	tr_name.addView(tv_name);
    	
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
    
}
