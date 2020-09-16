package com.lambdaschool.shoppingcart.repositories;

import com.lambdaschool.shoppingcart.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository
        extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
