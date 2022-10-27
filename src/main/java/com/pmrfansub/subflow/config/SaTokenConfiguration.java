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

package com.pmrfansub.subflow.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SaTokenConfiguration implements WebMvcConfigurer {

  // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
    registry.addInterceptor(new SaAnnotationInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/**", "/error");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowCredentials(true)
        .allowedMethods("*")
        .maxAge(3600);
  }
}
