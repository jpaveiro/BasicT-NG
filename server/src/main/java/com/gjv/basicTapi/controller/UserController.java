package com.gjv.basicTapi.controller;

import com.gjv.basicTapi.dto.UserRequestDto;
import com.gjv.basicTapi.usecase.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/v1/set")
    public ResponseEntity<?> setUser(@RequestBody UserRequestDto request)
    {
        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = userService.setUser(request);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        LOGGER.info("Elapsed time: " + elapsedTime + " milisseconds.");
        return response;
    }
}
