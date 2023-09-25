package starter.dataClass;

public class UserData {
    public String displayName;
    public String email;
    public String password;
    public String role ;
    public boolean adminRight;
    public boolean TilelyticsAccess;
    public String PUAssociations;
    public boolean activeUser;
    public String factory;


    public UserData(){
        email           = "text@worximity.com";
        password        = "Test1234";
        displayName     = "DefaulDisplayNumber";
        role            = "Management";
        adminRight      = true;
        TilelyticsAccess= true;
        PUAssociations  = "All";
        activeUser      = true;
        factory         = "#1"; //first factory in the dropdown list
    }

    public UserData(String displayName, String email, String password, String role,  String adminRight, String TilelyticsAccess, String PUAssociations,String activeUser, String factory) {
        this.displayName        = displayName;
        this.email              = email;
        this.password           = password;
        this.role               = role;
        this.adminRight         = adminRight.equalsIgnoreCase("false") ? false:true;
        this.TilelyticsAccess   = TilelyticsAccess.equalsIgnoreCase("false") ? false:true;
        this.PUAssociations     = PUAssociations;
        this.activeUser         = activeUser.equalsIgnoreCase("false") ? false:true;
        this.factory            = factory;
    }

    public UserData(UserData user){
        email           = user.email;
        password        = user.password;
        displayName     = user.displayName;
        role            = user.role;
        adminRight      = user.adminRight;
        TilelyticsAccess= user.TilelyticsAccess;
        PUAssociations  = user.PUAssociations;
        activeUser      = user.activeUser;
        factory         = user.factory;
    }

}
