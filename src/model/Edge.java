package model;

public class Edge
{
	private float length;
	
	private Node start;
	private Node end;

	public Edge(Node start, Node end, float length)
	{
		this.length = length;
		this.start = start;
		this.end = end;
	}
	// Returns the length of the edge
	public float getLength()
	{
		return length;
	}
	// Returns the start node of the edge
	public Node getStart()
	{
		return start;
	}
	// Returns the destination of the edge
	public Node getEnd()
	{
		return end;
	}
}
