package com.apinizer.example.rest;


import com.apinizer.example.service.CustomErrorType;
import com.apinizer.example.service.User;
import com.apinizer.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    UserService userService; //Service which will do all data retrieval/manipulation work

    // -------------------Retrieve All Users---------------------------------------------

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    // -------------------Retrieve Single User------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getByIdPathParam(@PathVariable("id") long id) {
        return getById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> getByIdQueryParam(@RequestParam("id") long id) {
        return getById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> getByIdBodyParam(@RequestBody long id) {
        return deleteById(id);
    }

    private ResponseEntity<?> getById(long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("User with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    // -------------------Create a User-------------------------------------------

    @RequestMapping(value = "/user/{id}/{name}/{age}/{salary}/{date}", method = RequestMethod.POST)
    public ResponseEntity<?> createByPathParam(@RequestParam("id") long id, @RequestParam("name") String name, @RequestParam("age") int age,
                                               @RequestParam("salary") double salary, @RequestParam("date") Date date, UriComponentsBuilder ucBuilder) {
        User user = new User(id, name, age, salary, date);
        return createUser(ucBuilder, user);
    }

    @RequestMapping(value = "/userByQueryParam", method = RequestMethod.POST)
    public ResponseEntity<?> createByQueryParam(@PathVariable("id") long id, @PathVariable("name") String name, @PathVariable("age") int age,
                                                @PathVariable("salary") double salary, @PathVariable("date") Date date, UriComponentsBuilder ucBuilder) {
        User user = new User(id, name, age, salary, date);
        return createUser(ucBuilder, user);
    }


    @RequestMapping(value = "/userByBody", method = RequestMethod.POST)
    public ResponseEntity<?> createByBody(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        return createUser(ucBuilder, user);
    }

    private ResponseEntity<?> createUser(UriComponentsBuilder ucBuilder, User user) {
        if (userService.isUserExist(user)) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // ------------------- Update a User ------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateByPathParam(@RequestParam("id") long id, @RequestParam("name") String name, @RequestParam("age") int age,
                                               @RequestParam("salary") double salary, @RequestParam("date") Date date) {
        User user = new User(id, name, age, salary, date);
        return updateUser(id, user);
    }

    @RequestMapping(value = "/userByQueryParam", method = RequestMethod.PUT)
    public ResponseEntity<?> updateByQueryParam(@PathVariable("id") long id, @PathVariable("name") String name, @PathVariable("age") int age,
                                                @PathVariable("salary") double salary, @PathVariable("date") Date date) {
        User user = new User(id, name, age, salary, date);
        return updateUser(id, user);
    }

    @RequestMapping(value = "/userByBody", method = RequestMethod.PUT)
    public ResponseEntity<?> updateByBody(@RequestBody User user) {
        return updateUser(user.getId(), user);
    }

    private ResponseEntity<?> updateUser(long id, User user) {
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
        currentUser.setDate(user.getDate());

        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    // ------------------- Delete a User-----------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByIdPathParam(@PathVariable("id") long id) {
        return deleteById(id);
    }

    @RequestMapping(value = "/userByIdQueryParam", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByIdQueryParam(@RequestParam("id") long id) {
        return deleteById(id);
    }

    @RequestMapping(value = "/userByIdBodyParam", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByIdBodyParam(@RequestBody long id) {
        return deleteById(id);
    }

    private ResponseEntity<?> deleteById(long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Users-----------------------------

    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
