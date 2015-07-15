package bkit4u.hoai.bcr.View.Fragment;

import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;

import bkit4u.hoai.bcr.Adapter.BluetoothAdapterExtend;
import bkit4u.hoai.bcr.Adapter.BluetoothDeviceAdapter;
import bkit4u.hoai.bcr.Model.BluetoothDeviceCollection;
import bkit4u.hoai.bcr.R;
import bkit4u.hoai.bcr.Recever.BluetoothReceiver;

/**
 * Created by hoai on 09/07/2015.
 */
public class PopupConnectFragment extends DialogFragment
{
    private static PopupConnectFragment mInstance = null;

    ListView mListView;
    BluetoothAdapterExtend mBluetoothAdapter;
    BluetoothDeviceAdapter DevicesAdapter;
    BluetoothReceiver mBluetoothReceiver;
    Boolean isRefresh = true;
    CompletedChooseDeviceCallback mcallback;

    private PopupConnectFragment()
    {

    }

    public static PopupConnectFragment getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new PopupConnectFragment();
        }

        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View rootView = inflater.inflate(R.layout.connect_dialog_layout, container,
                false);
        mListView = (ListView)rootView.findViewById(R.id.list1);

        Button refreshView = (Button) rootView.findViewById(R.id.dialog_refresh_btn);
        refreshView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mBluetoothAdapter.refreshDiscoveryDevice();
                DevicesAdapter = new BluetoothDeviceAdapter();
                DevicesAdapter.notifyDataSetChanged();
                isRefresh = true;
            }
        });


        mBluetoothAdapter = BluetoothAdapterExtend.getInstance();

        DevicesAdapter = new BluetoothDeviceAdapter();

        final DialogFragment targetDialog = this;

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                mBluetoothAdapter.stopDiscoveryDevice();
                BluetoothDevice device = DevicesAdapter.getItem(position);

                if(device.getBondState() == BluetoothDevice.BOND_BONDED)
                {
                    if(mcallback !=null)
                    {
                        mcallback.onCompletedChooseDevice(device);
                        targetDialog.dismiss();
                    }
                }
                else
                {
                    try {
                        Method method = device.getClass().getMethod("createBond", (Class[]) null);
                        method.invoke(device, (Object[]) null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mInstance = this;
        initBluetoothReceiver();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mBluetoothReceiver.startReceiver();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.startDiscoveryDevice();
        } else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 1);
        }
    }

    private void initBluetoothReceiver() {
        mBluetoothReceiver = new BluetoothReceiver(getActivity())
        {
            @Override
            public void onGotDevice(BluetoothDevice Device)
            {
                Log.e("Got device", Device.getName() + "/" + Device.getAddress());
                if(!DevicesAdapter.contain(Device))
                {
                    if(isRefresh)
                    {
                        BluetoothDeviceCollection mDevices = new BluetoothDeviceCollection(Device);
                        DevicesAdapter.refresh(mDevices);
                        isRefresh = false;
                    }
                    else
                    {
                        DevicesAdapter.addDevice(Device);
                    }

                    mListView.setAdapter(DevicesAdapter);
                }
            }

            @Override
            public void onPairDevice(Intent intent)
            {
                final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState	= intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING)
                {
                    Toast.makeText(getActivity(), "Paired..", Toast.LENGTH_SHORT).show();
                    mBluetoothAdapter.refreshDiscoveryDevice();
                    DevicesAdapter = new BluetoothDeviceAdapter();
                    DevicesAdapter.notifyDataSetChanged();
                    isRefresh = true;
                }
            }

        };
    }
    @Override
    public void onStop()
    {
        super.onStop();
        mBluetoothReceiver.stopReceiver();
        isRefresh = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            getActivity().finish();
        } else{
            mBluetoothAdapter.startDiscoveryDevice();
            Log.i("onActivityResult", "registerReceiver");
        }
    }

    public void setOnCompletedChooseDeviceCallBack(CompletedChooseDeviceCallback callBack)
    {
        this.mcallback = callBack;
    }

}
