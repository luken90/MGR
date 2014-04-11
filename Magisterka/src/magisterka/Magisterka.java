/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magisterka;
//package com.Neo4J;

import java.sql.SQLException;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


/**
 *
 * @author Hp
 */
public class Magisterka {

    /**
     * @param args the command line arguments
     */
    public static String randomString(int len){
        char[] str = new char[100];

        for (int i = 0; i < len; i++){
              str[i] = (char) (((int)(Math.random() * 26)) + (int)'A');
            }

        return (new String(str, 0, len));
    }

    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        Neo4j neo4j = new Neo4j();
        Oracle oracle = new Oracle();
        int uzytkownicy=30;
        for(int i=1 ; i <=uzytkownicy; i++){
            String imie= randomString(16);
            String nazwisko= randomString(16);
            oracle.DodajRekordyPerson(i,imie, nazwisko);
            neo4j.addNodeCYPHER(i,imie, nazwisko);
        }
        
        
        int relacje=100;
        //int maks= 10;
        for (int j=1; j <=relacje; j++){
            int person = (int)(Math.random()*uzytkownicy+1);
            int friend = (int)(Math.random()*uzytkownicy+1);
            System.out.println("Osoba "+ person + ", przyjaciel "+ friend);
            if (person != friend){
                if (oracle.SprawdzRelacje(person, friend)){
                    oracle.DodajRekordyFriends(person, friend);
                    neo4j.addRelacionCYPHER(person, friend);
                }
                else{
                    j--;
                }
            }
            else{
                j--;
            }

        }
        //neo4j.showCYPHER();
        //neo4j.deleteCYPHER();
        neo4j.shutDown();
        oracle.ZamknijPolaczenie();
        
    }

}
