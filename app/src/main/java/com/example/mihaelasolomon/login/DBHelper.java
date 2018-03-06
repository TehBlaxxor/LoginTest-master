package com.example.mihaelasolomon.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDB.db";
    public static final int DATABASE_VERSION =1;
    public static final String TABLE_NAME="users_table";
    public static final String ID="ID";
    public static final String USERNAME="USERNAME";
    public static final String PASSWORD="PASSWORD";
    public static final String EMAIL ="EMAIL";

    private static final String CREATE_SQL = "create table " + TABLE_NAME + " (ID integer primary key autoincrement, USERNAME TEXT, PASSWORD TEXT, EMAIL TEXT)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean createUser(String user, String pass, String mail, DBHelper dbhelper, Context context) {
        SQLiteDatabase database=dbhelper.getWritableDatabase();

        /*ContentValues values = new ContentValues();
        values.put("USERNAME", user);
        values.put("PASSWORD", pass);
        values.put("EMAIL", mail);*/
        try {
            String query = "INSERT INTO users_table (USERNAME, PASSWORD, EMAIL) VALUES ('`"+user+"`', '`"+pass+"`', '`"+mail+"`');";
            //database.insert(TABLE_NAME, null, values);
            database.rawQuery(query, null);
            return true;
        }
        catch(Exception e)
        {
            Toast.makeText(context, "Eroare la insertie.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<User> getUsers(DBHelper dbhelper)
    {
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * from users_table", null);

        ArrayList<User> users = new ArrayList<User>();

        while (cursor.moveToNext())
        {
            User user = new User();
            user.user = cursor.getString(cursor.getColumnIndex("USERNAME"));
            user.email = cursor.getString(cursor.getColumnIndex("EMAIL"));

            users.add(user);
        }

        return users;
    }

    public boolean deleteUser(String username, DBHelper dbhelper, Context context)
    {
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try {
            db.delete(TABLE_NAME, "USERNAME=?", new String[]{username});
        }
        catch (Exception e) {
            Log.d("+++", e.getStackTrace().toString());
            return false;
        }
        return true;
    }

    public boolean AttemptLogin(String username, String password, DBHelper dbhelper) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * from users_table WHERE USERNAME='" + username + "' AND PASSWORD='" + password+"';", null);

            if (cursor.getCount() > 0)
            {
                cursor.close();
                return true;
            }
            else {
                cursor.close();
                return false;
            }
        } catch (Exception e)
        {
            Log.d("nimic gasit", "nimic gasit");
            return false;
        }
    }

}
