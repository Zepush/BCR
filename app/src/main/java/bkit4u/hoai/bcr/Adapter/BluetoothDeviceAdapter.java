package bkit4u.hoai.bcr.Adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import bkit4u.hoai.bcr.Model.BluetoothDeviceCollection;
import bkit4u.hoai.bcr.R;

/**
 * Created by hoai on 30/03/2015.
 */
public class BluetoothDeviceAdapter extends BaseAdapter
{
    BluetoothDeviceCollection mBluetoothCollection;

    @Override
    public int getCount() {
        return mBluetoothCollection.getDevices().length;
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return mBluetoothCollection.getDevices()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDevice mDevice;
        View rootView;

        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rootView = inflater.inflate(R.layout.list_row, null);

        TextView nameTxt = (TextView)rootView.findViewById(R.id.device_Name);
        TextView statusTxt = (TextView)rootView.findViewById(R.id.device_Status);

        mDevice = getItem(position);
        nameTxt.setText(mDevice.getName());
        if(mDevice.getBondState() == BluetoothDevice.BOND_BONDED)
        {
            statusTxt.setText("Paired");
        }
        else
        {
            statusTxt.setText("NotPair");
        }

        return rootView;
    }

    public void refresh(BluetoothDeviceCollection devices)
    {
        mBluetoothCollection = devices;
    }

    public void addDevice(BluetoothDevice device)
    {
        mBluetoothCollection.append(device);
        notifyDataSetChanged();
    }

    public Boolean contain(BluetoothDevice device)
    {
        if(mBluetoothCollection == null)
        {
            return false;
        }
        return mBluetoothCollection.isContain(device);
    }
}
