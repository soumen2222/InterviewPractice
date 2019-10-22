package com.honeywell.effectivejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

//Wrapper class - uses composition in place of inheritance
public class InstrumentedSet<E> extends ForwardingSet<E> {
	private int addCount = 0;

	public InstrumentedSet(Set<E> s) {
		super(s);
	}

	@Override
	public boolean add(E e) {
		addCount++;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		addCount += c.size();
		return super.addAll(c);
	}

	public int getAddCount() {
		return addCount;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<String> list1 = new TreeSet<String>();
		InstrumentedSet<String> obj = new InstrumentedSet<String>(list1);
		obj.addAll(Arrays.asList("Snap", "Crackle", "good"));
		System.out.println("Count :" + obj.getAddCount());
		
		
		
	}
}