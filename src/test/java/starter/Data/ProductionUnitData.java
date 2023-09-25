package starter.Data;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class ProductionUnitData {
    private String name;
    private String availability;
    private String performance            ;
    private String globalProductionUnitQuality;
    private String convertedUnitName;

    private String costPerHour;
    private String giveawaySamplingSession            ;
    private String downtimeDelay;
    private String endOfDowntimeDelay;
    private String downtimeJustificationDelay;


    public String getPUName()
    {
        return name;
    }
    public String getAvailability()
    {
        return availability;
    }
    public String getPerformance()
    {
        return performance;
    }
    public String getGlobalProductionUnitQuality()
    {
        return globalProductionUnitQuality;
    }
    public String getConvertedUnitName()
    {
        return convertedUnitName;
    }
    public String getCostPerHour()
    {
        return costPerHour;
    }
    public String getGiveawaySamplingSession()
    {
        return giveawaySamplingSession;
    }
    public String getDowntimeDelay()
    {
        return downtimeDelay;
    }
    public String getEndOfDowntimeDelay()
    {
        return endOfDowntimeDelay;
    }
    public String getDowntimeJustificationDelay()
    {
        return downtimeJustificationDelay;
    }


    public ProductionUnitData(String name,String availability, String performance,String globalProductionUnitQuality, String convertedUnitName, String costPerHour, String giveawaySamplingSession, String downtimeDelay, String endOfDowntimeDelay, String downtimeJustificationDelay){
        this.name = name;
        this.availability = availability;
        this.performance = performance;
        this.globalProductionUnitQuality = globalProductionUnitQuality;
        this.convertedUnitName = convertedUnitName;
        this.costPerHour = costPerHour;
        this.giveawaySamplingSession = giveawaySamplingSession;
        this.downtimeDelay = downtimeDelay;
        this.endOfDowntimeDelay = endOfDowntimeDelay;
        this.downtimeJustificationDelay = downtimeJustificationDelay;
    }
}
