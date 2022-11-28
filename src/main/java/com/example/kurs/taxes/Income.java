package com.example.kurs.taxes;

public class Income {
    private String nameOfIncome;
    private double sizeOfIncome;
    public double percentageOfTax, sizeOfTax;

    public Income(){}

    public Income(String nameOfIncome, double sizeOfIncome) {
        this.nameOfIncome = nameOfIncome;
        this.sizeOfIncome = sizeOfIncome;
    }

    public String getNameOfIncome() { return nameOfIncome;}
    public void setNameOfIncome(String nameOfIncome) { this.nameOfIncome = nameOfIncome; }

    public double getSizeOfIncome() {
        return sizeOfIncome;
    }
    public void setSizeOfIncome(double sizeOfIncome) { this.sizeOfIncome = sizeOfIncome;}

    public double getSizeOfTax() {
        if (sizeOfTax == 0) {
            sizeOfTax = (sizeOfIncome * percentageOfTax) / 100.00;
        }
        return sizeOfTax;
    }
    public void setSizeOfTax(double sizeOfTax) { this.sizeOfTax = sizeOfTax;}

    public double getPercentageOfTax() {
        if (nameOfIncome.equals("award") || nameOfIncome.equals("main income") ||
                nameOfIncome.equals("additional income") || nameOfIncome.equals("transfer from abroad") ||
                nameOfIncome.equals("financial aid")){

            percentageOfTax = 19.5;
        }
        else if (nameOfIncome.equals("sale of property") || nameOfIncome.equals("funds as a gift")){
            percentageOfTax = 4.5;
        }

        return percentageOfTax;
    }
    public void setPercentageOfTax(double percentageOfTax) { this.percentageOfTax = percentageOfTax; }

    @Override
    public String toString() {
        return " Name:  " + "'" + nameOfIncome + "'" +
                ";      Income = " + sizeOfIncome +
                ";      Size of tax = " + sizeOfTax +
                ";        Percentage = " + percentageOfTax;
    }
}