package com.selfiecatalog;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraProgActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback, Camera.PreviewCallback, Camera.AutoFocusCallback
{
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;
    private Button shotBtn;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // set portrait view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // set full screen view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // set off title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_camera_prog);

        // the SurfaceView has name - SurfaceView01
        preview = (SurfaceView) findViewById(R.id.SurfaceView01);

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // buttons name is Button01
        shotBtn = (Button) findViewById(R.id.Button01);
        shotBtn.setText("Shot");
        shotBtn.setOnClickListener(this);
    }

    /* Checks if external storage is available for read and write 
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            //return true;
           
        }
        return false;
    }*/

    /* Checks if external storage is available to at least read 
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
            Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }*/
    
    @Override
    protected void onResume()
    {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (camera != null)
        {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        try
        {
            camera.setPreviewDisplay(holder);
            camera.setPreviewCallback(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Size previewSize = camera.getParameters().getPreviewSize();
        float aspect = (float) previewSize.width / previewSize.height;

        int previewSurfaceWidth = preview.getWidth();
        int previewSurfaceHeight = preview.getHeight();

        LayoutParams lp = preview.getLayoutParams();

        // set scale of preview

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        {
            // portrait view
            camera.setDisplayOrientation(90);
            lp.height = previewSurfaceHeight;
            lp.width = (int) (previewSurfaceHeight / aspect);
            ;
        }
        else
        {
            // landscape view
            camera.setDisplayOrientation(0);
            lp.width = previewSurfaceWidth;
            lp.height = (int) (previewSurfaceWidth / aspect);
        }

        preview.setLayoutParams(lp);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
    }

    @Override
    public void onClick(View v)
    {
        if (v == shotBtn)
        {
            
            // 	or autofocus
        	camera.autoFocus(this);
        	Toast toast1 = Toast.makeText(getApplicationContext(), 
      			   "Focus and take a shot", Toast.LENGTH_SHORT); 
      			toast1.show(); 
      			
      		// take shot
            //camera.takePicture(null, null, null, this);
            //Toast toast2 = Toast.makeText(getApplicationContext(), 
     			   //"Took picture", Toast.LENGTH_SHORT); 
     			//toast2.show(); 
            
        }
    }

    @Override
    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
    {
    	
    	
        // saves pics in folder /sdcard/CameraExample/
        // name of pic - System.currentTimeMillis()

        try
        {
            //File saveDir = new File("/sdcard/CameraExample/");
        	String root = Environment.getExternalStorageDirectory().toString();
            File saveDir = new File(root + "/.Selfie_cat_photos");    
            saveDir.mkdirs();
        	
            if (!saveDir.exists())
            {
                saveDir.mkdirs();
            }

            FileOutputStream os = new FileOutputStream(String.format(saveDir+"/%d.jpg", System.currentTimeMillis()));
            os.write(paramArrayOfByte);
            os.close();
            
            //try {
                //FileOutputStream out = new FileOutputStream(file);
                //finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                //out.flush();
                //out.close();

         //} catch (Exception e) {
           //     e.printStackTrace();
        // }
            
            Toast toast3 = Toast.makeText(getApplicationContext(), 
      			   "Picture saved in SD card", Toast.LENGTH_SHORT); 
      			toast3.show(); 
            
        }
        catch (Exception e)
        {
        	Toast toast = Toast.makeText(getApplicationContext(), 
        			   "Exeption", Toast.LENGTH_SHORT); 
        			toast.show(); 
        }

        // after we take shot , the preview turned off. need turn it on
        paramCamera.startPreview();
    }

    @Override
    public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
    {
        if (paramBoolean)
        {
            // if autofocus=true => take shot
            paramCamera.takePicture(null, null, null, this);
        }
    }

    @Override
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
    {
        // here we can add code for editing the preview
    }
}