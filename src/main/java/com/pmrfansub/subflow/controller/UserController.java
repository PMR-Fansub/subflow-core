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

package com.pmrfansub.subflow.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.pmrfansub.subflow.dto.LoggedResp;
import com.pmrfansub.subflow.dto.LoginReq;
import com.pmrfansub.subflow.dto.RegisterReq;
import com.pmrfansub.subflow.dto.UpdateUserInfoRequest;
import com.pmrfansub.subflow.entity.User;
import com.pmrfansub.subflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User controller
 *
 * @author Ginakira
 * @version 1.0
 * @since 1.0
 */
@Tag(name = "用户", description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  private final BCryptPasswordEncoder passwordEncoder;

  public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }


  @Operation(summary = "注册", description = "注册一个新用户")
  @PostMapping("register")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional(rollbackOn = Exception.class)
  public void register(@RequestBody @Valid RegisterReq registerReq, HttpServletRequest request) {
    User user = new User();
    Date now = new Date();
    String remoteAddr = request.getRemoteAddr();

    user.setUsername(registerReq.getUsername());
    user.setEmail(registerReq.getEmail());
    user.setNickname(user.getUsername());
    user.setRegister_time(now);
    user.setRegister_ip(remoteAddr);
    user.setLogin_time(now);
    user.setLogin_ip(remoteAddr);
    user.setPassword(passwordEncoder.encode(registerReq.getPassword()));

    userService.addNewUser(user);
  }


  @Operation(summary = "登录", description = "用户登录")
  @PostMapping("login")
  @ResponseStatus(HttpStatus.OK)
  public LoggedResp login(@RequestBody @Valid LoginReq loginReq, HttpServletRequest request) {
    return userService.loginUser(loginReq.getUsername(), loginReq.getPassword(),
        request.getRemoteAddr());
  }


  @Operation(summary = "获取用户信息", description = "获取用户信息")
  @SaCheckLogin
  @GetMapping("{uid}")
  @ResponseStatus(HttpStatus.OK)
  public User getUserInfo(@PathVariable Integer uid) {
    return userService.getUserByUid(uid);
  }

  @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息")
  @SaCheckLogin
  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public User getCurrentUserInfo() {
    Integer uid = StpUtil.getLoginId(-1);
    return getUserInfo(uid);
  }


  @Operation(summary = "更新用户信息", description = "更新用户信息")
  @SaCheckLogin
  @PatchMapping("{uid}")
  @ResponseStatus(HttpStatus.OK)
  public void updateUserInfo(@PathVariable Integer uid,
      @RequestBody @Valid UpdateUserInfoRequest updateUserInfoRequest) {
    if (updateUserInfoRequest.getNickname() != null) {
      userService.updateNickname(uid, updateUserInfoRequest.getNickname());
    }
  }
}