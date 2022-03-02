package com.example.applicationmodbussql.AddClasses;

public class Connection {
    private SQLConnection sqlConnection = new SQLConnection();
    private MODBUSConnection modbusConnection = new MODBUSConnection();
    private boolean connected;
    private int arrayLength;
    private int startingAdress;

    public int getArrayLength() {
        return arrayLength;
    }

    public void setArrayLength(int arrayLength) {
        this.arrayLength = arrayLength;
    }

    public int getStartingAdress() {
        return startingAdress;
    }

    public void setStartingAdress(int startingAdress) {
        this.startingAdress = startingAdress;
    }

    public Connection(SQLConnection sqlConnection, MODBUSConnection modbusConnection) {
        this.sqlConnection = sqlConnection;
        this.modbusConnection = modbusConnection;
    }

    public boolean isConnected(){

        return this.connected;
    }

    public boolean connection(){

        if(sqlConnection.sqlConnect()==true &&  modbusConnection.modbusConnect()==true){
            this.connected = true;
            return this.connected;
        }
        else{
            this.connected = false;
            return this.connected;
        }

    }
    public boolean disconnection(){
        if(sqlConnection.sqlDisconnect()==true && modbusConnection.modbusDisconnect()==true){
            this.connected = false;
            return true;
        }
        else{
            return false;
        }
    }

    ////// Odczyt po modbusie do array'a (wybor wielkosci arraya mozliwa edycja),
    ////// funkcja musi posiadac ten sam parametr arrayLength co wielkosc arraya,
    ////// startingAddress to poczatkowy adres modbusa
    public void repeatable(){
        int[] valuesFromModbus = new int[this.arrayLength];
        valuesFromModbus = modbusConnection.readIR(this.arrayLength,this.startingAdress);
        sqlConnection.sqlExecuteQuery(valuesFromModbus);
    }
}
