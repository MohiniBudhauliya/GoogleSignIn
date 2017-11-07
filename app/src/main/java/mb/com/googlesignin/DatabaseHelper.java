package mb.com.googlesignin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.UserHandle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;

import java.io.File;

import static android.R.attr.password;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Anshul on 02-11-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public SQLiteDatabase db;
    private static final String dataBaseName="fbandgoogledata.db";
    private static final int dataBase_version=1;
    private static final String Table_Name="UserDetails";
    private static final String COLUMN_User_Id="user_id";
    private static final String COLUMN_User_Name="user_name";
    private static final String COLUMN_User_Email="user_email";
    private static final String COLUMN_User_Password="user_password";
    private static final String COLUMN_Login_Time="login_time";
    private static final String COLUMN_App_Name="app_name";
    int count=0;
    LoginUserDetails userloginwith=new LoginUserDetails();
    String app;

    private String CREATE_USER_TABLE="CREATE TABLE "+ Table_Name +"("
            + COLUMN_User_Id +" INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_User_Name +
            " TEXT not null,"+ COLUMN_User_Email +" TEXT not null,"+COLUMN_App_Name+" TEXT not null,"+COLUMN_Login_Time
             +" DATE"+" )";



    public DatabaseHelper(Context context)
    {
       super(context,dataBaseName,null,dataBase_version);
//        super(context, Environment.getExternalStorageDirectory()
//                + File.separator + dataBaseName, null, dataBase_version);
    }
    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);
        this.db=db;
       //getWritableDatabase();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db,int old_version, int new_version)
    {
        String DROP_USER_TABLE="Drop table if exist "+Table_Name;
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }
    public void insertRecord(LoginUserDetails obj)
    {
        db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(COLUMN_User_Name,obj.getName());
        content.put(COLUMN_User_Email,obj.getEmail());
        content.put(COLUMN_Login_Time,obj.getLogintime());
        content.put(COLUMN_App_Name,obj.getAppname());
        db.insert(Table_Name,null,content);
    }

    public void delete()
    {
        String deleteQuery="delete from "+Table_Name;
        db.execSQL(deleteQuery);
    }


    public LoginUserDetails isLoggedIn()
    {
        db = this.getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM " + Table_Name;
        int noOfRows = getReadableDatabase().rawQuery(sql, null).getCount();
        String query = "Select " + COLUMN_App_Name + " from " + Table_Name;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        int count=cursor.getCount();
        if (cursor!=null)
        {
            if(count>0) {
                cursor.moveToFirst();
                app = cursor.getString(cursor.getColumnIndex("app_name"));
                userloginwith.setAppname(app);
            }
            else
            {
                userloginwith.setAppname("null");
            }
         }

       return  userloginwith;
    }

}
