package com.aitp.dlife.background;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aitp.dlife.service.TaskEngineService;

@Component
public class TaskEngineRunner implements DisposableBean, Runnable {
    private final Logger log = LoggerFactory.getLogger(TaskEngineRunner.class);

    private Thread thread;
    private volatile boolean running;
    private long waitTime = 6000L;
    
    @Autowired
    private TaskEngineService taskEngineService;
    
    public TaskEngineRunner() {
    	start();
    }
    
	@Override
	public void run() {
		log.info("start task engine runner");
		String message = "runner will wait " + this.waitTime + "ms";
		while(this.running) {
			try {
				if(taskEngineService != null) {
					taskEngineService.evaluateUserEvents();
					taskEngineService.cleanHistoryData();
				}
				
				log.debug(message);
				Thread.sleep(waitTime);
			} catch (Exception e) {
				log.error(e.toString(), e);
			}
		}
		log.info("end task engine runner");
	}

	@Override
	public void destroy() throws Exception {
		this.running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public void start() {
    	this.running = true;
    	this.thread = new Thread(this);
        this.thread.start();
	}

}
