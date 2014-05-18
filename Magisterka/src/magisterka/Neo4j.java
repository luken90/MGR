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
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;


/**
 *
 * @author Hp
 */
public class Neo4j {
    private static final String Neo4J_DBPath = "C:/Users/Hp/Downloads/neo4j-community-2.1.0-M01-windows/neo4j-community-2.1.0-M01";
    
    Node person;
    Node friend;
    Index<Node> personNIK;
    Relationship relation;
    GraphDatabaseService graphDataService;
    
    public Neo4j(){
        graphDataService = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4J_DBPath);

        Transaction transaction = graphDataService.beginTx();
        try{
        IndexManager index = graphDataService.index();
        personNIK = index.forNodes( "person" );
                    transaction.success();
        }
        finally{
            transaction.finish();
        } 
        

    }
    
    private static enum RelTypes implements RelationshipType{
        ZNA;//, NALEZY, MA_KATEGORIE, LUBI;
        
    }

    public void showCYPHER(){

        Transaction transaction = graphDataService.beginTx();
        ExecutionEngine engine = new ExecutionEngine(graphDataService);
        ExecutionResult result = engine.execute( "start n=node(*) return n");
        System.out.println(result.dumpToString());
        System.out.println("relacje");
        ExecutionResult result1 = engine.execute( "start r=rel(*) return r ");
        System.out.println(result1.dumpToString());
    }
    
    public void deleteCYPHER(){
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
    }
    
    public void addNodeCYPHER(int i, String firstname, String lastname){
        Transaction transaction = graphDataService.beginTx();
        try{
            ExecutionEngine engine = new ExecutionEngine(graphDataService);
            ExecutionResult result = engine.execute( "CREATE (n:Person {NIK : '"+i+"', firstname : '"+firstname+"', lastname: '"+lastname+"' }) RETURN n");
            transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }     
    }
    public void addRelationCYPHER(int person, int friend){
        Transaction transaction = graphDataService.beginTx();
        try{
            ExecutionEngine engine = new ExecutionEngine(graphDataService);
            ExecutionResult relacja = engine.execute("MATCH (person),(friend) WHERE person.NIK = '"+person+"' AND friend.NIK = '"+friend+"'\n"+
                                                        "CREATE (person)-[r:ZNA]->(friend)");
            transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }  
    }

    
    public void createPerson(int i, String firstname, String lastname){
        Transaction transaction = graphDataService.beginTx();
        try{
            //Create Body & set the properties 
            person = graphDataService.createNode();
            person.setProperty("firstname", firstname);
            person.setProperty("lastname",lastname);
            person.setProperty("NIK",i);
            personNIK.add(person, "NIK", person.getProperty("NIK"));
            
            //Succes transaction
            transaction.success();
        }
        finally{
            //finish the transaction
            transaction.finish();
        }    
    }
    public void createRelation(int person1, int friend1){
        Transaction transaction = graphDataService.beginTx();
        try{
            //System.out.println("Osoba "+ person1+ ", przyjaciel "+friend1);
            IndexHits<Node> hits = personNIK.get( "NIK", person1 );
            Node o1 = hits.getSingle();
            IndexHits<Node> hits1 = personNIK.get( "NIK", friend1 );
            Node o2 = hits1.getSingle();
            //System.out.println(o2.getProperty("NIK"));
            relation = o1.createRelationshipTo(o2, RelTypes.ZNA);
            relation.setProperty("relationship-type", "Znajomi");
//            System.out.println(first.getProperty("name").toString());
//            System.out.println(relation.getProperty("relationship-type").toString());
//            System.out.println(second.getProperty("name").toString());
            
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
            person.getSingleRelationship(RelTypes.ZNA, Direction.OUTGOING).delete();
            System.out.println("Usunięto węzły");
            //delete nodes
            person.delete();
            friend.delete();
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