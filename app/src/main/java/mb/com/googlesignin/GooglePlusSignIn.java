package mb.com.googlesignin;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class GooglePlusSignIn extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GooglePlusSignIn.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private Button GmailSignInButton, GmailSignOutButton, fb_signinButton, fb_signoutButton;
    private TextView LoginStatus, Email, UserName, OptionText;
    private ImageView UserPic;
    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_plus_sign_in);


        GmailSignInButton = (Button) findViewById(R.id.gmail_signinbutton);
        GmailSignOutButton = (Button) findViewById(R.id.gmail_signoutbutton);
        LoginStatus = (TextView) findViewById(R.id.login_status);
        UserName = (TextView) findViewById(R.id.UserName);
        Email = (TextView) findViewById(R.id.UserEmail);
        UserPic = (ImageView) findViewById(R.id.ProfilePic);
        OptionText = (TextView) findViewById(R.id.OptionText);

        fb_signinButton = (Button) findViewById(R.id.fb_signinbutton);
        fb_signoutButton = (Button) findViewById(R.id.fb_signoutbutton);

        fb_signinButton.setTextColor(Color.parseColor("#c5f5f0"));
        fb_signoutButton.setTextColor(Color.parseColor("#c5f5f0"));//#8f1010

        GmailSignInButton.setTextColor(Color.parseColor("#c5f5f0"));
        GmailSignOutButton.setTextColor(Color.parseColor("#c5f5f0"));

        GmailSignInButton.setOnClickListener(this);
        GmailSignOutButton.setOnClickListener(this);
        fb_signinButton.setOnClickListener(this);
        fb_signoutButton.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        setFacebookData(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        LoginStatus.setText("Login Cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        LoginStatus.setText("An Error Occured");
                    }
                });

        fb_signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("promo", MODE_PRIVATE);
                boolean activated = pref.getBoolean("activated", false);
                if (activated == false) {  // User hasn't actived the promocode -> activate it
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("activated", true);
                    editor.commit();
                    //LoginManager.getInstance().logInWithReadPermissions(GooglePlusSignIn.this, Arrays.asList("public_profile", "user_friends"));
                }
                else {
                    //LoginManager.getInstance().logOut();
                    LoginStatus.setText("Status");
                    UserPic.setVisibility(View.GONE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("activated", false);
                    editor.commit();

                }
            }


        });
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
        Toast.makeText(this, "You have successfully logged Out", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            String Pic = acct.getPhotoUrl().toString();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + Pic);

            UserName.setText(personName);
            Email.setText(email);
            Glide.with(getApplicationContext()).load(Pic)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(UserPic);
            Toast.makeText(this, "You have successfully logged in", Toast.LENGTH_SHORT).show();
            updateUI(true);
        }
        else
            {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    private void updateUI(boolean isSignedIn) {

        if (isSignedIn) {
            GmailSignInButton.setVisibility(View.GONE);
            LoginStatus.setText("Status");
            GmailSignOutButton.setVisibility(View.VISIBLE);
            UserName.setVisibility(View.VISIBLE);
            Email.setVisibility(View.VISIBLE);
            UserPic.setVisibility(View.VISIBLE);
            fb_signinButton.setVisibility(View.GONE);
            OptionText.setVisibility(View.GONE);
        } else {
            GmailSignOutButton.setVisibility(View.GONE);
            GmailSignInButton.setVisibility(View.VISIBLE);
            LoginStatus.setVisibility(View.VISIBLE);
            UserName.setVisibility(View.GONE);
            Email.setVisibility(View.GONE);
            UserPic.setVisibility(View.GONE);
            fb_signinButton.setVisibility(View.VISIBLE);
            OptionText.setVisibility(View.VISIBLE);
        }


    }


    private void setFacebookData(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());
                            GmailSignOutButton.setVisibility(View.GONE);
                            GmailSignInButton.setVisibility(View.GONE);
                            fb_signinButton.setVisibility(View.GONE);
                            OptionText.setVisibility(View.GONE);
                            fb_signoutButton.setVisibility(View.VISIBLE);
                            String email = response.getJSONObject().getString("email");
                            Profile profile = Profile.getCurrentProfile();
                            String link = profile.getLinkUri().toString();
                            String name = profile.getName();
                            LoginStatus.setText("Login Success");
                            UserName.setText(name);
                            Email.setText(email);
                            UserPic.setVisibility(View.VISIBLE);
                            Log.i("Link", link);
                            if (Profile.getCurrentProfile() != null) {
                                Glide.with(getApplicationContext()).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200))
                                        .thumbnail(0.5f)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(UserPic);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void fblouout()
    {
        GmailSignOutButton.setVisibility(View.GONE);
        GmailSignInButton.setVisibility(View.VISIBLE);
        fb_signinButton.setVisibility(View.VISIBLE);
        fb_signoutButton.setVisibility(View.GONE);
        LoginStatus.setVisibility(View.GONE);
        UserPic.setVisibility(View.GONE);
        Email.setVisibility(View.GONE);
        UserName.setVisibility(View.GONE);
        OptionText.setVisibility(View.VISIBLE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.gmail_signinbutton:
                signIn();
                break;
            case R.id.gmail_signoutbutton:
                signOut();
                break;
            case R.id.fb_signinbutton:
                LoginManager.getInstance().logInWithReadPermissions(GooglePlusSignIn.this
                        ,Arrays.asList("public_profile","user_friends","email"));
                break;
            case R.id.fb_signoutbutton:
                fblouout();
                break;
        }

    }

}

