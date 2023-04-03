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

package com.pmrfansub.subflow.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.pmrfansub.subflow.common.BusinessException;
import com.pmrfansub.subflow.common.ResultCode;
import com.pmrfansub.subflow.common.VideoTaskStatus;
import com.pmrfansub.subflow.dto.BasicVideoTaskInfo;
import com.pmrfansub.subflow.dto.VideoTaskInfo;
import com.pmrfansub.subflow.dto.forms.CreateVideoTaskReq;
import com.pmrfansub.subflow.entity.VideoTask;
import com.pmrfansub.subflow.repository.VideoTaskRepository;
import com.pmrfansub.subflow.service.VideoTaskService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * VideoTask controller
 *
 * @author Ginakira
 */
@RestController
@RequestMapping("/task")
@Slf4j
public class VideoTaskController {

  private final VideoTaskService videoTaskService;
  private final VideoTaskRepository videoTaskRepository;

  public VideoTaskController(VideoTaskService videoTaskService,
      VideoTaskRepository videoTaskRepository) {
    this.videoTaskService = videoTaskService;
    this.videoTaskRepository = videoTaskRepository;
  }

  @PostMapping()
  @SaCheckLogin
  public VideoTask createNewVideoTask(@RequestBody @Valid CreateVideoTaskReq createVideoTaskReq) {
    String activitiProcessInstanceId = videoTaskService.startNewActivitiProcess();
    Integer uid = StpUtil.getLoginId(-1);
    VideoTask videoTask = new VideoTask();
    videoTask.setName(createVideoTaskReq.getTaskName());
    videoTask.setProcessInstanceId(activitiProcessInstanceId);
    videoTask.setStatus(VideoTaskStatus.TO_BE_ASSIGNED.getValue());
    videoTaskRepository.save(videoTask);
    return videoTask;
  }

  @GetMapping("all")
  @SaCheckLogin
  public List<BasicVideoTaskInfo> queryAllTasks() {
    return videoTaskService.queryAllVideoTaskAs(BasicVideoTaskInfo.class);
  }

  @GetMapping("all/detail")
  @SaCheckLogin
  public List<VideoTaskInfo> queryAllDetailedTasks() {
    return videoTaskService.queryAllVideoTaskAs(VideoTaskInfo.class);
  }

  @GetMapping("detail/{taskId}")
  @SaCheckLogin
  public VideoTaskInfo queryDetailedTask(@PathVariable Integer taskId) {
    return videoTaskService.queryVideoTaskAs(taskId, VideoTaskInfo.class);
  }

  @PatchMapping("{taskId}")
  @SaCheckLogin
  public void modifyVideoTask(@PathVariable Integer taskId,
      @RequestParam String action,
      @RequestParam Optional<String> target) {
    switch (action) {
      case "accept" -> {
        String targetSubTask = target.orElseThrow(
            () -> new BusinessException(ResultCode.FORM_INVALID));
        videoTaskService.assignSubTask(taskId, targetSubTask, StpUtil.getLoginId(-1));
      }
      case "finish" -> {
        String targetSubTask = target.orElseThrow(
            () -> new BusinessException(ResultCode.FORM_INVALID));
        videoTaskService.completeSubTask(taskId, targetSubTask);
      }
      default -> throw new BusinessException(ResultCode.NOT_SUPPORTED);
    }
  }
}
