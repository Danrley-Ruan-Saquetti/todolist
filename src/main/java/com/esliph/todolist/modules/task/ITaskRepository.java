package com.esliph.todolist.modules.task;

import java.util.List;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByUserId(UUID userId);

    TaskModel findByIdAndUserId(UUID id, UUID userId);
}