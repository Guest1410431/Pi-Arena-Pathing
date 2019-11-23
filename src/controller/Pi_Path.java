package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.Graph;

public class Pi_Path
{
	private static final String PI = "S31415E92653E58979E32384E62643E38327E95028E84197E16939E93751E05820E97494E45923E07816E40628E62089E98628E03482E53421E17067E";

	private static final File PERMUTATION_FILE = new File("permutation");
	private static final File ITERATION_NODE_FILE = new File("iteration-Nodes");
	private static final File ITERATION_EDGE_FILE = new File("iteration-Edges");
	private static final File RESULTS_FILE = new File("results"); // CSV file holding results

	private static Graph graph;

	public Pi_Path()
	{
		bruteForcePath();
	}

	private void bruteForcePath()
	{
		String result = "Permutation-5-10-15-20-25-30-35-40-45-50-55-60-65-70-75-80-85-90-95-100\n";

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		try
		{
			bufferedReader = new BufferedReader(new FileReader("res/" + PERMUTATION_FILE + ".txt"));
			int lineNumber = 1;
			String line = "";
			
			while ((line = bufferedReader.readLine()) != null)
			{
				int length = 0;
				result += line + "-";

				writePermutationToNodeFile(line);
				writePermutationToEdgeFile();

				graph = new Graph(100);
				graph.init("iteration");

				for (int i = 0; i < PI.length() - 1; i++)
				{
					length += graph.bfs(PI.substring(i, i + 1), PI.substring(i + 1, i + 2));

					if (Math.floorMod(i + 1, 5) == 0)
					{
						result += length + "-";
					}
				}
				result += "\n";
				System.out.println("Permutation: " + line + " | Length: " + length + " | " + lineNumber++);
			}
			bufferedReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter("res/" + RESULTS_FILE + ".txt"));
			bufferedWriter.write(result);
			bufferedWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void writePermutationToNodeFile(String line)
	{
		BufferedWriter bufferedWriter = null;

		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter("res/" + ITERATION_NODE_FILE + ".txt"));

			bufferedWriter.write(line.replace(',', '\n'));
			bufferedWriter.write("S\nE\nI0\nI1\nI2\nI3\nI4");
			bufferedWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void writePermutationToEdgeFile()
	{
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		try
		{
			bufferedReader = new BufferedReader(new FileReader("res/" + ITERATION_NODE_FILE + ".txt"));
			bufferedWriter = new BufferedWriter(new FileWriter("res/" + ITERATION_EDGE_FILE + ".txt"));

			bufferedWriter.write("S I0 1\nI0 I1 1\nI1 I2 1\nI2 I3 1\nI3 I4 1\nI4 E 1\n");

			for (int i = 0; i < 10; i++)
			{
				bufferedWriter.write(bufferedReader.readLine() + " I" + Math.floorMod(i, 5) + " 1\n");
			}
			bufferedWriter.close();
			bufferedReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
