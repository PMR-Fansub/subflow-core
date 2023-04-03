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
import com.pmrfansub.subflow.dto.forms.LoggedResp;
import com.pmrfansub.subflow.dto.forms.LoginReq;
import com.pmrfansub.subflow.dto.forms.RegisterReq;
import com.pmrfansub.subflow.dto.forms.UpdateUserInfoReq;
import com.pmrfansub.subflow.entity.User;
import com.pmrfansub.subflow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  @Transactional(rollbackOn = Exception.class)
  public void register(@RequestBody @Valid RegisterReq registerReq, HttpServletRequest request) {
    User user = new User();
    Date now = new Date();
    String remoteAddr = request.getRemoteAddr();

    user.setUsername(registerReq.getUsername());
    user.setEmail(registerReq.getEmail());
    user.setNickname(user.getUsername());
    user.setRegisterTime(now);
    user.setRegisterIp(remoteAddr);
    user.setLoginTime(now);
    user.setLoginIp(remoteAddr);
    user.setPassword(passwordEncoder.encode(registerReq.getPassword()));

    userService.addNewUser(user);
  }


  @Operation(summary = "登录", description = "用户登录")
  @PostMapping("login")
  public LoggedResp login(@RequestBody @Valid LoginReq loginReq, HttpServletRequest request) {
    return userService.loginUser(loginReq.getUsername(), loginReq.getPassword(),
        request.getRemoteAddr());
  }


  @Operation(summary = "获取用户信息", description = "获取用户信息")
  @SaCheckLogin
  @GetMapping("{uid}")
  public User getUserInfo(@PathVariable Integer uid) {
    return userService.getUserByUid(uid);
  }

  @Operation(summary = "获取当前登录用户信息", description = "获取当前登录用户信息")
  @SaCheckLogin
  @GetMapping("")
  public User getCurrentUserInfo() {
    Integer uid = StpUtil.getLoginId(-1);
    return userService.getUserByUid(uid);
  }


  @Operation(summary = "更新用户信息", description = "更新用户信息")
  @SaCheckLogin
  @PatchMapping("{uid}")
  public void updateUserInfo(@PathVariable Integer uid,
      @RequestBody @Valid UpdateUserInfoReq updateUserInfoReq) {
    userService.updateUserInfo(uid, updateUserInfoReq);
  }

  @Operation(summary = "更新当前用户信息", description = "更新当前用户信息")
  @SaCheckLogin
  @PatchMapping("")
  public void updateCurrentUserInfo(
      @RequestBody @Valid UpdateUserInfoReq updateUserInfoReq) {
    Integer uid = StpUtil.getLoginId(-1);
    userService.updateUserInfo(uid, updateUserInfoReq);
  }
}
