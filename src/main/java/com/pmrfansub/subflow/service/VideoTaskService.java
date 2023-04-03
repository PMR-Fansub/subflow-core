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

import java.util.List;

/**
 * 视频任务服务接口
 *
 * @author Ginakira
 */
public interface VideoTaskService {

  /**
   * 开始一个新 Activiti 流程
   *
   * @return Activiti ProcessInstance ID
   */
  String startNewActivitiProcess();

  /**
   * 查询全部任务列表
   *
   * @param type 查询结果DTO类型
   * @return 指定类型的全部任务信息列表
   */
  <T> List<T> queryAllVideoTaskAs(Class<T> type);


  /**
   * 查询单个任务信息
   *
   * @param taskId 任务ID
   * @param type   查询结果DTO类型
   * @return 指定类型的任务信息
   */
  <T> T queryVideoTaskAs(Integer taskId, Class<T> type);


  /**
   * 指派子任务
   *
   * @param taskId         主任务ID
   * @param subTaskKey     子任务标识符（Activiti TaskDefinitionKey)
   * @param assigneeUserId 受让人用户ID
   */
  void assignSubTask(Integer taskId, String subTaskKey, Integer assigneeUserId);

  /**
   * 完成子任务
   *
   * @param taskId     主任务ID
   * @param subTaskKey 子任务标识符 （Activiti TaskDefinitionKey)
   */
  void completeSubTask(Integer taskId, String subTaskKey);
}