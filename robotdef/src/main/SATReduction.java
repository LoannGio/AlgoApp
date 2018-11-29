package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

public class SATReduction {
	
	
	/*
	 *  Reduction vers une formule SAT en CNF
	 *  Renvoie false s'il y a un sommet à dominer de degré 0 et vrai sinon
	 *  La formule utilise des variables qui vont de 0 à nb sommets de position 
	 */	
	public static boolean reduction(RGraph G, int cardinal, String filepath) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		ArrayList<RVertex> shotLine = new ArrayList<RVertex>(G.getShotLineVertices());
		ArrayList<RVertex> position = new ArrayList<RVertex>(G.getPositionVertices());
		
		//DOMINATION
		String dominationClauses = "";
		
		for(int i = 0; i < shotLine.size(); i++) {
			String clause = "";
			for(int j = 0; j < position.size(); j++) {
				if (Graphs.neighborListOf(G, shotLine.get(i)).contains(position.get(j)))
					clause += Integer.toString(j+1) + " ";
			}
			if (clause == "") {
				return false;
			}
			dominationClauses += clause;
			dominationClauses += "0\n";
		}
		
		//CARDINAL
		String cardinalClauses = "";
		int nbCardinalClauses = 0;
		for (HashSet<RVertex> I : SubsetCreator.allSubsetsOfSizeN(new HashSet<RVertex>(position), cardinal+1)) {
			String clause = "";
			for (RVertex v : I) {
				clause += Integer.toString(-(position.indexOf(v) + 1)) + " ";
			}
			cardinalClauses += clause;
			cardinalClauses += "0\n";
			nbCardinalClauses++;
		}
		
		//Première ligne
		String init = "p cnf " + position.size() + " " + (shotLine.size()+nbCardinalClauses) + " \n";
		
				
		try {
			fw = new FileWriter(filepath);
			bw = new BufferedWriter(fw);
			
			bw.write(init);		
			bw.write(dominationClauses);	
			bw.write(cardinalClauses);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
