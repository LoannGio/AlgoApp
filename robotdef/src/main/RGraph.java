package main;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.sound.sampled.ReverbType;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//class representing the model graph of the problem
public class RGraph extends SimpleGraph<RVertex, DefaultEdge> 
{

	//vertices are 3-uplets (position,theta,bool): shotline vertices are characterized by their position the angle of the shot and the bool is false;
	//position vertices are characterized by their position, and the bool is true (the theat angla value is not used in this case.
	
	//constructor from JSON file
	public RGraph(String filename, boolean collision) throws JSONException 
	{
		super(DefaultEdge.class);
		
		//parsing JSON file
		String problemString = readFile(filename);
		JSONObject problemObject = new JSONObject(problemString);

		// generating the list of possible position
		Set<Point2D.Double> listPoint = generatePointList(problemObject);
		

		// for each opponent, getting shot on target
		Set<Entry<Line2D.Double,Double>> listShotLine = getShotLineOnTarget(problemObject);
		
		for(Entry<Line2D.Double,Double> e : listShotLine)
		{
			System.out.println(e.getKey().getP1() + ":::" + e.getKey().getP2());
		}


		for (Entry<Line2D.Double,Double> l : listShotLine) 
		{
			addVertex(new RVertex(l.getKey().getP1(),l.getValue(), false));
		}
		double robotRadius = getRobotRadius(problemObject);


		for (Entry<Line2D.Double,Double> line : listShotLine) 
		{
			for (Point2D.Double pos : listPoint) 
			{
				//System.out.println("distance from line" + line.getKey().getP1() + "," + line.getKey().getP2() + " of point:" + pos.getX() + "," + pos.getY() + "=" + line.getKey().ptLineDist(pos));
				
				// there is a defense position
				if (line.getKey().ptSegDist(pos) < robotRadius && pos.distance(line.getKey().getP1()) > 2.0 * robotRadius) 
				{

					addVertex(new RVertex(pos, true));
					addEdge(new RVertex(line.getKey().getP1(),line.getValue(),false), new RVertex(pos, true));
				}
			}
		}

		//handling collision between defenders
		if (collision) {
			for (RVertex v1 : vertexSet()) {
				for (RVertex v2 : vertexSet()) {
					if (v1 != v2 && v1.is_goodGuy() && v2.is_goodGuy() && v1.get_position().distance(v2.get_position()) < 2.0 * robotRadius) {
						addEdge(v1, v2);
					}
				}
			}
		}
		
		//System.out.println(edgeSet().size());
		/*Set<RVertex> listShotLines = getShotLineVertices();
		for(RVertex v : listShotLines)
		{

		}*/

	}
	

	private double getRobotRadius(JSONObject problemObject) throws JSONException 
	{
		return problemObject.getDouble("robot_radius");
	}

	private Set<Entry<Point2D.Double, Point2D.Double>> generateListGoal(JSONObject problemObject) throws JSONException 
	{
		HashSet<Entry<Point2D.Double, Point2D.Double>> listGoal = new HashSet<>();

		JSONArray list = problemObject.getJSONArray("goals");
		for (int i = 0; i < list.length(); i++) {

			JSONObject goal = list.getJSONObject(i);

			JSONArray post = goal.getJSONArray("posts");

			Point2D.Double p1 = new Point2D.Double(post.getJSONArray(0).getDouble(0),
					post.getJSONArray(0).getDouble(1));

			Point2D.Double p2 = new Point2D.Double(post.getJSONArray(1).getDouble(0),
					post.getJSONArray(1).getDouble(1));

			listGoal.add(new SimpleEntry<>(p1, p2));

		}

		return listGoal;

	}

	private Set<Entry<Line2D.Double,Double>> getShotLineOnTarget(JSONObject problemObject) throws JSONException 
	{
		Set<Entry<Line2D.Double,Double>> listShot = new HashSet<>();
		Set<Point2D.Double> listOpp = generateListOpp(problemObject);
		Set<Entry<Point2D.Double, Point2D.Double>> listGoal = generateListGoal(problemObject);
		double thetaStep = problemObject.getDouble("theta_step");
		for (Point2D.Double opp : listOpp) 
		{
			System.out.println("checking opp:" + opp.getX() + "," + opp.getY());
			//Set<Double> listTheta = new HashSet<>();
			for (Entry<Point2D.Double, Point2D.Double> goal : listGoal) 
			{
				double theta1 = getAngle(opp, goal.getKey());
				double theta2 = getAngle(opp, goal.getValue());
				double thetaMin, thetaMax;
				if(Math.abs(theta2-theta1) < Math.PI)
				{
					if (theta1 <= theta2) 
					{
						thetaMin = theta1;
						thetaMax = theta2;
					} 
					else 
					{
						thetaMin = theta2;
						thetaMax = theta1;
					}
					
				}
				else
				{
					if (theta1 <= theta2) 
					{
						thetaMin = theta2 - 2.0*Math.PI;
						thetaMax = theta1;
					} 
					else 
					{
						thetaMin = theta1 - 2.0*Math.PI;
						thetaMax = theta2;
					}
					
				}
				
				System.out.println(thetaMin + "::" + thetaMax);
				
				
				System.out.println(opp + "::" + thetaMin + "," + thetaMax);

				// check if the angle are good ones
				int i=0;
				for (double thetaK = thetaMin; thetaK  < thetaMax; thetaK += thetaStep) 
				{
					
					Point2D.Double intersectionPoint = intersection(opp, thetaK, goal.getKey(), goal.getValue());
					Line2D.Double shotLine = new Line2D.Double(opp, intersectionPoint);
					listShot.add(new SimpleEntry<>(shotLine, thetaK));
					i++;
				}
				System.out.println(i);
				/*for(Entry<Line2D.Double, Double> e : listShot)
				{
					System.out.println(e.getKey().getP1() + "," + e.getKey().getP2() + "::" + e.getValue());
					
				}
				System.out.println();*/
			}
		}

		return listShot;
	}

