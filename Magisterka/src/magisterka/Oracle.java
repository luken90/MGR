/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magisterka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Hp
 */
public class Oracle {
    Connection connection;
    
        public Oracle() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection( "jdbc:oracle:thin:@localhost:1521:XE", "Oracle", "oracle");
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
        
        public String StworzTabele () throws ClassNotFoundException, SQLException {
        String tekst = "";
        if (connection != null) {
            java.sql.Statement s = connection.createStatement();
            s.execute("CREATE TABLE Oracle.Friends (" +
                        "  NIK INTEGER CONSTRAINT klienci_pk PRIMARY KEY," +
                        "  Person VARCHAR2(30)," +
                        "  Friend VARCHAR2(30)" +
                        ") ");
            tekst = "Dodano do bazy danych!";
            s.close();
            connection.close();
        } 
        else {
            tekst = "Nie moge się połączyć!";
        }
        return tekst;
    }

    public String zapiszKlientRekordy () throws ClassNotFoundException, SQLException {
        String tekst = "";
        if (connection != null) {
            java.sql.Statement s = connection.createStatement();
            s.execute("INSERT INTO Oracle.Friends (NIK, Person, Friend) VALUES (1, 'Janko', 'Muzykant')");   
            tekst = "Dodano do bazy danych!";
            s.close();
            connection.close();
        } 
        else {
            tekst = "Nie moge się połączyć!";
        }
        return tekst;
    }    
}
