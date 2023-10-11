package com.esliph.todolist.modules.task;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {

}