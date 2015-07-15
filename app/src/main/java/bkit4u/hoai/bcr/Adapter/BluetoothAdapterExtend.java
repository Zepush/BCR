package bkit4u.hoai.bcr.Adapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * Created by hoai on 08/07/2015.
 */
public class BluetoothAdapterExtend
{
    private static BluetoothAdapterExtend mInstance = null;
    BluetoothAdapter mDefaultAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothAdapterExtend(){
    }
    public static BluetoothAdapterExtend getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new BluetoothAdapterExtend();
        }

        return mInstance;
    }
    public boolean isEnabled()
    {
        return mDefaultAdapter.isEnabled();
    }

    public void startDiscoveryDevice()
    {
        mDefaultAdapter.startDiscovery();
    }

    public void refreshDiscoveryDevice()
    {
        mDefaultAdapter.cancelDiscovery();
        mDefaultAdapter.startDiscovery();
    }

    public void stopDiscoveryDevice()
    {
        if(mDefaultAdapter.isDiscovering())
        {
            mDefaultAdapter.cancelDiscovery();
        }
    }

    public BluetoothDevice getRemoteDevice(String macAddress)
    {
        return mDefaultAdapter.getRemoteDevice(macAddress);
    }

}
