package ru.kor_inc.gps_test;

import android.app.*;
import android.content.*;
import android.database.sqlite.*;
import android.graphics.*;
import android.location.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import java.sql.*;
import java.text.*;

public class GpsTest extends Activity implements LocationListener{
TextView tv;
TextView tvAl;
TextView tvSp;
LocationManager myManager;
ImageView iv;
ImageView ivAnim1;
ImageView ivAnim2;
FrameLayout ll;
LinearLayout llIns;
Button button;
DbTool dbWr = new DbTool();
private static final String TAG = "kor_ka Log";
boolean isItStartedFirstTime = true;

	@Override
	protected void onStart(){
		super.onStart();
		if(isItStartedFirstTime){
			
			Log.d(TAG, "888    d8P  .d88888b. 8888888b.         888    d8P        d8888 ");
			Log.d(TAG, "888   d8P  d88P' 'Y88b888   Y88b        888   d8P        d88888 ");
			Log.d(TAG, "888  d8P   888     888888    888        888  d8P        d88P888 ");
			Log.d(TAG, "888d88K    888     888888   d88P        888d88K        d88P 888 ");
			Log.d(TAG, "8888888b   888     8888888888P'         8888888b      d88P  888 ");
			Log.d(TAG, "888  Y88b  888     888888 T88b          888  Y88b    d88P   888 ");
			Log.d(TAG, "888   Y88b Y88b. .d88P888  T88b         888   Y88b  d8888888888 ");
			Log.d(TAG, "888    Y88b 'Y88888P' 888   T88b 888888 888    Y88bd88P     888 ");
			isItStartedFirstTime = false;
		}

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps_test);
		
		ll=(FrameLayout) findViewById(R.id.ll);
		llIns=(LinearLayout) findViewById(R.id.llIns);
		llIns.setBackgroundColor(Color.GRAY);
		tv= (TextView) findViewById(R.id.tv);
		tvAl= (TextView) findViewById(R.id.tvAl);
		tvSp= (TextView) findViewById(R.id.tvSp);
		iv =(ImageView) findViewById(R.id.iv);
		ivAnim1 =(ImageView) findViewById(R.id.ivAnim1);
		ivAnim2 =(ImageView) findViewById(R.id.ivAnim2);
		button=(Button) findViewById(R.id.button);
		
		tv.setText("Hello, world! RM is coming!"); 
		tvAl.setText("А пока ищу спутники...");

		
		
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alfa);
		Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alfa2);
