package test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Independance;

public class IndependanceTest {
	SimpleGraph<Integer, DefaultEdge> g;

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
	public void test_isIndependent() {
		// Ensemble indépendant de C5
		HashSet D1 = new HashSet<Integer>();
		D1.add(0);
		D1.add(3);
		boolean b = Independance.isIndependent(g, D1);
		// Test sur C5 avec D = {0, 3}
		assertEquals(b, true);

		// Ensemble non indépendant de C5
		HashSet D2 = new HashSet<Integer>();
		D2.add(0);
		D2.add(1);
		b = Independance.isIndependent(g, D2);
		//Test sur C5 avec D = {0, 1}
		assertEquals(b, false);

	}

}
