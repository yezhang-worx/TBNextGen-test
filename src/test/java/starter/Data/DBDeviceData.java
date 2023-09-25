package starter.Data;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class DBDeviceData {
    private String id;
    private String type;
    private String description;
    private String brand;


    public String getId()
    {
        return id;
    }
    public String getType()
    {
        return type;
    }
    public String getBrand()
    {
        return brand;
    }
    public String getDescription()
    {
        return description;
    }



    public DBDeviceData(String id, String type, String description, String brand){
        this.id = id;
        this.type = type;
        this.description = description;
        this.brand = brand;
    }
}
