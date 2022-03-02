package com.example.applicationmodbussql.Controllers;

import com.example.applicationmodbussql.AddClasses.Connection;
import com.example.applicationmodbussql.AddClasses.FileSettings;
import com.example.applicationmodbussql.AddClasses.MODBUSConnection;
import com.example.applicationmodbussql.AddClasses.SQLConnection;
import com.example.applicationmodbussql.ModbusSQLApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ModbusSQLController {

    private Timer t = new Timer();
    private TimerTask tt ;
    private SQLConnection sqlConnection = new SQLConnection();
    private MODBUSConnection modbusConnection = new MODBUSConnection();
    private Connection connection = new Connection(sqlConnection, modbusConnection);
    private boolean repeatable;
    private FileSettings fileSettings = new FileSettings();
    private Stage otherStage;
    private double x;
    private double y;

    @FXML
    private BorderPane borderPaneApp;
    @FXML
    private Text textModbusIP;
    @FXML
    private Text textModbusPort;
    @FXML
    private Text textServerUrl;
    @FXML
    private Text textDateBaseName;
    @FXML
    private Text textUsername;
    @FXML
    private Text textPassword;
    @FXML
    private Button buttonMinimize;
    @FXML
    private Text textArrayLength;
    @FXML
    private Text textStartingAddress;
    @FXML
    private Button buttonExit;
    @FXML
    private Button buttonConfigurationWindow;

    public void initialize(){
        readTextLabels();
        buttonExit.setPickOnBounds(true);
        buttonConfigurationWindow.setPickOnBounds(true);
    }

    @FXML
    public void onBorderPaneDragDetected(MouseEvent event){
        Stage stage = (Stage) borderPaneApp.getScene().getWindow();
        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);
    }
    @FXML
    public void onBorderPanePressed(MouseEvent event){
        x = event.getX();
        y = event.getY();
    }
    @FXML
    public void onButtonConfigClick() throws IOException {
        if(otherStage==null){
            otherStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(ModbusSQLApplication.class.getResource("configView.fxml"));
            Parent root=fxmlLoader.load();
            otherStage.setScene(new Scene(root, 200, 370));
            otherStage.setTitle("Configuration");
            otherStage.initStyle(StageStyle.UNDECORATED);
            otherStage.showAndWait();
            readTextLabels();
            }
        else if(otherStage.isShowing()){
            otherStage.toFront();
        }else{
            otherStage.show();
        }
    }
    @FXML
    public void onConnectButtonClick() {
        tt = new TimerTask(){
            @Override
            public void run() {
                if (connection.connection() == true) {
                    connection.repeatable();
                    System.out.println("Ping");
                } else {
                    System.out.println("NOT OK");
                }
                repeatable =true;
            }
        };
        t.schedule(tt,0,5000);
    }
    @FXML
    public void onDisconnectButtonClick(){

        tt.cancel();
        repeatable =false;
    }
    @FXML
    public void onButtonExitClick(){
        this.safeExit();
    }
    @FXML
    public void onButtonMinimizeClick(){
        Stage obj = (Stage) buttonMinimize.getScene().getWindow();
        obj.setIconified(true);
    }


    private void safeExit(){
        if(repeatable){
            tt.cancel();
            t.cancel();
        }

        if(connection.isConnected())
            connection.disconnection();

        Platform.exit();
        System.exit(0);

    }
    private void setTextModbusIP() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        if(isIPValid(defaultValue[4])){
            modbusConnection.setIpAddress((String)defaultValue[4]);
            textModbusIP.setText(defaultValue[4]);
        }
        else{
            modbusConnection.setIpAddress("0.0.0.0");
            textModbusIP.setText("0.0.0.0");
        }

        this.textModbusIP = textModbusIP;
    }
    private void setTextModbusPort() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        if(isNumberValid(defaultValue[5])){
            modbusConnection.setPort(Integer.parseInt(defaultValue[5]));
            textModbusPort.setText(defaultValue[5]);
        }
        else{
            modbusConnection.setPort(Integer.parseInt("0"));
            textModbusPort.setText("0");
        }
        this.textModbusPort = textModbusPort;
    }
    private void setTextServerUrl() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        String url = defaultValue[0];
        sqlConnection.setUrl(defaultValue[0]);
        textServerUrl.setText(defaultValue[0]);
        this.textServerUrl = textServerUrl;
    }
    private void setTextDateBaseName() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        sqlConnection.setDateBase((String)defaultValue[3]);
        textDateBaseName.setText(defaultValue[3]);
        this.textDateBaseName = textDateBaseName;
    }
    private void setTextUsername() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        sqlConnection.setUser((String) defaultValue[1]);
        textUsername.setText(defaultValue[1]);

        this.textUsername = textUsername;
    }
    private void setTextPassword() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        sqlConnection.setPassword((String)defaultValue[2]);
        textPassword.setText(defaultValue[2]);
        this.textPassword = textPassword;
    }
    private void setReadArrayLength() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        if(isNumberValid(defaultValue[6])){
            connection.setArrayLength(Integer.parseInt(defaultValue[6]));
            textArrayLength.setText(defaultValue[6]);
        }
        else{
            connection.setArrayLength(Integer.parseInt("0"));
            textArrayLength.setText("0");
        }

        this.textArrayLength = textArrayLength;
    }
    private void setReadStartingAddress() {
        String[] defaultValue = new String[8];
        defaultValue= fileSettings.readFromFile();
        if(isNumberValid(defaultValue[7])){
            connection.setStartingAdress(Integer.parseInt(defaultValue[7]));
            textStartingAddress.setText(defaultValue[7]);
        }
        else{
            connection.setStartingAdress(Integer.parseInt("0"));
            textStartingAddress.setText("0");
        }

        this.textStartingAddress = textStartingAddress;
    }
    private void readTextLabels(){
        setTextModbusIP();
        setTextModbusPort();
        setTextServerUrl();
        setTextDateBaseName();
        setTextUsername();
        setTextPassword();
        setReadArrayLength();
        setReadStartingAddress();
    }
    private boolean isNumberValid(String str){
        return str.matches("[0-9]+");
    }
    private boolean isIPValid(String str){
        return str.matches("[0-9.]+");
    }

}