package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubsetCreator {

	/*
	 * Source : Print all subsets of given size of a set
	 * https://www.geeksforgeeks.org/print-subsets-given-size-set/
	 */
	static <V> void recurs_subsetsOfSizeR(ArrayList<V> vertexes, int numberOfVertexes, int n, int index,
			ArrayList<V> subset, int i, ArrayList<Set<V>> subsets) {
		if (index == n) {
			subsets.add(new HashSet(subset));
			return;
		}

		if (i >= numberOfVertexes)
			return;

		subset.set(index, vertexes.get(i));
		recurs_subsetsOfSizeR(vertexes, numberOfVertexes, n, index + 1, subset, i + 1, subsets);
		recurs_subsetsOfSizeR(vertexes, numberOfVertexes, n, index, subset, i + 1, subsets);
	}

	// Returns a list of subsets of size n from the set S
	public static <V> List<Set<V>> allSubsetsOfSizeN(Set<V> S, int n) {
		// Converting Set -> List
		ArrayList<V> vertexes = new ArrayList<V>();
		for (V v : S) {
			vertexes.add(v);
		}

		// subsets = output (list of S's subsets of size n)
		ArrayList<Set<V>> subsets = new ArrayList<Set<V>>();

		// Tmp list of vertexes. Represents 1 subset
		ArrayList<V> subset = new ArrayList<V>();
		/*
		 * subset is modified threw "set" method thus its momery needs to be
		 * allocated before. We would like to use a simple array here but as "V"
		 * is not a type, we can't
		 */
		for (int i = 0; i < n; i++) {
			subset.add(null);
		}
		recurs_subsetsOfSizeR(vertexes, vertexes.size(), n, 0, subset, 0, subsets);
		return subsets;
	}
}
