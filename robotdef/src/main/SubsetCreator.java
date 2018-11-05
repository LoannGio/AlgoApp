package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

public class SubsetCreator<V> {

	// Returns a list of subsets of size n from the set S
	public static <V> HashSet<HashSet<V>> allSubsetsOfSizeN(Set<V> S, int k) {
		// Converting Set -> List
		ArrayList<V> vertices = new ArrayList<V>();
		for (V v : S) {
			vertices.add(v);
		}
		HashSet<HashSet<V>> subsets = new HashSet<HashSet<V>>();
		List<List<V>> result = subsets(vertices, k, vertices.get(0));

		HashSet<V> tmp;
		for (List<V> list : result) {
			tmp = new HashSet<V>();
			for (V v : list) {
				tmp.add(v);
			}
			subsets.add(new HashSet(tmp));
		}

		return subsets;
	}

	// getting the set of all subset of size k composed with elements of
	// original set
	private static <V> List<List<V>> subsets(List<V> set, int k, V start) {
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
		// TODO Loann : check avec jonathan
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

	public static void main(String[] args) {
		List<Integer> testSet = new Vector<>();
		for (int i = 0; i < 11; i++) {
			testSet.add(i);
		}

		System.out.println(testSet);
		List<List<Integer>> subsetsSizeK = subsets(testSet, 3, 0);
		System.out.println("size:" + subsetsSizeK.size());
		System.out.println(subsetsSizeK);
		System.out.println("------------");
		HashSet D3 = new HashSet<Integer>();
		for (int i = 0; i < 5; i++) {
			D3.add(i);
		}
		System.out.println(SubsetCreator.allSubsetsOfSizeN(D3, 2).toString());
	}

}
