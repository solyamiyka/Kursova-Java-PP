package com.example.kurs;

import com.example.kurs.db.DataBase;
import com.example.kurs.taxes.Income;
import com.example.kurs.taxes.Taxes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller  implements Initializable {

    @FXML
    public Label totalSumOfTax;
    @FXML
    private Label labelWrittenToDb;
    @FXML
    private ListView<String> setOfTaxes;

    @FXML
    private TableView<Income> table = new TableView<>();

    @FXML
    private TableColumn<Income, String> nameOfIncome //
            = new TableColumn<>("Name");

    @FXML
    private TableColumn<Income, Double> sizeOfIncome//
            = new TableColumn<>("Income");

    @FXML
    private TableColumn<Income, Double> sizeOfTax//
            = new TableColumn<>("Size of tax");

    @FXML
    private TableColumn<Income, Double> percentageOfTax//
            = new TableColumn<>("Percentage");

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField rangeA;
    @FXML
    private TextField rangeB;
    @FXML
    private TextField sizeIncome;
    private final String[] nameTaxes = {"award", "main income", "additional income",
                                    "transfer from abroad","financial aid", "sale of property","funds as a gift"};

    Double a,b;
    String nameTax;
    Taxes taxes = new Taxes();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Income> taxes1 = taxes.getTaxes();

        nameOfIncome.setCellValueFactory(new PropertyValueFactory<>("nameOfIncome"));
        sizeOfIncome.setCellValueFactory(new PropertyValueFactory<>("sizeOfIncome"));
        sizeOfTax.setCellValueFactory(new PropertyValueFactory<>("sizeOfTax"));
        percentageOfTax.setCellValueFactory(new PropertyValueFactory<>("percentageOfTax"));

        nameOfIncome.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Income> list = FXCollections.observableArrayList(taxes1);
        table.setItems(list);

        UpdateTotalSumTax();
        UpdateSet();
        choiceBox.getItems().addAll(nameTaxes);
        choiceBox.setOnAction(this::getTaxType);

        setDisableOrVisibleLabel(false, true);

    }

    public void setDisableOrVisibleLabel(boolean visible,boolean disable){
        labelWrittenToDb.setVisible(visible);
        labelWrittenToDb.setDisable(disable);
    }
    public void getTaxType(javafx.event.ActionEvent e){
        nameTax = choiceBox.getValue();
    }
    public void addTax(){

        double income = Double.parseDouble(sizeIncome.getText());
        Income inc1 = new Income(nameTax, income);
        taxes.addIncome(taxes.getTaxes(),inc1);
        UpdateTable(taxes.getTaxes());
        UpdateTotalSumTax();
        UpdateSet();
    }

    public void UpdateTable(List<Income> taxes){
        table.getItems().clear();

        ObservableList<Income> list = FXCollections.observableArrayList(taxes);
        table.setItems(list);
    }
    public void UpdateSet(){
        setDisableOrVisibleLabel(false, true);
        Set<Double> taxesSet = taxes.defineSetTaxes();
        setOfTaxes.getItems().clear();

        if (taxes.getTaxes().isEmpty()){
            setOfTaxes.getItems().add("There is no taxes!");
        } else
            for (Double tax : taxesSet) {
                setOfTaxes.getItems().addAll(tax.toString());
            }
    }
    @FXML
    public void submit(){
        a = Double.parseDouble(rangeA.getText());
        b = Double.parseDouble(rangeB.getText());
    }
    @FXML
    public void searchTax(){
        submit();
        List<Income> person = taxes.searchTaxes("ByTax", a, b );
        UpdateTable(person);

        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void searchInc(){
        submit();
        List<Income> person = taxes.searchTaxes("ByIncome",a,b );
        UpdateTable(person);
        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void sortAsc(){

        List<Income> person = taxes.sortTaxes("Asc");
        UpdateTable(person);
        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void sortDesc(){

        List<Income> person = taxes.sortTaxes("Desc");
        UpdateTable(person);
        UpdateTotalSumTax();
        UpdateSet();
    }
    @FXML
    public void writeToDB(){

        UpdateTable(taxes.getTaxes());
        UpdateTotalSumTax();
        UpdateSet();
        DataBase.writeTaxesInDB( taxes.getTaxes());
        setDisableOrVisibleLabel(true, false);
        System.out.println(" Taxes is written to DB successfully!");
    }
    public void UpdateTotalSumTax(){
        setDisableOrVisibleLabel(false, true);
        double totalSumTax = taxes.defineSumTaxes();
        totalSumOfTax.setText("Total sum of taxes: " + totalSumTax);
    }

}