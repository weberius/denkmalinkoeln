package de.illilli.opendata.service.denkmalinkoeln;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the write method.
 *
 */
public class DenkmalinkoelnFacadeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testWriteErrors() throws IOException {
		DenkmalinkoelnFacade.writeErrors("errors");
	}

}
