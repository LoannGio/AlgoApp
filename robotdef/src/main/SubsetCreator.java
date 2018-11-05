package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

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

	// getting the set of all subset of size k composed with elements of
	// original set
	public static <V> List<List<V>> subsets(List<V> set, int k, V start) {
		// System.out.println("set of size:" + k + ":: from : " + start);
		// set of size :k, composed of element of :set, starting at element
		// :start.
		List<List<V>> subsets = new Vector<>();
		// k=0 => returning singleton "emptyset"
		if (k == 0) {
			subsets.add(new Vector<>());
			return subsets;
		}
		// iterating on the list until finding element start
		ListIterator<V> it = set.listIterator();
		V v = null;
		while (it.hasNext()) {
			v = it.next();
			if (v == start)
				break;

		}
		// backing one step to retrieve the iterator of element start (not so
		// nice but
		// didn't find out how to do it a better way: this is the reason for
		// using List Interface)
		it.previous();
		while (it.hasNext()) {
			v = it.next();
			// recursion: getting the k-1 sized subsets starting at start+1
			List<List<V>> smallerSubsets = subsets(set, k - 1, v);
			// adding element start to every subsets of size k-1 => all subsets
			// are of size k
			for (List<V> s : smallerSubsets) {
				// check for unicity of element in the set(not so nice either,
				// but
				// the Vector class does not implement Set Interface)
				if (!s.contains(v)) {
					s.add(v);
					subsets.add(s);
				}
			}
		}
		return subsets;
	}

	/*
	 * public static void main(String[] args) { List<Integer> testSet = new
	 * Vector<>(); for(int i =0;i<11; i++) { testSet.add(i); }
	 * 
	 * System.out.println(testSet); List<List<Integer>> subsetsSizeK =
	 * subset(testSet, 3, 0); System.out.println("size:" + subsetsSizeK.size());
	 * System.out.println(subsetsSizeK); }
	 */
}
