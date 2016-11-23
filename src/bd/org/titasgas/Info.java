package bd.org.titasgas;

public class Info {
	private String Time, Pressure_p1, Temperature_t1, Pressure_p2,
			Temperature_t2, Base_volume_Vb1, Base_volume_Vb2, Flow_Q1, Flow_Q2;


	public Info(String time, String pressure_p1, String temperature_t1,
			String pressure_p2, String temperature_t2, String base_volume_Vb1,
			String base_volume_Vb2, String flow_Q1, String flow_Q2) {
		super();
		Time = time;
		Pressure_p1 = pressure_p1;
		Temperature_t1 = temperature_t1;
		Pressure_p2 = pressure_p2;
		Temperature_t2 = temperature_t2;
		Base_volume_Vb1 = base_volume_Vb1;
		Base_volume_Vb2 = base_volume_Vb2;
		Flow_Q1 = flow_Q1;
		Flow_Q2 = flow_Q2;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getPressure_p1() {
		return Pressure_p1;
	}

	public void setPressure_p1(String pressure_p1) {
		Pressure_p1 = pressure_p1;
	}

	public String getTemperature_t1() {
		return Temperature_t1;
	}

	public void setTemperature_t1(String temperature_t1) {
		Temperature_t1 = temperature_t1;
	}

	public String getPressure_p2() {
		return Pressure_p2;
	}

	public void setPressure_p2(String pressure_p2) {
		Pressure_p2 = pressure_p2;
	}

	public String getTemperature_t2() {
		return Temperature_t2;
	}

	public void setTemperature_t2(String temperature_t2) {
		Temperature_t2 = temperature_t2;
	}

	public String getBase_volume_Vb1() {
		return Base_volume_Vb1;
	}

	public void setBase_volume_Vb1(String base_volume_Vb1) {
		Base_volume_Vb1 = base_volume_Vb1;
	}

	public String getBase_volume_Vb2() {
		return Base_volume_Vb2;
	}

	public void setBase_volume_Vb2(String base_volume_Vb2) {
		Base_volume_Vb2 = base_volume_Vb2;
	}

	public String getFlow_Q1() {
		return Flow_Q1;
	}

	public void setFlow_Q1(String flow_Q1) {
		Flow_Q1 = flow_Q1;
	}

	public String getFlow_Q2() {
		return Flow_Q2;
	}

	public void setFlow_Q2(String flow_Q2) {
		Flow_Q2 = flow_Q2;
	}
	
	@Override
	public String toString() {
		return "Info [Time=" + Time + ", Pressure_p1=" + Pressure_p1
				+ ", Temperature_t1=" + Temperature_t1 + ", Pressure_p2="
				+ Pressure_p2 + ", Temperature_t2=" + Temperature_t2
				+ ", Base_volume_Vb1=" + Base_volume_Vb1 + ", Base_volume_Vb2="
				+ Base_volume_Vb2 + ", Flow_Q1=" + Flow_Q1 + ", Flow_Q2="
				+ Flow_Q2 + "]";
	}
}
