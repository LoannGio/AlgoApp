package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class AlgoRunner {

	public AlgoRunner(RGraph G, String extension, Boolean collision) {
		switch (extension) {
		case "Normal":
			runNormal(G, collision);
			break;
		case "GoalKeeper":
			runGoalKeeper(G, collision);
			break;
		case "MultiGoal":
			runMultiGoal(G, collision);
			break;
		case "PosInit":
			runPosInit(G, collision);
			break;
		case "SAT":
			runSAT(G, collision);
			break;
		case "All":
			runAll(G, collision);
		}
	}

	private void runSAT(RGraph G, Boolean collision) {
		long time;
		Boolean solFound = false;
		String out = "SATresult";
		String formula = "SATformula";
		String glucoseCmd = "glucose-syrup-4.1/simp/glucose";
		int nbDefendersThatCanDefend = -1;
		System.out.println("#MODE : SAT");

		time = System.currentTimeMillis();
		for (int i = 0; i <= 6; i++) {
			SATReduction.reduction(G, i, formula);
			Runtime rt = Runtime.getRuntime();
			try {
				ProcessBuilder pb = new ProcessBuilder(glucoseCmd, formula);
				pb.redirectOutput(new File(out));
				pb.redirectErrorStream(true);
				Process pr = pb.start();				
				try {
					pr.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// READ
				BufferedReader reader = new BufferedReader(new FileReader(out));
				String lastLine = "";
				String line = reader.readLine();
				while (line != null) {
					if((line = reader.readLine()) != null){
						lastLine = line;
					}
				}
				if (lastLine.contains("s SATISFIABLE")) {
					solFound = true;
					nbDefendersThatCanDefend = i;
					break;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		if (solFound && nbDefendersThatCanDefend >= 0)
			System.out.println("Solution trouvee avec " + nbDefendersThatCanDefend + " defenseurs stockee dans fichier : " + out);
		else
			System.out.println("Pas de solution ou erreur");
	}

	private void runAll(RGraph G, Boolean collision) {
		runNormal(G, collision);
		runGoalKeeper(G, collision);
		runMultiGoal(G, collision);
		runPosInit(G, collision);
		runSAT(G, collision);
	}

	private void runPosInit(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		System.out.println("#MODE : Position Initiale - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedyPosInit(G, G.getShotLineVertices(), G.getPositionVertices(),
				G.getInitPosDefenders(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyPosInit.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForcePosInit(G, G.getShotLineVertices(),
				G.getPositionVertices(), G.getInitPosDefenders(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForcePosInit.json");

	}

	private void runMultiGoal(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		System.out.println("#MODE : MultiGoal - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedy(G, G.getShotLineVertices(), G.getPositionVertices(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyMultiGoal.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(), G.getPositionVertices(),
				collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForceMultiGoal.json");
	}

	private void runGoalKeeper(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		Set<RVertex> defenders = G.getPositionVertices();
		defenders.removeAll(G.getGoalPosition());
		System.out.println("#MODE : GoalKeeper - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedyGoal(G, G.getShotLineVertices(), G.getGoalPosition(), defenders,
				collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyGoalKeeper.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForceGoal(G, G.getShotLineVertices(), G.getGoalPosition(),
				defenders, collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForceGoalKeeper.json");

	}

	private void runNormal(RGraph G, Boolean collision) {
		long time;
		Set<RVertex> solution;
		System.out.println("#MODE : Normal - Collision : " + collision);

		// Greedy
		System.out.println("---Greedy");
		time = System.currentTimeMillis();
		solution = Domination.dominatingSetGreedy(G, G.getShotLineVertices(), G.getPositionVertices(), collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionGreedyNormal.json");

		// BruteForce
		System.out.println("---Brute Force");
		time = System.currentTimeMillis();
		solution = Domination.smallestDominatingSetBruteForce(G, G.getShotLineVertices(), G.getPositionVertices(),
				collision);
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution, "solutionBruteForceNormal.json");
	}

}