	// intersection point between a line passing through p with an angle of
	// theta, and a line passing through p1 and p2
	private Point2D.Double intersection(Point2D.Double p, double theta, Point2D.Double p1, Point2D.Double p2)
	{

		// we first compute the cartesian equation for each line, then we solve
		// the system
		double a1, a2, b1, b2, c1, c2;

		///TODO:need to check for theta=+-Pi/2
		//shotline equation
		a1 = -1.0*Math.tan(theta);
		b1 = 1;
		c1 = -1.0*a1*p.getX() - p.getY();
		
		//goal line equation
		if (p1.getX() != p2.getX()) 
		{
			a2 = 1.0;
			b2 = -1.0*(p1.getY() - p2.getY()) / (p2.getX() - p1.getX());
			c2 = -1.0 * p1.getY() - b2 * p1.getX();
		} 
		else 
		{
			a2 = 1.0;
			b2 = 0.0;
			c2 = -1.0 * p1.getX();
		}
		System.out.println(p + "(" + theta + ")"  + "::" + a1 + "," + b1 + "," + c1 + "::" + a2 + "," + b2 + "," + c2);
		

		double x = (b1 * c2 - c1 * b2) / (a1 * b2 - b1 * a2);
		double y = (c1 * a2 - a1 * c2) / (a1 * b2 - b1 * a2);

		Point2D.Double intersectionPoint = new Point2D.Double(x, y);

		return intersectionPoint;

	}

	private double getAngle(Point2D.Double p1, Point2D.Double p2) 
	{
		double theta = 0;
		if (p1.getX() != p2.getX()) 
		{
			theta = Math.atan((p2.getY() - p1.getY()) / (p2.getX() - p1.getX()));
			theta = theta % (2.0 * Math.PI);
			if(theta <0) 
			{
				theta += 2.0*Math.PI;
			}
			if (p1.getX() > p2.getX()) 
			{
				theta += Math.PI;
				theta = theta % (2.0 * Math.PI);
				if(theta <0) 
				{
					theta += 2.0*Math.PI;
				}
			}
		} 
		else 
		{
			if (p2.getY() > p1.getY()) 
			{
				return Math.PI / 2.0;
			} 
			else 
			{
				return 3.0 * Math.PI / 2.0;
			}

		}

		return theta;
	}

	private Set<Point2D.Double> generateListOpp(JSONObject problemObject) throws JSONException 
	{
		HashSet<Point2D.Double> listOpp = new HashSet<>();

		JSONArray list = problemObject.getJSONArray("opponents");

		for (int i = 0; i < list.length(); i++) 
		{
			JSONArray pair = list.getJSONArray(i);
			

			listOpp.add(new Point2D.Double(pair.getDouble(0), pair.getDouble(1)));

		}
		
		System.out.println("nb d'opponent:" + listOpp.size());
		return listOpp;
	}

	/*private Set<Point2D.Double> generateList(JSONObject problemObject, String fieldName) throws JSONException 
	{

		HashSet<Point2D.Double> listOpp = new HashSet<>();

		JSONArray list = problemObject.getJSONArray(fieldName);

		for (int i = 0; i < list.length(); i++) {
			JSONArray pair = list.getJSONArray(i);

			listOpp.add(new Point2D.Double(pair.getDouble(0), pair.getDouble(1)));

		}
		return listOpp;

	}*/

	public static String readFile(String filename) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Set<Point2D.Double> generatePointList(JSONObject problemObject) throws JSONException 
	{
		int precision = 100;
		HashSet<Point2D.Double> listPoint = new HashSet<>();

		double posStep = problemObject.getDouble("pos_step");
		JSONArray arrayField = problemObject.getJSONArray("field_limits");
		JSONArray arrayXLimit = arrayField.getJSONArray(0);
		JSONArray arrayYLimit = arrayField.getJSONArray(1);
		double xMin, xMax, yMin, yMax;
		xMin = arrayXLimit.getDouble(0);
		xMax = arrayXLimit.getDouble(1);
		yMin = arrayYLimit.getDouble(0);
		yMax = arrayYLimit.getDouble(1);
		double currentX, currentY;
		currentX = xMin;
		currentY = yMin;
		while (currentY < yMax) {
			currentX = xMin;
			while (currentX < xMax) {
				double posX = (double) Math.round(currentX * precision) / precision;
				double posY = (double) Math.round(currentY * precision) / precision;
				Point2D.Double p = new Point2D.Double(posX, posY);
				listPoint.add(p);
				currentX += posStep;
			}

			currentY += posStep;
		}

		return listPoint;
	}
	
	
	public Set<RVertex> getPositionVertices()
	{
		HashSet<RVertex> listPositionVertices = new HashSet<>();
		Set<RVertex> listVertices = vertexSet();
		for(RVertex v : listVertices)
		{
			if(v.is_goodGuy())
			{
				listPositionVertices.add(v);
			}
		}
		
		return listPositionVertices;
	}
	
	public Set<RVertex> getShotLineVertices()
	{
		HashSet<RVertex> listPositionVertices = new HashSet<>();
		Set<RVertex> listVertices = vertexSet();
		for(RVertex v : listVertices)
		{
			if(!v.is_goodGuy())
			{
				listPositionVertices.add(v);
			}
		}
		
		return listPositionVertices;
	}
	
}
