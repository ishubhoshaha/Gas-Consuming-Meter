package bd.org.titasgas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends Activity implements View.OnClickListener {

	Button btnAA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Client");
		getActionBar().setIcon(R.drawable.logo_titas);

		btnAA = (Button) findViewById(R.id.btnAA);
		btnAA.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		case R.id.btnAA:
			Intent i = new Intent(MainMenu.this, AAPower.class);
			startActivity(i);
			break;
		}

	}

}
