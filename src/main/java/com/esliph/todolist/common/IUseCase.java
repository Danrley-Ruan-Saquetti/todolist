package com.esliph.todolist.common;

import com.esliph.todolist.services.Result;

public interface IUseCase<Args, ResultType> {
    Result<ResultType> perform(Args args);
}
