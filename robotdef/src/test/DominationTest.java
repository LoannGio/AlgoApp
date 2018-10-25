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
	private SimpleGraph<Integer, DefaultEdge> g;

	@Before
	public void setUp() throws Exception {
		g = new SimpleGraph<>(DefaultEdge.class);
		for (int i = 0; i < 5; i++) {
			g.addVertex(i);
		}
		for (int i = 0; i < 5; i++) {
			g.addEdge(i, (i + 1) % 5);
		}
	}

	@After
	public void tearDown() throws Exception {
		g = null;
	}

	@Test
	public void test_dominates() {
		// Test sur C5 avec D = {0, 3}
		HashSet D1 = new HashSet<Integer>();
		D1.add(0);
		D1.add(3);
		boolean b = Domination.dominates(g, D1);
		assertEquals(b, true);

		// Test sur C5 avec D = {0, 1}
		HashSet D2 = new HashSet<Integer>();
		D2.add(0);
		D2.add(1);
		b = Domination.dominates(g, D2);
		assertEquals(b, false);
	}

	@Test
	public void test_smallestDominatingSet() {
		// Test de smallestDominatingSet() sur C5
		HashSet<Integer> truth = new HashSet<Integer>();
		truth.add(0);
		truth.add(2);
		assertEquals(truth, Domination.smallestDominatingSetBruteForce(g));
	}

}
