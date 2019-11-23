package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Graph
{
	private int maxNodes;

	private ArrayList<Node> nodes;
	private LinkedList<Node> dfs_linked;
	private LinkedList<Node> bfs_linked;

	public Graph(int maxNodes)
	{
		this.maxNodes = maxNodes;
		nodes = new ArrayList<Node>();
	}

	public void init(String graphName)
	{
		String[] nodesFromFile = readInFile(graphName + "-Nodes");
		String[] edgesFromFile = readInFile(graphName + "-Edges");

		for (int i = 0; i < nodesFromFile.length; i++)
		{
			addNode(new Item(nodesFromFile[i].replaceAll(" ", "")));
		}
		for (int i = 0; i < edgesFromFile.length; i++)
		{
			String[] row = edgesFromFile[i].split(" ");

			addEdge(row[0], row[1], Integer.parseInt(row[2]), false);
		}
	}

	public void addNode(Item item)
	{
		if (nodes.size() < maxNodes)
		{
			nodes.add(new Node(item));
		}
		else
		{
			System.out.println("Too many nodes added");
		}
	}

	public void addEdge(String startNodeKey, String endNodeKey, float edgeWeight, boolean directed)
	{
		Node startNode = getNodeByNodeKey(startNodeKey);
		Node endNode = getNodeByNodeKey(endNodeKey);

		if (directed)
		{
			startNode.addEdge(new Edge(startNode, endNode, edgeWeight));
		}
		else
		{
			startNode.addEdge(new Edge(startNode, endNode, edgeWeight));
			endNode.addEdge(new Edge(endNode, startNode, edgeWeight));
		}
	}

	private Node getNodeByNodeKey(String nodeKey)
	{
		for (Node node : nodes)
		{
			if (node.getNodeKey().equals(nodeKey))
			{
				return node;
			}
		}
		System.out.println("Node: " + nodeKey + " -> not found");
		
		return null;
	}
	
	// Returns the lines of the file as elements in a String[]
	private String[] readInFile(String fileName)
	{
		ArrayList<String> fileLines = new ArrayList<String>();

		FileReader fileReader = null;

		try
		{
			fileReader = new FileReader(new File("res/" + fileName + ".txt"));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File not found in resource folder");
		}
		BufferedReader reader = new BufferedReader(fileReader);

		try
		{
			String line = reader.readLine();

			while (line != null)
			{
				fileLines.add(line);
				line = reader.readLine();
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		String[] fileRead = new String[fileLines.size()];

		return fileLines.toArray(fileRead);
	}
	
	public int bfs(String source, String destination)
	{
		Node sourceNode = getNodeByNodeKey(source);
		Node destinationNode = getNodeByNodeKey(destination);
		
		int length = _bfs(sourceNode, destinationNode);
		resetNodes();
		
		return length;
	}

	private int _bfs(Node sourceNode, Node destinationNode)
	{
		HashMap<Node, Integer>distances = new HashMap<Node, Integer>();

		LinkedList<Node>nextToVisit = new LinkedList<Node>();
		
		for(Node node: nodes)
		{
			distances.put(node, 0);
		}		
		nextToVisit.add(sourceNode);
		sourceNode.setVisited(true);
		
		while(nextToVisit.size() != 0)
		{
			Node temp = nextToVisit.remove();
			
			for(int i=0; i<temp.getEdges().size(); i++)
			{	
				Node connectedToTemp = temp.getEdges().get(i).getEnd();
				
				if(connectedToTemp.isVisited())
				{
					continue;
				}
				distances.put(connectedToTemp, distances.get(temp)+1);
				connectedToTemp.setVisited(true);
				nextToVisit.add(connectedToTemp);
			}
		}
		return distances.get(destinationNode);
	}

	// Resets all the "visited tags" for the nodes to false
	public void resetNodes()
	{
		for (Node node : nodes)
		{
			node.setVisited(false);
		}
	}

	// Matrix representation of the graphs
	// AKA, ugliest code I've written to date
	public String toString()
	{
		String stringBuilder = "    ";

		for (int i = 0; i < nodes.size(); i++)
		{
			int nodeKey = Integer.parseInt(nodes.get(i).getNodeKey());
			stringBuilder += nodeKey + ((nodeKey < 10) ? " " : "");
		}
		stringBuilder += "\n";

		for (int i = 0; i < nodes.size(); i++)
		{
			int nodeKey = Integer.parseInt(nodes.get(i).getNodeKey());
			stringBuilder += nodeKey + ((nodeKey < 10) ? " | " : "| ");

			for (int h = 0; h < nodes.size(); h++)
			{
				if (nodesConnected(nodes.get(i), nodes.get(h)))
				{
					stringBuilder += "1 ";
				}
				else
				{
					stringBuilder += "- ";
				}
			}
			stringBuilder += "\n";
		}
		return stringBuilder;
	}

	private boolean nodesConnected(Node iNode, Node hNode)
	{
		for (Edge edge : iNode.getEdges())
		{
			if (edge.getEnd().equals(hNode))
			{
				return true;
			}
		}
		return false;
	}
}
