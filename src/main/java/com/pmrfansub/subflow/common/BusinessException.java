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
 * 通用业务层异常
 *
 * @author Ginakira
 * @version 1.0
 * @since 1.0
 */
public class BusinessException extends RuntimeException {

  private ResultCode code = ResultCode.FAILED;

  /**
   * 业务层异常 构造函数
   */
  public BusinessException() {
  }

  /**
   * 业务层异常 构造函数
   *
   * @param message 异常信息
   */
  public BusinessException(String message) {
    super(message);
  }

  /**
   * 业务层异常 构造函数
   *
   * @param cause Throwable
   */
  public BusinessException(Throwable cause) {
    super(cause);
  }

  /**
   * 业务层异常 构造函数
   *
   * @param message 异常信息
   * @param cause   Throwable
   */
  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * 业务层异常 构造函数
   *
   * @param code    异常代码
   * @param message 异常信息
   */
  public BusinessException(ResultCode code, String message) {
    super(message);
    this.code = code;
  }

  /**
   * 业务层异常 构造函数
   *
   * @param code 异常代码
   */
  public BusinessException(ResultCode code) {
    super(code.getMessage());
    this.code = code;
  }

  /**
   * 业务层异常 构造函数
   *
   * @param code    异常代码
   * @param message 异常信息
   * @param cause   Throwable
   */
  public BusinessException(ResultCode code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public ResultCode getCode() {
    return code;
  }
}