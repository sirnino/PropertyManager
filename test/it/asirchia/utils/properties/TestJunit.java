/**
 * 
 */
package it.asirchia.utils.properties;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * @author asirchia
 *
 */
class TestJunit {
	
	private static final String key = "user.name";
	
	@Test
	void testFromFile() {
		Optional<String> opt = Properties.fromFile(key);
		assertTrue(opt.isPresent());
		
		System.out.println("Found from file "+key+" = "+opt.get());
		
		assertTrue("UsernameFromConfigurationFile".equals(opt.get()));
	}
	
	@Test
	void testFromEnv() {	
		Optional<String> opt = Properties.fromEnv(key);
		assertTrue(opt.isPresent());
		
		System.out.println("Found from environment "+key+" = "+opt.get());
		
		assertTrue("UsernameFromEnvironment".equals(opt.get()));
	}
	
	@Test
	void testFromZookeeper() {
		Optional<String> opt = Properties.fromZookeper(key);
		assertTrue(opt.isPresent());
		
		System.out.println("Found from zookeeper "+key+" = "+opt.get());
		
		assertTrue("UsernameFromZookeeper".equals(opt.get()));
	}

}
