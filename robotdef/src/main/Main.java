package main;

import java.io.FileNotFoundException;
import java.util.Set;

import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException, FileNotFoundException {

		String filepath = FileHandler.openFile();
		if (filepath == null) 
		{
			System.out.println("File not found");
			return;
		}
		RGraph G = new RGraph(filepath, true);

		System.out.println("---Test de smallestDominatingSetBruteForcePosInit");
		Set<RVertex> normalDefenders = G.getPositionVertices();
		normalDefenders.removeAll(G.getGoalPosition());
		
	
		/*Set<RVertex> solution = Domination.dominatingSetGreedyPosInit(G, G.getShotLineVertices(),
				G.getPositionVertices(), G.getInitPosDefenders());*/
		long time = System.currentTimeMillis();
		Set<RVertex> solution = null;
		//solution = Domination.dominatingSetGreedyGoal(G, G.getGoalPosition(), normalDefenders, G.getShotLineVertices());
		solution = Domination.smallestDominatingSetBruteForceGoal(G, G.getShotLineVertices(), G.getGoalPosition(), normalDefenders, true);
		if(solution == null)
		{
			System.out.println("not ok");
		}
		
		//solution = Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(), G.getPositionVertices(), true);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution);
	}
}
