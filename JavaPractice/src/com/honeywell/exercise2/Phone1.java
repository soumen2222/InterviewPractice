package com.honeywell.exercise2;
import java.util.Scanner;
class Phone1 {
	public static int min(int a,int b)
     {
     	return  a<b?a:b;
     }
    public static void main(String args[] ) throws Exception {
        Scanner scan=new Scanner(System.in);
        int n=scan.nextInt();
        int k=scan.nextInt();
        int res=Integer.MAX_VALUE;
        for(int i=1;i<=2 * k;i++)
	{
		res = min( res, Math.abs(i-k) + min(n % i , i - n % i) );
	}
        System.out.println(res);
    }
}