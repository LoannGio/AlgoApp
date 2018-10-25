package main;

import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

public class Independance {
	public static <V, E> boolean isIndependent(SimpleGraph<V, E> G, Set<V> I) {

		for (V v : I) {
			for (V u : Graphs.neighborSetOf(G, v)) {
				if (I.contains(u))
					return false;
			}
		}
		return true;
	}
}
