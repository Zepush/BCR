package bkit4u.hoai.bcr;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import bkit4u.hoai.bcr.AppConstant.AppConstant;


public class StartActivity extends Activity
{

    ProgressBar StartProgr;
    int progress=0;
    Handler h=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        StartProgr=(ProgressBar)findViewById(R.id.myProgress);

        new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i=0;i<=5;i++){
                    progress+=20;
                    h.post(new Runnable() {

                        @Override
                        public void run() {
                            StartProgr.setProgress(progress);
                            if(progress==StartProgr.getMax()){
                                goMainActivity();
                            }
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstant.FINISH_APP){
            Log.e("Result finish", "Finish app");
            finish();
        }
    }

    private void goMainActivity() {
        Intent myIntent=new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, AppConstant.FINISH_APP);
    }
}
