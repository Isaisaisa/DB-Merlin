package merlin.test;

import static org.junit.Assert.*;
import static merlin.utils.ConstantElems.*;

import merlin.base.Application;

import org.junit.Before;
import org.junit.Test;

public class PropertiesTest {
	
	private Application app;

	@Before
	public void setUp() throws Exception {
		app =  Application.getInstance();
		
		loadProperties();
//		app.putPropDefaults();
//		app.saveProp(dbPortPropKey, "666");
	}

	@Test
	public void testGetProp() throws Exception {
		assertEquals("1521", app.getProp(dbPortPropKey));
	}
	
	@Test
	public void testGetDbURL() throws Exception {
		assertEquals(propDefaults.get(dbURLPropKey), app.getDbURL());
	}
	
	@Test
	public void testStoreLoadAndRead() throws Exception {
		
	}
	
}
