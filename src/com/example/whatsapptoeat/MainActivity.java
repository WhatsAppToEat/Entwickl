package com.example.whatsapptoeat;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddNewFoodToTable("Apfel", "3", "Stück");
        AddNewFoodToTable("Mehl", "500", "Gramm");
        AddNewFoodToTable("Fleisch", "1", "KGramm");
        AddNewFoodToTable("Milch", "1.5", "Liter");
        AddNewFoodToTable("Ei", "6", "Stück");
        AddNewFoodToTable("Sahne", "100", "MLiter");
        AddNewFoodToTable("Zitrone", "2", "Stück");
        AddNewFoodToTable("Joghurt", "300", "MLiter");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void Add_Click(View v) { 	
    	
    }

    
    public void AddNewFoodToTable(String name, String menge, String masseinheit) {
    	
    	TextView tv_name = new TextView(this);
    	tv_name.setText(name);
    	
    	TextView tv_menge = new TextView(this);
    	tv_menge.setText(menge);
    	
    	TextView tv_masseinheit = new TextView(this);
    	tv_masseinheit.setText(masseinheit);
    	
    	TableRow tr_name = new TableRow(this);
    	tr_name.addView(tv_name);
    	
    	TableRow tr_menge = new TableRow(this);
    	tr_menge.addView(tv_menge);
    	
    	TableRow tr_masseinheit = new TableRow(this);
    	tr_masseinheit.addView(tv_masseinheit);
    	
    	TableLayout tl_name = (TableLayout)findViewById(R.id.tableLayout1);
    	tl_name.addView(tr_name);
    	
    	TableLayout tl_menge = (TableLayout)findViewById(R.id.tableLayout2);
    	tl_menge.addView(tr_menge);
    	
    	TableLayout tl_mass = (TableLayout)findViewById(R.id.tableLayout3);
    	tl_mass.addView(tr_masseinheit);
    	
    }
    
}
