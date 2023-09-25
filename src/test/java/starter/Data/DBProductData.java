package starter.Data;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class DBProductData {
    private String id;
    private String sku;
    private String description;
    private String category            ;
    private String target;
    private String targetUnit;
//    private List<String> associatedPU;

    public String getId()
    {
        return id;
    }
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
    public String getTargetUnit(){return targetUnit;}
//    public List<String> getAssociatedPU()
//    {
//        return associatedPU;
//    }

    public DBProductData(String id, String sku, String cat,String description, String target, String targetUnit){
        this.id = id;
        this.sku = sku;
        this.description = description;
        this.category = cat;
        this.target = target;
        this.targetUnit = targetUnit;
//        this.associatedPU = convertToList(associatedPUStr);
    }

    private List<String> convertToList(String str){
        return Lists.newArrayList(Splitter.on(";").trimResults().split(str));
    }
}
