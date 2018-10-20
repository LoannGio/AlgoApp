package main;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Independance {
	public static void main(String[] args) {
		// Test de isIndependent() avec un C5 et deux ensembles (un indépendant et un
		// non-indépendant)
		SimpleGraph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		for (int i = 0; i < 5; i++) {
			g.addVertex(i);
		}
		for (int i = 0; i < 5; i++) {
			g.addEdge(i, (i + 1) % 5);
		}

		// Ensemble indépendant de C5
		HashSet D1 = new HashSet<Integer>();
		D1.add(0);
		D1.add(3);

		// Ensemble non indépendant de C5
		HashSet D2 = new HashSet<Integer>();
		D2.add(0);
		D2.add(1);
		
		System.out.println("---Test de isIndependent()---");
		System.out.println("Test sur C5 avec D = {0, 3}");
		boolean b = isIndependent(g, D1);
		System.out.println(b);

		System.out.println("Test sur C5 avec D = {0, 1}");
		b = isIndependent(g, D2);
		System.out.println(b);
	}
	
	public static <V, E> boolean isIndependent(SimpleGraph<V, E> G, Set<V> I) {
		
		for (V v : I) {
			for(V u : Graphs.neighborSetOf(G, v)) {
				if(I.contains(u))
					return false;
			}
		}
		return true;
	}
}
