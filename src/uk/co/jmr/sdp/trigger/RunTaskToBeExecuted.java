package uk.co.jmr.sdp.trigger;




import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
 
public class RunTaskToBeExecuted extends QuartzJobBean {
	private TaskToBeExecuted runMeTask;
 
	public void setRunMeTask(TaskToBeExecuted runMeTask) {
		this.runMeTask = runMeTask;
	}
 
	protected void executeInternal(JobExecutionContext context)
		throws JobExecutionException {
			runMeTask.keepAppAlive();
			runMeTask.rentDueReminder();
	}
}
