package bkit4u.hoai.bcr.Model;

import android.bluetooth.BluetoothDevice;

/**
 * Created by hoai on 07/07/2015.
 */
public class BluetoothDeviceCollection
{
    BluetoothDevice[] mBluetoothDevices = new BluetoothDevice[0];

    public BluetoothDeviceCollection(BluetoothDevice device)
    {
        mBluetoothDevices = new BluetoothDevice[1];
        mBluetoothDevices[0] = device;
    }

    public BluetoothDevice[] getDevices()
    {
        return mBluetoothDevices;
    }

    public void append(BluetoothDevice device)
    {
            BluetoothDevice[] retDevices = new BluetoothDevice[mBluetoothDevices.length+1];
            for(int i = 0;i<mBluetoothDevices.length; i++)
            {
                retDevices[i] = mBluetoothDevices[i];
            }
            retDevices[mBluetoothDevices.length] = device;

            mBluetoothDevices = retDevices;
    }

    public Boolean isContain(BluetoothDevice device)
    {
        for (int i = 0; i < mBluetoothDevices.length; i++) {
            if (device.equals(mBluetoothDevices[i])) {
                return true;
            }
        }

        return false;
    }
}
