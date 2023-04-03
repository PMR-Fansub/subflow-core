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

package com.pmrfansub.subflow.service;

import com.pmrfansub.subflow.common.VideoTaskStatus;
import com.pmrfansub.subflow.dto.ActivitiTaskInfo;
import com.pmrfansub.subflow.entity.VideoTask;
import com.pmrfansub.subflow.utils.ParseUtil;
import java.util.Date;
import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;

/**
 * Activiti 查询服务
 *
 * @author Ginakira
 */
@Component
public class ActivitiQueryService {

  private final TaskService taskService;
  private final UserService userService;
  private final HistoryService historyService;

  public ActivitiQueryService(TaskService taskService, UserService userService,
      HistoryService historyService) {
    this.taskService = taskService;
    this.userService = userService;
    this.historyService = historyService;
  }

  private int getStatusFromHistoricTaskInstance(
      HistoricTaskInstance historicTaskInstance) {
    Date endTime = historicTaskInstance.getEndTime();
    String assignee = historicTaskInstance.getAssignee();
    VideoTaskStatus status;
    if (assignee == null) {
      status = VideoTaskStatus.TO_BE_ASSIGNED;
    } else if (endTime == null) {
      status = VideoTaskStatus.IN_PROGRESS;
    } else {
      status = VideoTaskStatus.COMPLETED;
    }
    return status.getValue();
  }

  public List<ActivitiTaskInfo> queryAllSubTasksByVideoTask(VideoTask videoTask) {
    return historyService.createHistoricTaskInstanceQuery()
        .processInstanceId(videoTask.getProcessInstanceId())
        .list()
        .stream()
        .map(task -> ActivitiTaskInfo.builder()
            .id(task.getId())
            .name(task.getName())
            .status(getStatusFromHistoricTaskInstance(task))
            .description(task.getDescription())
            .taskDefKey(task.getTaskDefinitionKey())
            .assignee(userService.getBasicUserInfoByUid(ParseUtil.tryParseInt(task.getAssignee())))
            .duration(task.getDurationInMillis())
            .build())
        .toList();
  }
}
