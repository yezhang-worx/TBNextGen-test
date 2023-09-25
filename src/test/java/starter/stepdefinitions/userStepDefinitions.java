package starter.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.annotations.Steps;
import starter.dataClass.UserData;
import starter.Pages.userEditPage;

import java.util.ArrayList;
import java.util.List;

public class userStepDefinitions {

    UserData newUserData = new UserData();
    int originalUserCount = 0;
    String newUserName = "";

    @Steps
    userEditPage userPage;


    @When("{actor} wants to create a new user:")
    public void createUser(Actor actor, DataTable dataTable) {
//        List<UserData> users = dataTable.asList(UserData.class);
//        List<UserData> links = dataTable.transpose().asList(UserData.class);

        originalUserCount = userPage.getUserCount();
        ArrayList<UserData> users = getUsers(dataTable);
        for(UserData user : users.subList( 1, users.size())) {
            Boolean correctRole = !user.role.equalsIgnoreCase("Management") && !user.role.equalsIgnoreCase("Operation") && !user.role.equalsIgnoreCase("Display");
            actor.attemptsTo(Ensure.that(correctRole).isTrue());
            newUserName = user.displayName;
            userPage.createUser();
            userPage.fillUser(user);
            System.out.println("User is created");
            newUserData = user;
        }
    }



    @When("{actor} updates identified by email user with data:")
    public void heUpdatesUserByEmailWithData(Actor actor, DataTable dataTable) {
        ArrayList<UserData> users = getUsers(dataTable);
        for(UserData user : users.subList( 1, users.size())) {
            Boolean correctRole = !user.role.equalsIgnoreCase("Management") && !user.role.equalsIgnoreCase("Operation") && !user.role.equalsIgnoreCase("Display") && !user.role.equalsIgnoreCase("-");
            actor.attemptsTo(Ensure.that(correctRole).isTrue());
            String row = userPage.findUser(user.email);
            userPage.editTargetUser();
            userPage.updateUser(user);
            System.out.println("User is updated");
            newUserData = user;
        }
    }

    @Then("{actor} should see new created user displayed in user list with correct information")
    public void heShouldSeeNewCreatedUserDisplayedInUserList(Actor actor) {
        int count = userPage.getUserCount();
        actor.attemptsTo(Ensure.that(count).isGreaterThan(originalUserCount));
        userPage.findUser(newUserData.email);
        userPage.validateNewUserInfo(newUserData);
    }
    @When("{actor} searches new created user")
    public void heSearchesNewCreatedUser(Actor actor) {
       userPage.findUser("emailAddress");
    }
    @Then("{actor} should see the user updated")
    public void heShouldSeeTheUserUpdated(Actor actor) {
        userPage.findUser(newUserData.email);
        userPage.validateNewUserInfo(newUserData);
    }

    private ArrayList<UserData> getUsers(DataTable table) {
        ArrayList<UserData> users = new ArrayList();
        List<List<String>> rows = table.asLists(String.class);
        for (List<String> columns : rows) {
            users.add(new UserData(columns.get(0), columns.get(1), columns.get(2), columns.get(3), columns.get(4), columns.get(5), columns.get(6), columns.get(7), columns.get(8)));
        }
        return users;
    }

    @When("{actor} searches user {string}")
    public void heSearchesUser(Actor actor, String email) {
        userPage.findUser(email);
    }

    @When("{actor} deletes user by email {string}")
    public void heDeletesUserByEmail(Actor actor, String email) {
        userPage.deleteUser(email);
    }

    @Then("{actor} should see that user with email {string} is not displayed in users list")
    public void heShouldSeeThatUserWithEmailIsNotDisplayedInUsersList(Actor actor, String email) {
        actor.attemptsTo(
                Ensure.that(userPage.findUser(email).isEmpty()).isTrue()
        );
    }

    @And("{actor} should see that user is not editable")
    public void ShouldSeeThatUserIsNotEditable(Actor actor) {
       userPage.userNotEditable();
    }
}
