/*
 * Copyright 2015-2016 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.api;

import static org.junit.gen5.api.Assertions.assertFalse;
import static org.junit.gen5.api.Assertions.assertThrows;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.gen5.api.Assertions.fail;

import java.util.function.Supplier;

import org.opentest4j.AssertionFailedError;

/**
 * Unit tests for {@link Assertions}.
 *
 * @since 5.0
 */
public class AssertionsTests {

	@Test
	void failWithString() {
		try {
			fail("test");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "test");
		}
	}

	@Test
	void failWithMessageSupplier() {
		try {
			fail(() -> "test");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "test");
		}
	}

	@Test
	void failWithNullString() {
		try {
			fail((String) null);
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageIsNull(ex);
		}
	}

	@Test
	void failWithNullMessageSupplier() {
		try {
			fail((Supplier<String>) null);
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageIsNull(ex);
		}
	}

	@Test
	void assertTrueWithBooleanTrue() {
		assertTrue(true);
	}

	@Test
	void assertTrueWithBooleanSupplierTrue() {
		assertTrue(() -> true);
	}

	@Test
	void assertTrueWithBooleanTrueAndString() {
		assertTrue(true, "test");
	}

	@Test
	void assertTrueWithBooleanFalse() {
		try {
			assertTrue(false);
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageIsNull(ex);
		}
	}

	@Test
	void assertTrueWithBooleanFalseAndString() {
		try {
			assertTrue(false, "test");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "test");
		}
	}

	@Test
	void assertTrueWithBooleanSupplierFalseAndMessageSupplier() {
		try {
			assertTrue(() -> false, () -> "test");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "test");
		}
	}

	@Test
	void assertFalseWithBooleanFalse() {
		assertFalse(false);
	}

	@Test
	void assertFalseWithBooleanTrueAndString() {
		try {
			assertFalse(true, "test");
			expectAssertionFailedError();
		}
		catch (AssertionFailedError ex) {
			assertMessageEquals(ex, "test");
		}
	}

	@Test
	void assertThrowsStackOverflowError() {
		assertThrows(StackOverflowError.class, this::recurseIndefinitely);
	}

	private void recurseIndefinitely() {
		// simulate infinite recursion
		throw new StackOverflowError();
	}

	// -------------------------------------------------------------------

	private static void expectAssertionFailedError() {
		throw new AssertionError("Should have thrown an " + AssertionFailedError.class.getName());
	}

	private static void assertMessageIsNull(AssertionFailedError ex) throws AssertionError {
		if (ex.getMessage() != null) {
			throw new AssertionError("Message in AssertionFailedError should be null");
		}
	}

	private static void assertMessageEquals(AssertionFailedError ex, String msg) throws AssertionError {
		if (!msg.equals(ex.getMessage())) {
			throw new AssertionError(
				"Message in AssertionFailedError should be '" + msg + "', but was '" + ex.getMessage() + "'.");
		}
	}

}