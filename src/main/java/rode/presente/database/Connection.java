package rode.presente.database;

import lombok.AllArgsConstructor;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.*;
import java.sql.*;

public class Connection {
    private static Connection instance;
    private static Connection getInstance(){
        if(instance == null)
            instance = new Connection();
        return instance;
    }
    private java.sql.Connection geti() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:sql/banco/BD.db");
    }
    public static void query(String sql, Object[] params, CallbackRS callback) throws SQLException {
        try(java.sql.Connection con = getInstance().geti();
            PreparedStatement stmt = con.prepareStatement(sql);){
            if(params != null)
                for(int i=0; i<params.length; i++)
                    bindParam(stmt, params[i], i+1);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next() && callback != null)
                    callback.aply(rs,rs.getRow());
            }
        }
    }
    public static void queryCRS(String sql, Object[] params, CallbackCRS callback) throws SQLException {
        try(java.sql.Connection con = getInstance().geti();
            PreparedStatement stmt = con.prepareStatement(sql);){
            if(params != null)
                for(int i=0; i<params.length; i++)
                    bindParam(stmt, params[i], i+1);
            try(ResultSet rs = stmt.executeQuery()){
                    CachedRowSet crs = getCRS(rs);
                    if(crs.next() && callback != null)
                        callback.aply(crs);

            }
        }
    }
    public static void update(String sql, Object[] params, CallbackRS callback) throws SQLException {
        System.out.println("sql>>>>>>>>>>>>.\n");
        System.out.println(sql);
        System.out.println("<<<<<<<<<<<<");
        try(java.sql.Connection con = getInstance().geti();
            PreparedStatement stmt = con.prepareStatement(sql)){
            if(params != null)
                for(int i=0; i<params.length; i++)
                    bindParam(stmt, params[i], i+1);
            int rows = stmt.executeUpdate();
            if(callback != null)
                callback.aply(null, rows);
        }
    }

    private static void  bindParam(PreparedStatement stmt, Object obj, int index) throws SQLException {
        Class<?> objClass = obj.getClass();
        if(objClass.equals(Integer.class) || objClass.equals(int.class))
            stmt.setInt(index, (int) obj);
        else if(objClass.equals(Double.class) || objClass.equals(double.class))
            stmt.setDouble(index, (double) obj);
        else if(objClass.equals(byte.class) || objClass.equals(Byte.class))
            stmt.setByte(index, (byte) obj);
        else if(objClass.equals(byte[].class) || objClass.equals(Byte[].class))
            stmt.setBytes(index, (byte[]) obj);
        else
            stmt.setString(index, obj.toString());
    }

    public static void create() throws SQLException, IOException {
        File f = new File("sql/up.sql");
        BufferedReader ois = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String s="",k="";
        while((k = ois.readLine()) != null)
            s += k;
        for(String str : s.split(";"))
            update(
                    String.format("%s;",str),
                    null,
                    (rs, n) -> System.out.println(n)
            );
    }

    public static CachedRowSet getCRS(ResultSet rs) throws SQLException {
        RowSetFactory rsf = RowSetProvider.newFactory();
        CachedRowSet crs = rsf.createCachedRowSet();
        crs.populate(rs);
        crs.moveToCurrentRow();
        return crs;
    }

    public static interface CallbackRS {
        public void aply(ResultSet rs, int rows) throws SQLException;
    }
    public static interface CallbackCRS{
        public void aply(CachedRowSet rs) throws SQLException;
    }


    public static class DataGetter{
        private CachedRowSet rs;
        private ResultSetMetaData rsmd;
        private String tableName;

        public DataGetter(CachedRowSet rs, String tableName) throws SQLException {
            this.rs = rs;
            this.rsmd = rs.getMetaData();
            this.tableName = tableName;
        }

        public int getColumnIndex( String column) throws SQLException {
            int n = rsmd.getColumnCount();
            for(int x = 1; x <= n ; x++)
                if(rsmd.getTableName(x).equalsIgnoreCase(tableName) && rsmd.getColumnName(x).equalsIgnoreCase(column)) {
                    return x;
                }
            return -1;
        }

        public static int getColumnIndex(ResultSet rs, String tableName, String columnName) throws SQLException {
            ResultSetMetaData rsmd = rs.getMetaData();
            int n = rsmd.getColumnCount();
            for(int x = 1; x <= n ; x++)
                if(rsmd.getTableName(x).equalsIgnoreCase(tableName) && rsmd.getColumnName(x).equalsIgnoreCase(columnName)) {
                    return x;
                }
            return -1;
        }

        public String getString(String column) throws SQLException {
            return rs.getString(getColumnIndex(column));
        }

        public int getInt(String column) throws SQLException{
            return rs.getInt(getColumnIndex(column));
        }

        public double getDouble(String column) throws SQLException{
            return rs.getDouble(getColumnIndex(column));
        }

        public byte[] getBytes(String column) throws SQLException{
            return rs.getBytes(getColumnIndex(column));
        }

        public boolean getBoolean(String column) throws SQLException{
            return rs.getBoolean(getColumnIndex(column));
        }
    }
}
