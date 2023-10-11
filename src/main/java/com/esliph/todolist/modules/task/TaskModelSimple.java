package com.esliph.todolist.modules.task;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class TaskModelSimple {

    private UUID userId;
    private String description;
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
}