package com.esliph.todolist.modules.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.esliph.todolist.common.IUseCase;
import com.esliph.todolist.modules.user.IUserRepository;
import com.esliph.todolist.modules.user.UserModel;
import com.esliph.todolist.modules.user.UserModelSimple;
import com.esliph.todolist.services.CryptoPassword;
import com.esliph.todolist.services.ErrorResult;
import com.esliph.todolist.services.Result;

@Component
public class UserCreateUseCase implements IUseCase<UserModelSimple, String> {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Result<String> perform(UserModelSimple args) {
        var userWithSameUsername = this.userRepository.findByUsername(args.getUsername());

        if (userWithSameUsername != null) {
            return Result.failure(
                    new ErrorResult().title("Create User")
                            .message("Already exists user with username \"" + args.getUsername() + "\"")
                            .description("you cannot inform username already in use. Please, try again")
                            .causes("Username already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        var userInstance = new UserModel();

        var passwordHashed = CryptoPassword.hashPassword(args.getUsername());

        userInstance.setPassword(passwordHashed);
        userInstance.setName(args.getName());
        userInstance.setUsername(args.getUsername());

        var userCreated = this.userRepository.save(userInstance);

        return Result.success("User created with successfully", HttpStatus.CREATED);
    }
}
