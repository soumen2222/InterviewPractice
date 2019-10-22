package com.honeywell.hackerearth;

public class Josephus {

	/**
	 * @param args
	 */
	
	public static int nextMan(int currentManToKill, int killStep, int population) {
		int nextman = (currentManToKill + killStep) % population;
		System.out.println(nextman + " ");
	    return nextman;
	  }

	  // Objective function
	  public static int findLastManStanding(int population, int killStep) {
	    // Value function
	    int[] manToKill = new int[population+1];
	    manToKill[0] = nextMan(0, killStep, population);

	    for (int i = 1; i <= population; i++)
	      manToKill[i] = nextMan(manToKill[i - 1], killStep, population);

	    return nextMan(manToKill[population], killStep, population);
	  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Josephus.findLastManStanding(6, 2);

	}

}
