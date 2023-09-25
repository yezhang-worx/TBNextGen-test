package starter.Common;

import starter.Data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBConnection {

    private static String url = "jdbc:postgresql://tileplus-postgresv13-staging.postgres.database.azure.com:5432/tileplus?";
    private static String user = "wxadmin";
    private static String password = "$H2Y2E1$";
    private static String email = "ellenTest@worximity.com";

    private static DBConnection dbObject;
    public static Connection sqlConnection;
    public String defaultFactoryId;
    public String defaultFactoryName;
    public String companyName;
    public static String accountId;
    public String userId = null;
    List<DBFactoryData> fList = new ArrayList<>();
    List<DBPUData> PUlist = new ArrayList<>();
    List<DBProductData> Productslist = new ArrayList<>();
    List<DBDeviceData> Devicelist = new ArrayList<>();
    List<DBAlertData> Alertlist = new ArrayList<>();
    public static DBConnection getDBInstance() {

        HelperClass helper = new HelperClass();

        // create object if it's not already created
        if (dbObject == null) {
            dbObject = new DBConnection();
            sqlConnection = null;

            url = helper.getDBConnection();
            user = helper.getDBName();
            password = helper.getDBPW();
            email = helper.getUserName();

            try {
                Class.forName("org.postgresql.Driver");

                sqlConnection = DriverManager.getConnection(url, user, password);
                sqlConnection.setAutoCommit(false);
                System.out.println("Opened database successfully : " + url);
                initDB();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // returns the singleton object
        return dbObject;
    }
    public void dismissAll() throws SQLException {
        DBConnection.sqlConnection.close();
        dbObject = null;
     }

     public static void initDB() throws SQLException {
         DBConnection.getDBInstance().getDefaultFactory();
     }

    public String execute(String query) {
        String result = "";
        try {
            Statement statement = sqlConnection.createStatement();
            System.out.println("SQL query: " + query);
            statement.execute(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            result = ex.getMessage();
        }
        return result;
    }

    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = sqlConnection.createStatement();
            System.out.println("SQL query: " + query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }

    public void getDefaultFactory() throws SQLException {
//        String sql_factoryList = "Select id, account_id, factory_id from users where email= ?";
        String sql_factoryList = "Select users.id as id, users.account_id as account_id, factory_id, f.name as name, a.name as companyName " +
                "from users " +
                "join factories f on f.id = users.factory_id " +
                "join accounts a on a.id = f.account_id " +
                "where email= ?";
        PreparedStatement pstmt = sqlConnection.prepareStatement(sql_factoryList);
        pstmt.setQueryTimeout(120);
        pstmt.setString(1, email);
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            System.out.println(" ------------- default Factory  sql = " + sql_factoryList);
            userId              = resultSet.getString("id");
            accountId           = resultSet.getString("account_id");
            defaultFactoryId    = resultSet.getString("factory_id");
            defaultFactoryName  = resultSet.getString("name");
            companyName         = resultSet.getString("companyName");
            System.out.println("accountId = " + accountId);
            System.out.println("defaultFactoryId = " + defaultFactoryId);
        }
        pstmt.close();
    }

    public List<DBFactoryData> getFactoryList() throws SQLException {
        String sql = "select id, name, timezone, currency_code from factories where account_id = ?";

        String sql2 = "    select * from user_pu_associations " +
                "    where deletion_time is null " +
                "    and user_id = ? " +
                "    and production_unit_id in (select id from production_units where factory_id = ?)";
        System.out.println("factory list sql = " + sql);
        System.out.println("byAccountID = " + accountId);
        PreparedStatement pstmt = DBConnection.sqlConnection.prepareStatement(sql);
        pstmt.setQueryTimeout(120);
        pstmt.setObject(1, UUID.fromString(userId));
        pstmt.setObject(1, UUID.fromString(accountId));

        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        fList.clear();
        while (rs.next()) {
            DBFactoryData factory = new DBFactoryData(rs.getString("id"), rs.getString("name"), rs.getString("timezone"),rs.getString("currency_code") );
            fList.add(factory);
            System.out.println("Factory name = " + rs.getString("name"));
        }
        pstmt.close();
        return fList;
    }

    public void getPUList(String byAccountID) throws SQLException {

        String sql = "select id, name, factory_id, quality, converted_unit_name, cost_per_hour from production_units where account_id = ?";
        System.out.println("sql = " + sql);
        System.out.println("byAccountID = " + byAccountID);
        PreparedStatement pstmt = DBConnection.sqlConnection.prepareStatement(sql);
        pstmt.setQueryTimeout(120);
        pstmt.setObject(1, UUID.fromString(byAccountID));
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        PUlist.clear();
        while (rs.next()) {
            DBPUData PU = new DBPUData(rs.getString("id"), rs.getString("name"), rs.getString("factory_id"),rs.getString("quality"), rs.getString("converted_unit_name"),rs.getString("cost_per_hour") );

            PUlist.add(PU);
            System.out.println("PU name = " + rs.getString("name"));
        }
        pstmt.close();
    }
    public List<DBProductData> getProductListOfDefaultFactory() throws SQLException {

        String sql = "select  " +
                "distinct p.id as id, p.sku as sku, p.category as category, p.description as description, p.target_value as target_value, p.target_value_unit as target_value_unit " +
                "from products p " +
                "right join product_pu_associations pua on p.id = pua.product_id " +
                "where p.deletion_time is null and p.factory_id = ? " +
                "and p.description is not null and pua.deletion_time is null";

//        String sql = "select id, sku, category, description, target_value, target_value_unit " +
//                "from products  " +
//                "where factory_id = ? " +
//                "and deletion_time is null and description is not null";
        System.out.println("sql = " + sql);
        PreparedStatement pstmt = DBConnection.sqlConnection.prepareStatement(sql);
        pstmt.setQueryTimeout(120);
        pstmt.setObject(1, UUID.fromString(defaultFactoryId));
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        Productslist.clear();
        while (rs.next()) {
            DBProductData product = new DBProductData(rs.getString("id"), rs.getString("sku"), rs.getString("category"),rs.getString("description"), rs.getString("target_value"),rs.getString("target_value_unit") );

            Productslist.add(product);
            System.out.println("Product name = " + product.getSKU());
        }
        pstmt.close();
        return Productslist;
    }

    public List<DBDeviceData> getDeviceListOfDefaultFactory() throws SQLException {

        String sql = "select id, type, description, brand " +
                "from devices  " +
                "where factory_id = ? " +
                "and deletion_time is null ";
        System.out.println("sql = " + sql);
        PreparedStatement pstmt = DBConnection.sqlConnection.prepareStatement(sql);
        pstmt.setQueryTimeout(120);
        pstmt.setObject(1, UUID.fromString(defaultFactoryId));
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        Devicelist.clear();
        while (rs.next()) {
            DBDeviceData device = new DBDeviceData(rs.getString("id"), rs.getString("type"), rs.getString("description"), rs.getString("brand") );

            Devicelist.add(device);
            System.out.println("Device id = " + device.getId());
        }
        pstmt.close();
        return Devicelist;
    }

    public List<DBAlertData> getAlertsListOfDefaultFactory() throws SQLException {

        String sql = "select id, alert_type, rules, enabled " +
                "from alerts " +
                "where factory_id = ? " +
                "and deletion_time is null";

        System.out.println("sql = " + sql);
        PreparedStatement pstmt = DBConnection.sqlConnection.prepareStatement(sql);
        pstmt.setQueryTimeout(120);
        pstmt.setObject(1, UUID.fromString(defaultFactoryId));
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        Devicelist.clear();
        while (rs.next()) {
            DBAlertData alert = new DBAlertData(rs.getString("id"), rs.getString("alert_type"), rs.getString("rules"), rs.getString("enabled") );

            Alertlist.add(alert);
            System.out.println("Alert id = " + alert.getId());
        }
        pstmt.close();
        return Alertlist;
    }

    public List<DBDeviceData> getAlertListOfDefaultFactory() throws SQLException {

        String sql = "select id, type, description, brand " +
                "from devices  " +
                "where factory_id = ? " +
                "and deletion_time is null ";
        System.out.println("sql = " + sql);
        PreparedStatement pstmt = DBConnection.sqlConnection.prepareStatement(sql);
        pstmt.setQueryTimeout(120);
        pstmt.setObject(1, UUID.fromString(defaultFactoryId));
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        Devicelist.clear();
        while (rs.next()) {
            DBDeviceData device = new DBDeviceData(rs.getString("id"), rs.getString("type"), rs.getString("description"), rs.getString("brand") );

            Devicelist.add(device);
            System.out.println("Device id = " + device.getId());
        }
        pstmt.close();
        return Devicelist;
    }
    public List<DBPUData> getPUListByDefaultFactoryId() throws SQLException {

        String sql = "select pu.id as id, pu.name as name, pu.factory_id as factory_id, pu.quality as quality, converted_unit_name, cost_per_hour " +
                "from user_pu_associations upa " +
                "join production_units pu on pu.id = upa.production_unit_id " +
                "join factories f on f.id = pu.factory_id " +
                "where upa.deletion_time is null  " +
                "and upa.user_id = ? " +
                "and f.id = ? " +
                "and production_unit_id in (select id from production_units where account_id = ?) " +
                "order by pu.factory_id";
        System.out.println("sql = " + sql);

        PreparedStatement pstmt = DBConnection.sqlConnection.prepareStatement(sql);
        pstmt.setQueryTimeout(120);
        pstmt.setObject(1, UUID.fromString(userId));
        pstmt.setObject(2, UUID.fromString(defaultFactoryId));
        pstmt.setObject(3, UUID.fromString(accountId));
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        PUlist.clear();
        while (rs.next()) {
            DBPUData PU = new DBPUData(rs.getString("id"), rs.getString("name"), rs.getString("factory_id"),rs.getString("quality"), rs.getString("converted_unit_name"),rs.getString("cost_per_hour") );

            PUlist.add(PU);
            System.out.println("PU name = " + rs.getString("name"));
        }
        pstmt.close();
        return PUlist;
    }

//    SQL query : samples
//    SELECT gs.id, gs.production_unit_id, gs.session_id, gs.measured_value, gs,sku FROM public.giveaway_samples gs
//    join public.sampling_sessions s on s.id=gs.session_id
//    where s.deletion_time is null and completion_time is not null and sku <>''

    public static void main(String args[]) throws SQLException {

        DBConnection.getDBInstance().getDefaultFactory();
//        DBConnection.getDBInstance().getPUList(accountId);
//        DBConnection.getDBInstance().getFactoryList(accountId);
        DBConnection.getDBInstance().getPUListByDefaultFactoryId();
        DBConnection.sqlConnection.close();
    }

//    public static void main(String args[]) {
//        Connection c = null;
//        Statement stmt = null;
//        String email = "ellenTest@worximity.com";
//        try {
//            Class.forName("org.postgresql.Driver");
//            c = DriverManager.getConnection(url, user, password);
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");
//
//            stmt = c.createStatement();
//            String sql_factoryList = "Select account_id, factory_id, email, display_name from users where email= '" + email + "'";
//
//            ResultSet resultSet = stmt.executeQuery(sql_factoryList);
//            ResultSetMetaData rsmd = resultSet.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
//
//            String factoryId;
//            String factoryName = "Factory WX";
//            while (resultSet.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {
//                    System.out.print(" ------------- Factory List -----------");
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = resultSet.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//                System.out.println("");
//            }
//
//            String sql_PUList = "select pu.id as puid, " +
//                        "pu.name as puname, " +
//                        "pu.factory_id as factoryid, " +
//                        "factories.name as factoryname " +
//                        "from production_units as pu " +
//                        "join factories on factories.id = pu.factory_id " +
//                        "where pu.account_id in (select account_id from users where email = ' " + email + "') " +
//                        "order by factories.name ";
//
//            resultSet = stmt.executeQuery(sql_PUList);
//            rsmd = resultSet.getMetaData();
//            columnsNumber = rsmd.getColumnCount();
//
//            while (resultSet.next()) {
//                for (int i = 1; i <= columnsNumber; i++) {
//                    if (i > 1) System.out.print(",  ");
//                    String columnValue = resultSet.getString(i);
//                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
//                }
//                System.out.println("");
//            }
//
//
////            List<PUFactory> testData=new ArrayList<PUFactory>();
////            while(resultSet.next()) {
////                PUFactory instance = new PUFactory();
////                instance.setPUId(resultSet.getString("puid"));
////                System.out.print("PUId = " + resultSet.getString("puid"));
////                instance.setPUName(resultSet.getString("puname"));
////                System.out.print("PUName = " + resultSet.getString("puname"));
////                instance.setFactoryId(resultSet.getString("factoryid"));
////                System.out.print("FactoryId = " + resultSet.getString("factoryid"));
////                instance.setFactoryName(resultSet.getString("factoryname"));
////                System.out.print("FactoryName = " + resultSet.getString("factoryname"));
////
////                testData.add(instance);
////            }
//
//            String defaultFactoryIdSQL = "Select factory_id from users where email= '" + email + "'";;
//
//            resultSet = stmt.executeQuery(defaultFactoryIdSQL);
//            rsmd = resultSet.getMetaData();
//            columnsNumber = rsmd.getColumnCount();
//
//            while(resultSet.next()) {
//                System.out.print(" ------------- default Factory -----------");
//                resultSet.getString("factory_id");
//                System.out.print("default factory_id = " + resultSet.getString("factory_id"));
//            }
//
//            stmt.close();
//            c.commit();
//            c.close();
//        } catch (Exception e) {
//            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Records created successfully");
//    }

}
