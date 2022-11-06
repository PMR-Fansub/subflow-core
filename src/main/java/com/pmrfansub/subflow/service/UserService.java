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

package com.pmrfansub.subflow.service;

import com.pmrfansub.subflow.dto.LoggedResp;
import com.pmrfansub.subflow.dto.UpdateUserInfoRequest;
import com.pmrfansub.subflow.entity.User;

/**
 * 用户服务接口
 *
 * @author Ginakira
 * @version 1.0
 * @since 1.0
 */
public interface UserService {

  /**
   * 注册新用户
   *
   * @param user 用户实体
   * @throws com.pmrfansub.subflow.common.BusinessException 通用业务异常
   */
  void addNewUser(User user);

  /**
   * 用户登录
   *
   * @param username   用户名
   * @param password   密码
   * @param remoteAddr 远端地址
   * @return Token及用户信息
   * @throws com.pmrfansub.subflow.common.BusinessException 通用业务异常
   */
  LoggedResp loginUser(String username, String password, String remoteAddr);

  /**
   * 通过UID查询用户信息
   *
   * @param uid 用户UID
   * @return 用户实体
   * @throws com.pmrfansub.subflow.common.BusinessException 通用业务异常
   */
  User getUserByUid(Integer uid);

  /**
   * 更新用户信息
   *
   * @param uid             用户UID
   * @param userInfoRequest 请求实体
   * @throws com.pmrfansub.subflow.common.BusinessException 通用业务异常
   */
  void updateUserInfo(Integer uid, UpdateUserInfoRequest userInfoRequest);

  /**
   * 更新昵称
   *
   * @param uid      用户UID
   * @param nickname 新昵称
   * @throws com.pmrfansub.subflow.common.BusinessException 通用业务异常
   */
  void updateNickname(Integer uid, String nickname);
}
