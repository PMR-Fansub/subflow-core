/*
 * Copyright (c) 2022-2023. PMR Fansub
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

package com.pmrfansub.subflow.entity;

import com.pmrfansub.subflow.utils.AuditableEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * @author Ginakira
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class VideoTask extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Integer id;

  @Column(nullable = false, name = "process_instance_id")
  @Comment("Activiti ProcessInstance ID")
  private String processInstanceId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = true, name = "video_id")
  private Integer videoId;

  @Column(nullable = false)
  private Integer status;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = true, name = "completion_time")
  private Date completionTime;
}
