package starter.Data;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class ProductData
{
    private String sku;
    private String description;
    private String category            ;
    private String target;
    private String associatedPUStr;
    //TODO : real association PU LIST
    private List<String>  associatedPU;

    public String getSKU()
    {
        return sku;
    }

    public String getDescription()
    {
        return description;
    }
    public String getCategory()
    {
        return category;
    }
    public String getTarget()
    {
        return target;
    }
    public String getAssociatedPUStr()
    {
        return associatedPUStr;
    }
    public List<String> getAssociatedPU()
    {
        return associatedPU;
    }
    public ProductData(String sku, String description, String cat,String target, String associatedPUStr){
        this.sku = sku;
        this.description = description;
        this.category = cat;
        this.target = target;
        this.associatedPUStr = associatedPUStr;
        this.associatedPU = convertToList(associatedPUStr);
    }
    private List<String> convertToList(String str){
        return Lists.newArrayList(Splitter.on(";").trimResults().split(str));

    }
}
