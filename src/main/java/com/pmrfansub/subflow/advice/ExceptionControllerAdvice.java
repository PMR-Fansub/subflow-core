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

package com.pmrfansub.subflow.advice;

import cn.dev33.satoken.exception.NotLoginException;
import com.pmrfansub.subflow.common.BusinessException;
import com.pmrfansub.subflow.common.Result;
import com.pmrfansub.subflow.common.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常拦截器
 *
 * @author Ginakira
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(BusinessException.class)
  public Result<?> handleBusinessException(BusinessException e) {
    return Result.failed(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    ObjectError error = e.getBindingResult().getAllErrors().get(0);
    return Result.failed(ResultCode.FORM_INVALID, error.getDefaultMessage());
  }

  @ExceptionHandler(NotLoginException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Result<?> handleNotLoginException(NotLoginException e) {
    return Result.failed(ResultCode.NOT_LOGIN, e.getMessage());
  }
}
