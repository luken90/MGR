/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magisterka;
//package com.Neo4J;

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
    private static final String Neo4J_DBPath = "/Users/Hp/Downloads/neo4j-community-2.1.0-M01-windows/neo4j-community-2.1.0-M01";
    //private static final String Neo4J_DBPath = "/Users/Hp/Documents/Neo4j/default.graphdb";
    
    Node first;
    Node second;
    Relationship relation;
    GraphDatabaseService graphDataService;
    
    
    //lista relacji znających węzeł 2
    private static enum RelTypes implements RelationshipType{
        KNOWS;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Magisterka hello = new Magisterka();
        hello.createDataBase();
        hello.removeData();
        hello.shutDown();
    }
    void createDataBase(){
        //GraphDatabaseService
        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        //BeginTransaction
        Transaction transaction = graphDataService.beginTx();
        try{
            //Create Body & set the properties 
            first = graphDataService.createNode();
            first.setProperty("name", "Łukasz");
            
            second = graphDataService.createNode();
            second.setProperty("name", "Gąbka");
            //Relationship
            relation = first.createRelationshipTo(second, RelTypes.KNOWS);
            relation.setProperty("relationship-type", "Dane");
            System.out.println(first.getProperty("name").toString());
            System.out.println(relation.getProperty("relationship-type").toString());
            System.out.println(second.getProperty("name").toString());
            
            //Succes transaction
            transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }
        
    }
    void removeData(){
        Transaction transaction = graphDataService.beginTx();
        try{
            //delete
            first.getSingleRelationship(RelTypes.KNOWS, Direction.OUTGOING).delete();
            System.out.println("Usunięto węzły");
            //delete nodes
            first.delete();
            second.delete();
            transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }
    }
    void shutDown(){
        //shutdown graphDataService
        graphDataService.shutdown();
        System.out.println("Wyłączono bazę");
    }
}
