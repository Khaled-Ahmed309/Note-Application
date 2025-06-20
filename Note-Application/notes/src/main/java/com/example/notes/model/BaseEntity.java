package com.example.notes.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created_date;

    @CreatedBy
    @Column(updatable = false)
    private String  created_by;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updated_date;

    @LastModifiedBy
    @Column(insertable = false)
    private String updated_by;
}
