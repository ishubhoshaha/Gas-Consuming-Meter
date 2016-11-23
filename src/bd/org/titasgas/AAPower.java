package bd.org.titasgas;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.datatype.Duration;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class AAPower extends Activity {
	private final String REQ_URL = "http://titasgas.6te.net/titas_gas/aapower/getallinfo.php";
	final static int SUCCESS = 1;
	final static int FAIL = 0;

	ProgressDialog pd;
	
	private ArrayList<Info> allInfo;
	public Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("AAPower");
		getActionBar().setIcon(R.drawable.logo_titas);
		
		
		setContentView(R.layout.activity_view_data);
		LoadDB();

		// lst = (ListView) findViewById(R.id.lstView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu2, menu);
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
		case R.id.refresh:
			LoadDB();
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

	private void LoadDB() {
		if (isNetworkAvailable()) {
			
			pd =  ProgressDialog.show(this, "Wait", "Fetching Data from Server", false, true);
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
						allInfo = new ArrayList<Info>();
						for (int i = 0; i < size; i++) {
							JSONObject bookobj = booksArray.getJSONObject(i);
							String Time = bookobj.getString("Time");
							String Pressure_p1 = bookobj
									.getString("Pressure_p1");
							String Temperature_t1 = bookobj
									.getString("Temperature_t1");
							String Pressure_p2 = bookobj
									.getString("Pressure_p2");
							String Temperature_t2 = bookobj
									.getString("Temperature_t2");
							String Base_volume_Vb1 = bookobj
									.getString("Base_volume_Vb1");
							String Base_volume_Vb2 = bookobj
									.getString("Base_volume_Vb2");
							String Flow_Q1 = bookobj.getString("Flow_Q1");
							String Flow_Q2 = bookobj.getString("Flow_Q2");

							Info info = new Info(Time, Pressure_p1,
									Temperature_t1, Pressure_p2,
									Temperature_t2, Base_volume_Vb1,
									Base_volume_Vb2, Flow_Q1, Flow_Q2);
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
				CALL();

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
				// Toast.makeText(getApplicationContext(), "Succesully Connect",
				// Toast.LENGTH_LONG).show();
				return true;
			}
		}

		return false;
	}

	protected void CALL() {
		if (allInfo != null && allInfo.size() > 0) {
			int s = allInfo.size();
			CreateTable();

		}

	}

	public void CreateTable() {
		TableLayout tb1 = (TableLayout) findViewById(R.id.tablelayout);
		View tableRow = LayoutInflater.from(this).inflate(R.layout.list_item,
				null, false);
		TextView time = (TextView) tableRow.findViewById(R.id.time);
		TextView t1 = (TextView) tableRow.findViewById(R.id.t1);
		TextView p1 = (TextView) tableRow.findViewById(R.id.p1);
		TextView t2 = (TextView) tableRow.findViewById(R.id.t2);
		TextView p2 = (TextView) tableRow.findViewById(R.id.p2);
		TextView bv1 = (TextView) tableRow.findViewById(R.id.bv1);
		TextView bv2 = (TextView) tableRow.findViewById(R.id.bv2);
		TextView bf1 = (TextView) tableRow.findViewById(R.id.bf1);
		TextView bf2 = (TextView) tableRow.findViewById(R.id.bf2);
		time.setText("Time");
		time.setTextColor(Color.parseColor("#000000"));
		t1.setText("T1");
		t1.setTextColor(Color.parseColor("#000000"));
	
		t1.setTypeface(null,Typeface.BOLD);
		p1.setText("P1");
		p1.setTextColor(Color.parseColor("#000000"));
		t2.setText("t2");
		t2.setTextColor(Color.parseColor("#000000"));
		p2.setText("P2");
		p2.setTextColor(Color.parseColor("#000000"));
		bv1.setText("Base Val.1");
		bv1.setTextColor(Color.parseColor("#000000"));
		bv2.setText("Base Val.2");
		bv2.setTextColor(Color.parseColor("#000000"));
		bf1.setText("Base Flow.1");
		bf1.setTextColor(Color.parseColor("#000000"));
		bf2.setText("Base Flow.2");
		bf2.setTextColor(Color.parseColor("#000000"));
		tb1.addView(tableRow);

		for (Info in : allInfo) {
			tableRow = LayoutInflater.from(this).inflate(R.layout.list_item,
					null, false);
			time = (TextView) tableRow.findViewById(R.id.time);
			t1 = (TextView) tableRow.findViewById(R.id.t1);
			p1 = (TextView) tableRow.findViewById(R.id.p1);
			t2 = (TextView) tableRow.findViewById(R.id.t2);
			p2 = (TextView) tableRow.findViewById(R.id.p2);
			bv1 = (TextView) tableRow.findViewById(R.id.bv1);
			bv2 = (TextView) tableRow.findViewById(R.id.bv2);
			bf1 = (TextView) tableRow.findViewById(R.id.bf1);
			bf2 = (TextView) tableRow.findViewById(R.id.bf2);
			time.setText(in.getTime());
			t1.setText(in.getTemperature_t1());
			p1.setText(in.getPressure_p1());
			t2.setText(in.getTemperature_t2());
			p2.setText(in.getPressure_p2());
			bv1.setText(in.getBase_volume_Vb1());
			bv2.setText(in.getBase_volume_Vb2());
			bf1.setText(in.getFlow_Q1());
			bf2.setText(in.getFlow_Q2());
			tb1.addView(tableRow);
			time.setTextColor(Color.parseColor("#000000"));
			time.setTextSize(7.5f);
			t1.setTextColor(Color.parseColor("#000000"));
			t1.setTextSize(7.5f);
			p1.setTextColor(Color.parseColor("#000000"));
			p1.setTextSize(7.5f);
			t2.setTextColor(Color.parseColor("#000000"));
			t2.setTextSize(7.5f);
			p2.setTextColor(Color.parseColor("#000000"));
			p2.setTextSize(7.5f);
			bv1.setTextColor(Color.parseColor("#000000"));
			bv1.setTextSize(7.5f);
			bv2.setTextColor(Color.parseColor("#000000"));
			bv2.setTextSize(7.5f);
			bf1.setTextColor(Color.parseColor("#000000"));
			bf1.setTextSize(7.5f);
			bf2.setTextColor(Color.parseColor("#000000"));
			bf2.setTextSize(7.5f);
			
			

		}

	}
}
