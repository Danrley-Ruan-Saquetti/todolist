package com.esliph.todolist.modules.task;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.esliph.todolist.modules.task.UseCase.TaskCreateUseCase;
import com.esliph.todolist.modules.task.UseCase.TaskListUseCase;
import com.esliph.todolist.modules.task.UseCase.TaskUpdateUseCase;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskCreateUseCase taskCreate;
    @Autowired
    private TaskListUseCase taskList;
    @Autowired
    private TaskUpdateUseCase taskUpdate;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody TaskModelSimple taskModel, HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        taskModel.setUserId((int) userId);

        var result = this.taskCreate.perform(taskModel);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }

    @GetMapping("")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        var result = this.taskList.perform((int) userId);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody TaskModel taskModel, @PathVariable int id,
            HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        taskModel.setUserId((int) userId);
        taskModel.setId(id);

        var result = this.taskUpdate.perform(taskModel);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }
}