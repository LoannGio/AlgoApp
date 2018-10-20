package main;

import java.awt.geom.Point2D;


//Class representing the vertices of the graph: characterized by a position (in the field), 
//an angle if the vertex is a shotline vertex, and a bool that id true if the vertex is a
//position vertex(ie a potential defender position)

public class RVertex 
{
	
	
	public RVertex(java.awt.geom.Point2D _position, double _theta, boolean _goodGuy) 
	{
		this._position = _position;
		this._theta = _theta;
		this._goodGuy = _goodGuy;
	}
	
	public RVertex(java.awt.geom.Point2D _position, boolean _goodGuy) 
	{
		this._position = _position;
		this._goodGuy = _goodGuy;
	}


	public double get_theta()
	{
		return _theta;
	}
	
	public Point2D get_position()
	{
		return _position;
	}
	
	public boolean is_goodGuy()
	{
		return _goodGuy;
		
	}
	
	public void set_goodGuy(boolean goodGuy)
	{
		_goodGuy = goodGuy;
		
	}
	

	public void set_position(Point2D.Double _position) 
	{
		this._position = _position;
	}

	public void set_theta(double _theta)
	{
		this._theta = _theta;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (_goodGuy ? 1231 : 1237);
		result = prime * result + ((_position == null) ? 0 : _position.hashCode());
		long temp;
		temp = Double.doubleToLongBits(_theta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (_goodGuy != other._goodGuy)
			return false;
		if (_position == null) {
			if (other._position != null)
				return false;
		} else if (!_position.equals(other._position))
			return false;
		if (Double.doubleToLongBits(_theta) != Double.doubleToLongBits(other._theta))
			return false;
		return true;
	}




	private Point2D _position;
	private double _theta;
	private boolean _goodGuy;
	
	
}
