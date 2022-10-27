/*
 * Copyright (c) 2022. PMR Fansub
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
 * 用户状态枚举
 *
 * @author Ginakira
 * @version 1.0
 * @since 1.0
 */
public enum UserStatus {
  /**
   * 已激活
   */
  ACTIVE(0),
  /**
   * 未激活
   */
  INACTIVE(1),
  /**
   * 已封禁
   */
  BANNED(2),
  /**
   * 已注销
   */
  DELETED(3),
  /**
   * 未知
   */
  UNKNOWN(-1);

  private final int value;

  UserStatus(int value) {
    this.value = value;
  }

  public static String toString(UserStatus status) {
    return switch (status) {
      case ACTIVE -> "已激活";
      case INACTIVE -> "未激活";
      case BANNED -> "已封禁";
      case DELETED -> "已注销";
      default -> "未知";
    };
  }

  public int getValue() {
    return this.value;
  }
}
