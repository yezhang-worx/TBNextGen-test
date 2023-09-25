package starter.Data;

public class DBAlertData {
    private String id;
    private String type;
    private String rules;
    private String enabled;


    public String getId()
    {
        return id;
    }
    public String getType()
    {
        return type;
    }
    public String getRules()
    {
        return rules;
    }
    public String getEnabled()
    {
        return enabled;
    }



    public DBAlertData(String id, String type, String rules, String enabled){
        this.id = id;
        this.type = type;
        this.rules = rules;
        this.enabled = enabled;
    }
}
