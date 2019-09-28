/**
 * 
 */
package it.asirchia.utils.properties;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import it.asirchia.utils.properties.getters.GetterFromEnvironment;
import it.asirchia.utils.properties.getters.GetterFromEtcd;
import it.asirchia.utils.properties.getters.GetterFromFile;
import it.asirchia.utils.properties.getters.GetterFromZookeeper;

/**
 *  TestJunit - to test the PropertyManager project
 *  Copyright (C) 2018  - Antonino Sirchia
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *  @author Antonino Sirchia (asirchia@gmail.com)
 */
class TestCase {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestCase.class);

	
	private static final String key = "user.name";
	
	private static final String expectedFileString = "UsernameFromConfigurationFile";
	private static final String expectedEnvString = "UsernameFromEnvironment";
	private static final String expectedZookeeperString = "UsernameFromZookeeper";
	private static final String expectedEtcdString = "etcdusername";
	
	/**
	 * Tests the configuration from file
	 */
	@Test
	void testFromFile() {

		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		LOG.debug("Executing test");
		Optional<String> opt = new GetterFromFile().get(key);
		assertTrue(opt.isPresent());
		
		assertTrue(expectedFileString.equals(opt.get()));
		
		LOG.info("Test OK");
	}
	
	/**
	 * Tests the configuration from environment variable
	 */
	@Test
	void testFromEnv() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		LOG.debug("Mocking environmental data");
		GetterFromEnvironment getterFromEnvironment = mock(GetterFromEnvironment.class, RETURNS_DEEP_STUBS);
		when(getterFromEnvironment.get(key)).thenReturn(Optional.ofNullable(expectedEnvString));

		LOG.debug("Executing test");
		Optional<String> opt = getterFromEnvironment.get(key);
		assertTrue(opt.isPresent());
		
		assertTrue(expectedEnvString.equals(opt.get()));
		
		LOG.info("Test OK");
	}
	
	/**
	 * Tests the configuration from Zookeeper server
	 */
	@Test
	void testFromZookeeper() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		LOG.debug("Mocking environmental data");
		GetterFromZookeeper getterFromZookeeper = mock(GetterFromZookeeper.class, RETURNS_DEEP_STUBS);
		when(getterFromZookeeper.get(key)).thenReturn(Optional.ofNullable(expectedZookeeperString));

		LOG.debug("Executing test");
		Optional<String> opt = getterFromZookeeper.get(key);
		assertTrue(opt.isPresent());
			
		assertTrue(expectedZookeeperString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	/**
	 * Tests the configuration from Zookeeper server
	 */
	@Test
	void testFromEtcd() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		LOG.debug("Mocking environmental data");
		GetterFromEtcd getterFromEtcd = mock(GetterFromEtcd.class, RETURNS_DEEP_STUBS);
		when(getterFromEtcd.get(key)).thenReturn(Optional.ofNullable(expectedEtcdString));

		LOG.debug("Executing test");
		Optional<String> opt = getterFromEtcd.get(key);
		assertTrue(opt.isPresent());
		
		assertTrue(expectedEtcdString.equals(opt.get()));
		
		LOG.info("Test OK");			
	}


}
