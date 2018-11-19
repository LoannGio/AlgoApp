package test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Domination;

public class DominationTest {
	// Un cycle de longueur 5
	private SimpleGraph<Integer, DefaultEdge> c5;
	// Un cycle de longueur 6
	private SimpleGraph<Integer, DefaultEdge> c7;
	// Un graphe complet � 6 sommets
	private SimpleGraph<Integer, DefaultEdge> k6;

	@Before
	public void setUp() throws Exception {
		c5 = new SimpleGraph<>(DefaultEdge.class);
		for (int i = 0; i < 5; i++) {
			c5.addVertex(i);
		}
		for (int i = 0; i < 5; i++) {
			c5.addEdge(i, (i + 1) % 5);
		}

		c7 = new SimpleGraph<>(DefaultEdge.class);
		for (int i = 0; i < 7; i++) {
			c7.addVertex(i);
		}
		for (int i = 0; i < 7; i++) {
			c7.addEdge(i, (i + 1) % 7);
		}

		k6 = new SimpleGraph<>(DefaultEdge.class);
		for (int i = 0; i < 6; i++) {
			k6.addVertex(i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 6; j++)
				k6.addEdge(i, j);
		}
	}

	@After
	public void tearDown() throws Exception {
		c5 = null;
		c7 = null;
		k6 = null;
	}

	@Test
	public void test_dominates() {
		// Test sur C5 avec D = {0, 3}
		HashSet D1 = new HashSet<Integer>();
		D1.add(0);
		D1.add(3);
		boolean b = Domination.dominates(c5, D1);
		assertEquals(b, true);

		// Test sur C5 avec D = {0, 1}
		HashSet D2 = new HashSet<Integer>();
		D2.add(0);
		D2.add(1);
		b = Domination.dominates(c5, D2);
		assertEquals(b, false);
	}

	@Test
	public void test_smallestDominatingSetBruteForce() {
		// Test de smallestDominatingSet() sur C5
		assertEquals(2, Domination.smallestDominatingSetBruteForce(c5, false).size());

		// Test de smallestDominatingSet() sur C7
		assertEquals(3, Domination.smallestDominatingSetBruteForce(c7, false).size());

		// Test de smallestDominatingSet() sur K6
		assertEquals(1, Domination.smallestDominatingSetBruteForce(k6, false).size());
	}

	@Test
	public void test_dominatingSetGreedy() {
		// Test de smallestDominatingSet() sur C5
		assertEquals(true, Domination.dominates(c5, Domination.dominatingSetGreedy(c5)));

		// Test de smallestDominatingSet() sur C7
		assertEquals(true, Domination.dominates(c7, Domination.dominatingSetGreedy(c7)));

		// Test de smallestDominatingSet() sur K6
		assertEquals(true, Domination.dominates(k6, Domination.dominatingSetGreedy(k6)));
	}

}
