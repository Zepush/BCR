package bkit4u.hoai.bcr.Controller;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import bkit4u.hoai.bcr.Adapter.BluetoothAdapterExtend;
import bkit4u.hoai.bcr.AppConstant.AppConstant;

/**
 * Created by hoai on 08/07/2015.
 */
public abstract class BluetoothConnectionController
{
    ConnectThread mConnectThread;
    ConnectedThread mConnectedThread;

    public void startConnect(String macAdress)
    {
        mConnectThread = new ConnectThread(macAdress);
        mConnectThread.start();
    }

    public void stopConnect()
    {
        mConnectThread.cancel();
        mConnectedThread.cancel();
    }

    public void sendDataToDevice(String data)
    {
        mConnectedThread.write(data);
    }

    public abstract void onConnected();
    public abstract void onDisConnected();

    /*Connect Thread*/
    public class ConnectThread extends  Thread{
        private final BluetoothSocket mSocket;
        private final BluetoothDevice mDevice;

        public ConnectThread(String macAddress) {
            BluetoothSocket tmp = null;
            mDevice = BluetoothAdapterExtend.getInstance().getRemoteDevice(macAddress);

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also use by server code
                tmp = mDevice.createRfcommSocketToServiceRecord(AppConstant.MY_UUID);
            } catch (IOException e) {
            }
            mSocket = tmp;
        }
        public void run() {
            // Cancel discovery because it will slow button_down connection
            BluetoothAdapterExtend.getInstance().stopDiscoveryDevice();

            try {
                // Connect the device through the socket. This will block until
                // it succeeds or throws an exception
                mSocket.connect();
            } catch (IOException connectException)
            {
                // Unable to connect; close the socket and get out
                Log.i("disconnect", "unable to connect");
                onDisConnected();
                try {
                    mSocket.close();
                } catch (IOException closeException)
                {
                    //Log.i(tag, "close fail");
                }
                return;
            }

            mConnectedThread = new ConnectedThread(mSocket);
            mConnectedThread.start();
            Log.e("Connected","!");
            onConnected();
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /*ConnectedThread will run after connected with device*/
    public class ConnectedThread extends Thread
    {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temporary objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024]; // buffer store for the stream
            int bytes; // bytes returned from read();

            // Keep listening to the InputStream until an exception occurs
            while (!Thread.interrupted()) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                } catch (IOException e)
                {
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
                Log.e("Write", new String(bytes));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void write(String data)
        {
            byte[] bytes = data.getBytes();
            write(bytes);
        }

        public void cancel()
        {
            try
            {
                mmInStream.close();
                mmOutStream.close();
                mmSocket.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }




}
