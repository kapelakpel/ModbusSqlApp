package com.example.applicationmodbussql.AddClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileSettings {
    private String settingsFileName = "settings.txt";
    File myObj = new File(this.settingsFileName);

    public FileSettings() {
    }

    public  void saveToFile(String input[]){
        if (myObj.exists()) {
            writeFunction(input);
        }
        else{
            createFunction();
            writeFunction(input);
        }

    }

    public String[] readFromFile(){
        String[] output=new String [8];
        int i=0;
        try{
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                output[i] = myReader.nextLine();
                i++;
            }
        }
        catch (FileNotFoundException e) {
           output[0]="An error occurred.";
            e.printStackTrace();
        }
        return output;
    }

    private void createFunction(){
        try {
            File myObj = new File(this.settingsFileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void writeFunction(String[] input){
        try {
            FileWriter myWriter = new FileWriter(this.settingsFileName);
            for(int i =0; i<input.length;i++){
                myWriter.write(input[i]);
                myWriter.write(System.lineSeparator());
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
