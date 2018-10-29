package cn.yfchen.cn;

import com.alibaba.fastjson.JSON;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseInfo {

    private String host;
    private String user;
    private  String password;
    private  String db;
    private Connection conn = null;
    private Statement stmt = null;
    // JDBC 驱动名及数据库 URL
    private String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String DB_URL ;

    /**
     * 实例化对象
     * @param host
     * @param user
     * @param password
     * @param db
     * @param port
     * @throws SQLException
     */
    public DatabaseInfo(String host, String user, String password, String db, Integer port) throws SQLException {
        this.host=host;
        this.user=user;
        this.password=password;
        this.db=db;
        this.DB_URL= "jdbc:mysql://"+this.host+":"+port+"/information_schema?characterEncoding=utf8";
        conn = DriverManager.getConnection(this.DB_URL,this.user,this.password);
        this.stmt = conn.createStatement();
    }

    /**
     * 获取数据库每个表的信息
     * @return
     */
    public List<HashMap>  getTableInfoByDatabase() {
        List<HashMap> talbes = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            String sql;
            sql = "SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = '" + this.db + "'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            talbes = new ArrayList<HashMap>();
            ;
            // 展开结果集数据库
            while (rs.next()) {
                HashMap table = new HashMap();
                table.put("TABLE_SCHEMA", rs.getString("TABLE_SCHEMA"));
                table.put("TABLE_NAME", rs.getString("TABLE_NAME"));
                table.put("TABLE_TYPE", rs.getString("TABLE_TYPE"));
                table.put("ENGINE", rs.getString("ENGINE"));
                table.put("VERSION", rs.getString("VERSION"));
                table.put("ROW_FORMAT", rs.getString("ROW_FORMAT"));
                table.put("AVG_ROW_LENGTH", rs.getString("AVG_ROW_LENGTH"));
                table.put("DATA_LENGTH", rs.getString("DATA_LENGTH"));
                table.put("MAX_DATA_LENGTH", rs.getString("MAX_DATA_LENGTH"));
                table.put("INDEX_LENGTH", rs.getString("INDEX_LENGTH"));
                table.put("DATA_FREE", rs.getString("DATA_FREE"));
                table.put("AUTO_INCREMENT", rs.getString("AUTO_INCREMENT"));
                table.put("CREATE_TIME", rs.getString("CREATE_TIME"));
                table.put("UPDATE_TIME", rs.getString("UPDATE_TIME"));
                table.put("CHECK_TIME", rs.getString("CHECK_TIME"));
                table.put("TABLE_COLLATION", rs.getString("TABLE_COLLATION"));
                table.put("CHECKSUM", rs.getString("TABLE_COLLATION"));
                table.put("CREATE_OPTIONS", rs.getString("TABLE_COLLATION"));
                table.put("TABLE_COMMENT", rs.getString("TABLE_COMMENT"));
                talbes.add(table);
//                String tableName = rs.getString("TABLE_NAME");
//                System.out.println("数据表名称：" + tableName);
//                System.out.print("\n");
            }
//            System.out.println(JSON.toJSON(talbes));
            // 完成后关闭
            rs.close();
//            stmt.close();
//            conn.close();
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
        }
        return talbes;

    }

    /**
     * 通过表名称去获取每个字段的信息
     * @param tablename
     * @return
     * @throws SQLException
     */
    public List<HashMap> getTableDetailByTableName(String tablename) throws SQLException {
        String sql;
        sql = "SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '" + this.db + "' and `TABLE_NAME` = '"+tablename+"'";
//        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        List<HashMap> talbeinfos = new ArrayList<HashMap>();
        // 展开结果集数据库
        while (rs.next()) {
            HashMap  columninfo = new HashMap();
            columninfo.put("TABLE_SCHEMA", rs.getString("TABLE_SCHEMA"));
            columninfo.put("TABLE_NAME", rs.getString("TABLE_NAME"));
            columninfo.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
            columninfo.put("ORDINAL_POSITION", rs.getString("ORDINAL_POSITION"));
            columninfo.put("COLUMN_DEFAULT", rs.getString("COLUMN_DEFAULT"));
            columninfo.put("IS_NULLABLE", rs.getString("IS_NULLABLE"));
            columninfo.put("DATA_TYPE", rs.getString("DATA_TYPE"));
            columninfo.put("CHARACTER_MAXIMUM_LENGTH", rs.getString("CHARACTER_MAXIMUM_LENGTH"));
            columninfo.put("CHARACTER_OCTET_LENGTH", rs.getString("CHARACTER_OCTET_LENGTH"));
            columninfo.put("NUMERIC_PRECISION", rs.getString("NUMERIC_PRECISION"));
            columninfo.put("NUMERIC_SCALE", rs.getString("NUMERIC_SCALE"));
            columninfo.put("DATETIME_PRECISION", rs.getString("DATETIME_PRECISION"));
            columninfo.put("CHARACTER_SET_NAME", rs.getString("CHARACTER_SET_NAME"));
            columninfo.put("COLLATION_NAME", rs.getString("COLLATION_NAME"));
            columninfo.put("COLUMN_TYPE", rs.getString("COLUMN_TYPE"));
            columninfo.put("COLUMN_KEY", rs.getString("COLUMN_KEY"));
            columninfo.put("EXTRA", rs.getString("EXTRA"));
            columninfo.put("PRIVILEGES", rs.getString("PRIVILEGES"));
            columninfo.put("COLUMN_COMMENT", rs.getString("COLUMN_COMMENT"));
            columninfo.put("GENERATION_EXPRESSION", rs.getString("GENERATION_EXPRESSION"));
            talbeinfos.add(columninfo);
//            String tableName = rs.getString("TABLE_NAME");
//            System.out.println("数据表名称：" + tableName);
//            System.out.print("\n");
        }
        rs.close();
        return  talbeinfos;
    }

    public HashMap getDatabaseAllInfo() throws SQLException {
        HashMap databaseAllInfo =new HashMap();
        List<HashMap> tablelists=this.getTableInfoByDatabase();
        databaseAllInfo.put("tablelists",tablelists);

        HashMap tablecolumnMaps = new HashMap();
        for (HashMap table:tablelists){
            String tablename= (String) table.get("TABLE_NAME");
            tablecolumnMaps.put(tablename,this.getTableDetailByTableName(tablename));

        }
        databaseAllInfo.put("tableColumnlists",tablecolumnMaps);

        return  databaseAllInfo;
    }
    protected void finalize() throws SQLException {
        stmt.close();
        conn.close();
    }
}
