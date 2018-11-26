package main;

import java.awt.geom.Point2D;

//Class representing the vertices of the graph: characterized by a position (in the field), 
//an angle if the vertex is a shotline vertex, and a bool that id true if the vertex is a
//position vertex(ie a potential defender position)

public class RVertex {

	private Point2D _position;
	private double _theta;
	// private boolean _goodGuy;
	private RVertexType _type;

	public RVertex(java.awt.geom.Point2D _position, double _theta) {
		this._position = _position;
		this._theta = _theta;
		// this._goodGuy = _goodGuy;
		this._type = RVertexType.BAD_GUY;
	}

	public RVertex(java.awt.geom.Point2D _position, RVertexType _type) {
		this._position = _position;
		// this._goodGuy = _goodGuy;
		this._theta = 0.0;
		this._type = _type;
	}

	public double get_theta() {
		return _theta;
	}

	public Point2D get_position() {
		return _position;
	}

	public boolean is_goodGuy() {
		// return _goodGuy;
		return (_type == RVertexType.GOOD_GUY || _type == RVertexType.GOAL_GUY);

	}

	public boolean is_goal() {
		return (_type == RVertexType.GOAL_GUY);

	}

	/*
	 * public void set_goodGuy(boolean goodGuy) { _goodGuy = goodGuy;
	 * 
	 * }
	 */

	public void set_position(Point2D.Double _position) {
		this._position = _position;
	}

	public void set_theta(double _theta) {
		this._theta = _theta;
	}

	public RVertexType get_type() {
		return _type;
	}

	public void set_type(RVertexType _type) {
		this._type = _type;
	}

	@Override
	public String toString() {
		String str = "";
		if (is_goodGuy()) 
		{
			if(_type == RVertexType.GOAL_GUY)
			{
				str += "G:";
			}
			else
			{
				str += "D:";
			}
			str +=  "[" + _position.getX() + "," + _position.getY() + "]";
		} else {
			str += "T:" + "[" + _position.getX() + "," + _position.getY() + "]" + "::" + _theta;
		}
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_position == null) ? 0 : _position.hashCode());
		long temp;
		temp = Double.doubleToLongBits(_theta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((_type == null) ? 0 : _type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RVertex other = (RVertex) obj;
		if (_position == null) {
			if (other._position != null)
				return false;
		} else if (!_position.equals(other._position))
			return false;
		if (Double.doubleToLongBits(_theta) != Double.doubleToLongBits(other._theta))
			return false;
		if (_type != other._type)
			return false;
		return true;
	}
}
