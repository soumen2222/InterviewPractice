

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class JobQueueSoumen {
    private int[] jobs;
    private PriorityQueue<Worker> pq;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueueSoumen().solve();
    }

    private void readData() throws IOException {
        int numWorkers = in.nextInt();       
        int m = in.nextInt();
        pq = new PriorityQueue<>(numWorkers, new WorkerComparator());        
        for (int i = 0; i < numWorkers; ++i) {
        	Worker w=new Worker(i,0);
			pq.add(w);            
        }
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs() {       
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];        
        for (int i = 0; i < jobs.length; i++) {           
            Worker newWorker = pq.poll();
            assignedWorker[i]=newWorker.getPriority();
            startTime[i]=newWorker.getProcessingStartTime();
            newWorker.setProcessingStartTime(newWorker.getProcessingStartTime()+ jobs[i]);
            pq.add(newWorker);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
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
    
    class Worker {
        private long processingStartTime;
        private int priority;
       
        public Worker(int priority,long processingStartTime) {
         
            this.processingStartTime = processingStartTime;
            this.priority = priority;
        }

		public long getProcessingStartTime() {
			return processingStartTime;
		}

		public void setProcessingStartTime(long processingStartTime) {
			this.processingStartTime = processingStartTime;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}         
       
    }
    
    class WorkerComparator implements Comparator<Worker>{
        
        // Overriding compare()method of Comparator 
                    // for descending order of cgpa
        public int compare(Worker w1, Worker w2) {
            if (w1.getProcessingStartTime() < w2.getProcessingStartTime())
                return -1;
            else if (w1.getProcessingStartTime() > w2.getProcessingStartTime())
                return 1;
            else if (w1.getPriority() < w2.getPriority())
                return -1;
            	//Processing time is same, check the priority
            else if (w1.getPriority() > w2.getPriority())
                return 1;
         
                return 0;
            }
    }
}
