/*
 * Copyright (C) 2013 Rudolf Schmidt <mail@rudolfschmidt.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.rudolfschmidt.simpleinjector;

import java.lang.reflect.Constructor;

/**
 *
 * @author Rudolf Schmidt <mail@rudolfschmidt.com>
 */
public class SimpleInjector {

	/**
	 * Returns an instance of the injector
	 *
	 * @return an instance of the injector
	 */
	public static SimpleInjector getInjector() {
		return new SimpleInjector();
	}

	/**
	 * Creates an instance of clazz
	 *
	 * @param <T> can be any type
	 * @param clazz the clazz reference
	 * @return a new instance of given clazz
	 */
	public <T> T getInstance(Class<T> clazz) {

		final Constructor<?>[] constructors = clazz.getConstructors();

		if (clazz.equals(String.class)) {
			return getSimpleInstsance(clazz);
		} else if (constructors.length == 0) {
			return getSimpleInstsance(clazz);
		} else if (constructors.length == 1) {
			final int length = constructors[0].getParameterTypes().length;
			if (length == 0) {
				return getSimpleInstsance(clazz);
			} else if (length > 0) {
				return (T) getConstructorInstance(constructors[0]);
			}
		}

		return getMultiContructorsInstance(clazz);
	}

	/**
	 * Creates an instance with given constructor. Object dependencies will be
	 * built recursivly.
	 *
	 * @param constructor the constructor a new instance built with
	 * @return a new instance built with the constructor
	 */
	private Object getConstructorInstance(Constructor<?> constructor) {
		final Class<?>[] parameterTypes = constructor.getParameterTypes();
		final Object[] parameterInstances = new Object[parameterTypes.length];
		for (int i = 0; i < parameterInstances.length; i++) {
			parameterInstances[i] = getInstance(parameterTypes[i]);
		}
		final Object newInstance;
		try {
			newInstance = constructor.newInstance(parameterInstances);
		} catch (ReflectiveOperationException ex) {
			throw new IllegalArgumentException(ex);
		}
		return newInstance;
	}

	/**
	 * Creates a new instance of given clazz. the clazz has to be simple to be
	 * built. either no constructor or only one constructor without any
	 * parameters are allowed.
	 *
	 * @param <T> can be any type
	 * @param clazz the clazz reference
	 * @return an instance created with the clazz reference.
	 */
	private <T> T getSimpleInstsance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (ReflectiveOperationException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	/**
	 * Handles the usecase an object with multiplied constructors is supposed to
	 * be built. the implementation is not complete.
	 *
	 * @param <T> can be any type
	 * @param clazzn a reference of the clazz a new instance is supposed to be
	 * built with.
	 * @return a new object instance of type T.
	 */
	private <T> T getMultiContructorsInstance(Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

}
