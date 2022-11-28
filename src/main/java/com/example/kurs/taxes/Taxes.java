package com.example.kurs.taxes;

import com.example.kurs.db.DataBase;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.kurs.Main.logger;


public class Taxes extends Income {

    public static List<Income> taxes;
    public Taxes(List<Income> taxes2){ taxes = taxes2; }
    public Taxes() {
        taxes = DataBase.getTaxesDB();
    }

    public List<Income> getTaxes() {
        return taxes;
    }

    public void addIncome(List<Income> taxes, Income income){
        logger.fine("Getting size of tax by giving income and adding tax information to list of taxes");
        income.getPercentageOfTax();
        income.getSizeOfTax();
        taxes.add(income);
    }

    public Set<Double> defineSetTaxes(){

        if (taxes.isEmpty()) {
            logger.info("List of tax is empty");
            return null;
        }

        logger.info("Defining set of size of taxes");
        Set<Double> taxesSet = new HashSet<>();
        for(Income tax: taxes){
            taxesSet.add(tax.getSizeOfTax());
        }
        System.out.println("\n Set of taxes:");
        for (Double obj : taxesSet)
            System.out.println(obj);

        return taxesSet;
    }

    public double defineSumTaxes(){

        logger.info("Defining sum of tax");
        if (taxes.isEmpty()) {
            logger.info("List of tax is empty");
            return 0;
        }

        double total = 0.0;
        for(Income tax: taxes){
            total += tax.getSizeOfTax();
        }
        System.out.println("\n Sum of taxes: " + total);

        return total;
    }
    public List<Income> sortTaxes(String choice){

        List<Income> sortedTaxes = new ArrayList<>(taxes);

        if (Objects.equals(choice, "Asc")) {
            logger.info("Sorting taxes by size of tax and by ascending");
            System.out.println("\n Sorted taxes by ascending:");
            sortedTaxes.sort(new ComparatorSortAsc());
        }  if (Objects.equals(choice, "Desc")) {
            logger.info("Sorting taxes by size of tax and by descending");
            System.out.println("\n Sorted taxes by descending:");
            sortedTaxes.sort(new ComparatorSortDesc());
        }
        info(sortedTaxes);
        return sortedTaxes;
    }
    public List<Income> searchTaxes(String type, double a, double b){

        if (Objects.equals(type, "ByIncome")){
            logger.info("Searching taxes by income");
            System.out.println("\n Taxes searched by income in range [" + a +"; "+ b +"]:");
        }
        else {
            logger.info("Searching taxes by size of tax");
            System.out.println("\n Taxes searched by tax in range [" + a + "; " + b + "]:");
        }
        taxes = filterTaxes(type,a,b);

        info(taxes);
        return taxes;
    }

    public List<Income> filterTaxes(String type, double a, double b){

        if (Objects.equals(type, "ByIncome")){
            return taxes.stream().filter(k -> k.getSizeOfIncome() > a && k.getSizeOfIncome() < b).collect(Collectors.toCollection(ArrayList::new));
        }
        else{
            return taxes.stream().filter(k -> k.getSizeOfTax() > a && k.getSizeOfTax() < b ).collect(Collectors.toCollection(ArrayList::new));
        }

    }

    public void info(List<Income> taxes){
        logger.info("Printing List of taxes");
        for(int i = 0; i < taxes.size(); i++) {
            System.out.print((i + 1) + ". " + taxes.get(i).toString() + "\n");
        }
    }
}
