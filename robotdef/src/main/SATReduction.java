package main;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;

public class SATReduction {
	
	
	/*
	 *  Reduction vers une formule SAT en CNF
	 *  
	 *  La formule utilise des variables qui vont de 0 à nb sommets de position	 *  
	 */	
	public static void reduction(RGraph G, String filepath) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		ArrayList<RVertex> shotLine = new ArrayList<RVertex>(G.getShotLineVertices());
		ArrayList<RVertex> position = new ArrayList<RVertex>(G.getPositionVertices());
		
		String init = "p \n";
		String clauses = "";
		
		for(int i = 0; i < shotLine.size(); i++) {
			for(int j = 0; j < shotLine.size(); j++) {
				if (Graphs.neighborListOf(G, shotLine.get(i)).contains(position.get(j)))
					clauses += Integer.toString(j) + " ";
			}
			clauses += "\n";
		}
		
				
		try {
			fw = new FileWriter(filepath);
			bw = new BufferedWriter(fw);
			
			bw.write(init);		
			bw.write(clauses);	
			
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
		
	}

}
