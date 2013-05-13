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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void Add_Click(View v) {
    	Food temp = new Food();
    	temp.name = "Apfel";
    	temp.menge = "3";
    	temp.masseinheit = "Stück";
    	
    	TableRow tr_name = new TableRow(this);
    	//TableRow tr_menge = new TableRow(this);
    	//TableRow tr_mass = new TableRow(this);
    	
    	TextView tv_name = new TextView(this);
    	tv_name.setText(temp.name);
    	
    	TextView tv_menge = new TextView(this);
    	tv_menge.setText(temp.menge);
    	
    	TextView tv_mass = new TextView(this);
    	tv_mass.setText(temp.masseinheit);
    	
    	tr_name.addView(tv_name);
    	tr_name.addView(tv_menge);
    	tr_name.addView(tv_mass);
    	//tr_menge.addView(tv_menge);
    	//tr_mass.addView(tv_mass);
    	
    	TableLayout tl = (TableLayout)findViewById(R.id.tableLayout1);
    	//TableLayout tl_menge = (TableLayout)findViewById(R.id.tableLayout2);
    	//TableLayout tl_mass = (TableLayout)findViewById(R.id.tableLayout3);
    	
    	tl.addView(tr_name);
    	
    	//tl_name.addView(tr_name);
    	//tl_menge.addView(tr_menge);
    	//tl_mass.addView(tr_mass);
    	
    	
    }
    
    
}