//		Связываем менеджер и сервис
		myManager = (LocationManager) getSystemService(LOCATION_SERVICE);
				//Назначаем слушателя
		myManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		if	(myManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			// TODO Auto-generated method stub
			tv.setText("Hello, world! RM is coming!"); 
			tvAl.setText("А пока ищу спутники...");
			tvSp.setText("");
			tv.setTextSize(40);
			tv.setTextColor(Color.WHITE);

			tvAl.setTextSize(20);
			ll.setBackgroundColor(Color.YELLOW);


			tvAl.startAnimation(anim);

			iv.setBackgroundResource(R.drawable.on0);
			ivAnim1.setBackgroundResource(R.drawable.on1);
			ivAnim2.setBackgroundResource(R.drawable.on2);
			ivAnim1.startAnimation(anim);
			ivAnim2.startAnimation(anim2);
			ivAnim1.setVisibility(ivAnim1.VISIBLE);
			ivAnim2.setVisibility(ivAnim2.VISIBLE);
		
		
		}
		else
		{
			// TODO Auto-generated method stub
			tv.setText("GPS мне запили!"); 
			tv.setTextSize(40);
			tv.setTextColor(Color.WHITE);
			tvAl.setText("Не могу искать спутники, пока модуль не включишь (Да-да! Залезай ручками в настройки и включай :р)");
			tvAl.setTextSize(20);

			ll.setBackgroundColor(Color.RED);

			Animation stopAnim = AnimationUtils.loadAnimation(this, R.anim.stopanim);
			tvAl.setAnimation(stopAnim);
			
			ivAnim1.setAnimation(stopAnim);
			ivAnim2.setAnimation(stopAnim);
			
			iv.setBackgroundResource(R.drawable.off);	
			ivAnim1.setVisibility(ivAnim1.INVISIBLE);
			ivAnim2.setVisibility(ivAnim2.INVISIBLE);
		}		

	
	}

	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Animation stopAnim = AnimationUtils.loadAnimation(this, R.anim.stopanim);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alfa);
		Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alfa2);
		tvAl.startAnimation(stopAnim);
		tvAl.setAlpha(2);
		tv.setText("shirota=" + location.getLatitude());
		tvAl.setText("dolgota=" + location.getLongitude());
		tvSp.setText("speed=" + location.getSpeed());
		iv.setAnimation(null);
		tvAl.setTextSize(40);
		tvSp.setTextSize(40);
		tv.setTextSize(40);
		tv.setTextColor(Color.BLACK);
		ll.setBackgroundColor(Color.GREEN);
		
		iv.setBackgroundResource(R.drawable.working);
		iv.setBackgroundResource(R.drawable.on0);
		ivAnim1.setBackgroundResource(R.drawable.working1);
		ivAnim2.setBackgroundResource(R.drawable.working2);
		ivAnim1.startAnimation(anim);
		ivAnim2.startAnimation(anim2);
		ivAnim1.setVisibility(ivAnim1.VISIBLE);
		ivAnim2.setVisibility(ivAnim2.VISIBLE);
		
		button.setClickable(false);
		button.setAlpha(0.3f);
		
		String format = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
	    String time = sdf.format(new Date(System.currentTimeMillis()));
	    
	//	dbWr.LocationToLog(location.getLatitude(), location.getLongitude(), time);
		dbWr.WriteToSql(location.getLatitude(), location.getLongitude(), time, this);
	}
	@Override
	public void onProviderDisabled(String arg0) {
		
		// TODO Auto-generated method stub
		tv.setText("GPS мне запили!"); 
		tv.setTextSize(40);
		tv.setTextColor(Color.WHITE);
		tvAl.setText("Не могу искать спутники, пока модуль не включишь (Да-да! Залезай ручками в настройки и включай :р)");
		tvAl.setTextSize(20);
		tvSp.setText("");
		
		ll.setBackgroundColor(Color.RED);
		
		Animation stopAnim = AnimationUtils.loadAnimation(this, R.anim.stopanim);
		tvAl.setAnimation(stopAnim);
		tvAl.setAlpha(2);
		
		ivAnim1.setAnimation(stopAnim);
		ivAnim2.setAnimation(stopAnim);
		iv.setBackgroundResource(R.drawable.off);	
		ivAnim1.setVisibility(ivAnim1.INVISIBLE);
		ivAnim2.setVisibility(ivAnim2.INVISIBLE);
		button.setClickable(false);
		button.setAlpha(0.3f);
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		tv.setText("Hello, world! RM is coming!"); 
		tvAl.setText("А пока ищу спутники...");
		tvAl.setTextSize(20);
		tvSp.setText("");
		
		ll.setBackgroundColor(Color.YELLOW);
		
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alfa);
		Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alfa2);
//		iv.startAnimation(anim);
		tvAl.startAnimation(anim);
		
		iv.setBackgroundResource(R.drawable.on);
		iv.setBackgroundResource(R.drawable.on0);
		ivAnim1.setBackgroundResource(R.drawable.on1);
		ivAnim2.setBackgroundResource(R.drawable.on2);
		ivAnim1.startAnimation(anim);
		ivAnim2.startAnimation(anim2);
		ivAnim1.setVisibility(ivAnim1.VISIBLE);
		ivAnim2.setVisibility(ivAnim2.VISIBLE);
		
		button.setClickable(true);
		button.setAlpha(2);
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		menu.add(0,1,1,"выгрузить координаты в лог");
		menu.add(0,2,2,"очистить историю");
		menu.add(0,3,3,"мама, где я был?");
		
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case 1:
				Toast.makeText(this, "Пытаюсь прочесть координаты из sql...", Toast.LENGTH_SHORT).show();
				dbWr.ReadFromSqlToLog(this);
			break;
			
			case 2:
				dbWr.clear(this);
			break;
		
			case 3:
				Intent intent = new Intent(this, CoordAct.class);
				this.startActivity(intent);
			break;
		}
	return true;	
	}
	
	class DBHelper extends SQLiteOpenHelper {

	    public DBHelper(Context context) {
	      // конструктор суперкласса
	      super(context, "myDB", null, 1);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	      Log.d(TAG, "--- onCreate database ---");
	      // создаем таблицу с полями
	      db.execSQL("create table mytable ("
	          + "id integer primary key autoincrement," 
	          + "name text,"
	          + "email text" + ");");
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	    }
	  }

}
