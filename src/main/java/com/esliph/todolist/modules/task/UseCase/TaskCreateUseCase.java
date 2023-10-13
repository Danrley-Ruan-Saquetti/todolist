package com.esliph.todolist.modules.task.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

import com.esliph.todolist.services.ErrorResult;
import com.esliph.todolist.common.IUseCase;
import com.esliph.todolist.modules.task.ITaskRepository;
import com.esliph.todolist.modules.task.TaskModel;
import com.esliph.todolist.modules.task.TaskModelSimple;
import com.esliph.todolist.services.Result;

@Component
public class TaskCreateUseCase implements IUseCase<TaskModelSimple, String> {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public Result<String> perform(TaskModelSimple args) {
        var taskInstance = new TaskModel();

        taskInstance.setUserId(args.getUserId());
        taskInstance.setDescription(args.getDescription());
        taskInstance.setTitle(args.getTitle());
        taskInstance.setStartAt(args.getStartAt());
        taskInstance.setEndAt(args.getEndAt());
        taskInstance.setPriority(args.getPriority());

        var now = LocalDateTime.now();

        if (now.isBefore(taskInstance.getStartAt()) || now.isBefore(taskInstance.getEndAt())) {
            return Result.failure(new ErrorResult().title("Create Task")
                    .message("Task with start/end before than current date")
                    .description("You cannot create task with start/end before than current date")
                    .causes("Start/End is before than current date"),
                    HttpStatus.BAD_REQUEST);
        }

        if (taskInstance.getStartAt().isAfter(taskInstance.getEndAt())) {
            return Result.failure(new ErrorResult().title("Create Task")
                    .message("Task with start after than end")
                    .description("You cannot create task with start after than end")
                    .causes("Start is after than end"),
                    HttpStatus.BAD_REQUEST);
        }

        var userCreated = this.taskRepository.save(taskInstance);

        return Result.success("Task created with successfully", HttpStatus.CREATED);
    }
}
