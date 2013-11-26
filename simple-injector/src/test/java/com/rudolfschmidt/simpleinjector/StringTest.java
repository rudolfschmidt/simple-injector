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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Rudolf Schmidt <mail@rudolfschmidt.com>
 */
public class StringTest {

	private final SimpleInjector injector = SimpleInjector.getInjector();

	@Test
	public void simple() {
		final Simple instance = injector.getInstance(Simple.class);
		Assert.assertNotNull(instance);
		Assert.assertNotNull(instance.s);
		Assert.assertEquals("", instance.s);
	}

	@Test
	public void multi() {
		final Multi instance = injector.getInstance(Multi.class);
		Assert.assertNotNull(instance);
		Assert.assertNotNull(instance.s1);
		Assert.assertNotNull(instance.s2);
		Assert.assertEquals("", instance.s1);
		Assert.assertEquals("", instance.s2);
	}

	public static class Simple {

		private final String s;

		public Simple(String s) {
			this.s = s;
		}

	}

	public static class Multi {

		private final String s1;
		private final String s2;

		public Multi(String s1, String s2) {
			this.s1 = s1;
			this.s2 = s2;
		}

	}

}
