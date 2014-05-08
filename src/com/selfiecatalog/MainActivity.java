package com.selfiecatalog;



import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity {
	
	String savedImageDirectory = Environment.getExternalStorageDirectory() + File.separator + ".SelfieCat";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}
	
	//Open DisplayPicture activity (gallery) by clicking Gallery button.
	public void openLibrary(View v){
		startActivity(LibraryActivity.intentWithImageDirectory(this, savedImageDirectory));
		//Toast.makeText(getApplicationContext(), "Open Gallery", Toast.LENGTH_SHORT).show();
	}
	

	
	//Starts CameraProgActivity by clicking on Camera_Prog Button.
    public void startProgCameraActivity(View v){
		
		Intent intent = new Intent(this, CameraActivity.class);
		startActivity(intent);
	}
	

}

