package bkit4u.hoai.bcr.Recever;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;

/**
 * Created by hoai on 13/04/2015.
 */
public abstract class BluetoothReceiver extends BroadcastReceiver
{
    Context mContext;
    public BluetoothReceiver(Context context)
    {
        mContext = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String Action = intent.getAction();
        if(Action.equals(BluetoothDevice.ACTION_FOUND)){
            BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if(mDevice != null){
                onGotDevice(mDevice);
            }
        }
        else if(Action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
        {
            onPairDevice(intent);
        }
    }

    public void startReceiver()
    {
        IntentFilter Filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mContext.registerReceiver(this, Filter);
    }

    public void stopReceiver()
    {
        mContext.unregisterReceiver(this);
    }

    public abstract void onGotDevice(BluetoothDevice Device);
    public abstract void onPairDevice(Intent intent);
}
