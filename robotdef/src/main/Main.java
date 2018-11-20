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
		Set<RVertex> solution = (Set<RVertex>) Domination.dominatingSetGreedy(G, G.getShotLineVertices(),
				G.getPositionVertices());
		time = System.currentTimeMillis() - time;
		System.out.println("Duree de la generation de la solution (ms) : " + time);
		JSonSolution.saveJSonSolution(solution);
		System.out.println(solution);
		System.out.println(G.getPositionVertices());
		
		
		
		/*test saving as dot file
		 * 
		 * 
		 * 
		 */
		
		DOTExporter<RVertex, DefaultEdge> dotExporter = new DOTExporter<>(new IntegerComponentNameProvider<>(), new ComponentNameProvider<RVertex>()
		{

			@Override
			public String getName(RVertex v)
			{
				// TODO Auto-generated method stub
				String name = "";
				if(v.is_goodGuy())
				{
					name += "d(";
				}
				else
				{
					name += "o(";
				}
				name += v.get_position().getX() + "," + v.get_position().getY();
				
				if(v.is_goodGuy())
				{
					name += ")";
				}
				else 
				{
					name += "," + v.get_theta() + ")";
				}
				return name;
			}
		},null,
		new ComponentAttributeProvider<RVertex>()
		{

			@Override
			public Map<String, Attribute> getComponentAttributes(RVertex v)
			{
				Map<String,Attribute> mapAttributes = new HashMap<>();
				if(solution.contains(v))
				{
					mapAttributes.put("color", new Attribute()
					{
						
						@Override
						public String getValue()
						{
							return "red";

						}
						
						@Override
						public AttributeType getType()
						{
							return AttributeType.STRING;
						}
					});
					mapAttributes.put("style", new Attribute()
					{
						
						@Override
						public String getValue()
						{
							return "filled";
						}
						
						@Override
						public AttributeType getType()
						{
							return AttributeType.STRING;
						}
					});
				}
				return mapAttributes;
			}
		},
		new ComponentAttributeProvider<DefaultEdge>()
		{

			@Override
			public Map<String, Attribute> getComponentAttributes(DefaultEdge e)
			{
				Map<String,Attribute> mapAttribute = new HashMap<>();
				if(solution.contains(G.getEdgeSource(e)) || solution.contains(G.getEdgeTarget(e)))				
				{
					mapAttribute.put("color", new Attribute()
					{
						
						@Override
						public String getValue()
						{
							return "red";
						}
						
						@Override
						public AttributeType getType()
						{
							return AttributeType.STRING;
						}
					});
					
				}
				
				return mapAttribute;
			}
		});
		dotExporter.putGraphAttribute("ratio", "0.025");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("solution.dot");
			dotExporter.exportGraph(G, fileWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		 * 
		 * end test
		 */
	}
}
