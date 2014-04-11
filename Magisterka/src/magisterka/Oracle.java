/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magisterka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Hp
 */
public class Oracle {
    Connection connection;
    //Connection connection1;
    
        public Oracle() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection( "jdbc:oracle:thin:@localhost:1521:XE", "Oracle", "oracle");
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
        
        public String StworzTabelePerson () throws ClassNotFoundException, SQLException {
        String tekst = "";
        if (connection != null) {
            java.sql.Statement s = connection.createStatement();
            s.execute("CREATE TABLE Oracle.Person (" +
                        "  NIK INTEGER CONSTRAINT person_pk PRIMARY KEY," +
                        "  FirstName VARCHAR2(260)," +
                        "  LastName VARCHAR2(260)" +
                        ") ");
            tekst = "Dodano do bazy danych!";
            s.close();
            //ZamknijPolaczenie();
        } 
        else {
            tekst = "Nie moge się połączyć!";
        }
        return tekst;
    }
        
        
    public String StworzTabeleFriends () throws ClassNotFoundException, SQLException {
        String tekst = "";
        if (connection != null) {
            java.sql.Statement s = connection.createStatement();
            s.execute("CREATE TABLE Oracle.Friends (" +
                        "  Person INTEGER CONSTRAINT osoba REFERENCES Person(NIK)," +
                        "  Friend INTEGER CONSTRAINT przyjaciel REFERENCES Person(NIK)," +
                        "  CONSTRAINT friend_pk PRIMARY KEY(Person,Friend)" +
                        ") ");
            tekst = "Dodano do bazy danych!";
            s.close();
            //ZamknijPolaczenie();
        } 
        else {
            tekst = "Nie moge się połączyć!";
        }
        return tekst;
    }
    
        public String StworzSekwencje () throws ClassNotFoundException, SQLException {
        String tekst = "";
        if (connection != null) {
            java.sql.Statement s = connection.createStatement();
                       
            s.execute("create sequence Oracle.person_seq \n" +
                        "start with 1 \n" +
                        "increment by 1 \n" +
                        "nomaxvalue;\n" +
                        "\n" +
                        "create or replace TRIGGER Oracle.TRIGGER_Person\n" +
"                        BEFORE INSERT ON Oracle.Person\n" +
"                        REFERENCING NEW AS NEW\n" +
"                        FOR EACH ROW\n" +
"                          BEGIN\n" +
"                            SELECT person_seq.nextval INTO :NEW.NIK FROM dual;\n" +
"                          END;");

            tekst = "Dodano do bazy danych!";
            s.close();
            //ZamknijPolaczenie();
        } 
        else {
            tekst = "Nie moge się połączyć!";
        }
        return tekst;
    }

    public void/* String*/ DodajRekordyPerson (int i, String FirstName, String LastName) throws ClassNotFoundException, SQLException {
        String tekst = "";
        if (connection != null) {
            java.sql.Statement s = connection.createStatement();
            s.execute("INSERT INTO Oracle.Person (NIK, FirstName, LastName) VALUES ("+i+" ,'"+FirstName+"', '"+LastName+"')");   
            tekst = "Dodano do bazy danych!";
            s.close();
            //ZamknijPolaczenie();
        } 
        else {
            tekst = "Nie moge się połączyć!";
        }
        //return tekst;
    }
    public void ZamknijPolaczenie() throws SQLException{
        connection.close();
    }
    public String DodajRekordyFriends (int Person, int Friend) throws ClassNotFoundException, SQLException {
        String tekst = "";
        if (connection != null) {
            java.sql.Statement s = connection.createStatement();
            s.execute("INSERT INTO Oracle.Friends (Person, Friend) VALUES ('"+Person+"', '"+Friend+"')");   
            tekst = "Dodano do bazy danych!";
            s.close();
            //ZamknijPolaczenie();
        } 
        else {
            tekst = "Nie moge się połączyć!";
        }
        return tekst;
    }
    public boolean SprawdzRelacje(int person, int friend) throws SQLException{
        boolean flaga=false;
        java.sql.Statement s = connection.createStatement();
        ResultSet rs;  
        rs=s.executeQuery("Select count(1) From Friends WHERE Person="+person+" and Friend="+friend+"");
        rs.next();
        int a = rs.getInt(1);
        s.close();
        if (a==0){
            flaga=true;
        }
        return flaga;
    }
}
