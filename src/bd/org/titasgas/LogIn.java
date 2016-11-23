package bd.org.titasgas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bd.org.titasgas.AAPower.RequestThread;
import android.R.bool;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LogIn extends Activity implements OnClickListener {
	Button btn;
	ImageButton ibtn;
	EditText user, pass;
	CheckBox saveLoginCheckBox;
	ProgressDialog pd;
	
	boolean saveLogin;
	String yourName, yourPass;

	final String REQ_URL = "http://titasgas.6te.net/login/getUuser.php";
	final static int SUCCESS = 1;
	final static int FAIL = 0;
	
	
	
	
	/*SavePassword */ 
	private static final String PREFS_NAME = "preferences";
	private static final String PREF_UNAME = "Username";
	private static final String PREF_PASSWORD = "Password";

	private final String DefaultUnameValue = "";
	private String UnameValue;

	private final String DefaultPasswordValue = "";
	private String PasswordValue;
	
	

	ArrayList<UserInfo> allInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("LogIn");
		getActionBar().setIcon(R.drawable.logo_titas);
		
		ibtn = (ImageButton) findViewById(R.id.imageView1);
		btn = (Button) findViewById(R.id.btnLogin);
		user = (EditText) findViewById(R.id.etUser);
		pass = (EditText) findViewById(R.id.etPass);
		btn.setOnClickListener(this);

		saveLoginCheckBox = (CheckBox) findViewById(R.id.checkblogin);
		
		retrivepassword();
		if(!isNetworkAvailable()){
			Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
		}
		
		
	}

	private void retrivepassword() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
	            Context.MODE_PRIVATE);

	    // Get value
	    UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
	    PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
	    user.setText(UnameValue);
	    pass.setText(PasswordValue);
	    //System.out.println("onResume load name: " + UnameValue);
	    //System.out.println("onResume load password: " + PasswordValue);
		
	}

	private void savePassword() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
	            Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = settings.edit();

	    // Edit and commit
	    UnameValue =user.getText().toString();
	    PasswordValue = pass.getText().toString();
	    //System.out.println("onPause save name: " + UnameValue);
	    //System.out.println("onPause save password: " + PasswordValue);
	    editor.putString(PREF_UNAME, UnameValue);
	    editor.putString(PREF_PASSWORD, PasswordValue);
	    editor.commit();
		
	}

	public void getuser() {
		if (isNetworkAvailable()) {

			pd = ProgressDialog.show(this, "", "Logging In", false, true);
			RequestThread t = new RequestThread();
			t.start();
		}
	}

	class RequestThread extends Thread {
		@Override
		public void run() {
			try {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet req = new HttpGet(REQ_URL);
				HttpResponse response = client.execute(req);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					String jsonString = EntityUtils.toString(entity);
					// Log.d("DATA", jsonString);

					JSONObject respObject = new JSONObject(jsonString);
					int success = respObject.getInt("success");
					if (success == 1) {

						JSONArray booksArray = respObject.getJSONArray("books");
						int size = booksArray.length();
						allInfo = new ArrayList<UserInfo>();
						for (int i = 0; i < size; i++) {
							JSONObject bookobj = booksArray.getJSONObject(i);
							String username = bookobj.getString("username");
							String password = bookobj.getString("password");
							UserInfo info = new UserInfo(username, password);
							allInfo.add(info);

						}

						handler.sendEmptyMessage(SUCCESS);
					} else {
						handler.sendEmptyMessage(FAIL);
					}

				} else {
					handler.sendEmptyMessage(FAIL);
				}

			} catch (ClientProtocolException e) {
				handler.sendEmptyMessage(FAIL);
				e.printStackTrace();
			} catch (IOException e) {
				handler.sendEmptyMessage(FAIL);
				e.printStackTrace();
			} catch (JSONException e) {
				handler.sendEmptyMessage(FAIL);
				e.printStackTrace();
			}

		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			pd.dismiss();
			switch (msg.what) {
			case SUCCESS:
				// for(UserInfo u : allInfo){
				// Toast.makeText(getApplicationContext(), u.toString(),
				// Toast.LENGTH_SHORT).show();
				// }
				UserAuthinticate();

				break;
			case FAIL:
				Toast.makeText(getApplicationContext(), "Not",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	};

	private boolean isNetworkAvailable() {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cManager.getActiveNetworkInfo();
		if (netInfo != null) {
			if (netInfo.isAvailable() && netInfo.isConnected()) {
				return true;
			}
		}

		return false;
	}

	protected void UserAuthinticate() {
		yourName = user.getText().toString();
		yourPass = pass.getText().toString();
		boolean logSuccess = false;
        for (UserInfo u : allInfo) {
			String un = u.getUser(), pas = u.getPassword();
			if (un.equals(yourName) &&  pas.equals(yourPass)) {
				if(saveLoginCheckBox.isChecked()){
					savePassword();
				}
				Intent i = new Intent(LogIn.this, MainMenu.class);
				startActivity(i);
				Toast.makeText(getApplicationContext(), "LogIn Successfull",
						Toast.LENGTH_SHORT).show();
				logSuccess = true;
				break;

			}
		}
		if (logSuccess == false) {
			Toast.makeText(getApplicationContext(), "LogIn Error",
					Toast.LENGTH_LONG).show();
		}
	}

	public void www(View v) {
		Intent browserintent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.innocuousenergy.com/"));
		startActivity(Intent.createChooser(browserintent, "Choose browser"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		case R.id.devInfo:
			Intent temp = new Intent("android.intent.action.DEV");
			startActivity(temp);
			break;
		default:
			break;
		}
		return (super.onOptionsItemSelected(item));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			
			getuser();
			
			break;

		default:
			break;
		}

	}

}
