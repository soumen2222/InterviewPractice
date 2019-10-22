package com.honeywell.exercise;

class Hawk extends Raptor {
public static void main(String[] args) {
System.out.print("pre ");
Hawk h1 = new Hawk();
Bird B1 = new Bird();
System.out.println("hawk ");

if (B1 instanceof Foo )
{
	System.out.println("H1 is instance of Foo ");
}

if (B1 instanceof Bird )
{
	System.out.println("H1 is instance of Bird ");
}

if (B1 instanceof Raptor )
{
	System.out.println("H1 is instance of Raptor ");
}

if (B1 instanceof Hawk )
{
	System.out.println("H1 is instance of Hawk ");
}


}
}