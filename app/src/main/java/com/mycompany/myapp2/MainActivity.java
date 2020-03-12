package com.mycompany.myapp2;

import android.app.*;
import android.os.*;
import android.content.*;
//import android.support.v7.app.*;
public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_AppCompat_DayNight);
		//SemiMainActivity ac=new SemiMainActivity();
		Intent init=new Intent(this,SemiMainActivity.class);
		startActivity(init);
	}
}
