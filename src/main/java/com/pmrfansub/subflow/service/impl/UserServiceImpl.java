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

package com.pmrfansub.subflow.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.pmrfansub.subflow.common.BusinessException;
import com.pmrfansub.subflow.common.ResultCode;
import com.pmrfansub.subflow.common.UserStatus;
import com.pmrfansub.subflow.dto.LoggedResp;
import com.pmrfansub.subflow.dto.UpdateUserInfoRequest;
import com.pmrfansub.subflow.entity.User;
import com.pmrfansub.subflow.repository.UserRepository;
import com.pmrfansub.subflow.service.UserService;
import java.util.Date;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author Ginakira
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void addNewUser(User user) {
    if (userRepository.findByUsername(user.getUsername()) != null) {
      throw new BusinessException(ResultCode.REGISTER_FAILED, "用户名已被使用");
    } else if (userRepository.findByEmail(user.getEmail()) != null) {
      throw new BusinessException(ResultCode.REGISTER_FAILED, "邮箱地址已被使用");
    }
    user.setStatus(UserStatus.ACTIVE.getValue());
    userRepository.save(user);
  }

  @Override
  public LoggedResp loginUser(String username, String password, String remoteAddr) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new BusinessException(ResultCode.LOGIN_FAILED, "用户名不存在");
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BusinessException(ResultCode.LOGIN_FAILED, "用户名或密码错误");
    }

    user.setLogin_ip(remoteAddr);
    user.setLogin_time(new Date());
    userRepository.save(user);

    StpUtil.login(user.getId());
    return new LoggedResp(StpUtil.getTokenInfo(), user);
  }

  @Override
  public User getUserByUid(Integer uid) {
    User user = userRepository.findById(uid).orElse(null);
    if (user == null) {
      throw new BusinessException(ResultCode.QUERY_FAILED, "用户不存在");
    }
    return user;
  }

  @Override
  public void updateUserInfo(Integer uid, UpdateUserInfoRequest userInfoRequest) {
    String nickname = userInfoRequest.getNickname();
    if (nickname != null) {
      updateNickname(uid, nickname);
    }
  }

  @Override
  public void updateNickname(Integer uid, String nickname) {
    User user = getUserByUid(uid);
    user.setNickname(nickname);
    userRepository.save(user);
  }
}
