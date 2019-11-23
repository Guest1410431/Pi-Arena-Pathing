package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.Arena;

public class Permutation
{
	private final String DIGITS = "123456789"; // 1-9, add 0 after permute
	private final String FILE_PATH = "./src/permutation.txt";

	private static ArrayList<String> permutations;
	private static ArrayList<Arena> arenaPermutations;
	private static ArrayList<Arena> reducedPermutations;

	public Permutation()
	{
		permutations = new ArrayList<String>();
		arenaPermutations = new ArrayList<Arena>();
		reducedPermutations = new ArrayList<Arena>();

		permute();
		reducePermute();
		saveToFile();
	}

	// Permutes the given DIGITS variable
	private void permute()
	{
		_permute("", DIGITS);
	}

	private static void _permute(String prefix, String str)
	{
		int n = str.length();

		if (n == 0)
		{
			permutations.add("0" + prefix);
		}
		else
		{
			for (int i = 0; i < n; i++)
			{
				_permute(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
			}
		}
	}

	// Limits the number of stored permutations
	private void reducePermute()
	{
		System.out.println("Permutations: " + permutations.size());

		for (String string : permutations)
		{
			arenaPermutations.add(new Arena(string));
		}
		for (int i = 0; i < arenaPermutations.size() - 1; i++)
		{
			Arena arena = arenaPermutations.get(i);
			boolean match = false;

			for (int h = i + 1; h < arenaPermutations.size(); h++)
			{
				if (arena.compare(arenaPermutations.get(h)))
				{
					match = true;
					break;
				}
			}
			if (!match)
			{
				reducedPermutations.add(arena);
			}
		}
		System.out.println("Reduced Perm: " + reducedPermutations.size());
	}

	// Prints reduced permutations to file
	private void saveToFile()
	{
		try
		{
			FileWriter writer = new FileWriter(FILE_PATH);

			for (Arena arena : reducedPermutations)
			{
				writer.write(arena.toString() + System.lineSeparator());
			}
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
