package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.dto.DeleteUserRequestDto;
import com.gjv.basicTapi.dto.EditRequestDto;
import com.gjv.basicTapi.dto.LoginRequestDto;
import com.gjv.basicTapi.dto.UserRequestDto;
import com.gjv.basicTapi.usecase.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private ResponseEntity<?> executeAndLogElapsedTime(Supplier<ResponseEntity<?>> serviceMethod) {
        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = serviceMethod.get();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time: {} milliseconds.", elapsedTime);
        return response;
    }

    @PostMapping("/v1/set")
    public ResponseEntity<?> setUser(@RequestBody UserRequestDto request) {
        return executeAndLogElapsedTime(() -> userService.setUser(request));
    }

    @PostMapping("/v1/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        return executeAndLogElapsedTime(() -> userService.login(request));
    }

    @PutMapping("/v1/edit")
    public ResponseEntity<?> editUser(@RequestBody EditRequestDto request) {
        return executeAndLogElapsedTime(() -> userService.editUser(request));
    }

    @DeleteMapping("/v1/delete")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteUserRequestDto request) {
        return executeAndLogElapsedTime(() -> userService.deleteUser(request));
    }

    @GetMapping("/v1/get/")
    public ResponseEntity<?> getUser(@RequestParam("id") String id) {
        return executeAndLogElapsedTime(() -> userService.getUserName(id));
    }
}
