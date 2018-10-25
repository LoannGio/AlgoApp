package main;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.json.JSONException;

import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;

public class RGraphVisualizer extends JApplet {

	private static final long serialVersionUID = 2202072534703043194L;

	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	private JGraphXAdapter<RVertex, DefaultEdge> jgxAdapter;

	/**
	 * An alternative starting point for this demo, to also allow running this
	 * applet as an application.
	 *
	 * @param args
	 *            command line arguments
	 * @throws JSONException
	 */
	public static void main(String[] args) throws JSONException {
		RGraphVisualizer applet = new RGraphVisualizer();

		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraphX Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void init() {
		// create a JGraphT graph
		ListenableGraph<RVertex, DefaultEdge> g = null;
		String filepath = "";
		try {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files (*.json)", "json");

			chooser.setFileFilter(filter);

			int retValue = chooser.showOpenDialog(null);
			if (retValue == JFileChooser.APPROVE_OPTION) {
				filepath = chooser.getSelectedFile().getPath();
				g = new DefaultListenableGraph<>(new RGraph("test.json", false));
			} else {
				System.out.println("File not found");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create a visualization using JGraph, via an adapter
		jgxAdapter = new JGraphXAdapter<>(g);

		setPreferredSize(DEFAULT_SIZE);
		mxGraphComponent component = new mxGraphComponent(jgxAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		getContentPane().add(component);
		// resize(DEFAULT_SIZE);
		resize(DEFAULT_SIZE);

		// positioning via jgraphx layouts
		// mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
		// center the circle
		/*
		 * int radius = 50; layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
		 * layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
		 * layout.setRadius(radius);
		 */
		// layout.setMoveCircle(true);
		// mxOrganicLayout layout = new mxOrganicLayout(jgxAdapter);
		// mxPartitionLayout layout = new mxPartitionLayout(jgxAdapter);
		mxOrthogonalLayout layout = new mxOrthogonalLayout(jgxAdapter);
		// jgxAdapter.setLabelsVisible(false);
		double scale = 600.0;
		double distToCenter = 1.0;
		double xOffset = 0.5;
		double yOffset = 0.5;
		HashMap<RVertex, mxICell> tableVertices = jgxAdapter.getVertexToCellMap();

		for (Entry<RVertex, mxICell> e : tableVertices.entrySet()) {
			double pX = e.getKey().get_position().getX();
			double pY = e.getKey().get_position().getY();
			double theta = e.getKey().get_theta();
			if (e.getKey().is_goodGuy()) {
				layout.setVertexLocation(e.getValue(), scale * (pX + xOffset), scale * (pY + yOffset));
			} else {
				layout.setVertexLocation(e.getValue(), scale * (pX + xOffset + distToCenter * Math.cos(theta)),
						scale * (pY + yOffset + distToCenter * Math.sin(theta)));
			}
		}
		
		HashMap<DefaultEdge, mxICell> tableEdges = jgxAdapter.getEdgeToCellMap();
		for(Entry<DefaultEdge,mxICell> e : tableEdges.entrySet())
		{
			e.getValue().setId("");
		}

		layout.execute(jgxAdapter.getDefaultParent());
		// that's all there is to it!...
	}

}
