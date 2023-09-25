package starter;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import starter.Common.DBConnection;

import java.sql.SQLException;

import static net.serenitybdd.core.Serenity.getDriver;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
//        features = "src/test/resources/features/TilePlus/TilePlusSanityCheck.feature",
        features = "src/test/resources/features"

//        features = {"src/test/resources/features"}
//        ,dryRun = true
//        tags= "@app=Tilelytics and @app=TilePlus"
)
public class CucumberTestSuite {
    @BeforeAll
    public static void setup() throws SQLException {
        getDriver().manage().window().maximize();
        System.out.println(" Setup DB connection and retrieve default factory");
        DBConnection.getDBInstance();
    }
    @AfterAll
    public static void tearDown() throws Exception {
        System.out.println("Disconnect DB");
        DBConnection.getDBInstance().dismissAll();
    }
}


//    mvn clean verify -Dtags="color:red"
//    mvn clean verify -Dtags="app:Tilelytics"
//    @color=red