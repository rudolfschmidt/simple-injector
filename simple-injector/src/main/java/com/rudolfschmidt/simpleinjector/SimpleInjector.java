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

	public static SimpleInjector getInjector() {
		return new SimpleInjector();
	}

	/*
	 * create an instance of given class
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

	/*
	 * object creation with exactly one constructor and multiplied dependencies.
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

	/*
	 * simple object creation without constructor
	 */
	private <T> T getSimpleInstsance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (ReflectiveOperationException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	/*
	 * complex object creation with multi constructors
	 */
	private <T> T getMultiContructorsInstance(Class<T> clazz) {
		throw new UnsupportedOperationException();
	}

}
