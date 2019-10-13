package io.github.sirnino.utils.properties.getters.impl;

import java.util.Optional;

import io.github.sirnino.utils.properties.getters.PropertyGetter;

public class GetterFromEnvironment implements PropertyGetter {

	@Override
	public Optional<String> get(String key) {
		return Optional.ofNullable(System.getenv(key));
	}

}
