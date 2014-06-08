package merlin.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.UnsupportedLookAndFeelException;

import merlin.data.BirdwatcherRepository;
import merlin.data.entities.Birdwatcher;
import merlin.data.entities.BirdwatcherImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class BirdwatcherRepositoryTest {
	
@BeforeClass
public static void beforeClass() throws ClassNotFoundException, SQLException{
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
public void createTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedLookAndFeelException {
	
	// test repr‰sentativ??
	
	Birdwatcher newBw = BirdwatcherRepository.create("Watcher", "Birdy", "dimo", "merlindemo", "demo@merlin.de");
	// TODO id des erstellten birdwatchers holen und anschlieﬂend birdwatcher Im repo aufnehmen
	Birdwatcher bw = BirdwatcherImpl.valueOf("Watcher" , "Birdy", "dimo", "merlindemo", "demo@merlin.de");
	
	assertEquals(bw, newBw);
}


@Test
public void isRegisteredTest() throws Exception {
	System.out.println("isRegistered");
//	assertTrue(BirdwatcherRepository.isRegistered("'demo'","'merlindemo'".toCharArray()));
	assertEquals(true, BirdwatcherRepository.isRegistered("demo","merlindemo".toCharArray()));
}
	

}

