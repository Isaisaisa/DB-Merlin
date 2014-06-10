package merlin.test;

import static org.junit.Assert.*;

import java.sql.SQLException;


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
	public void createTest() throws Exception {
		
		// test repräsentativ??
		
		Birdwatcher newBw = BirdwatcherRepository.create("Watcher", "Birdy", "what", "merlindemo", "demo@merlin.de");
	
		Birdwatcher bw = BirdwatcherImpl.valueOf("Watcher", "Birdy", "what", "merlindemo", "demo@merlin.de");
	
		
		assertEquals(bw, newBw);
	}

}

