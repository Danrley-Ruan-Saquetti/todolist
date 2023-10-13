package com.esliph.todolist.modules.task.UseCase;

import java.util.List;
import java.util.UUID;

import com.esliph.todolist.common.IUseCase;
import com.esliph.todolist.modules.task.ITaskRepository;
import com.esliph.todolist.modules.task.TaskModel;
import com.esliph.todolist.services.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskListUseCase implements IUseCase<UUID, List<TaskModel>> {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Result<List<TaskModel>> perform(UUID userId) {
        var tasks = this.taskRepository.findByUserId(userId);

        return Result.success(tasks, HttpStatus.OK);
    }
}
