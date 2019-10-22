package com.EPL.Arrays;

public class DutchFlag {
	
	
	public static void partition(int[] items, int pivot)
	{
		int pivotElement = items[pivot]; 		
		swap(items,0,pivot);
		int j=0;
		for(int k=1 ;k< items.length ;k++)
		{
			if(items[k] <= pivotElement)
			{
				swap(items,k,j+1);
				j++;
			}
			
		}
		swap(items,0,j);
		
	}
	
	public static void partition2(int[] items, int pivot)
	{
		int pivotElement = items[pivot]; 		
		int j=0;
		int equal =0;
		int larger = items.length;
		while (equal <larger)
		{
			if(items[equal] <pivotElement)
			{
				swap(items,equal++,j++);
				
			}else
				if(items[equal] == pivotElement)
				{
					++equal;
				}
				else
				{//items[equal] > pivotElement
					swap(items,equal,--larger);
				}			
		}		
	}
	
	private static void swap(int[] items , int a , int b)
	{
		int temp = items[a];
		items[a] = items[b];
		items[b] =temp;
	}
	
	public static void main(String args[])
	{
		
		int[] itemList = {4,5,2,5,3,5,8,1,2};
		partition2(itemList ,3);
		for(int i = 0 ; i<itemList.length ;i++)
		{
			System.out.print(itemList[i] + ", ");
		}
		
		
	}
	}

