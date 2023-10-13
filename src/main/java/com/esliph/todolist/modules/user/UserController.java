package com.esliph.todolist.modules.user;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.esliph.todolist.modules.user.UseCase.UserCreateUseCase;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserCreateUseCase userCreate;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody UserModelSimple userModel) {
        var result = this.userCreate.perform(userModel);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }
}