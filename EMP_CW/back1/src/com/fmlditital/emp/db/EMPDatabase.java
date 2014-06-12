package com.fmlditital.emp.db;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.fmlditital.emp.model.NavigationModel;

public class EMPDatabase {

	private static final String DATABASE_NAME = "emp.db";
	private static final String DATABASE_TABLE = "tabTable";
	private static final int DATABASE_VERSION = 1;

	// for tab
	public static final String TAB_ID = "_id";
	public static final String TAB_NAME = "tabName";
	public static final String TAB_FUNCTION = "tabFUNCTION=";
	public static final String TAB_ICON = "tabIcom";

	private SQLiteDatabase db;
	private final Context context;
	private DBOpenHelper dbHelper;

	public EMPDatabase(Context _context) {
		this.context = _context;
		dbHelper = new DBOpenHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public void close() {
		db.close();
	}

	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}

	public long insertTab(NavigationModel model) {
		Log.v("", ">>>>>>>>>>>>>>>>>>>>insertTab(TabModel model): ");
		 final ByteArrayOutputStream os = new ByteArrayOutputStream();
//		 Bitmap bmp=model.getIcon().getBitmap();
//		 bmp.compress(Bitmap.CompressFormat.PNG, 100, os);  
		 
		ContentValues newTaskValues = new ContentValues();
		newTaskValues.put(TAB_ID, model.getId());
		newTaskValues.put(TAB_NAME, model.getTitle());
		newTaskValues.put(TAB_FUNCTION, model.getFunction());
		newTaskValues.put(TAB_FUNCTION, model.getFunction());
		newTaskValues.put(TAB_ICON, os.toByteArray());
		// Insert the row.
		return db.insert(DATABASE_TABLE, null, newTaskValues);
	}

	  public boolean removeTab(long _rowIndex) {
	    return db.delete(DATABASE_TABLE, TAB_ID + "=" + _rowIndex, null) > 0;
	  }
	  
	  // Update a task
	  public boolean updateTab(long _rowIndex, NavigationModel model) {
//	    ContentValues newValue = new ContentValues();
//	    newValue.put(KEY_TASK, _task);
//	    return db.update(DATABASE_TABLE, newValue, KEY_ID + "=" + _rowIndex, null) > 0;
		  return false;
	  }
	  
	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		// SQL Statement to create a new database. a table for tab SQL
		private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
				+ DATABASE_TABLE
				+ " ("
				+ TAB_ID
				+ " integer primary key autoincrement, "
				+ TAB_NAME
				+ " text not null, "
				+ TAB_FUNCTION
				+ "text not null,"
				+ TAB_ICON + " BLOB );";

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			// Drop the old table.
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(_db);
		}

	}

}
