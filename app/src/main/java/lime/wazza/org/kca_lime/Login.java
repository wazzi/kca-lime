package lime.wazza.org.kca_lime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import lime.wazza.org.kca_lime.auxillary.LoginManager;


public class Login extends ActionBarActivity {

    //    LinearLayout layout;
    Button login;
    TextView txtUser;
    TextView txtPass;
    String userName, password;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_login);
        txtUser = (TextView) findViewById(R.id.etxt_user_name);
        txtPass = (TextView) findViewById(R.id.etxt_password);
//            final TextView txtHttp=(TextView)findViewById(R.id.txtHttpContentView);

        preferences = getSharedPreferences("MOODLE_TOKEN_OBJ", Context.MODE_PRIVATE);

        //set a listener on the login button...
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = txtUser.getText().toString();
                password = txtPass.getText().toString();

                /**
                 * 1. Connect to the moodle site using username and password
                 * 2. check if a web service access token is received...
                 * 3. Store the token for reuse for subsequent requests
                 */
                if (isConnected()) {

                    String loginUrl = LoginManager.createLoginURL(userName, password, "http://10.0.2.2/moodle/login/token.php?");
                    new LoginAsyncTask().execute(loginUrl);

                    //check if token has been saved...

                    if (preferences.contains("moodle_token")) {

                        //store the token using the cookie manager
                        Log.d("Token Object: ", preferences.getString("moodle_token", "No token stored"));
                        Intent intent = new Intent(Login.this, GridMenu.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(view.getContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(view.getContext(),
                            "No Internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    /**
     * Some utility methods...
     * Check for internet connection
     */

    public boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else
            return false;

    }

    public class LoginAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.d("ASYNC", strings[0]);
            return LoginManager.authenticate(strings[0]);
        }

        @Override
        public void onPostExecute(String result) {

//            store access token for later use...

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("moodle_token", result);
            editor.commit();
        }
    }


}

