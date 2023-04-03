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

package com.pmrfansub.subflow.utils;

import com.pmrfansub.subflow.entity.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Ginakira
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

  @CreatedBy
//  @Column(name = "created_by")
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(
      name = "created_by",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
  )
  private User createdBy;

  @CreatedDate
  @Column(name = "created_date")
  private Date createdDate;

  @LastModifiedBy
//  @Column(name = "last_modified_by")
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(
      name = "last_modified_by",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
  )
  private User lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private Date lastModifiedDate;
}
