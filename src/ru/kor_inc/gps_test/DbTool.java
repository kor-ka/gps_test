package ru.kor_inc.gps_test;


import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;
import java.util.*;


public class DbTool{

 private static final String TAG = "kor_ka Log";
 private static final String DBTAG = "kor_ka DB Log";
 DBHelper dbHelper;
 ContentValues cv;
 
 
 	public void LocationToLog(double latitude, double longtitude, String time){
//Made to test my ability to create classes and send data between them 
		Log.d(TAG, "широта "+latitude+" | "+" долгота "+longtitude+" | "+"время "+time);
	}
 
 	public void WriteToSql(double latitude, double longtitude, String time, Context context){
	 dbHelper = new DBHelper(context);
	 SQLiteDatabase db = dbHelper.getWritableDatabase();
	 cv = new ContentValues();
	 
	 cv.put("latitude", latitude);
	 cv.put("longtitude",longtitude);
	 cv.put("time",time);
	 
	 db.insert("координаты", null, cv);
	 db.close();
	}
	
	public void ReadFromSqlToLog(Context context){
		dbHelper = new DBHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor c = db.query("координаты", null, null, null, null, null, null);

// ставим позицию курсора на первую строку выборки 
		// если в выборке нет строк, вернется false 
		if (c.moveToFirst()) {

// определяем номера столбцов по имени в выборке 
			int idColIndex = c.getColumnIndex("id");
			int latitudeColIndex = c.getColumnIndex("latitude");
			int longtitudeColIndex = c.getColumnIndex("longtitude");
			int timeColIndex = c.getColumnIndex("time");
		
			do { 
				// получаем значения по номерам столбцов и пишем все в лог

				Log.d(DBTAG, "ID = " + c.getInt(idColIndex) + "| latitude = " + c.getString(latitudeColIndex) + "| longtitude = " + c.getString(longtitudeColIndex)+"|  time = " + c.getString(timeColIndex)); 
				// переход на следующую строку 
				// а если следующей нет (текущая -последняя), то false - выходим из цикла
			} while (c.moveToNext());
		
			c.close();
			db.close();
		}else{
			Log.d(DBTAG, "как то пусто в твоей таблице...");
			c.close();
			db.close();
		}
	}
	public void ReadFromSqlToMap(Context context){
		dbHelper = new DBHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor c = db.query("координаты", null, null, null, null, null, null);
		Map coords = new HashMap();
// ставим позицию курсора на первую строку выборки 
		// если в выборке нет строк, вернется false 
		if (c.moveToFirst()) {

// определяем номера столбцов по имени в выборке 
			int idColIndex = c.getColumnIndex("id");
			int latitudeColIndex = c.getColumnIndex("latitude");
			int longtitudeColIndex = c.getColumnIndex("longtitude");
			int timeColIndex = c.getColumnIndex("time");

			do { 
				// получаем значения по номерам столбцов и пишем все в лог

				Log.d(DBTAG, "ID = " + c.getInt(idColIndex) + "| latitude = " + c.getString(latitudeColIndex) + "| longtitude = " + c.getString(longtitudeColIndex)+"|  time = " + c.getString(timeColIndex)); 
				// переход на следующую строку 
				// а если следующей нет (текущая -последняя), то false - выходим из цикла
			} while (c.moveToNext());

			c.close();
			db.close();
		}else{
			Log.d(DBTAG, "как то пусто в твоей таблице...");
			c.close();
			db.close();
		}
	}
	public void clear (Context context){
 		DBHelper dbHelper = new DBHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete("координаты",null,null);
		Log.d(DBTAG, "ты убил их! как ты мог :0");
		db.close();
	} 
	
	class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context){ 
		// конструктор суперкласса 
		super(context, "координаты", null, 1); }

		@Override 
		public void onCreate(SQLiteDatabase db){
		Log.d(DBTAG, "--- onCreate database ---");
		// создаем таблицу с полями 
		db.execSQL("create table координаты (" + "id integer primary key autoincrement," + "latitude double," + "longtitude double,"+"time text" + ");");

		}

		@Override 
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

		} 
	}
}
