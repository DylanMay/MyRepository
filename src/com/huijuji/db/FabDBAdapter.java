package com.fab.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.acra.ErrorReporter;
import org.json.JSONException;
import org.json.JSONObject;

public class FabDBAdapter
{
  private static final String COL_EMAIL = "email";
  private static final String COL_FPA_COOKIE = "fpa_cookie";
  private static final String COL_KEY_ROW = "_id";
  private static final String COL_RATE_APP = "rate_app";
  private static final String COL_USER_ID = "user_id";
  private static final String COL_USER_NAME = "username";
  private static final String DATABASE_NAME = "fab.dbs";
  private static final int DATABASE_VERSION = 1;
  private static final String SQL_TABLE_FAB_USER = "create table fabuser( _id integer primary key autoincrement,username text,user_id integer,email text,fpa_cookie text,rate_app integer);";
  private static final String TABLE_FAB_USER = "fabuser";
  private Context context;
  private DatabaseHelper dbHelper;
  private SQLiteDatabase sqlDB;

  public FabDBAdapter(Context paramContext)
  {
    this.context = paramContext;
    this.dbHelper = new DatabaseHelper(this.context);
  }

  public void close()
  {
    this.dbHelper.close();
  }

  public long emptyUsers()
  {
    return this.sqlDB.delete("fabuser", null, null);
  }

  public JSONObject getUserInfo()
  {
    JSONObject localJSONObject = new JSONObject();
    Cursor localCursor = this.sqlDB.query("fabuser", null, null, null, null, null, null);
    try
    {
      if (localCursor.getCount() == 1)
      {
        localCursor.moveToNext();
        localJSONObject.put("id", localCursor.getInt(localCursor.getColumnIndex("user_id")));
        localJSONObject.put("username", localCursor.getString(localCursor.getColumnIndex("username")));
        localJSONObject.put("fpa", localCursor.getString(localCursor.getColumnIndex("fpa_cookie")));
        localJSONObject.put("email", localCursor.getString(localCursor.getColumnIndex("email")));
      }
      while (true)
      {
        return localJSONObject;
        if (localCursor.getCount() > 1)
          emptyUsers();
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localCursor.close();
    }
    finally
    {
      localCursor.close();
    }
  }

  public long insertUserInfo(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("user_id", Integer.valueOf(paramInt));
    localContentValues.put("username", paramString1);
    localContentValues.put("email", paramString3);
    localContentValues.put("fpa_cookie", paramString2);
    ErrorReporter localErrorReporter = ErrorReporter.getInstance();
    try
    {
      localErrorReporter.putCustomData("UserId", paramInt);
      localErrorReporter.putCustomData("UserName", paramString1);
      localErrorReporter.putCustomData("UserEmail", paramString3);
      label91: return this.sqlDB.insert("fabuser", null, localContentValues);
    }
    catch (Exception localException)
    {
      break label91;
    }
  }

  public boolean isAppRated()
  {
    SQLiteDatabase localSQLiteDatabase = this.sqlDB;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "rate_app";
    Cursor localCursor = localSQLiteDatabase.query("fabuser", arrayOfString, null, null, null, null, null);
    while (true)
    {
      try
      {
        if (localCursor.moveToFirst())
        {
          int i = localCursor.getInt(localCursor.getColumnIndex("rate_app"));
          if (i == 1)
          {
            localCursor.close();
            bool = true;
            return bool;
          }
        }
      }
      finally
      {
        localCursor.close();
      }
      localCursor.close();
      boolean bool = false;
    }
  }

  public FabDBAdapter open()
    throws SQLException
  {
    this.sqlDB = this.dbHelper.getWritableDatabase();
    return this;
  }

  public long updateRateApp(int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("rate_app", Integer.valueOf(paramInt));
    return this.sqlDB.update("fabuser", localContentValues, null, null);
  }

  public long updateUserInfo(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("user_id", Integer.valueOf(paramInt));
    localContentValues.put("username", paramString1);
    localContentValues.put("email", paramString3);
    localContentValues.put("fpa_cookie", paramString2);
    return this.sqlDB.update("fabuser", localContentValues, "user_id = " + paramInt, null);
  }

  private static class DatabaseHelper extends SQLiteOpenHelper
  {
    public DatabaseHelper(Context paramContext)
    {
      super("fab.dbs", null, 1);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      paramSQLiteDatabase.execSQL("create table fabuser( _id integer primary key autoincrement,username text,user_id integer,email text,fpa_cookie text,rate_app integer);");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS fabuser");
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.db.FabDBAdapter
 * JD-Core Version:    0.6.2
 */