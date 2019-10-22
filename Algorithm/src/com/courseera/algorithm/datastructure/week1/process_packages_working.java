package com.courseera.algorithm.datastructure.week1;



import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size = size;
        this.finish_time = new LinkedList<>();
    }

    public Response Process(Request request) {	
    	
        if(finish_time.size()<size)
        {
        	
        	// Allowed to buffer . So processing is possible.
        	
        	// Empty Queue. Process immediately
        	if(finish_time.isEmpty())
        	{
        		lastfinalTime = request.arrival_time + request.process_time;
        		finish_time.add(lastfinalTime);        		
        		return new Response(false, request.arrival_time);
        	}
        	else
        	{
        		if( request.arrival_time <lastfinalTime)
        		{
        			request.arrival_time=lastfinalTime;
        			lastfinalTime= request.arrival_time + request.process_time;
        		}else
        		{
        			lastfinalTime= request.arrival_time + request.process_time;
        		}
        			

        		finish_time.add(lastfinalTime);
        		return new Response(false, request.arrival_time);        	
        		
        	}
        	
        }
        else
        {
        	int finaltime =0;
        	int size =finish_time.size();
        	for(int i =0; i <size;i++)
    		{
    			finaltime = finish_time.poll();
    			if(request.arrival_time < finaltime)
    			{
    				finish_time.add(finaltime);
    			}
    		}        	
        	
        	if(finish_time.size()>=size)
        	{
        		return new Response(true, request.arrival_time);
        	}
        	else
        	{   lastfinalTime=Math.max(finaltime,request.arrival_time) + request.process_time;
        		finish_time.add(lastfinalTime);
        		return new Response(false, Math.max(finaltime,request.arrival_time));
        	} 
        }
      }
      
    
    private int size;
    private Queue<Integer> finish_time;
    private int lastfinalTime =0;
   
}

class process_packages_working {
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

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
