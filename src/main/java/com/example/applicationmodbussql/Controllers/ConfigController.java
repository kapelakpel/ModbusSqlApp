package com.example.applicationmodbussql.Controllers;

import com.example.applicationmodbussql.AddClasses.FileSettings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConfigController {

    private FileSettings fileSettings = new FileSettings();
    private double x;
    private double y;

    @FXML
    private Button closeButtonServer;
    @FXML
    private Button closeButtonModbus;
    @FXML
    private TextField textFieldSQLServer;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldDateBaseName;
    @FXML
    private TextField textFieldModbusIp;
    @FXML
    private TextField textFieldModbusPort;
    @FXML
    private TextField textFieldDataLength;
    @FXML
    private TextField textFieldStartAddress;
    @FXML
    private Text textInvalidModbusPort;
    @FXML
    private Text textInvalidModbusIP;
    @FXML
    private Text textInvalidModbusDataLength;
    @FXML
    private Text textInvalidModbusStartAddress;
    @FXML
    private TabPane tabPaneApp;

    @FXML
    public void onTabPaneDragDetected(MouseEvent event){
        Stage stage = (Stage) tabPaneApp.getScene().getWindow();
        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);

    }
    @FXML
    public void onTabPanePressed(MouseEvent event){
        x = event.getX();
        y = event.getY();
    }

    @FXML
    public void onTextFieldPLCIpClick(){
        textInvalidModbusIP.setVisible(false);
        textFieldModbusIp.clear();
    }
    @FXML
    public void onTextFieldPLCPortClick(){
        textInvalidModbusPort.setVisible(false);
        textFieldModbusPort.clear();
    }
    @FXML
    public void onTextFieldDataLengthClick(){
        textInvalidModbusDataLength.setVisible(false);
        textFieldDataLength.clear();
    }
    @FXML
    public void onTextFieldStartAddressClick(){
        textInvalidModbusStartAddress.setVisible(false);
        textFieldStartAddress.clear();
    }

    @FXML
    public void onCloseButtonServerClick(){
    Stage obj = (Stage) closeButtonServer.getScene().getWindow();
    obj.close();
    }
    @FXML
    public void onCloseButtonModbusClick(){
        Stage obj = (Stage) closeButtonModbus.getScene().getWindow();
        obj.setOnCloseRequest(event->{System.out.println("Closing");});
        obj.close();
    }
    @FXML
    public void onSaveButtonModbusClick(){
        if(this.savePropertiesModbus())
            this.onCloseButtonServerClick();
    }
    @FXML
    public void onSaveButtonServerClick(){
        this.savePropertiesServer();
        this.onCloseButtonModbusClick();
    }

    private void savePropertiesServer(){
        String[] settings = new String[8];
        settings= fileSettings.readFromFile();

        settings[0]=textFieldSQLServer.getText(); //przypisanie nowych wartosci do poszcegolnych elementow arraya
        settings[1]=textFieldUsername.getText();
        settings[2]=textFieldPassword.getText();
        settings[3]=textFieldDateBaseName.getText();

        fileSettings.saveToFile(settings); //zapisanie pomocniczego arraya do pliku
    }
    private boolean savePropertiesModbus(){
        boolean isValid;
        String[] settings = new String[8];
        settings= fileSettings.readFromFile();
        if(isIPValid(textFieldModbusIp.getText())){
            settings[4]=textFieldModbusIp.getText();
            isValid = true;
        }
        else{
            textInvalidModbusIP.setVisible(true);
            isValid = false;
        }
        if(isNumberValid(textFieldModbusPort.getText())){
            settings[5]= textFieldModbusPort.getText();
            isValid = true;
        }
        else{
            textInvalidModbusPort.setVisible(true);
            isValid=false;
        }
        if(isNumberValid(textFieldDataLength.getText())){
            settings[6]=textFieldDataLength.getText();
            isValid = true;
        }
        else{
            textInvalidModbusDataLength.setVisible(true);
            isValid = false;
        }
        if(isNumberValid(textFieldStartAddress.getText())){
            settings[7]=textFieldStartAddress.getText();
            isValid = true;
        }
        else{
            textInvalidModbusStartAddress.setVisible(true);
            isValid = false;
        }
        if(isValid)
            fileSettings.saveToFile(settings); //zapisanie pomocniczego arraya do pliku
        return isValid;
    }
    private boolean isNumberValid(String str){
        return str.matches("[0-9]+");
    }
    private boolean isIPValid(String str){
        return str.matches("[0-9.]+");
    }
}
