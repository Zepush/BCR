package bkit4u.hoai.bcr;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import bkit4u.hoai.bcr.Controller.BluetoothConnectionController;
import bkit4u.hoai.bcr.Controller.PreferencesController;
import bkit4u.hoai.bcr.Model.SendDataModel;
import bkit4u.hoai.bcr.View.Fragment.CompletedChooseDeviceCallback;
import bkit4u.hoai.bcr.View.Fragment.CompletedSettingsCallback;
import bkit4u.hoai.bcr.View.Fragment.PopupConnectFragment;
import bkit4u.hoai.bcr.View.Fragment.PopupSettingFragment;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener
{

    final int PICK = 1;
    final int DROP = 2;
    final int CONNECTED = 1;
    final int DISCONNECT = 2;

    BluetoothConnectionController mBluetoothConnectionController;
    PreferencesController mPreferencesController;
    SendDataModel mSendData;
    Button btnForward, btnBackward, btnTurnLeft, btnTurnRight, btnUp,btnDown,btnPickDrop;
    int mPickDropStatus = 0;
    int mConnectStatus = DISCONNECT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        initUI();
        mBluetoothConnectionController = new BluetoothConnectionController()
        {
            @Override
            public void onConnected()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mConnectStatus = CONNECTED;
                        MainActivity.this.getSupportActionBar().setTitle("Connected");
                    }
                });

            }

            @Override
            public void onDisConnected()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mConnectStatus = DISCONNECT;
                        MainActivity.this.getSupportActionBar().setTitle("Unconnected");
                    }
                });
            }
        };

        mPreferencesController = new PreferencesController(MainActivity.this);
        mSendData = mPreferencesController.restoringPreferences();
        mPickDropStatus = PICK;

        getSupportActionBar().setTitle("Unconnected");
    }

    private void initUI()
    {
        btnForward = (Button)findViewById(R.id.btn_fw);
        btnForward.setOnTouchListener(this);

        btnBackward = (Button)findViewById(R.id.btn_bw);
        btnBackward.setOnTouchListener(this);

        btnTurnLeft = (Button)findViewById(R.id.btn_left);
        btnTurnLeft.setOnTouchListener(this);

        btnTurnRight = (Button)findViewById(R.id.btn_right);
        btnTurnRight.setOnTouchListener(this);

        btnUp = (Button)findViewById(R.id.btn_up);
        btnUp.setOnTouchListener(this);

        btnDown = (Button)findViewById(R.id.btn_down);
        btnDown.setOnTouchListener(this);

        btnPickDrop = (Button)findViewById(R.id.btn_pd);

    }

    public void pickAndDrop(View v)
    {
        if(mConnectStatus == CONNECTED)
        {
            switch (mPickDropStatus)
            {
                case PICK:
                    mPickDropStatus = DROP;
                    btnPickDrop.setBackgroundResource(R.drawable.btn_drop);
                    mBluetoothConnectionController.sendDataToDevice(mSendData.getPickUpString());
                    break;
                case DROP:
                    mPickDropStatus = PICK;
                    btnPickDrop.setBackgroundResource(R.drawable.btn_pick);
                    mBluetoothConnectionController.sendDataToDevice(mSendData.getDropDownString());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if(mConnectStatus == CONNECTED)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                switch (v.getId())
                {
                    case R.id.btn_fw:
                        mBluetoothConnectionController.sendDataToDevice(mSendData.getGoForwardString());
                        break;
                    case R.id.btn_bw:
                        mBluetoothConnectionController.sendDataToDevice(mSendData.getGoBackwardString());
                        break;
                    case R.id.btn_left:
                        mBluetoothConnectionController.sendDataToDevice(mSendData.getTurnLeftString());
                        break;
                    case R.id.btn_right:
                        mBluetoothConnectionController.sendDataToDevice(mSendData.getTurnRightString());
                        break;
                    case R.id.btn_up:
                        mBluetoothConnectionController.sendDataToDevice(mSendData.getGoUpString());
                        break;
                    case R.id.btn_down:
                        mBluetoothConnectionController.sendDataToDevice(mSendData.getGoDownString());
                        break;
                    default:
                        break;
                }

                return false;
            }
            else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                mBluetoothConnectionController.sendDataToDevice(mSendData.getStopString());
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_layout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_connect:
                PopupConnectFragment.getInstance().setOnCompletedChooseDeviceCallBack(new CompletedChooseDeviceCallback()
                {
                    @Override
                    public void onCompletedChooseDevice(BluetoothDevice device)
                    {
                        Log.e("Connecting to device",device.getName()+"/"+device.getAddress());
                        mBluetoothConnectionController.startConnect(device.getAddress());
                    }
                });

                PopupConnectFragment.getInstance().show(getFragmentManager(), "CONNECTPOPUP");
                break;
            case R.id.action_settings:
                PopupSettingFragment.getInstance().setCompletedSettingsCallback(new CompletedSettingsCallback()
                {
                    @Override
                    public void onCompletedSettingsAndGetSendDataModel(SendDataModel model)
                    {
                        mSendData = model;
                    }
                });
                PopupSettingFragment.getInstance().show(getFragmentManager(), "SETTINGPOPUP");
                break;
            case R.id.action_disconnect:
                if(mConnectStatus == CONNECTED)
                {
                    mBluetoothConnectionController.stopConnect();
                    mConnectStatus = DISCONNECT;
                    getSupportActionBar().setTitle("Unconnected");
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
