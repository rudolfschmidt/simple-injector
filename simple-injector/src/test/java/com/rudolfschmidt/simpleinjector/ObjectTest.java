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
public class ObjectTest {

	private final SimpleInjector injector = SimpleInjector.getInjector();

	@Test
	public void simple() {
		final Simple instance = injector.getInstance(Simple.class);
		Assert.assertNotNull(instance);
	}

	@Test(expected = IllegalArgumentException.class)
	public void hiddenConstructor() {
		final HiddenConstructor instance = injector.getInstance(HiddenConstructor.class);
	}

	@Test
	public void dependencies() {
		final Dependencies instance = injector.getInstance(Dependencies.class);
		Assert.assertNotNull(instance);
		Assert.assertNotNull(instance.a);
		Assert.assertNotNull(instance.b);
	}

	@Test
	public void complexDependencies() {
		final ComplexDependencies instance = injector.getInstance(ComplexDependencies.class);
		Assert.assertNotNull(instance);
		Assert.assertNotNull(instance.a);
		Assert.assertNotNull(instance.a.a);
		Assert.assertNotNull(instance.a.b);
		Assert.assertNotNull(instance.b);
		Assert.assertNotNull(instance.b.a);
		Assert.assertNotNull(instance.b.b);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void multiConstructors() {
		final MultiConstructors instance = injector.getInstance(MultiConstructors.class);
	}

	public static class Simple {

	}

	public static class HiddenConstructor {

		private HiddenConstructor() {

		}
	}

	public static class Dependencies {

		private final Simple a;
		private final Simple b;

		public Dependencies(Simple a, Simple b) {
			this.a = a;
			this.b = b;
		}

	}

	public static class ComplexDependencies {

		private final Dependencies a;
		private final Dependencies b;
		private final Dependencies c;

		public ComplexDependencies(Dependencies a, Dependencies b, Dependencies c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

	}

	public static class MultiConstructors {

		private Simple a;
		private Simple b;

		public MultiConstructors(Simple a) {
			this.a = a;
		}

		public MultiConstructors(Simple a, Simple b) {
			this.a = a;
			this.b = b;
		}

	}
}
