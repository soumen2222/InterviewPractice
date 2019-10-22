package com.soumen.generic.collectionexp;

public final class PriorityTask implements Comparable<PriorityTask> {

	private final Task task;
	private final Priority priority;
	
	PriorityTask(Task task1,Priority priority1 )
	{
		task =task1;
		priority=priority1;
	}
	
		
	public Task getTask() {
		return task;
	}



	public Priority getPriority() {
		return priority;
	}

	@Override
	public int compareTo(PriorityTask pt) {
		// TODO Auto-generated method stub		
		int c = priority.compareTo(pt.getPriority());
		return c!=0 ? c : task.compareTo(pt.getTask());
	}

	
	public boolean equals(Object o){
		
		if (o instanceof PriorityTask ){
			PriorityTask pt = (PriorityTask)o;
			
			return task.equals(pt.getTask()) && priority.equals(pt.getPriority());			
		}
		else{
		return false;}
		
	}
	
	public int hashcode()
	{
		return task.hashCode();
	}
	
	public String toString()
	{
		return task + ": " + priority;
	}
}
