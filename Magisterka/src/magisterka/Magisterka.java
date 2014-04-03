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
    public String randomString(int len){
        char[] str = new char[100];

        for (int i = 0; i < len; i++){
              str[i] = (char) (((int)(Math.random() * 26)) + (int)'A');
            }

        return (new String(str, 0, len));
    }

    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // TODO code application logic here
        Neo4j hello = new Neo4j();
        //hello.createDataBase();
        //hello.removeData();
        //hello.deleteCYPHER();
        //hello.shutDown();
        //hello.addCYPHER(2);
        hello.showCYPHER();
        //String a =hello.randomString(7);
        //System.out.println((int)'A');
        //Oracle ora = new Oracle();
        //System.out.println(ora.zapiszKlientRekordy());
    }

}
