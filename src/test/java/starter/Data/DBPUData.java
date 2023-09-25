package starter.Data;

public class DBPUData {
    private String PUId;
    private String PUName;
    private String factoryId;
    private String quality;
    private String convertedUnitName;
    private String costPerHour;



    public String getPUName()
    {
        return PUName;
    }
    public String getPUId()
    {
        return PUId;
    }
    public String getFactoryId()
    {
        return factoryId;
    }
    public String getQuality()
    {
        return quality;
    }
    public String getConvertedUnitName()
    {
        return convertedUnitName;
    }
    public String getCostPerHour()
    {
        return costPerHour;
    }


    public DBPUData(String PUId, String name, String factoryId, String quality, String convertedUnitName, String costPerHour){
        this.PUName = name;
        this.PUId = PUId;
        this.factoryId = factoryId;
        this.quality = quality;
        this.convertedUnitName = convertedUnitName;
        this.costPerHour = costPerHour;
    }
}
