package data;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "food.db";
	private static final int DATABASE_VERSION = 1;
	
	private final static String FOOD_TABLE = "essen";
	
	private final static String FOOD_Name = "_name";
	private final static String FOOD_Menge = "_menge";
	private final static String FOOD_Masseinheit = "_masseinheit";
	private final static String FOOD_Ablaufdatum = "_ablaufdatum";

	public DatabaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createDB = "CREATE TABLE" + FOOD_TABLE + " (" +
				FOOD_Name + " TEXT PRIMARY KEY, " + 
				FOOD_Menge + " TEXT, " +
				FOOD_Masseinheit + " TEXT, " +
				FOOD_Ablaufdatum + "DATE)";
		db.execSQL(createDB);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
		onCreate(db);
	}
	
	public void addFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(FOOD_Name, food.getName());
		values.put(FOOD_Menge, food.getMenge());
		values.put(FOOD_Masseinheit, food.getMasseinheit());
		values.put(FOOD_Ablaufdatum, food.getAblaufdatum().toString());
		
		
		db.insert(FOOD_TABLE, null, values);
		db.close();
		
	}

	public Food getFood(String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor cursor = db.query(FOOD_TABLE, 
				new String[] {FOOD_Name,  FOOD_Menge,  
				FOOD_Masseinheit,  FOOD_Ablaufdatum},  
				FOOD_Name + "=?" , 
				new String[] { String.valueOf(name) }, 
				null, 
				null, 
				null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		
		Food food = new Food(cursor.getString(0), 
				Double.parseDouble(cursor.getString(1)), 
				cursor.getString(2), 
				cursor.getString(3));
		
		return food;
		
	}
}
