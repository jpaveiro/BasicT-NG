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
import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<?> setUser(UserRequestDto request)
    {

        String id, name, cellphone, email, cpf, rg, stateRg, password;
        Date birthDate;

        try {
            id = Utils.generateId();
            name = Utils.checkName(request.getName());
            cellphone = Utils.formatPhone(request.getCellphone());
            email = request.getEmail();
            cpf = Utils.checkCpf(request.getCpf());
            rg = Utils.checkRg(request.getRg());
            stateRg = request.getStateRg();
            birthDate = Utils.checkBirthDate(request.getBirthDate());
            password = Utils.hashPassword(request.getPassword());
        }
        catch (NullPointerException e)
        {
            return Utils.generateStandardResponseEntity("Error: You must fill all fields.", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> fieldsToValidate = new HashMap<>();
        fieldsToValidate.put("name", name);
        fieldsToValidate.put("cellphone", cellphone);
        fieldsToValidate.put("email", email);
        fieldsToValidate.put("cpf", cpf);
        fieldsToValidate.put("rg", rg);
        fieldsToValidate.put("birthDate", Objects.toString(birthDate));

        for (Map.Entry<String, String> entry : fieldsToValidate.entrySet()) {
            ResponseEntity<StandardResponse> responseError = Utils.validateField(entry.getKey(), entry.getValue());
            if (responseError != null) {
                return responseError;
            }
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

            return Utils.generateStandardResponseEntity("Success: User registered.", HttpStatus.OK);
        }
        catch (Exception e)
        {
            LOGGER.info("Error: The system was unable to register the user.\nDetails: {}", e.getMessage());
            return Utils.generateStandardResponseEntity("Error: The system was unable to register the user. Probably user already registered.", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> login(LoginRequestDto request)
    {
        String emailEntered = request.getEmail();
        String passwordEntered = Utils.hashPassword(request.getPassword());

        Map<String, String> fieldsToValidate = new HashMap<>();
        fieldsToValidate.put("email", emailEntered);
        fieldsToValidate.put("password", passwordEntered);

        for (Map.Entry<String, String> entry : fieldsToValidate.entrySet()) {
            ResponseEntity<StandardResponse> responseError = Utils.validateField(entry.getKey(), entry.getValue());
            if (responseError != null) {
                return responseError;
            }
        }

        User user = userRepository.getUser(emailEntered, passwordEntered);

        if (user == null || !emailEntered.equals(user.getEmail()) || !passwordEntered.equals(user.getPassword()))
        {
            return Utils.generateStandardResponseEntity("Error: Incorrect informations are provided.", HttpStatus.UNAUTHORIZED);
        }
        // Gera 10 tokens e os armazena num array chamado TokenList
        List<String> tokenList = new ArrayList<>();
        for (int c = 0; c < 9; c++) {
            String token = Utils.generateToken();
            tokenList.add(token);
        }

        UserResponse response = UserResponse.builder()
                .token(tokenList)
                .name(user.getName())
                .userId(user.getId())
                .build();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public ResponseEntity<?> editUser(EditRequestDto request) {

        String name, cellphone, email, cpf, rg, password;

        try {
            name = Utils.checkName(request.getName());
            cellphone = Utils.formatPhone(request.getCellphone());
            email = request.getEmail();
            cpf = Utils.checkCpf(request.getCpf());
            rg = Utils.checkRg(request.getRg());
            password = Utils.hashPassword(request.getPassword());
        } catch (NullPointerException e) {
            return Utils.generateStandardResponseEntity("Error: You must fill all fields.", HttpStatus.BAD_REQUEST);
        }

        Map<String, String> fieldsToValidate = new HashMap<>();
        fieldsToValidate.put("name", name);
        fieldsToValidate.put("cellphone", cellphone);
        fieldsToValidate.put("email", email);
        fieldsToValidate.put("cpf", cpf);
        fieldsToValidate.put("rg", rg);
        fieldsToValidate.put("id", request.getId());

        for (Map.Entry<String, String> entry : fieldsToValidate.entrySet()) {
            ResponseEntity<StandardResponse> responseError = Utils.validateField(entry.getKey(), entry.getValue());
            if (responseError != null) {
                return responseError;
            }
        }

        User user = userRepository.getUser(request.getId());
        if (user == null) {
            return Utils.generateStandardResponseEntity("Error: User does not exist.", HttpStatus.NOT_FOUND);
        }

        try {
            userRepository.editUser(name, cellphone, email, cpf, rg, password, request.getId());

            return Utils.generateStandardResponseEntity("Success: User edited.", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Error: The system was unable to edit the user.\nDetails: {}", e.getMessage());
            return Utils.generateStandardResponseEntity("Error: The system was unable to edit the user. Verify if users exists.", HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> deleteUser(DeleteUserRequestDto request)
    {
        User user = userRepository.getUser(request.getIdUser());

        ResponseEntity<?> responseError = Utils.validateField("user", user);
        if (responseError != null) {
            return responseError;
        }

        userRepository.deleteUser(request.getIdUser());

        return Utils.generateStandardResponseEntity("Success: User has been deleted.", HttpStatus.FOUND);
    }

    public ResponseEntity<?> getUserName(String id) {
        User user = userRepository.getUser(id);

        ResponseEntity<?> responseError = Utils.validateField("user", user);
        if (responseError != null) {
            return responseError;
        }

        UserResponse response = UserResponse.builder()
                .name(user.getName())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
