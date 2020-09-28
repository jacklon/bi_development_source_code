//package org.java.bi.ck.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class CkService {
//
//    @Autowired
//    private Connection getClickHouseConn;
//
//    public List<Map<String,String>> exeSql(String sql){
//
//        Connection connection = getClickHouseConn;
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet results = statement.executeQuery(sql);
//            ResultSetMetaData rsmd = results.getMetaData();
//            List<Map<String,String>> list = new ArrayList<>();
//            while(results.next()){
//                Map<String,String> row = new HashMap<>();
//                for(int i = 1;i<=rsmd.getColumnCount();i++){
//                    row.put(rsmd.getColumnName(i),results.getString(rsmd.getColumnName(i)));
//                }
//                list.add(row);
//            }
//
//            return list;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
