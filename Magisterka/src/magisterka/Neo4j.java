/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magisterka;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;


/**
 *
 * @author Hp
 */
public class Neo4j {
    private static final String Neo4J_DBPath = "C:/Users/Hp/Downloads/neo4j-community-2.1.0-M01-windows/neo4j-community-2.1.0-M01";
    
    Node first;
    Node second;
    Relationship relation;
    GraphDatabaseService graphDataService;
    
    public Neo4j(){
        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        //Index<Node> nodeIndex = graphDataService.index().forNodes("Person");

    }
    
    private static enum RelTypes implements RelationshipType{
        KNOWS;
    }

    public void showCYPHER(){

        //graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        Transaction transaction = graphDataService.beginTx();
        ExecutionEngine engine = new ExecutionEngine(graphDataService);
        ExecutionResult result = engine.execute( "start n=node(*) return n");
        System.out.println(result.dumpToString());
        System.out.println("relacje");
        ExecutionResult result1 = engine.execute( "start r=rel(*) return r ");
        System.out.println(result1.dumpToString());
    }
    
    public void deleteCYPHER(){

        //graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
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
            transaction.finish();
        }  
        //ExecutionResult result = engine.execute("START n = node(*) OPTIONAL MATCH n-[r?]-() WHERE ID(n)>0 DELETE n, r");
        
    }
    
    public void addNodeCYPHER(int i, String firstname, String lastname){

        //graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
        Transaction transaction = graphDataService.beginTx();
        try{
            ExecutionEngine engine = new ExecutionEngine(graphDataService);


//            WSTAWIANIE WIERZCHOŁKÓW POJEDYNCZO
//            for(int i =0; i < prog; i++){
//                int random = (int)(Math.random()*100);
//                ExecutionResult result = engine.execute( "CREATE (a { name : '"+random+"' }) RETURN a");
//            }


//            WSTAWIANIE WIERZCHOŁKÓW PARAMI           
//            for(int i =0; i < prog; i++){
//                int random = (int)(Math.random()*100);
//                int random2 = (int)(Math.random()*100);
                ExecutionResult result = engine.execute( "CREATE (n:Person {NIK : '"+i+"', firstname : '"+firstname+"', lastname: '"+lastname+"' }) RETURN n");
                //ExecutionResult result1 = engine.execute( "CREATE (a { name : '"+random2+"' }) RETURN a");
                //ExecutionResult result = engine.execute( "CREATE (a { name : '"+random+"' }) RETURN a");
                //ExecutionResult result1 = engine.execute( "CREATE (a { name : '"+random2+"' }) RETURN a");
                /*ExecutionResult relacja = engine.execute("MATCH (a),(b)\n" +
                                                        "WHERE a.name = '"+random+"' AND b.name = '"+random2+"'\n" +
                                                        "CREATE (a)-[r:ZNA]->(b)\n" +
                                                        "RETURN r");*/
                
//            }            
            
            
            //System.out.println(result.dumpToString());
                        transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }  
        //shutDown();
        //ExecutionResult result = engine.execute("START n = node(*) OPTIONAL MATCH n-[r?]-() WHERE ID(n)>0 DELETE n, r");
        
    }
    public void addRelacionCYPHER(int person, int friend){

        Transaction transaction = graphDataService.beginTx();
        try{
            ExecutionEngine engine = new ExecutionEngine(graphDataService);
                //"MATCH (Person { NIK:'14' }) RETURN Person"
                //"MATCH (a),(b) WHERE a.NIK = '14' AND b.NIK = '15' return a,b"
                ExecutionResult relacja = engine.execute("MATCH (person),(friend) WHERE person.NIK = '"+person+"' AND friend.NIK = '"+friend+"'\n"+
                                                        "CREATE (person)-[r:ZNA]->(friend)");
                /*ExecutionResult relacja = engine.execute("MATCH (a),(b)\n" +
                                                        "WHERE a.name = '"+random+"' AND b.name = '"+random2+"'\n" +
                                                        "CREATE (a)-[r:ZNA]->(b)\n" +
                                                        "RETURN r");*/
                
//            }            
            
            
            //System.out.println(result.dumpToString());
                        transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }  
        //shutDown();
        //ExecutionResult result = engine.execute("START n = node(*) OPTIONAL MATCH n-[r?]-() WHERE ID(n)>0 DELETE n, r");
        
    }

    
    public void createDataBase(){
        //GraphDatabaseService
        //graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);
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
}



/*Zapytania
match p=(n {NIK:'1'})-[r:ZNA*..2]->m return m.NIK, length(p) as dystans order by dystans

match p=(n {NIK:'1'})-[r:ZNA*..2]->m return m.NIK, length(p) as dystans, extract(x in nodes(p) : x.NIK) as sciezka order by dystans











start n=node:node_auto_index(NIK='1') match p = n-[r:ZNA*..3]->m return m.NIK, length(p) as distance, extract(x in nodes(p) : x.NIK) as nodes_in_path


start n=node:node_auto_index(NIK='1') match p = n-[r:ZNA*..3]->m return m.NIK, length(p) as distance, extract(x in nodes(p) : x.NIK) as nodes_in_path


match (n) where n.NIK='1' match p = n-[r:ZNA*..3]->m return m.NIK, length(p) as distance, extract(x in nodes(p) : x.NIK) as nodes_in_path

match (n) where n.NIK='1' return n
match p = n-[r:ZNA*..3]->m return m.NIK, length(p) as distance, extract(x in nodes(p) : x.NIK) as nodes_in_path

match n,p where n.NIK='1' and p = n-[r:ZNA*..3]->m return m.NIK, length(p) as distance, extract(x in nodes(p) : x.NIK) as nodes_in_path
*/