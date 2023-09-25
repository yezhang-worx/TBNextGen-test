package starter.Data;

public class DBFactoryData {
    private String name;
    private String id;
    private String timezone;
    private String currencyCode;


    public String getName(){return name;}
    public String getId()
    {
        return id;
    }
    public String getTimezone()
    {
        return timezone;
    }
    public String getCurrency_code()
    {
        return currencyCode;
    }


    public DBFactoryData(String factoryId, String name, String timezone, String currencyCode){

        this.id = factoryId;
        this.name = name;
        this.timezone = timezone;
        this.currencyCode = currencyCode;

    }
}
