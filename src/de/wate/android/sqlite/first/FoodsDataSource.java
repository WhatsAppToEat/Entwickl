package de.wate.android.sqlite.first;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FoodsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_MENGE, MySQLiteHelper.COLUMN_MASSEINHEIT, MySQLiteHelper.COLUMN_ABLAUFDATUM };

  public FoodsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Food createFood(String[] food) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_NAME, food[0]);
    values.put(MySQLiteHelper.COLUMN_MENGE, food[1]);
    values.put(MySQLiteHelper.COLUMN_MASSEINHEIT, food[2]);
    values.put(MySQLiteHelper.COLUMN_ABLAUFDATUM, food[3]);
    long insertId = database.insert(MySQLiteHelper.TABLE_FOOD, null,
        values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_FOOD,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Food newFood = cursorToFood(cursor);
    cursor.close();
    return newFood;
  }

  public void deleteFood(Food food) {
    long id = food.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_FOOD, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<Food> getAllFood() {
    List<Food> foods = new ArrayList<Food>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_FOOD,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Food food = cursorToFood(cursor);
      foods.add(food);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return foods;
  }

  private Food cursorToFood(Cursor cursor) {
    Food food = new Food();
    food.setId(cursor.getLong(0));
    food.setName(cursor.getString(1));
    food.setMenge(Double.parseDouble(cursor.getString(2)));
    food.setMasseinheit(cursor.getString(3));
    food.setAblaufdatum(cursor.getString(4));
    return food;
  }
}
