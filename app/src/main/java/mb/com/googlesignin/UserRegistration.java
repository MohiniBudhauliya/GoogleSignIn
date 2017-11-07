package mb.com.googlesignin;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistration extends AppCompatActivity {

    EditText UserName,UserEmail,Password,UserDOB;
    String UName,UEid,Pwd,DOB;
    Button RegisterButton;
    boolean email_result = false;
    boolean validDate=false;
    private Vibrator vib;
    Animation animShake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregistration_page);

        UserName = (EditText) findViewById(R.id.UserName);
        UserEmail = (EditText) findViewById(R.id.UserEmail);
        Password = (EditText) findViewById(R.id.Password);
        RegisterButton = (Button) findViewById(R.id.RegisterButton);
        UserDOB=(EditText)findViewById(R.id.UserDOB);
        //animShake= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    public void register()
    {
        initialize();
        if(!Validate())
        {
            Toast.makeText(this,"Your Registration is not done yet.",Toast.LENGTH_SHORT);
        }
        else
        {
            onRegistrationSuccess();
        }
    }
    public void onRegistrationSuccess()
    {
        //After Successful Registration
    }

    public boolean Validate() {
        boolean valid = true;
        if (UName.isEmpty()||UName.length()>=32) {
            UserName.setError("Please Enter Valid name");
        }
        if(UEid.isEmpty())
        {
            UserEmail.setError("Please Enter Email");

        }
        else
        {
            isValidEmailAddress(UEid);
            if(email_result)
            {
                UserEmail.setText(UEid);
            }
            else
            {
                UserEmail.setError("Enter valid email address.");
            }
        }
        if(Pwd.isEmpty())
        {
            Password.setError("Please enter password");
        }
        else
        {
            if(Pwd.length()>=8&&isValidPassword(Password.getText().toString()))
            {
                Password.setText(Pwd);
            }
            else
            {
                Password.setError("1.Password must contain alphanumeric value +\n+" +
                        "2.Atleast one special character"+"\n"+
                        "3.Password length mustbe atleast 8");
            }
        }
        if(DOB.isEmpty())
        {
            UserDOB.setError("Please enter DOB");
        }
        else
        {
            isvalidDate(DOB);
            if(validDate)
            {
                UserDOB.setText(DOB);
            }
            else
            {
                UserDOB.setError("Age should be greater than or equal to 18");
            }
        }

        return valid;
    }

    public boolean isvalidDate(String DateOfBirth)
    {
        String[] s=DateOfBirth.split("/");
        int day=Integer.parseInt(s[0]);
        int month=Integer.parseInt(s[1]);
        int year=Integer.parseInt(s[2]);

        if(year>=1947&&year<=1999)
        {
            if(month>=1&&month<=12)
            {
                if (year % 4 == 0 || year % 100 == 0 || year % 400 == 0) {
                    if (month == 2) {
                        if (day >= 1 && day <= 29) {
                            validDate = true;
                            return validDate;
                        } else {
                            UserDOB.setError("February has 29 days in leap year.");
                        }
                    }
                } else if (month == 2) {
                    if (day >= 1 && day <= 28) {
                        validDate = true;
                        return validDate;
                    }
                    else
                    {
                        UserDOB.setError("February has 28 days in non leap year.");
                    }
                }
                else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
                {
                    if (day >= 1 && day <= 30) {
                        validDate = true;
                        return validDate;
                    }
                    else
                    {
                        return validDate;
                    }
                }
            }
            else
            {
                UserDOB.setError("Month range should be 1 to 12");
            }

        }
        else
        {
            UserDOB.setError("You are underage.");
        }
        return validDate;
    }

    public  boolean isValidEmailAddress(String email)
    {

        if(email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))//email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        {
            email_result=true;
            return  email_result;
        }
        return email_result;
    }

    public  boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void initialize()
    {
        UName=UserName.getText().toString();
        UEid=UserEmail.getText().toString();
        Pwd=Password.getText().toString();
        DOB =UserDOB.getText().toString();
    }
}
