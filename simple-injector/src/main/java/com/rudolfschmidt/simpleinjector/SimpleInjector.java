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
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Rudolf Schmidt <mail@rudolfschmidt.com>
 */
public class SimpleInjector {

	public static SimpleInjector getInjector() {
		return new SimpleInjector();
	}

	public <T> T getInstance(Class<T> clazz) {
		if (clazz.equals(String.class)) {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new IllegalArgumentException(ex);
			}
		}
		final Constructor<?>[] constructors = clazz.getConstructors();
		final int length = constructors.length;
		if (length > 1) {
			throw new IllegalArgumentException("Only one constructor is allowed: " + clazz);
		} else if (length == 0) {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new IllegalArgumentException(ex);
			}
		} else if (length == 1 && constructors[0].getParameterTypes().length == 0) {
			try {
				return clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException ex) {
				throw new IllegalArgumentException(ex);
			}
		} else {
			final Constructor<?> constructor = constructors[0];
			final Class<?>[] parameterTypes = constructor.getParameterTypes();
			final Object[] parameterInstances = new Object[parameterTypes.length];
			for (int i = 0; i < parameterInstances.length; i++) {
				parameterInstances[i] = getInstance(parameterTypes[i]);
			}
			final Object newInstance;
			try {
				newInstance = constructor.newInstance(parameterInstances);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				throw new IllegalArgumentException(ex);
			}
			return (T) newInstance;
		}
	}
}
