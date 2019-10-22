

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BuildHeap {
    private int[] data;
    private int size=0;
    private List<Swap> swaps=new ArrayList<Swap>();

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        int n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
          size++;
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateHeap() {
       for (int i = (data.length/2) -1; i >= 0; i--) {
    	   heapifyDown(i);
      }
    }

    /** @param parentIndex The index of the parent element.
    @return The index of the left child.
**/
public int getLeftChildIndex(int parentIndex) {
    return 2 * parentIndex + 1;
}

/** @param parentIndex The index of the parent element.
    @return The index of the right child.
**/
public int getRightChildIndex(int parentIndex) {
    return 2 * parentIndex + 2;
}

/** @param childIndex The index of the child element.
    @return The index of the parent element.
**/
public int getParentIndex(int childIndex) {
    return (childIndex - 1) / 2;
}

/** @param index The index of the element you are checking.
    @return true if the Heap contains enough elements to fill the left child index, 
            false otherwise.
**/
public boolean hasLeftChild(int index) {
    return getLeftChildIndex(index) < size;
}

/** @param index The index of the element you are checking.
    @return true if the Heap contains enough elements to fill the right child index, 
            false otherwise.
**/
public boolean hasRightChild(int index) {
    return getRightChildIndex(index) < size;
}

/** @param index The index of the element you are checking.
    @return true if the calculated parent index exists within array bounds
            false otherwise.
**/
public boolean hasParent(int index) {
    return getParentIndex(index) >= 0;
}

/** @param index The index of the element whose child you want.
    @return the value in the left child.
**/
public int leftChild(int index) {
    return data[getLeftChildIndex(index)];
}

/** @param index The index of the element whose child you want.
    @return the value in the right child.
**/
public int rightChild(int index) {
    return data[getRightChildIndex(index)];
}

/** @param index The index of the element you are checking.
    @return the value in the parent element.
**/
public int parent(int index) {
    return data[getParentIndex(index)];
}

/** @param indexOne The first index for the pair of elements being swapped.
    @param indexTwo The second index for the pair of elements being swapped.
**/
public void swap(int indexOne, int indexTwo) {
	swaps.add(new Swap(indexOne, indexTwo));
    int temp = data[indexOne];
    data[indexOne] = data[indexTwo];
    data[indexTwo] = temp;
}
    
 public void heapifyDown(int index) {        
        while(hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            
            if(    hasRightChild(index) 
                && rightChild(index) < leftChild(index)
            ) {
                smallerChildIndex = getRightChildIndex(index);
            }
            
            if(data[index] < data[smallerChildIndex]) {
                break;
            }
            else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }
    
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateHeap();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
