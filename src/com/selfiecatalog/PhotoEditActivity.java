package com.selfiecatalog;

import java.io.FileInputStream;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoEditActivity extends Activity {

ImageView imageA;
Bitmap bitmapA;



@Override
protected void onCreate(Bundle savedInstanceState) {
	 // TODO Auto-generated method stub
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.activity_photo_edit);
	      imageA = (ImageView)findViewById(R.id.imagea);
	      	
	      
	      
	      Intent extras = getIntent(); 
	      String path = extras.getStringExtra("prompt");
Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();

bitmapA = BitmapFactory.decodeFile(path);

Matrix matrix = new Matrix();
matrix.postRotate(90);
Bitmap rotated = Bitmap.createBitmap(bitmapA, 0, 0, bitmapA.getWidth(), bitmapA.getHeight(),matrix, true); 

ImageView img = new ImageView(this);
img.setImageBitmap(rotated);
imageA.setImageBitmap(rotated);
	
}
}