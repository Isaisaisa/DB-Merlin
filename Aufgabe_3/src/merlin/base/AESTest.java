package merlin.base;

import static org.junit.Assert.*;
import static merlin.base.AES.*;

import org.junit.Test;

public class AESTest {

	@Test
	public void testEncryptDecrypt() throws Exception {
		String test = "teststring  }0123St ring";
		assertEquals(decrypt(encrypt(test)), test);
	}

}
