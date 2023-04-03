/*
 * Copyright (c) 2022-2023. PMR Fansub
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

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 统一响应状态码
 *
 * @author Ginakira
 * @version 1.0
 * @since 1.0
 */

@Getter
public enum ResultCode {
  /**
   * 成功
   */
  SUCCESS(true, 1000, "", HttpStatus.OK),
  /**
   * 失败
   */
  FAILED(false, 1001, "", HttpStatus.BAD_REQUEST),
  /**
   * 注册失败
   */
  REGISTER_FAILED(false, 1002, "注册失败", HttpStatus.BAD_REQUEST),
  /**
   * 表单验证失败
   */
  FORM_INVALID(false, 1003, "表单验证失败", HttpStatus.BAD_REQUEST),
  /**
   * 登录失败
   */
  LOGIN_FAILED(false, 1004, "登录失败", HttpStatus.BAD_REQUEST),
  /**
   * 登录态验证失败
   */
  NOT_LOGIN(false, 1005, "未登录", HttpStatus.UNAUTHORIZED),
  /**
   * 用户操作失败
   */
  USER_OPERATION_FAILED(false, 1006, "用户操作失败", HttpStatus.BAD_REQUEST),
  /**
   * 查询操作失败
   */
  QUERY_FAILED(false, 1007, "查询失败", HttpStatus.NOT_FOUND),
  /**
   * 权限不足
   */
  PERMISSION_DENIED(false, 1008, "权限不足", HttpStatus.FORBIDDEN),
  /**
   * 资源不存在
   */
  NOT_FOUND(false, 4000, "资源不存在", HttpStatus.NOT_FOUND),
  /**
   * 资源不存在
   */
  NOT_SUPPORTED(false, 4001, "不支持此操作", HttpStatus.METHOD_NOT_ALLOWED),
  /**
   * 未知的错误
   */
  UNKNOWN(false, 9999, "未知错误", HttpStatus.INTERNAL_SERVER_ERROR);

  private final Boolean success;
  private final int code;

  private final String message;

  private final HttpStatus httpStatus;

  ResultCode(Boolean success, int code, String message, HttpStatus httpStatus) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
