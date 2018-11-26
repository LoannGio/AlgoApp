package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

public class SubsetCreator<V> {

	public static <V> HashSet<HashSet<V>> allSubsetsOfSizeNGoal(Set<V> Sgoal, Set<V> S, int k) {

		HashSet<HashSet<V>> subsets = new HashSet<HashSet<V>>();
		HashSet<HashSet<V>> tmp;


		// pour chaque position que peut prendre le goal
		for (V v : Sgoal) {
			tmp = new HashSet<HashSet<V>>();

				tmp = allSubsetsOfSizeN(S, k - 1);

				// on ajoute le gardien � chaque solution trouvee
				for (HashSet<V> subset : tmp) {
					subset.add(v);
					subsets.add(subset); // devrait marcher
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
		if (k <= 0) {
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

	@SuppressWarnings("unchecked")
	private static <V> void generatePermutations(int n, ArrayList<V> listSet, ArrayList<ArrayList<V>> permutations) {
		if (n == 1) {
			permutations.add(new ArrayList<V>(listSet));
			return;
		}
		for (int i = 0; i < n; i++) {
			generatePermutations(n - 1, listSet, permutations);
			if ((n & 1) == 0) {
				V tmp = listSet.get(i);
				listSet.set(i, listSet.get(n - 1));
				listSet.set(n - 1, tmp);
			} else {
				V tmp = listSet.get(0);
				listSet.set(0, listSet.get(n - 1));
				listSet.set(n - 1, tmp);
			}
		}
	}

	public static <V> ArrayList<ArrayList<V>> permutations(Set<V> set) {
		ArrayList<ArrayList<V>> permutations = new ArrayList<ArrayList<V>>();
		int n = set.size();
		ArrayList<V> listSet = new ArrayList<V>();
		for (V v : set) {
			listSet.add(v);
		}
		generatePermutations(n, listSet, permutations);
		return permutations;
	}
}
