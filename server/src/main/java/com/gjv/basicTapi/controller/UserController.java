package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.dto.DeleteUserRequestDto;
import com.gjv.basicTapi.dto.EditRequestDto;
import com.gjv.basicTapi.dto.LoginRequestDto;
import com.gjv.basicTapi.dto.UserRequestDto;
import com.gjv.basicTapi.usecase.UserService;
import com.gjv.basicTapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/v1/set")
    public ResponseEntity<?> setUser(@RequestBody UserRequestDto request) {
        return Utils.executeAndLogElapsedTime(() -> userService.setUser(request), LOGGER);
    }

    @PostMapping("/v1/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        return Utils.executeAndLogElapsedTime(() -> userService.login(request), LOGGER);
    }

    @PutMapping("/v1/edit")
    public ResponseEntity<?> editUser(@RequestBody EditRequestDto request) {
        return Utils.executeAndLogElapsedTime(() -> userService.editUser(request), LOGGER);
    }

    @DeleteMapping("/v1/delete")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequestDto request) {
        return Utils.executeAndLogElapsedTime(() -> userService.deleteUser(request), LOGGER);
    }

    @GetMapping("/v1/get")
    public ResponseEntity<?> getUser(@RequestParam("id") String id) {
        return Utils.executeAndLogElapsedTime(() -> userService.getUserName(id), LOGGER);
    }
}
