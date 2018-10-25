package test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.SubsetCreator;

public class SubsetCreatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_allSubsetsOfSizeN() {
		// Test sur [0, 1, 2, 3, 4] avec n = 2
		HashSet D3 = new HashSet<Integer>();
		for (int i = 0; i < 5; i++) {
			D3.add(i);
		}
		String result = SubsetCreator.allSubsetsOfSizeN(D3, 2).toString();
		String truth = "[[0, 1], [0, 2], [0, 3], [0, 4], [1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]";

		assertEquals(result, truth);
	}

}
