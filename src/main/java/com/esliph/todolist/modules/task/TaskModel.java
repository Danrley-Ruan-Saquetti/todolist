package com.esliph.todolist.modules.task;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity(name = "tasks")
public class TaskModel {

    @Id()
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID userId;
    private String description;
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    @CreationTimestamp
    private LocalDateTime createAt;
    @UpdateTimestamp
    private LocalDateTime updateAt;
}