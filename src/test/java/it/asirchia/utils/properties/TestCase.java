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
import it.asirchia.utils.properties.getters.RemotePropertyGetter;

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
	
	private void initMocks(String fileValue, String envValue, Class<? extends RemotePropertyGetter> remoteGetter, String remoteValue) {
		
		LOG.debug("Mocking file data");
		//The configuration file contains a default value for "key"
		GetterFromFile getterFromFile = mock(GetterFromFile.class, RETURNS_DEEP_STUBS);
		when(getterFromFile.get(key)).thenReturn(Optional.ofNullable(fileValue));
		
		LOG.debug("Mocking environmental data");
		//No environmental variable for "key" is defined
		GetterFromEnvironment getterFromEnvironment = mock(GetterFromEnvironment.class, RETURNS_DEEP_STUBS);
		when(getterFromEnvironment.get(key)).thenReturn(Optional.ofNullable(envValue));
		
		LOG.debug("Mocking remote ["+remoteGetter.getSimpleName()+"] data");
		RemotePropertyGetter remoteGetterInstance = mock(remoteGetter, RETURNS_DEEP_STUBS);
		when(remoteGetterInstance.get(key)).thenReturn(Optional.ofNullable(remoteValue));
		
		PropertiesConfig.build(getterFromFile, getterFromEnvironment, remoteGetterInstance);
	}
	
	@Test
	void testFromFile_Zookeeper() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, null, GetterFromZookeeper.class, null);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the zookeeper's one
		assertTrue(opt.isPresent());
		assertTrue(expectedFileString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testFromFile_Etcd() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, null, GetterFromEtcd.class, null);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the zookeeper's one
		assertTrue(opt.isPresent());
		assertTrue(expectedFileString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testFromZookeeper() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, null, GetterFromZookeeper.class, expectedZookeeperString);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the Zookeeper's one
		assertTrue(opt.isPresent());
		assertTrue(expectedZookeeperString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testFromEtcd() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, null, GetterFromEtcd.class, expectedEtcdString);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the Etcd's one
		assertTrue(opt.isPresent());
		assertTrue(expectedEtcdString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	
	@Test
	void testFromEnvironment_emptyZookeeper() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, expectedEnvString, GetterFromZookeeper.class, null);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the environment's one
		assertTrue(opt.isPresent());
		assertTrue(expectedEnvString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testFromEnvironment_emptyEtcd() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, expectedEnvString, GetterFromEtcd.class, null);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the environment's one
		assertTrue(opt.isPresent());
		assertTrue(expectedEnvString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testFromEnvironment_Zookeeper() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, expectedEnvString, GetterFromZookeeper.class, expectedZookeeperString);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the environment's one
		assertTrue(opt.isPresent());
		assertTrue(expectedEnvString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testFromEnvironment_Etcd() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(expectedFileString, expectedEnvString, GetterFromEtcd.class, expectedEtcdString);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the environment's one
		assertTrue(opt.isPresent());
		assertTrue(expectedEnvString.equals(opt.get()));
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testEmpty_Etcd() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(null, null, GetterFromEtcd.class, null);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the empty
		assertTrue(!opt.isPresent());
		
		LOG.info("Test OK");
		
	}
	
	@Test
	void testEmpty_Zookeeper() {
		
		LOG.info("Running " + new Throwable().getStackTrace()[0].getMethodName());
		
		initMocks(null, null, GetterFromZookeeper.class, null);
		
		LOG.debug("Executing test");
		Optional<String> opt = Properties.get(key);
		
		//The Properties should be the empty
		assertTrue(!opt.isPresent());
		
		LOG.info("Test OK");
		
	}


}
