package com.esliph.todolist.modules.user;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
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
    private IUserRepository userRepository;
    @Autowired
    private UserCreateUseCase userCreate;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModelSimple userModel) {
        var result = this.userCreate.perform(userModel);

        return ResponseEntity.status(result.getStatus()).body(result.getResponse());
    }

    @GetMapping("")
    public ResponseEntity findAll() {
        var users = this.userRepository.findAll();

        users.forEach(user -> {
            user.setPassword(null);
        });

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}