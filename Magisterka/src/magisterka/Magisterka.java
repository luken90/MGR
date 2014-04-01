/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magisterka;
//package com.Neo4J;

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
    private static final String Neo4J_DBPath = "C:/Users/Hp/Downloads/neo4j-community-2.1.0-M01-windows/neo4j-community-2.1.0-M01";
    //private static final String Neo4J_DBPath = "C:/Users/Hp/Documents/Neo4j/default.graphdb";
    
    Node first;
    Node second;
    Relationship relation;
    GraphDatabaseService graphDataService;
    
    //lista relacji znających węzeł 2
    private static enum RelTypes implements RelationshipType{
        KNOWS;
    }
    

    public void wyswietlCYPHER(){

        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        Transaction transaction = graphDataService.beginTx();
        ExecutionEngine engine = new ExecutionEngine(graphDataService);
        ExecutionResult result = engine.execute( "start n=node(*) return n");
        System.out.println(result.dumpToString());
        System.out.println("relacje");
        ExecutionResult result1 = engine.execute( "start r=rel(*) return r ");
        System.out.println(result1.dumpToString());
    }
    
    public void kasowanieCYPHER(){

        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        Transaction transaction = graphDataService.beginTx();
        try{
        ExecutionEngine engine = new ExecutionEngine(graphDataService);
        ExecutionResult result = engine.execute( "MATCH (n)\n" +
                                                    "OPTIONAL MATCH (n)-[r]-()\n" +
                                                    //"WHERE (ID(n)>2000 AND ID(n)<2010)\n"+
                                                    "DELETE n,r");
        System.out.println(result.dumpToString());
                    transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }  
        //ExecutionResult result = engine.execute("START n = node(*) OPTIONAL MATCH n-[r?]-() WHERE ID(n)>0 DELETE n, r");
        
    }
    
        public void addCYPHER(int prog){

        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        Transaction transaction = graphDataService.beginTx();
        try{
            ExecutionEngine engine = new ExecutionEngine(graphDataService);


//            WSTAWIANIE WIERZCHOŁKÓW POJEDYNCZO
//            for(int i =0; i < prog; i++){
//                int random = (int)(Math.random()*100);
//                ExecutionResult result = engine.execute( "CREATE (a { name : '"+random+"' }) RETURN a");
//            }


//            WSTAWIANIE WIERZCHOŁKÓW PARAMI           
            for(int i =0; i < prog; i++){
                int random = (int)(Math.random()*100);
                int random2 = (int)(Math.random()*100);
                ExecutionResult result = engine.execute( "CREATE (a { name : '"+random+"' }) RETURN a");
                ExecutionResult result1 = engine.execute( "CREATE (a { name : '"+random2+"' }) RETURN a");
                ExecutionResult relacja = engine.execute("MATCH (a),(b)\n" +
                                                        "WHERE a.name = '"+random+"' AND b.name = '"+random2+"'\n" +
                                                        "CREATE (a)-[r:ZNA]->(b)\n" +
                                                        "RETURN r");
                
            }            
            
            
            //System.out.println(result.dumpToString());
                        transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }  
        //ExecutionResult result = engine.execute("START n = node(*) OPTIONAL MATCH n-[r?]-() WHERE ID(n)>0 DELETE n, r");
        
    }
    
    public void createDataBase(){
        //GraphDatabaseService
        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        //BeginTransaction
        Transaction transaction = graphDataService.beginTx();
        try{
            //Create Body & set the properties 
            first = graphDataService.createNode();
            first.setProperty("name", "jan");
            
            second = graphDataService.createNode();
            second.setProperty("name", "nowak");
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
    
    public void removeData(){
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
    public void shutDown(){
        //shutdown graphDataService
        graphDataService.shutdown();
        System.out.println("Wyłączono bazę");
    }   
    
    public String randomString(int len){
        char[] str = new char[100];

        for (int i = 0; i < len; i++){
              str[i] = (char) (((int)(Math.random() * 26)) + (int)'A');
            }

        return (new String(str, 0, len));
    }

    
    public static void main(String[] args) {
        // TODO code application logic here
        Magisterka hello = new Magisterka();
        //hello.createDataBase();
        //hello.removeData();
        //hello.kasowanieCYPHER();
        //hello.shutDown();
        //hello.addCYPHER(2);
        //hello.wyswietlCYPHER();
        //String a =hello.randomString(7);
        //System.out.println((int)'A');
    }

}
