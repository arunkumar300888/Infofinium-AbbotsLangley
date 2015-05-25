package com.ardhika.wfar;

public class TaskSummary {

	private String taskName;
	private long taskCount;

	public TaskSummary(String taskName, long taskCount) {

		super();
		this.taskName = taskName;
		this.taskCount = taskCount;
	}

	public long getTaskCount() {

		return taskCount;
	}

	public String getTaskName() {

		return taskName;
	}
}
