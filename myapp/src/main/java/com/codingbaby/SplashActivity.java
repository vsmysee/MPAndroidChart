package com.codingbaby;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;


public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY = 3000;

    public DataHolder dataHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).request(new RequestCallback() {
            @Override
            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                if (!allGranted) {
                    Toast.makeText(SplashActivity.this, "你拒绝了权限，将不能朗读单词", Toast.LENGTH_LONG).show();
                }
            }
        });

        dataHolder = new DataHolder(this);

        CustomTextView.dataHolder = dataHolder;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this,
                        MainActivity.class);


                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }

        }, SPLASH_DISPLAY);
    }
}
