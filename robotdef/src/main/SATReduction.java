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
		for (int i = 1; i <= position.size(); i++)
		{
			String clause = "";
			clause += Integer.toString(-i) + " ";
			for (int g = 1; g <= cardinal; g++)
			{
				clause += Integer.toString(getVarNumberT(g, i, cardinal, position.size())) + " ";
			}
			cardinalClauses += clause;
			cardinalClauses += "0\n";
			nbCardinalClauses++;
			
			for(int g = 1; g <= cardinal; g++)
			{
				for(int j = 1; j <= Integer.toBinaryString(position.size()).length(); j++)
				{
					clause = "";
					clause += Integer.toString(- getVarNumberT(g, i, cardinal, position.size())) + " ";
					clause += Integer.toString(phiFunction(i, g, j, cardinal, position.size()));
					cardinalClauses += clause;
					cardinalClauses += " 0\n";
					nbCardinalClauses++;
				}
			}
		}
		
		
		
		//Première ligne
		String init = "p cnf " + 
				(position.size() + cardinal * position.size() 
				+ cardinal * Integer.toBinaryString(position.size()).length()) 
				+ " " + (shotLine.size()+nbCardinalClauses) + " \n";
		
				
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
	
	private static int getVarNumberT(int x, int y, int k, int n)
	{
		return n + x + (y-1) * k;
	}
	
	private static int getVarNumberB(int x, int y, int k, int n) {
		return n + k*n + x + (y-1) * k;
	}
	
	private static int phiFunction(int i, int g, int j, int k, int n) {
		int logn = Integer.toBinaryString(n).length();
		String si = Integer.toBinaryString(i);
		while(si.length() < logn)
			si = "0" + si;
			
		if (si.charAt(j-1) == '1')
			return getVarNumberB(g, j, k, n);
		else
			return -getVarNumberB(g, j, k, n);
	}
	
}
