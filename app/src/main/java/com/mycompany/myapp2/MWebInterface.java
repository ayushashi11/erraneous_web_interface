package com.mycompany.myapp2;
import android.content.*;
import android.webkit.*;
import android.widget.*;
import android.app.*;
import android.support.v4.app.*;
import android.*;
import android.hardware.*;
import org.json.*;
import java.util.*;

public class MWebInterface
{
	Activity mActivity;
	Context mContext;
	MWebSensor mSense;
	SensorManager sm;
	boolean sensorActive;
	boolean sensorActivated;
	MWebInterface(Activity activty,Context context){
		mContext=context;
		mActivity=activty;
		mSense=new MWebSensor();
	}
	@JavascriptInterface
	public void showToast(String toast,int dur){
		Toast.makeText(mContext,toast,dur).show();
	}
	@JavascriptInterface
	public boolean requestSensors(){
		ActivityCompat.requestPermissions(mActivity,new String[]{Manifest.permission_group.SENSORS},100);
		return true;
	}
	@JavascriptInterface
	public JSONObject testSensors(){
		if(!sensorActive){
			sm=(SensorManager)mActivity.getSystemService(Context.SENSOR_SERVICE);
			Sensor s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sm.registerListener(mSense,s,sm.SENSOR_DELAY_NORMAL);
			sensorActive=true;
		}
		if(true){
			JSONArray jslis=new JSONArray();
			for(float el:mSense.result){
				try{
					jslis.put((double)el);
				}catch(JSONException e){
					showToast(e.getMessage(),1000);
				}
			}
			try{showToast("android:"+jslis.get(0),1000);}catch(JSONException e){showToast("err",1000);}
			JSONObject jsobj=new JSONObject();
			try{jsobj.put("data",jslis);}catch(JSONException e){showToast(e.getMessage(),1000);}
			return jsobj;
		}
		return new JSONObject();
	}
	public void pauseSensor(){
		if(sensorActive){
			sm.unregisterListener(mSense);
			sensorActive=false;
		}
	}
	public void resumeSensor(){
		//TODO
	}
}
class MWebSensor implements SensorEventListener
{
	float[] result;
	public MWebSensor(){
		this.result=new float[]{};
	}
	@Override
	public void onSensorChanged(SensorEvent p1)
	{
		this.result=p1.values;
	}

	@Override
	public void onAccuracyChanged(Sensor p1, int p2)
	{
		return;
	}
}
