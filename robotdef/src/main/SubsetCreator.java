package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;

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
	
	//getting the set of all subset of size k composed with elements of original set
	public static <V> List<List<V>> subsets(List<V> set,int k,V start )
	{
		//System.out.println("set of size:" + k + ":: from : " + start);
		//set of size :k, composed of element of :set, starting at element :start.
		List<List<V>> subsets = new Vector<>();
		//k=0 => returning singleton "emptyset"
		if(k == 0)
		{
			subsets.add(new Vector<>());
			return subsets;
		}
		//iterating on the list until finding element start
		ListIterator<V> it = set.listIterator();
		V v = null;
		while(it.hasNext())
		{
			v = it.next();
			if(v == start)
				break;
			
		}
		//backing one step to retrieve the iterator of element start (not so nice but
		//didn't find out how to do it a better way: this is the reason for 
		//using List Interface)
		it.previous();
		while(it.hasNext())
		{
			v = it.next();
			//recursion: getting the k-1 sized subsets starting at start+1
			List<List<V>> smallerSubsets = subsets(set,k-1,v);
			//adding element start to every subsets of size k-1 => all subsets are of size k
			for(List<V> s : smallerSubsets)
			{
				//check for unicity of element in the set(not so nice either, but 
				//the Vector class does not implement Set Interface)
				if(!s.contains(v))
				{
					s.add(v);
					subsets.add(s);
				}
			}
		}
		return subsets;
	}
	
	/*public static void main(String[] args)
	{
		List<Integer> testSet = new Vector<>();
		for(int i =0;i<11; i++)
		{
			testSet.add(i);
		}
		
		System.out.println(testSet);
		List<List<Integer>> subsetsSizeK = subset(testSet, 3, 0);
		System.out.println("size:" + subsetsSizeK.size());
		System.out.println(subsetsSizeK);
	}*/
}
