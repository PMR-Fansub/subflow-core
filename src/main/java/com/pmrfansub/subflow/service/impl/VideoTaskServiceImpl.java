/*
 * Copyright (c) 2023. PMR Fansub
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pmrfansub.subflow.service.impl;

import com.pmrfansub.subflow.common.VideoTaskStatus;
import com.pmrfansub.subflow.entity.VideoTask;
import com.pmrfansub.subflow.repository.VideoTaskRepository;
import com.pmrfansub.subflow.service.UserService;
import com.pmrfansub.subflow.service.VideoTaskService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

/**
 * 视频任务服务实现
 *
 * @author Ginakira
 */
@Slf4j
@Service
public class VideoTaskServiceImpl implements VideoTaskService {

  private final VideoTaskRepository videoTaskRepository;

  private final RuntimeService runtimeService;
  private final TaskService taskService;
  private final UserService userService;

  public VideoTaskServiceImpl(RuntimeService runtimeService, TaskService taskService,
      VideoTaskRepository videoTaskRepository, UserService userService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
    this.videoTaskRepository = videoTaskRepository;
    this.userService = userService;
  }

  @Override
  public String startNewActivitiProcess() {
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
        "VideoNormalSerialTask");
    String processInstanceId = processInstance.getId();
    log.debug("Activiti ProcessInstance created. Id: {}", processInstanceId);
    return processInstanceId;
  }

  @Override
  public <T> List<T> queryAllVideoTaskAs(Class<T> type) {
    return videoTaskRepository.findBy(type);
  }

  @Override
  public <T> T queryVideoTaskAs(Integer taskId, Class<T> type) {
    return videoTaskRepository.findById(taskId, type);
  }

  @Override
  public void assignSubTask(Integer taskId, String subTaskKey, Integer assigneeUserId) {
    log.info("Assign subtask. taskId: {}, subTaskKey: {}, assignee: {}", taskId, subTaskKey,
        assigneeUserId);
    VideoTask videoTask = videoTaskRepository.getById(taskId);
    String processInstanceId = videoTask.getProcessInstanceId();
    Task subTask = taskService.createTaskQuery()
        .processInstanceId(processInstanceId)
        .taskDefinitionKey(subTaskKey)
        .singleResult();
    subTask.setAssignee(assigneeUserId.toString());
    videoTask.setStatus(VideoTaskStatus.IN_PROGRESS.getValue());
    taskService.saveTask(subTask);
    videoTaskRepository.save(videoTask);
    //hey
  }

  @Override
  public void completeSubTask(Integer taskId, String subTaskKey) {
    log.info("Complete subtask. taskId: {}, subTaskKey: {}", taskId, subTaskKey);
    VideoTask videoTask = videoTaskRepository.getById(taskId);
    String processInstanceId = videoTask.getProcessInstanceId();
    Task subTask = taskService.createTaskQuery()
        .processInstanceId(processInstanceId)
        .taskDefinitionKey(subTaskKey)
        .singleResult();
    if (subTask != null) {
      taskService.complete(subTask.getId());
    }

    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
        .processInstanceId(processInstanceId).singleResult();
    if (processInstance == null) {
      log.info("VideoTask completed. id: {}, instanceId: {}", videoTask.getId(), processInstanceId);
      videoTask.setStatus(VideoTaskStatus.COMPLETED.getValue());
      videoTaskRepository.save(videoTask);
    }
  }
}