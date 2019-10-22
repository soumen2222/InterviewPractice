

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

class LinkedHashEntry 
{    
	int key;
    String value;
    LinkedHashEntry next;
    
 
    /* Constructor */
    LinkedHashEntry(int key,String value) 
    {
    	this.key=key;
        this.value = value;
        this.next = null;
       
    }
}

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;
    
    private LinkedHashEntry[] table;
    
    public static void main(String[] args) throws IOException {
        new HashChains().processQueries();
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
        {   long tempHashValue = hash * multiplier + s.charAt(i);
        	hash = ((tempHashValue % prime) + prime) % prime;
        }
            
        return (int)hash % bucketCount;
    }
    
    
    
    /* Function to insert a key value pair */
    public void insert(String value) 
    {
        int hash = hashFunc( value );
        if (table[hash] == null){
            table[hash] = new LinkedHashEntry(hash,value);            
        }
        else 
        {
            LinkedHashEntry entry = table[hash];
            while (entry.next != null && !entry.value.equals(value))
                entry = entry.next;
            if (!entry.value.equals(value))
            {//Enter at the start of the list
            	LinkedHashEntry entry1 = table[hash];
            	LinkedHashEntry newData= new LinkedHashEntry(hash, value);
            	newData.next=entry1;
            	table[hash]=newData;
            	
            } 
        }
       
    }
    
    public void remove(String value) 
    {
    	int hash = hashFunc( value );
        if (table[hash] != null) 
        {
            LinkedHashEntry prevEntry = null;
            LinkedHashEntry entry = table[hash];
            while (entry.next != null && !entry.value.equals(value)) 
            {
                prevEntry = entry;
                entry = entry.next;
            }
            if (entry.value.equals(value)) 
            {
                if (prevEntry == null)
                    table[hash] = entry.next;
                else
                    prevEntry.next = entry.next;               
            }
        }
    }
    
	public String check(int key) {
		StringBuilder output = new StringBuilder();
		if (key > bucketCount)
			return output.toString();
		else {
			if (table[key] != null) {
				LinkedHashEntry entry = table[key];
				while (entry != null) {
					output.append(entry.value).append(" ");
					entry = entry.next;
					
				}
			}
			return output.toString();
		}
	}
    
    public boolean get(String value) 
    {
    	int hash = hashFunc( value );
        if (table[hash] == null)
            return false;
        else 
        {
            LinkedHashEntry entry = table[hash];
            while (entry != null && !entry.value.equals(value))
                entry = entry.next;
            if (entry == null)
                return false;
            else
                return true;
        }
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        out.flush(); //uncomment
    }

    private void processQuery(Query query) {
        switch (query.type) {
            case "add":
                insert(query.s);
                break;
            case "del":
               remove(query.s);
                break;
            case "find":
                writeSearchResult(get(query.s));
                break;
            case "check":
            	out.print(check(query.ind));                
                out.println();
                out.flush(); //uncomment
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueries() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        table= new LinkedHashEntry[bucketCount];
        for (int i = 0; i < bucketCount; i++)
            table[i] = null;
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
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
