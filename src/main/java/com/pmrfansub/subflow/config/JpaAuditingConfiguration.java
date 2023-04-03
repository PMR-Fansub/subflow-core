/*
 * Copyright (c) 2023. PMR Fansub
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

import cn.dev33.satoken.stp.StpUtil;
import com.pmrfansub.subflow.entity.User;
import com.pmrfansub.subflow.repository.UserRepository;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Ginakira
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

  private final UserRepository userRepository;

  public JpaAuditingConfiguration(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public AuditorAware<User> auditorProvider() {
        /*
          if you are using spring security, you can get the currently logged username with following code segment.
          SecurityContextHolder.getContext().getAuthentication().getName()
         */
    return () -> Optional.ofNullable(userRepository.findById(StpUtil.getLoginId(-1)).orElse(null));
  }
}
