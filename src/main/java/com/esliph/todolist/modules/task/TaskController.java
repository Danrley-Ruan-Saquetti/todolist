package com.esliph.todolist.modules.task;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.esliph.todolist.modules.task.UseCase.TaskCreateUseCase;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskCreateUseCase taskCreate;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody TaskModelSimple taskModel) {
        var result = this.taskCreate.perform(taskModel);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }
}