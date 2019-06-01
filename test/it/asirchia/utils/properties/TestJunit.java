/**
 * 
 */
package it.asirchia.utils.properties;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
class TestJunit {
	
	private static final String key = "user.name";
	
	@BeforeAll
	static void before() {
		System.out.println("Active profile is "+Properties.get("activeprofile"));
	}
	
	/**
	 * Tests the configuration from file
	 */
	@Test
	void testFromFile() {
		Optional<String> opt = new GetterFromFile().get(key);
		assertTrue(opt.isPresent());
		
		System.out.println("Found from file "+key+" = "+opt.get());
		
		assertTrue("UsernameFromConfigurationFile".equals(opt.get()));
	}
	
	/**
	 * Tests the configuration from environment variable
	 */
	@Test
	void testFromEnv() {	
		Optional<String> opt = new GetterFromEnvironment().get(key);
		assertTrue(opt.isPresent());
		
		System.out.println("Found from environment "+key+" = "+opt.get());
		
		assertTrue("UsernameFromEnvironment".equals(opt.get()));
	}
	
	/**
	 * Tests the configuration from Zookeeper server
	 */
	@Test
	void testFromZookeeper() {
		if(GetterFromZookeeper.isActive()) {
			Optional<String> opt = new GetterFromZookeeper().get(key);
			assertTrue(opt.isPresent());
			
			System.out.println("Found from zookeeper "+key+" = "+opt.get());
			
			assertTrue("UsernameFromZookeeper".equals(opt.get()));
		}
		
	}
	
	/**
	 * Tests the configuration from Zookeeper server
	 */
	@Test
	void testFromEtcd() {
		if(GetterFromEtcd.isActive()) {
			Optional<String> opt = new GetterFromEtcd().get(key);
			assertTrue(opt.isPresent());
			
			System.out.println("Found from etcd "+key+" = "+opt.get());
			
			assertTrue("etcdusername".equals(opt.get()));
		}
	}

}
