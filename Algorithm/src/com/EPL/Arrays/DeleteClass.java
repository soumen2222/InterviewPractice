package com.EPL.Arrays;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DeleteClass {
	
	public static <T> List<T> nCopies(int n, T o) {
        if (n < 0)
            throw new IllegalArgumentException("List length = " + n);
        return new CopiesList<>(n, o);
    }

	    /**
	     * @serial include
	     */
	    private static class CopiesList<E> extends AbstractList<E>
	        
	    {
	        private static final long serialVersionUID = 2739099268398711800L;

	        final int n;
	        final E element;

	        CopiesList(int n, E e) {
	           // assert n >= 0;
	            this.n = n;
	            element = e;
	        }

			

	      public int size() {
	            return n;
	        }

	/*        public boolean contains(Object obj) {
	            return n != 0 && eq(obj, element);
	        }

	        public int indexOf(Object o) {
	            return contains(o) ? 0 : -1;
	        }

	        public int lastIndexOf(Object o) {
	            return contains(o) ? n - 1 : -1;
	        }
*/
	        public E get(int index) {
	            if (index < 0 || index >= n)
	                throw new IndexOutOfBoundsException("Index: "+index+
	                                                    ", Size: "+n);
	            return element;
	        }



	       public Object[] toArray() {
	            final Object[] a = new Object[10];
	           
	            return a;
	        }

	        @SuppressWarnings("unchecked")
	        public <T> T[] toArray(T[] a) {	           
	            return a;
	        }

	       
	       
	    }
	    
	    static boolean eq(Object o1, Object o2) {
	        return o1==null ? o2==null : o1.equals(o2);
	    }
}
