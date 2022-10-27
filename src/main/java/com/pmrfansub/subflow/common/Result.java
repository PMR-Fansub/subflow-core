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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 统一返回数据结构
 *
 * @author Ginakira
 */
@Data
public class Result<T> implements Serializable {

  /**
   * 服务器当前时间戳
   */
  private Long timestamp;

  /**
   * 是否成功
   */
  private Boolean success;

  /**
   * 响应数据
   */
  private T data;

  /**
   * 状态码
   */
  private int code;

  /**
   * 响应信息
   */
  private String message;

  /**
   * HTTP 状态码 不参与序列化 仅用于统一响应拦截设置状态码
   */
  @JsonIgnore
  private HttpStatus httpStatus;

  public Result() {
    this.timestamp = System.currentTimeMillis();
  }

  public static <T> Result<T> fill(boolean success, T data, int code, String message,
      HttpStatus httpStatus) {
    Result<T> result = new Result<>();
    result.setSuccess(success);
    result.setData(data);
    result.setCode(code);
    result.setMessage(message);
    result.setHttpStatus(httpStatus);
    return result;
  }

  public static <T> Result<T> fill(T data, ResultCode code, String message) {
    return fill(code.getSuccess(), data, code.getCode(), message, code.getHttpStatus());
  }

  public static <T> Result<T> fill(T data, ResultCode code) {
    return fill(data, code, code.getMessage());
  }

  public static <T> Result<T> ok(T data) {
    return fill(data, ResultCode.SUCCESS);
  }

  public static <T> Result<T> failed(ResultCode code) {
    return fill(null, code);
  }

  public static <T> Result<T> failed(ResultCode code, String message) {
    return fill(null, code, message);
  }
}
