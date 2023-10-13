package com.esliph.todolist.modules.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, Integer> {
    List<TaskModel> findByUserId(int userId);

    TaskModel findByIdAndUserId(int id, int userId);
}