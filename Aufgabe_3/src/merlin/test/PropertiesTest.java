package merlin.test;

import static org.junit.Assert.*;
import static merlin.utils.ConstantElems.*;

import merlin.base.Application;

import org.junit.Before;
import org.junit.Test;

public class PropertiesTest {

	@Before
	public void setUp() throws Exception {
		loadProperties();
		Application.getInstance().putPropDefaults();
		Application.getInstance().saveProp(dbPortPropKey, "666");
	}

	@Test
	public void testGetProp() throws Exception {
		assertEquals("666", Application.getInstance().getProp(dbPortPropKey));
	}
	
	@Test
	public void testGetDbURL() throws Exception {
		assertEquals(propDefaults.get(dbURLPropKey),Application.getInstance().getDbURL());
	}
}
