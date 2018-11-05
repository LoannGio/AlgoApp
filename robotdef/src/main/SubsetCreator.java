package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SubsetCreator<V> {

	private static <V> HashSet<HashSet<V>> recurs_allSubsetOfSizeN(int start, int end, int k,
			HashSet<HashSet<V>> subsets, ArrayList<V> vertices) {
		for (int i = start; i < end; i++) {
			HashSet<HashSet<V>> tmp = new HashSet<HashSet<V>>();
			tmp = recurs_allSubsetOfSizeN(i + 1, end, k, subsets, vertices);
			for (HashSet<V> v : tmp) {
				HashSet<V> tmp2 = new HashSet<V>();
				tmp2.add(vertices.get(i));
				for (V v2 : v) {
					tmp2.add(v2);
					if (tmp2.size() == k) {
						subsets.add(tmp2);
						tmp2 = new HashSet<V>();
						tmp2.add(vertices.get(i));
					}
				}
				System.out.println(tmp2);
			}
		}
		return subsets;
	}

	// Returns a list of subsets of size n from the set S
	public static <V> HashSet<HashSet<V>> allSubsetsOfSizeN(Set<V> S, int k) {
		// Converting Set -> List
		ArrayList<V> vertices = new ArrayList<V>();
		for (V v : S) {
			vertices.add(v);
		}

		// subsets = output (list of S's subsets of size n)

		// recurs_allSubsetOfSizeN(0, verti!ces.size() - 1, k, subsets,
		// vertices);
		HashSet<HashSet<V>> subsets = new HashSet<HashSet<V>>();
		HashSet<V> subset = new HashSet<V>();

		// System.out.println(subsets);
		return subsets;
	}
}
