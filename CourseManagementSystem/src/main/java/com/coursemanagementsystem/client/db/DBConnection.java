/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursemanagementsystem.client.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
    
    Connection conn = ConnectDB();

    public Connection ConnectDB() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cms?", "root", "");

            return conn;
        } catch (Exception e) {
            System.out.println("Error!!");
        }
        return conn;
    }
    
    public void insert(String sql) throws SQLException {

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.execute();
    }
    
    public void remove(String sql) throws SQLException {

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
    public void update(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
    public ResultSet retrieve(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

}
