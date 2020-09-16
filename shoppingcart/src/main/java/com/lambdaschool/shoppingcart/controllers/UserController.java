package com.lambdaschool.shoppingcart.controllers;

import com.lambdaschool.shoppingcart.models.User;
import com.lambdaschool.shoppingcart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/{userId}",
            produces = {"application/json"})
    public ResponseEntity<?> getUserById(
            @PathVariable
                    Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/user", consumes = {"application/json"})
    public ResponseEntity<?> addUser(@Valid @RequestBody User newuser)
    {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/user/{userId}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    Long userId)
    {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/users/myinfo
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/myinfo", produces = {"application/json"})
    public ResponseEntity<?> findCurrentUser(Authentication auth)
    {
        User currentUser = userService.findUserById(userService.findByName(auth.getName()).getUserid());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}
