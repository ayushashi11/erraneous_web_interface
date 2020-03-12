package com.mycompany.myapp2;

import android.app.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;
import android.view.View;
import android.support.design.widget.*;
import android.support.v7.widget.Toolbar;
import java.util.*;
import android.view.*;
import android.content.*;
import android.*;
import android.support.v7.app.AppCompatActivity;
public class SemiMainActivity extends AppCompatActivity 
{
	public Stack<String> history=new Stack<String>();
	WebView webw;
	TextView stw;
	MWebInterface js;
	boolean firstcreate=true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_AppCompat_DayNight);
        setContentView(R.layout.main);
		Toolbar tlbr=(Toolbar)findViewById(R.id.my_toolbar);
		setSupportActionBar(tlbr);
		FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					backPressed();
					Snackbar.make(view, "Back", Snackbar.LENGTH_SHORT)
						.setAction("Action", null).show();
				}
			});
		webw=(WebView)findViewById(R.id.webview);
		stw=(TextView)findViewById(R.id.textview);
		webw.getSettings().setJavaScriptEnabled(true);
		webw.setWebViewClient(new WebViewClient() {
				public boolean shouldOverrideUrlLoading(WebView view, String url){
					// do your handling codes here, which url is the requested url
					// probably you need to open that url rather than redirect:
					history.push(url);
					stw.setText(history.toArray().toString());
					view.loadUrl(url);
					setTitle(view.getTitle());
					return false; // then it is not handled by default action
				}
			});
		js=new MWebInterface(this,this);
		webw.addJavascriptInterface(js,"droid");
		webw.evaluateJavascript("var alert=function(text){android.showToast(text,"+Toast.LENGTH_LONG+");}",null);
		webw.setWebChromeClient(new WebChromeClient(){
				@Override
				public boolean onJsAlert(WebView vw,String url,String mesaage,JsResult jsr){
					AlertDialog.Builder builder=new AlertDialog.Builder(SemiMainActivity.this);
					builder.setTitle(url);
					builder.setMessage(url);
					builder.setPositiveButton("ok",null);
					builder.create().show();
					return true;
				}
			});
		if(firstcreate)
			history.push("http://localhost:8000");
		webw.loadUrl(history.lastElement());
		setTitle(webw.getTitle());
		firstcreate=false;
    }

	public void backPressed()
	{
		// TODO: Implement this method
		if(history.size()==1){
			webw.clearView();
			super.onBackPressed();
			return;
		}
		history.pop();
		stw.setText(history.toArray().toString());
		webw.loadUrl(history.lastElement());
		setTitle(webw.getTitle());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater mi=getMenuInflater();
		mi.inflate(R.menu.menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Toast.makeText(getApplicationContext(),"menuclicked",Toast.LENGTH_SHORT)
			.show();
		switch(item.getItemId()){
			case R.id.item:
				Intent inte=new Intent(this,SettingsActivity.class);
				startActivity(inte);
				Toast.makeText(getApplicationContext(),"settings clicked",Toast.LENGTH_SHORT)
					.show();
				break;
			default:
				break;
		}
		return false;
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		js.resumeSensor();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		js.pauseSensor();
	}
}
