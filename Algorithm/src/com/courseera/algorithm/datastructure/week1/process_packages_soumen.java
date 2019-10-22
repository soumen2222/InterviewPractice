package com.courseera.algorithm.datastructure.week1;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;



class Point {
    public int startTime;
    public int processingTime;

    public Point(int startTime, int processingTime) {
        this.startTime = startTime;
        this.processingTime = processingTime;
    }
}

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}


class process_packages_soumen {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static List<Integer> ProcessRequests(ArrayList<Request> requests, int bufferSize) {
        List<Integer> responses = new ArrayList<>();
        List<Point> points = new ArrayList<>();  
        
        for(int i =0 ; i <requests.size() ; i++)
            {
        	Point p1 = new Point(requests.get(i).arrival_time,"l");
        	Point p2 = new Point(requests.get(i).arrival_time+requests.get(i).process_time,"r");
        	points.add(p1);	
        	points.add(p2);	
            }
        
       
        
        long tempBufferSize = 0;
        int lastprocessingtime=0;
        for (int i = 0; i < points.size(); i++) {
        	
        	if(points.get(i).identifier.equals("l"))
        	{
        		if(tempBufferSize< bufferSize){
        			// The point is processed
        			tempBufferSize++;
        			lastprocessingtime = points.get(i).point;
        			responses.add(points.get(i).point);        			
        		}
        		else
        		{
        			// The point is not processed
        			responses.add(-1);
        		}
        		
        	}else
        	{// Finished processing a point
        		tempBufferSize--;
        	}
        
        }
        return responses;
    }

    private static void PrintResponses(List<Integer> responses) {
        for (int i = 0; i < responses.size(); ++i) {            
                System.out.println(responses.get(i));
            }
       
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();       

        ArrayList<Request> requests = ReadQueries(scanner);
        List<Integer> responses = ProcessRequests(requests, buffer_max_size);
        PrintResponses(responses);
    }
}
