package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubsetCreator {

	private static <V> void generatePermutations(int n, ArrayList<V> array, ArrayList<Set<V>> permutations) {
		if (n == 1) {
			permutations.add(new HashSet(array));
			return;
		}
		for (int i = 0; i < n; i++) {
			generatePermutations(n - 1, array, permutations);
			if ((n & 1) == 0) {
				V tmp = array.get(i);
				array.set(i, array.get(n - 1));
				array.set(n - 1, tmp);
			} else {
				V tmp = array.get(0);
				array.set(0, array.get(n - 1));
				array.set(n - 1, tmp);
			}
		}
	}

	public static <V> List<Set<V>> allSubsetsOfSizeN(Set<V> S, int n) {
		ArrayList<V> list = new ArrayList<V>();
		for (V v : S) {
			list.add(v);
		}
		ArrayList<Set<V>> permutations = new ArrayList<Set<V>>();
		generatePermutations(n, list, permutations);
		return permutations;
	}
}
