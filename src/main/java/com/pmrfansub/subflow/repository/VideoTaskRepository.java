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

package com.pmrfansub.subflow.repository;

import com.pmrfansub.subflow.entity.VideoTask;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 视频任务 Repo
 *
 * @author sakata
 */
public interface VideoTaskRepository extends JpaRepository<VideoTask, Integer> {

  /**
   * 查询所有指定状态的视频任务
   *
   * @param status 任务状态
   * @return 视频任务列表
   */
  <T> List<T> findAllByStatus(Integer status, Class<T> type);

  <T> List<T> findBy(Class<T> type);

  <T> T findById(Integer id, Class<T> type);

}