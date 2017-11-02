package mb.com.googlesignin;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.UserHandle;

import static android.R.attr.password;

/**
 * Created by Anshul on 02-11-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String dataBaseName="fbandgoogledata.db";
    private static final int dataBase_version=1;
    private static final String Table_Name="UserDetails";
    private static final String COLUMN_User_Id="user_id";
    private static final String COLUMN_User_Name="user_name";
    private static final String COLUMN_User_Email="user_email";
    private static final String COLUMN_User_Password="user_password";
    private static final String COLUMN_Login_Time="login_time";

    private static final String COLUMN_App_Name="app_name";

    private SQLiteDatabase database;

    private String CREATE_USER_TABLE="CREATE TABLE "+ Table_Name +"("
            + COLUMN_User_Id +" INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_User_Name +
            " TEXT not null,"+ COLUMN_User_Email +" TEXT not null,"+COLUMN_App_Name+" TEXT not null"+COLUMN_Login_Time
             +" DATE"+" )";

    private String DROP_USER_TABLE="Drop table if exist"+Table_Name;

    public DatabaseHelper(Context context)
    {
       super(context,dataBaseName,null,dataBase_version);
    }
    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int old_version, int new_version)
    {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void insertRecord(String name,String email,String app,String time) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_User_Name,name);
        contentValues.put(COLUMN_User_Email,email);
        contentValues.put(COLUMN_App_Name,app);
        contentValues.put(COLUMN_Login_Time,time);
    }
}
