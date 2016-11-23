package bd.org.titasgas;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomizeAdapter extends ArrayAdapter<Info>{
	Activity con;
	ArrayList<Info> infoList;
	public CustomizeAdapter(Context context, ArrayList<Info> info) {
		super(context, R.layout.list_item, info);
		con = (Activity) context;
		this.infoList = info;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if(convertView==null){
//			LayoutInflater inflator = con.getLayoutInflater();
//			v = inflator.inflate(R.layout.list_item, null);
//			TextView time = (TextView)v.findViewById(R.id.tvTime);
//			TextView p1 = (TextView)v.findViewById(R.id.tvP1);
//			TextView t1 = (TextView)v.findViewById(R.id.tvT1);
//			TextView p2 = (TextView)v.findViewById(R.id.tvP2);
//			TextView t2 = (TextView)v.findViewById(R.id.tvT2);
//			TextView bv1 = (TextView)v.findViewById(R.id.tvBV1);
//			TextView bv2 = (TextView)v.findViewById(R.id.tvBV2);
//			TextView bf1 = (TextView)v.findViewById(R.id.tvBF1);
//			TextView bf2 = (TextView)v.findViewById(R.id.tvBF2);
//			Info in = infoList.get(position);
//			time.setText(in.getTime());
//			p1.setText(in.getPressure_p1());
//			t1.setText(in.getTemperature_t1());
//			p2.setText(in.getPressure_p2());
//			t2.setText(in.getTemperature_t2());
//			bv1.setText(in.getBase_volume_Vb1());
//			bv2.setText(in.getBase_volume_Vb2());
//			bf1.setText(in.getFlow_Q1());
//			bf2.setText(in.getFlow_Q2());
			
			
		}else{
			v = convertView;
		}
		return v;
	}
}
