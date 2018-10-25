package main;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;

public class RGraphVisualizer extends JApplet {

	private static final long serialVersionUID = 2202072534703043194L;

	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	private JGraphXAdapter<RVertex, DefaultEdge> jgxAdapter;

	private RGraph G;

	public RGraphVisualizer(RGraph g) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setTitle("JGraphT Adapter to JGraphX Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1200, 760));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		G = g;

		this.init();
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	@Override
	public void init() {
		// create a JGraphT graph
		ListenableGraph<RVertex, DefaultEdge> g = null;
		g = new DefaultListenableGraph<>(G, false);
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
		for (Entry<DefaultEdge, mxICell> e : tableEdges.entrySet()) {
			e.getValue().setId("");
		}

		layout.execute(jgxAdapter.getDefaultParent());
		// that's all there is to it!...
	}

}
