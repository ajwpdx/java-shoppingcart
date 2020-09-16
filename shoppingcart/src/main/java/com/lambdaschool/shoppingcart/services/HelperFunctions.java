package com.lambdaschool.shoppingcart.services;

import com.lambdaschool.shoppingcart.models.ValidationError;

import java.util.List;

public interface HelperFunctions
{
    List<ValidationError> getConstraintViolation(Throwable cause);

    boolean isAuthorizedToMakeChange(String username);

}
