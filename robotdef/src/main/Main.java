package main;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.AttributeType;
import org.jgrapht.io.ComponentAttributeProvider;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;
import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException, FileNotFoundException {

		String filepath = FileHandler.openFile();
		if (filepath == null) {
			System.out.println("File not found");
			return;
		}
		RGraph G = new RGraph(filepath, false);
		
		

		// Test de smallestDominatingSet()
		System.out.println("---Test de smallestDominatingSet()");
		long time = System.currentTimeMillis();
		/*Set<RVertex> solution =  Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(),
				G.getPositionVertices(), true);*/
		//Set<RVertex> solution = Domination.dominatingSetGreedy(G, G.getShotLineVertices(), G.getPositionVertices());
		Set<RVertex> defenders = G.getPositionVertices();
		defenders.removeAll(G.getGoalPosition());
		//Set<RVertex> solution = Domination.smallestDominatingSetBruteForceGoal(G, G.getShotLineVertices(), G.getGoalPosition(), defenders, false);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution);
		System.out.println(solution);
		//System.out.println(G.getPositionVertices());
	}
}