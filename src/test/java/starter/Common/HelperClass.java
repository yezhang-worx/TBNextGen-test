package starter.Common;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.environment.UndefinedEnvironmentVariableException;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import starter.Data.DBFactoryData;
import starter.Data.DBPUData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.core.Serenity.getDriver;
import static org.bouncycastle.util.Properties.getPropertyValue;

public class HelperClass {

    //TODO how to
    Boolean runLocally = true;

    EnvironmentVariables environmentVariables = Injectors.getInjector().getInstance(EnvironmentVariables.class);
//    EnvironmentVariables environmentVariables;

    public ArrayList<String> getValidations(DataTable table) {
        ArrayList<String> validations = new ArrayList();
        List<String> rows = table.asList(String.class);
        for (String columns : rows) {
            validations.add(columns);
        }
        return validations;
    }

//    public String getProperty(String propertyName) {
//        String propertyValue = getPropertyValue(propertyName);
//        if (propertyValue == null) {
//            throw new UndefinedEnvironmentVariableException("Environment '"
//                    + propertyName
//                    + "' property undefined for environment '"
//                    + getDefinedEnvironment(environmentVariables) + "'");
//        }
//        return substituteProperties(propertyValue);
//    }


    public String getUserName(){

        String userName =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("management.username").toString();
        return userName;
    }

    public String getPW(){

        String pw =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("management.password").toString();
        return pw;
    }

    public String getAdminUserName(){

        String userName =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("wxadmin.username").toString();
        return userName;
    }

    public String getAdminPW(){

        String pw =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("wxadmin.password").toString();
        return pw;
    }

    public String getDBConnection(){

        String DBConnection =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("DB.connectionString").toString();
        return DBConnection;
    }

    public String getDBName(){

        String DBName =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("DB.username").toString();
        return DBName;
    }

    public String getDBPW(){

        String DBpw =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("DB.password").toString();
        return DBpw;
    }

    public String getSF4PU(){

        String DBpw =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("DB.SF4PU").toString();
        return DBpw;
    }

    public String getNoSF4PU(){

        String DBpw =  EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("DB.NOSF4PU").toString();
        return DBpw;
    }

    public void logToReport(String term, String actual){

        if(runLocally)
            System.out.println(term + " = " + actual);
        else Serenity.recordReportData().withTitle("Exported XML report").andContents(term + " result  = " + actual);
    }

    public List<DBPUData> getPUList() throws SQLException {
        return DBConnection.getDBInstance().getPUListByDefaultFactoryId();
    }

    public List<DBFactoryData> getFactoryList() throws SQLException {
        return DBConnection.getDBInstance().getFactoryList();
    }



    public List<String> getPUNameList() throws SQLException {
        List<String> PUList = new ArrayList<>();
        List<DBPUData> puList = DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        puList.forEach((e) -> {
            System.out.println("Validate string =" + e.getPUName());
            PUList.add(e.getPUName());
        });
        return PUList;
    }

    public boolean isAttribtuePresent(WebElement element, String attribute) {
        Boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if (value != null){
                result = true;
            }
        } catch (Exception e) {}

        return result;
    }

}
