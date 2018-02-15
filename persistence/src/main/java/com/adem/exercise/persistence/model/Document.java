package com.adem.exercise.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"key"})
)
public class Document {
    @Id
    @GeneratedValue
    private Long docId;
    private String key;
    private String token;
}
