package com.example.kurs;
import com.example.kurs.db.DataBase;
import com.example.kurs.taxes.Income;
import com.example.kurs.taxes.Taxes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.Assert;
import org.testfx.api.FxAssert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.testfx.matcher.control.LabeledMatchers;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AutomationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("design.fxml")));
            Scene scene = new Scene(root);
            Image icon = new Image("D:\\Study\\ПП\\kurs\\src\\main\\resources\\com\\example\\kurs\\piggy-bank.png");

            stage.getIcons().add(icon);
            stage.setResizable(false);
            String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Program for taxes!");
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    public void clickButtonAddTax() {

        FxAssert.verifyThat("#addTax", LabeledMatchers.hasText("add tax"));

        clickOn("#choiceBox").clickOn("financial aid");
        sleep(800);
        clickOn("#sizeIncome").write("4400");
        sleep(800);
        clickOn("#addTax");
        sleep(1000);

        FxAssert.verifyThat("#totalSumOfTax", LabeledMatchers.hasText("Total sum of taxes: 25516.5"));
        String example = Taxes.taxes.get(Taxes.taxes.size() - 1).toString();
        Assert.assertEquals(" Name:  " + "'" + "financial aid" + "'" +
                ";      Income = " + 4400.0 +
                ";      Size of tax = " + 858.0 +
                ";        Percentage = " + 19.5, example);
        sleep(1000);
    }

    @Test
    public void testSearchTax() {
        FxAssert.verifyThat("#searchTax", LabeledMatchers.hasText("search by tax"));
        clickOn("#rangeA").write("200");
        clickOn("#rangeB").write("3000");
        sleep(700);
        clickOn("#submit");
        sleep(700);
        clickOn("#searchTax");
        sleep(1000);

        FxAssert.verifyThat("#totalSumOfTax", LabeledMatchers.hasText("Total sum of taxes: 2760.0"));
        String example1 = Taxes.taxes.get(0).toString();
        Assert.assertEquals(" Name:  " + "'" + "additional income" + "'" +
                ";      Income = " + 13000.0 +
                ";      Size of tax = " + 2535.0 +
                ";        Percentage = " + 19.5, example1);

        String example2 = Taxes.taxes.get(1).toString();
        Assert.assertEquals(" Name:  " + "'" + "funds as a gift" + "'" +
                ";      Income = " + 5000.0 +
                ";      Size of tax = " + 225.0 +
                ";        Percentage = " + 4.5, example2);

        sleep(1000);
    }

    @Test
    public void testSearchInc() {
        FxAssert.verifyThat("#searchInc", LabeledMatchers.hasText("search by inc"));
        clickOn("#rangeA").write("35000");
        clickOn("#rangeB").write("50000");
        sleep(700);
        clickOn("#submit");
        sleep(700);
        clickOn("#searchInc");
        sleep(1000);

        FxAssert.verifyThat("#totalSumOfTax", LabeledMatchers.hasText("Total sum of taxes: 8892.0"));
        String example = Taxes.taxes.get(0).toString();
        Assert.assertEquals(" Name:  " + "'" + "main income" + "'" +
                ";      Income = " + 45600.0 +
                ";      Size of tax = " + 8892.0 +
                ";        Percentage = " + 19.5, example);

        sleep(1000);
    }

    @Test
    public void testSortAsc() {

        FxAssert.verifyThat("#sortAsc", LabeledMatchers.hasText("sort by asc"));
        Taxes taxes = new Taxes();

        clickOn("#sortAsc");
        sleep(1000);

        List<Income> person = new ArrayList<>();
        Taxes taxes2 = new Taxes(person);

        taxes2.addIncome(person, new Income("funds as a gift", 5000));
        taxes2.addIncome(person, new Income("additional income'", 13000));
        taxes2.addIncome(person, new Income("award", 16700));
        taxes2.addIncome(person, new Income("sale of property", 78000));
        taxes2.addIncome(person, new Income("transfer from abroad", 32000));
        taxes2.addIncome(person, new Income("main income", 45600));

        sleep(600);
        String actual = null,expected = null;

        for(int i = 0; i < Taxes.taxes.size(); i++){
            expected += taxes2.getTaxes().get(i).toString();
            actual += taxes.getTaxes().get(i).toString();
        }

        Assert.assertEquals(expected,actual);
        FxAssert.verifyThat("#totalSumOfTax", LabeledMatchers.hasText("Total sum of taxes: 24658.5"));
    }

    @Test
    public void testSortDesc() {
        FxAssert.verifyThat("#sortDesc", LabeledMatchers.hasText("sort by desc"));
        Taxes taxes = new Taxes();

        clickOn("#sortDesc");
        sleep(1000);

        List<Income> person = new ArrayList<>();
        Taxes taxes2 = new Taxes(person);

        taxes2.addIncome(person, new Income("main income", 45600));
        taxes2.addIncome(person, new Income("transfer from abroad", 32000));
        taxes2.addIncome(person, new Income("sale of property", 78000));
        taxes2.addIncome(person, new Income("award", 16700));
        taxes2.addIncome(person, new Income("additional income'", 13000));
        taxes2.addIncome(person, new Income("funds as a gift", 5000));

        sleep(600);
        String actual = null,expected = null;

        for(int i = 0; i < Taxes.taxes.size(); i++){
            expected += taxes2.getTaxes().get(i).toString();
            actual += taxes.getTaxes().get(i).toString();
        }

        Assert.assertEquals(expected,actual);
        FxAssert.verifyThat("#totalSumOfTax", LabeledMatchers.hasText("Total sum of taxes: 24658.5"));

    }

    @Test
    public void testWriteToDB() {
        List<Income> person = new Taxes().getTaxes();

        clickOn("#writeToDB");
        sleep(700);

        List<Income> taxes = DataBase.getTaxesDB();
        String actual = null,expected = null;

        for(int i = 0; i < person.size(); i++){
            expected += person.get(i).toString();
            actual += taxes.get(i).toString();
        }

        Assert.assertEquals(expected,actual);
    }
}

