# BCR
An android app use to control arduino board via module bluetooth HC-05.
# How to use this App

  After install this app you can use this to control your arduino board.
  
  Firstly, connect your phone with your module bluetooth with connect action in menu bar.
  
  Then, when you press buttons, your arduino board will received data and you can setting your sending data with setting action in menubar.
  
  You can disconnect with disconnect action in menubar.
  
# How to read Code
* BluetoothReceiver:

    BluetoothReceiver is extended from BroadcastReceiver to discovery bluetooth devices nearby and detect when a device is paired 
    with phone.
    
* BluetoothConnectionController:

  BluetoothConnectionController use to control the connection with module bluetooth. We use two thread to connect with module bluetooth:
  * connectThread:  Create a socket by MAC address of module bluetooth.
  * connectedThread :   Create inputstream and output stream to get and send data to module bluetooth.
* PreferencesController:

  PreferenceController use to save and reload our setting for sending data.

# Reference

  How to connect your arduino board with module bluetooth HC-05
  
  Tx pin (HC-05) --> Rx pin (Arduino Board).
  
  Rx pin (HC-05) --> Tx pin (Arduino Board).
  
  GND pin (HC-05) --> GND pin (Arduino Board).
  
  VCC pin (HC-05) --> VCC 5V pin (Arduino Board).
