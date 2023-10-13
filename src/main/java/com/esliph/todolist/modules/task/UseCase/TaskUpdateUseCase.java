package com.esliph.todolist.modules.task.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.esliph.todolist.utils.Utils;
import com.esliph.todolist.services.ErrorResult;
import com.esliph.todolist.common.IUseCase;
import com.esliph.todolist.modules.task.ITaskRepository;
import com.esliph.todolist.modules.task.TaskModel;
import com.esliph.todolist.services.Result;

@Component
public class TaskUpdateUseCase implements IUseCase<TaskModel, String> {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Result<String> perform(TaskModel args) {
        var task = this.taskRepository.findByIdAndUserId(args.getId(), args.getUserId());

        if (task == null) {
            return Result.failure(
                    new ErrorResult().title("Update task")
                            .message("Task not found")
                            .description("Cannot found task")
                            .causes("Identificator"),
                    HttpStatus.BAD_REQUEST);
        }

        if (args.getTitle().length() > 50) {
            return Result.failure(
                    new ErrorResult().title("Update task")
                            .message("Task title cannot be biggest than 50 characters")
                            .description("You cannot set title with biggest than 50 characters")
                            .causes("Title"),
                    HttpStatus.BAD_REQUEST);
        }

        Utils.copyNonNullProperties(args, task);

        this.taskRepository.save(task);

        return Result.success("Task updated with successfully", HttpStatus.OK);
    }
}
