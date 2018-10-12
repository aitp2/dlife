package com.aitp.dlife.service;

import java.time.ZonedDateTime;

import com.aitp.dlife.domain.TaskDefine;
import com.aitp.dlife.domain.UserEvent;
import com.aitp.dlife.domain.UserTask;

public class Conditions {
	private String condition;
	private long records;
	private Integer param1;
	private String param2;
	private String param3;
	private int point;
	private int gaintPoint;
	private UserEvent userEvent;
	private TaskDefine taskDefine;
	private UserTask userTask;
	private ZonedDateTime periodStart;
	private ZonedDateTime periodEnd;

	public ZonedDateTime getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(ZonedDateTime periodStart) {
		this.periodStart = periodStart;
	}

	public ZonedDateTime getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(ZonedDateTime periodEnd) {
		this.periodEnd = periodEnd;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public UserEvent getUserEvent() {
		return userEvent;
	}

	public void setUserEvent(UserEvent userEvent) {
		this.userEvent = userEvent;
		param1 = userEvent.getParam1();
		param2 = userEvent.getParem2();
		param3 = userEvent.getParam3();
	}

	public TaskDefine getTaskDefine() {
		return taskDefine;
	}

	public void setTaskDefine(TaskDefine taskDefine) {
		this.taskDefine = taskDefine;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public Integer getParam1() {
		return param1;
	}

	public void setParam1(Integer param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public UserTask getUserTask() {
		return userTask;
	}

	public void setUserTask(UserTask userTask) {
		this.userTask = userTask;
	}

	public int getGaintPoint() {
		return gaintPoint;
	}

	public void setGaintPoint(int gaintPoint) {
		this.gaintPoint = gaintPoint;
	}

	private String target;
}
