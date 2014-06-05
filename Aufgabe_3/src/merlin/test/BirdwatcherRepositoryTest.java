package merlin.test;

import static org.junit.Assert.*;

import java.sql.SQLException;



//import merlin.base.DbWrapper;
import merlin.data.BirdwatcherRepository;
import merlin.data.entities.Birdwatcher;
import merlin.data.entities.BirdwatcherImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class BirdwatcherRepositoryTest {
//	private static DbWrapper dbWrapper;
	
@BeforeClass
public static void beforeClass() throws ClassNotFoundException, SQLException{
//	dbWrapper = DbWrapper.valueOf();
//	
	System.out.println( "@BeforeClass" );
	}
@AfterClass 
public static void afterClass() {
	
	System.out.println( "@AfterClass" );}
@Before 
public void setUp(){
	System.out.println( " @Before" );}
@After 
public void tearDown(){
	System.out.println( " @After" );}



@Test
public void createTest() {
	Birdwatcher bw = BirdwatcherImpl.valueOf(3, "Watcher" , "Birdy", "damo", "merlindemo", "demo@merlin.de", "R03");
	Birdwatcher newBw = BirdwatcherRepository.create("Watcher", "Birdy", "damo", "merlindemo".toCharArray(), "demo@merlin.de");
	assertEquals(bw, newBw);
}


@Test
public void isRegisteredTest(){
	System.out.println("isRegistered");
	assertTrue(BirdwatcherRepository.isRegistered("'demo'","'merlindemo'".toCharArray()));
	
}
	

}

