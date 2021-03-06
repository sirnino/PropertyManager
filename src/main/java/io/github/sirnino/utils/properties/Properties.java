package io.github.sirnino.utils.properties;

import java.util.Optional;

import io.github.sirnino.utils.properties.getters.PropertyGetter;
import io.github.sirnino.utils.properties.getters.RemotePropertyGetter;
import io.github.sirnino.utils.properties.getters.impl.GetterFromEnvironment;
import io.github.sirnino.utils.properties.getters.impl.GetterFromEtcd;
import io.github.sirnino.utils.properties.getters.impl.GetterFromFile;
import io.github.sirnino.utils.properties.getters.impl.GetterFromZookeeper;

/**
 *  Property Manager - to retrieve application configuration
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
public class Properties {
	
	/**
	 * Retrieves the Property with the specified keyword.
	 * First it searches the properties in the Environment Variables, 
	 * if no match is found it searches in the Zookeeper Configuration Manager,
	 * if no match is found yet, it takes the default value in the configuration file.
	 *  
	 * @param key - the String that represent the name of the property to retrieve
	 * @return a java.util.Optional that may contain the value of the retrieved property
	 * @see java.util.Optional
	 * 
	 */
	
	private static PropertyGetter envGetter = new GetterFromEnvironment();
	private static PropertyGetter fileGetter = new GetterFromFile();
	private static Optional<RemotePropertyGetter> remoteSource;
	
	private static void setEnvGetter(PropertyGetter getter) {
		Properties.envGetter = getter;
	}
	
	private static void setFileGetter(PropertyGetter getter) {
		Properties.fileGetter = getter;
	}
	
	private static void setRemoteGetter(RemotePropertyGetter getter) {
		Properties.remoteSource = Optional.ofNullable(getter);
	}
	
	static void configure(PropertyGetter fileGetter, PropertyGetter envGetter, RemotePropertyGetter remoteGetter) {
		if(fileGetter != null)
			Properties.setFileGetter(fileGetter);
		
		if(envGetter != null)
			Properties.setEnvGetter(envGetter);
		
		if(remoteGetter != null)
			Properties.setRemoteGetter(remoteGetter);
	}
	
	public static Optional<String> get(String key) {
		
		if(remoteSource == null) {
			if(GetterFromZookeeper.isActive())
				remoteSource = Optional.ofNullable(new GetterFromZookeeper());
			else if(GetterFromEtcd.isActive())
				remoteSource = Optional.ofNullable(new GetterFromEtcd());
		}
		
		Optional<String> ret = envGetter.get(key);
		
		if(!ret.isPresent() && remoteSource.isPresent()) {
			ret = remoteSource.get().get(key);
		}
		
		if(!ret.isPresent()) {
			ret = fileGetter.get(key);
		}
		
		return ret;
	}
	
}
