package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.AttributeType;
import org.jgrapht.io.ComponentAttributeProvider;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

public class FileHandler {

	public static String openFile() throws FileNotFoundException {
		String filepath = "";
		JFileChooser chooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir") + "/problems");
		chooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files (*.json)", "json");

		chooser.setFileFilter(filter);

		int retValue = chooser.showOpenDialog(null);
		if (retValue == JFileChooser.APPROVE_OPTION) {
			filepath = chooser.getSelectedFile().getPath();
		} else {
			filepath = null; // ou throw new
								// FileNotFoundException(), mais de
								// toute facon
			// si il n y a
			// pas de fichier on execute pas...
		}
		return filepath;
	}
	
	public static void saveToDot(RGraph G,Set<RVertex> solution,String filename)
	{
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
			fileWriter = new FileWriter(filename + ".dot");
			dotExporter.exportGraph(G, fileWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
