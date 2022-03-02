package com.example.applicationmodbussql.AddClasses;

import de.re.easymodbus.modbusclient.ModbusClient;

public class MODBUSConnection {
    private String ipAddress="";
    private int port=0;

    public MODBUSConnection(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public MODBUSConnection() {

    }

    ModbusClient modbusClient = new ModbusClient();

    public String getIpAddress(){

        return ipAddress;
    }

    public void setIpAddress(String ipAddress){

        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean modbusConnect() {
        try {
            modbusClient.Connect(this.ipAddress,this.port);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean modbusDisconnect() {
        if (modbusClient.isConnected()) {
            try {
                modbusClient.Disconnect();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        else
            return false;
    }

    public int[] readIR(int arrayLength, int startingAddress) {
        int[] valuesIR = new int[arrayLength];
        try {
            valuesIR = modbusClient.ReadInputRegisters(startingAddress, arrayLength);
        } catch (Exception e) {
            System.out.println("Read Input Register failed");
        }
        return valuesIR;
    }
}
