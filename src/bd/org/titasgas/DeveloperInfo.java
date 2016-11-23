package bd.org.titasgas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DeveloperInfo extends Activity {
	TextView phn1, phn2, mail, www;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.developer_info);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Developers Info");
		getActionBar().setIcon(R.drawable.logo_titas);

		phn1 = (TextView) findViewById(R.id.cellNum);
		phn2 = (TextView) findViewById(R.id.cellNum1);
		mail = (TextView) findViewById(R.id.mailID);
		www = (TextView) findViewById(R.id.website);
	}

	public void WEB(View v) {
		Intent browserintent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.innocuousenergy.com/"));
		startActivity(Intent.createChooser(browserintent, "Choose browser"));
	}

	public void MAIL(View v) {
		String mailid = "info@innocousenergy.com";
		Intent intent = new Intent(Intent.ACTION_SEND);
		Uri uri = Uri.parse("mailto:" + mailid);
		Intent myActivity2 = new Intent(Intent.ACTION_SENDTO, uri);
		startActivity(myActivity2);
	}

	public void CALL1(View v) {
		String mnum = "01799617798";
		Intent callintent = new Intent(Intent.ACTION_CALL);
		callintent.setData(Uri.parse("tel:" + mnum));
		startActivity(callintent);
	}

	public void CALL2(View v) {
		String mnum = "01799617799";
		Intent callintent = new Intent(Intent.ACTION_CALL);
		callintent.setData(Uri.parse("tel:" + mnum));
		startActivity(callintent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		default:
			break;
		}
		return (super.onOptionsItemSelected(item));
	}
}
