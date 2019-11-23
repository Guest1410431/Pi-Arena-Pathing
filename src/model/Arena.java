package model;

import java.lang.reflect.Array;

public class Arena
{
	private final int ARRAY_OFFSET = 5;
	private final int CHAR_TO_INT_OFFSET = 48;

	private int[] left;
	private int[] right;

	public Arena(String digits)
	{
		if (digits.length() != 10)
		{
			System.err.println("Incorrect Number of digits provided. Expected 10, recieved " + digits.length());
			return;
		}
		left = new int[5];
		right = new int[5];

		for (int i = 0; i < ARRAY_OFFSET; i++)
		{
			left[i] = digits.charAt(i) - CHAR_TO_INT_OFFSET;
			right[i] = digits.charAt(i + ARRAY_OFFSET) - CHAR_TO_INT_OFFSET;
		}
		for (int i = 0; i < ARRAY_OFFSET; i++)
		{
			if (left[i] > right[i])
			{
				swap(i);
			}
		}
	}

	private void swap(int index)
	{
		int a = left[index];
		left[index] = right[index];
		right[index] = a;
	}

	public boolean compare(Arena arena)
	{
		for (int i = 0; i < ARRAY_OFFSET; i++)
		{
			if (this.left[i] != arena.left[i] || this.right[i] != arena.right[i])
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj)
	{
		return joinArrays(this.left, this.right).equals(joinArrays(((Arena) obj).left, ((Arena) obj).right));
	}
	@Override
	public int hashCode()
	{
		return joinArrays(this.left, this.right).hashCode();
	}

	private int[] joinArrays(int[]... arrays)
	{
		int length = 0;
		for (int[] array : arrays)
		{
			length += array.length;
		}

		// T[] result = new T[length];
		final int[] result = (int[]) Array.newInstance(arrays[0].getClass().getComponentType(), length);
		int offset = 0;
		
		for (int[] array : arrays)
		{
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	public String toString()
	{
		int[]result = joinArrays(left, right);
		String stringBuilder = "";
		
		for(int i=0; i<result.length; i++)
		{
			stringBuilder += result[i] + ",";
		}		
		return stringBuilder;
	}
}
