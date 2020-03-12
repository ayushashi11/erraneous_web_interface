package com.mycompany.myapp2;
import android.app.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;
import android.view.View;
import android.support.design.widget.*;
import android.widget.Toolbar;
import java.util.*;
import android.view.*;
public class SettingsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_AppCompat_DayNight);
		setContentView(R.layout.settings);
		FloatingActionButton fsab = findViewById(R.id.fab_settings);
		fsab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onBackPressed();
				}
			});
	}
	
}
