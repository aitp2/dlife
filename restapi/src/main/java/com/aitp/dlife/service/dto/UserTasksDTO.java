package com.aitp.dlife.service.dto;

import java.util.List;

public class UserTasksDTO {
	private Long id;
	private List<UserTaskDTO> userTask;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<UserTaskDTO> getUserTask() {
		return userTask;
	}

	public void setUserTask(List<UserTaskDTO> userTask) {
		this.userTask = userTask;
	}
}
