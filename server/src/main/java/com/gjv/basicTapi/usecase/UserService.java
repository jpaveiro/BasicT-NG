package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.controller.UserController;
import com.gjv.basicTapi.dto.LoginRequestDto;
import com.gjv.basicTapi.dto.UserRequestDto;
import com.gjv.basicTapi.model.StandardResponse;
import com.gjv.basicTapi.model.User;
import com.gjv.basicTapi.model.UserResponse;
import com.gjv.basicTapi.repository.UserRepository;
import com.gjv.basicTapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<?> setUser(UserRequestDto request)
    {
        if (request.getName() == null || request.getCellphone() == null ||
        request.getEmail() == null || request.getCpf() == null ||
        request.getStateRg() == null || request.getBirthDate() == null ||
        request.getRg() == null || request.getPassword() == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: You must fill in all fields.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String id = Utils.generateId();
        String name = Utils.checkName(request.getName());
        String cellphone = Utils.formatPhone(request.getCellphone());
        String email = request.getEmail();
        String cpf = Utils.checkCpf(request.getCpf());
        String rg = Utils.checkRg(request.getRg());
        String stateRg = request.getStateRg();
        Date birthDate = Utils.checkBirthDate(request.getBirthDate());
        String password = Utils.hashPassword(request.getPassword());

        ResponseEntity<StandardResponse> responseError;

        responseError = Utils.validateField("name", name);
        if (responseError != null) {
            return responseError;
        }

        responseError = Utils.validateField("cellphone", cellphone);
        if (responseError != null) {
            return responseError;
        }

        responseError = Utils.validateField("email", email);
        if (responseError != null) {
            return responseError;
        }

        responseError = Utils.validateField("cpf", cpf);
        if (responseError != null) {
            return responseError;
        }

        responseError = Utils.validateField("rg", rg);
        if (responseError != null) {
            return responseError;
        }

        responseError = Utils.validateField("birthDate", birthDate);
        if (responseError != null) {
            return responseError;
        }

        try {
            userRepository.setUser(
                    id,
                    name,
                    cellphone,
                    email,
                    cpf,
                    rg,
                    stateRg,
                    birthDate,
                    password
            );

            StandardResponse response = StandardResponse.builder()
                    .message("Sucess: User registered.")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: User alredy registered.")
                    .build();
            LOGGER.info("Error: User alredy registered.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    public ResponseEntity<?> login(LoginRequestDto request)
    {
        if (request.getEmail() == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: The entered email field is invalid.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (request.getPassword() == null) {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: The entered password field is invalid.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String emailEntered = request.getEmail();
        String passwordEntered = Utils.hashPassword(request.getPassword());

        User user = userRepository.getUser(emailEntered, passwordEntered);

        if (user == null || !emailEntered.equals(user.getEmail()) || !passwordEntered.equals(user.getPassword()))
        {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: Incorrect informations are provided.")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = Utils.generateToken();

        UserResponse response = UserResponse.builder()
                .token(token)
                .name(user.getName())
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
