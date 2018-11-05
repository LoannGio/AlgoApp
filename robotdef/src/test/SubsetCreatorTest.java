package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.SubsetCreator;

public class SubsetCreatorTest<V> {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_allSubsetsOfSizeN() {
		// Test sur [0, 1, 2, 3, 4] avec k = 2
		HashSet D3 = new HashSet<Integer>();
		int n = 5;
		int k = 2;
		for (int i = 0; i < n; i++) {
			D3.add(i);
		}

		// Manually building the expected set
		HashSet<HashSet<Integer>> truth = new HashSet<HashSet<Integer>>();
		HashSet<Integer> tmp;
		for (int i = 0; i < n - 1; i++) {
			tmp = new HashSet<Integer>();
			tmp.add(i);
			for (int j = i + 1; j < n; j++) {
				tmp.add(j);
				truth.add(new HashSet<Integer>(tmp));
				tmp.clear();
				tmp.add(i);
			}
		}

		HashSet<HashSet<Integer>> result = SubsetCreator.allSubsetsOfSizeN(D3, k);
		assertEquals(myEqual(truth, result), true);
	}

	private boolean myEqual(HashSet<HashSet<Integer>> set1, HashSet<HashSet<Integer>> set2) {
		if (set1.size() != set2.size())
			return false;

		ArrayList<Integer> tmp;

		ArrayList<ArrayList<Integer>> list1 = new ArrayList<ArrayList<Integer>>();
		for (HashSet<Integer> subset : set1) {
			tmp = new ArrayList<Integer>();
			for (Integer i : subset) {
				tmp.add(i);
			}
			list1.add(tmp);
		}

		ArrayList<ArrayList<Integer>> list2 = new ArrayList<ArrayList<Integer>>();
		Iterator<HashSet<Integer>> iter = set2.iterator();
		while (iter.hasNext()) {
			tmp = new ArrayList<Integer>();
			Iterator iter2 = iter.next().iterator();
			while (iter2.hasNext()) {
				tmp.add((Integer) iter2.next());
			}
			list2.add(tmp);
		}

		return list1.equals(list2);
	}

}
