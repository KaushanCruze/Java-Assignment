package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class controller {
    ToggleGroup toggleGroup;
    functions data = new functions();
    int column=-1;

    @FXML
    private VBox colNames;

    @FXML
    private TextField pathString;

    @FXML
    private TextArea resultView;

    @FXML
    private TableView<ObservableList<String>> dataTable;


    @FXML
    public void initialize() {
      
    }
    @FXML
    protected void startSort() {

        if(column!=-1){
            dataTable.getItems().clear();
    
            Thread thread = new Thread(() -> {
                String message = "";
                long start = System.nanoTime(); //performance check
                ArrayList<functions.SingleDataFormat> resultShell = data.shellSort(column);
                double shellTime = (System.nanoTime() - start) / 1e6;
                message += "Shell sort: " + String.format("%.2f", shellTime) + " ms\n";
               
    
    
                start = System.nanoTime();
                ArrayList<functions.SingleDataFormat> resultMerge = data.mergeSort(column);
                double mergeTime = (System.nanoTime() - start) / 1e6;
                message += "Merge sort: " + String.format("%.2f", mergeTime) + " ms\n";
               
    
                start = System.nanoTime();
                ArrayList<functions.SingleDataFormat> resultHeap = data.heapSort(column);
                double heapTime = (System.nanoTime() - start) / 1e6;
                message += "Heap sort: " + String.format("%.2f", heapTime) + " ms\n";
             
    
                start = System.nanoTime();
                ArrayList<functions.SingleDataFormat> resultQuick = data.quickSort(column);
                double quickTime = (System.nanoTime() - start) / 1e6;
                message += "Quick sort: " + String.format("%.2f", quickTime) + " ms\n";
              
    
                start = System.nanoTime();
                ArrayList<functions.SingleDataFormat> resultInsertion = data.insertionSort(column);
                double insertionTime = (System.nanoTime() - start) / 1e6;
                message += "Insertion sort: " + String.format("%.2f", insertionTime) + " ms";
              
                resultView.setText(message); //display
    
                for (int i = 0; i < resultShell.size(); i++) {
                    ObservableList<String> row = FXCollections.observableArrayList(Arrays.asList(data.data.get(resultShell.get(i).index).value));
                    dataTable.getItems().add(row);
                }
    
                
            });
            thread.start();
            
        }
    }
//csv file upload
    @FXML
    protected void handleFileSelection() {
        data=new functions();
        colNames.getChildren().clear();
        dataTable.getColumns().clear();
        dataTable.getItems().clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        File selectedFile = fileChooser.showOpenDialog(colNames.getScene().getWindow());
        if(selectedFile!=null){
            String fileName = selectedFile.getName();
            if(!fileName.endsWith(".csv")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("File not valid, Please select a csv file !");
                alert.showAndWait();
                return;
            }
        }

        if (selectedFile != null) {
            pathString.setText(selectedFile.getAbsolutePath());
            pathString.setDisable(true);
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                String headersLine = br.readLine();
                String[] headers = headersLine.split(",");
                dataTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
                for (int i = 0; i < headers.length; i++) {

                    final int colIndex = i;
                    javafx.scene.control.TableColumn<ObservableList<String>, String> column = new javafx.scene.control.TableColumn<>(headers[i]);
                    column.setCellValueFactory(cellData ->
                            new SimpleStringProperty(cellData.getValue().get(colIndex))
                    );
                    column.setSortable(false);
                    dataTable.getColumns().add(column);
                }
                boolean firstLine = true;
                int x=0;
                ToggleGroup toggleGroup=new ToggleGroup();
                while ((line = br.readLine()) != null) {
                    String[] rowValues = line.split(",");
                    data.data.add(new Data(x, rowValues));
                    x++;

                    if(firstLine){
                        for(int i = 0; i < rowValues.length; i++){
                            try{
                                Double.parseDouble(rowValues[i]);
                                int finalI = i;
                                RadioButton radioButton=new RadioButton();
                                radioButton.setText(headers[finalI]);
                                radioButton.setToggleGroup(toggleGroup);
                                radioButton.setOnAction(event -> {
                                    column= finalI;
                                    System.out.println(column);
                                });
                                colNames.getChildren().add(radioButton);
                            
                            }catch(NumberFormatException e){
                            }
                        }
                        firstLine = false;
                    }
                    ObservableList<String> row = FXCollections.observableArrayList(Arrays.asList(rowValues));
                    dataTable.getItems().add(row);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}