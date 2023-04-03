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

package com.pmrfansub.subflow.common;

/**
 * 视频任务状态枚举
 *
 * @author Ginakira
 */

public enum VideoTaskStatus {
  /**
   * 待分派
   */
  TO_BE_ASSIGNED(0),
  IN_PROGRESS(1),
  COMPLETED(2);


  private final int value;

  VideoTaskStatus(int value) {
    this.value = value;
  }

  public static String toString(VideoTaskStatus status) {
    return switch (status) {
      case TO_BE_ASSIGNED -> "待分派";
      case IN_PROGRESS -> "进行中";
      case COMPLETED -> "已完成";
      default -> "未知";
    };
  }

  public int getValue() {
    return value;
  }
}
