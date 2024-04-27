package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.EditRequestDto;
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
import com.gjv.basicTapi.dto.DeleteUserRequestDto;
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
                    .message("Error: The system was unable to register the user. Probably user already registered.")
                    .build();
            LOGGER.info("Error: The system was unable to register the user.\nDetails: " + e.getMessage());
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
    public ResponseEntity<?> editUser(EditRequestDto request)
    {
        if (request.getName() == null || request.getCellphone() == null ||
        request.getEmail() == null || request.getCpf() == null ||
        request.getRg() == null || request.getPassword() == null || request.getId() == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: You must fill in all fields.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String id = request.getId();
        String name = Utils.checkName(request.getName());
        String cellphone = Utils.formatPhone(request.getCellphone());
        String email = request.getEmail();
        String cpf = Utils.checkCpf(request.getCpf());
        String rg = Utils.checkRg(request.getRg());
        String password = Utils.hashPassword(request.getPassword());

        try {
            userRepository.editUser(name, cellphone, email, cpf, rg, password, id);

            StandardResponse response = StandardResponse.builder()
                .message("Sucess: User has been edited.")
                .build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            StandardResponse response = StandardResponse.builder()
                .message("Error: User can't be edited.")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    public ResponseEntity<?> deleteUser(DeleteUserRequestDto request)
    {
        String id = request.getIdUser();
        String emailAdmin = request.getEmailAdmin();
        String password = Utils.hashPassword(request.getPasswordAdmin());

        User user = userRepository.getUser(request.getIdUser());

        ResponseEntity<?> responseError = Utils.validateField("user", user);
        if (responseError != null) {
            return responseError;
        }

        userRepository.deleteUser(id);

        StandardResponse response = StandardResponse.builder()
                .message("Sucess: User has been deleted.")
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
