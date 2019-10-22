package com.honeywell.exercise2;

public class Lemur {

	/**
	 * @param args
	 */
	private long lemur;
	private long banana;
	
	public static void main(String[] args) {
		
	System.out.println(" Enter Banana");
	int banana = 40;
	System.out.println(" Enter Lemur");
	int lemur = 14;
	Lemur algolemur = new Lemur();
	int minumsteps= algolemur.HungryLemurs(lemur,banana)	;
	System.out.println(" Minum steps is :" + minumsteps );	
		

	}
	
	public int HungryLemurs(int K , int N )
	{
		
		int min = 0;
		
		//check for lemur range 3K/2 to k/2 and search for minimum value( |N-N'| + |K-K'|)
		if (K<N){
			
			int low = K/2;
			int high = 3*K/2;
			
			int minK =K;
			int minN =N;
			
			while ( low<=high){
												
				int j=0;
				int i = 1;
				while (j<2*N){
					//factors of K
					j=low*i;
					if ( Math.abs(N-j) <= minN){
						minN = Math.abs(N-j);
						if (Math.abs(K-low) <minK){
							minK = Math.abs(K-low);
						}
					}
					 
					i++;
					
				}
				min = 	minN+ minK;	
				low = low +1;
				
			}	
			return min;
			
		}
		//check for banana range 3N/2 to N/2 and search for minimum value( |N-N'| + |K-K'|)
		else {
			
			int low = N/2;
			int high = 3*N/2;
			int i = 1;
			
			while ( low<=high){
				low = low +1;
				int minK = Math.abs(N-low);
								
				while (i<2*K){
					//factors of N
					i=low*i;
					
					int minN = Math.abs(K-i);	
					
					if ((minN+ minK)<min){
						min = minN+ minK;
					}
					i++;
					
				}
				
			}	
			return min;
			
		}
		
	}

}
