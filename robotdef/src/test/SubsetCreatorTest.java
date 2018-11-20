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
	public HashSet<HashSet<Integer>> result;

	@Before
	public void setUp() throws Exception {
		result = new HashSet<HashSet<Integer>>();
	}

	@After
	public void tearDown() throws Exception {
		result = null;
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

	@Test
	public void test_allSubsetsOfSizeNGoal() {
		HashSet goalKeeperPositions = new HashSet<Integer>();
		int n = 2;
		int k = 2;
		for (int i = 0; i < n; i++) {
			goalKeeperPositions.add(i);
		}

		HashSet defenserPositions = new HashSet<Integer>();
		for (int i = n; i < n + 3; i++) {
			defenserPositions.add(i);
		}
		result = SubsetCreator.allSubsetsOfSizeNGoal(goalKeeperPositions, defenserPositions, k);
		HashSet<HashSet<Integer>> truth = new HashSet<HashSet<Integer>>();
		HashSet<HashSet<Integer>> tmp;
		for (int i = 0; i < n; i++) {
			tmp = SubsetCreator.allSubsetsOfSizeN(defenserPositions, k - 1);
			for (HashSet<Integer> subset : tmp) {
				subset.add(i);
				truth.add(subset);
			}
		}
		assertEquals(myEqual(truth, result), true);
	}

	@Test
	public void test_permutations() {
		// Test sur [0, 1, 2]
		HashSet D3 = new HashSet<Integer>();
		int n = 3;
		for (int i = 0; i < n; i++) {
			D3.add(i);
		}
		ArrayList<ArrayList<Integer>> permutations = SubsetCreator.permutations(D3);

		ArrayList<ArrayList<Integer>> truth = new ArrayList<ArrayList<Integer>>();
		truth.add(new ArrayList<Integer>() {
			{
				add(0);
				add(1);
				add(2);
			}
		});
		truth.add(new ArrayList<Integer>() {
			{
				add(1);
				add(0);
				add(2);
			}
		});
		truth.add(new ArrayList<Integer>() {
			{
				add(2);
				add(0);
				add(1);
			}
		});
		truth.add(new ArrayList<Integer>() {
			{
				add(0);
				add(2);
				add(1);
			}
		});
		truth.add(new ArrayList<Integer>() {
			{
				add(1);
				add(2);
				add(0);
			}
		});
		truth.add(new ArrayList<Integer>() {
			{
				add(2);
				add(1);
				add(0);
			}
		});
		assertEquals(truth.toString(), permutations.toString());
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
