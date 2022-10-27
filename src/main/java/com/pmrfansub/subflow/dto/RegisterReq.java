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

package com.pmrfansub.subflow.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class RegisterReq {

  @NotNull(message = "用户名不能为空")
  @NotBlank(message = "用户名不能为空")
  @Length(min = 4, max = 32, message = "用户名长度必须在4-32之间")
  private String username;

  @NotNull(message = "密码不能为空")
  @NotBlank(message = "密码不能为空")
  @Length(min = 6, max = 64, message = "密码长度必须在6-64之间")
  private String password;

  @NotNull(message = "邮件地址不能为空")
  @NotBlank(message = "邮件地址不能为空")
  @Email(message = "邮件地址格式不正确")
  private String email;
}
