package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Node
{
	private boolean visited;

	private String nodeKey;

	private Item item;

	private ArrayList<Edge> edges;


	public Node(Item item)
	{
		this.nodeKey = item.getValue();
		this.item = item;
		
		edges = new ArrayList<Edge>();
		visited = false;
	}

	public String getNodeKey()
	{
		return nodeKey;
	}

	public Item getItem()
	{
		return item;
	}

	// Checks to make sure that duplicate edges are handled
	// Adds edge to the list, then sorts by weight
	public void addEdge(Edge edge)
	{
		// TODO: Duplicate handling
		edges.add(edge);

		edges.sort(new Comparator<Edge>()
		{
			public int compare(Edge edge1, Edge edge2)
			{
				return Integer.compare((int) (edge1.getLength()), (int) (edge2.getLength()));
			}
		});
	}

	public ArrayList<Edge> getEdges()
	{
		return edges;
	}

	public void setVisited(boolean visited)
	{
		this.visited = visited;
	}

	public boolean isVisited()
	{
		return visited;
	}

	public String toString()
	{
		String stringBuilder = "";

		stringBuilder += "[" + item.getValue() + "]";

		for (Edge edge : edges)
		{
			stringBuilder += "->" + edge.getEnd().getItem().getValue();
		}
		return stringBuilder;
	}

	public boolean isConnected(Node node)
	{
		for (Edge edge : edges)
		{
			if (edge.getEnd().equals(node))
			{
				return true;
			}
		}
		return false;
	}
}
