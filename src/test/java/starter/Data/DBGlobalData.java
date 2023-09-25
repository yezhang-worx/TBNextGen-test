package starter.Data;

import com.google.common.collect.ImmutableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBGlobalData {
    public String defaultFactoryName;
    private static Map<String, String> factoryMap;


//    public List<FactoryData> createFactoryMap(ResultSet rs) throws SQLException {
//        List<FactoryData> factories = new ArrayList<FactoryData>();
//        int i = 0;
//
//        while (rs.next()) {
//            FactoryData factory = new FactoryData(rs.getString(0),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
//            factories.add(factory);
//            i++;
//        }
//        return factories;
//    }

    public List<DBPUData> createPUMap(ResultSet rs) throws SQLException {
        List<DBPUData> PUs = new ArrayList<DBPUData>();
        int i = 0;

        while (rs.next()) {
            DBPUData pu = new DBPUData(rs.getString(0),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
            PUs.add(pu);
            i++;
        }
        return PUs;
    }

    private static ImmutableList<List<Object>> readRows(ResultSet rs) throws SQLException
    {
        ImmutableList.Builder<List<Object>> rows = ImmutableList.builder();
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            List<Object> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            rows.add(row);
        }
        return rows.build();
    }
}
