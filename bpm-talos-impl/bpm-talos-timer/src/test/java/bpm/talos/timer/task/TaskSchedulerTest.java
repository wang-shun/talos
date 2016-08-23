package bpm.talos.timer.task;

import javax.annotation.Resource;

import me.ele.bpm.talos.timer.task.TaskScheduler;

import org.junit.Test;

import bpm.talos.timer.base.TestBase;

public class TaskSchedulerTest extends TestBase {
	
	@Resource
	private TaskScheduler taskScheduler;
	
	@Test
	public void testactivityTask() {
		taskScheduler.activityTask();
	}

}
