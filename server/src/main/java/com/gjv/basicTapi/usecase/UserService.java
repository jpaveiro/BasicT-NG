package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.UserRequestDto;
import com.gjv.basicTapi.exception.UserUnderLegalAgeException;
import com.gjv.basicTapi.model.StandardResponse;
import com.gjv.basicTapi.model.User;
import com.gjv.basicTapi.model.UserResponse;
import com.gjv.basicTapi.repository.UserRepository;
import com.gjv.basicTapi.utils.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Registra um novo usuário no sistema com base nos dados fornecidos.
     *
     * @param request Um objeto UserRequestDto contendo as informações do novo usuário a ser registrado.
     * @return ResponseEntity indicando que o usuário foi registrado com sucesso ou uma resposta de erro se ocorrer algum problema durante o registro.
     */
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
        catch (UserUnderLegalAgeException e)
        {
            return Utils.generateStandardResponseEntity("Error: User must be over 18 years old.", HttpStatus.NOT_ACCEPTABLE);
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
        catch (DataIntegrityViolationException e)
        {
            return Utils.generateStandardResponseEntity("Error: The user is already registered", HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e)
        {
            LOGGER.info("Error: The system was unable to register the user.\nDetails: {}", e.getMessage());
            return Utils.generateStandardResponseEntity("Error: The system was unable to register the user. Probably user already registered.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Autentica um usuário no sistema com base nas credenciais fornecidas.
     *
     * @param request Um objeto LoginRequestDto contendo o email e senha do usuário para autenticação.
     * @return ResponseEntity indicando que o usuário foi autenticado com sucesso e fornecendo informações do usuário, incluindo tokens de acesso, ou uma resposta de erro se as credenciais estiverem incorretas.
     */
    public ResponseEntity<?> login(UserRequestDto request)
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

        if (user == null)
        {
            return Utils.generateStandardResponseEntity("Error: Incorrect informations are provided.", HttpStatus.UNAUTHORIZED);
        }

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

    /**
     * Edita as informações de um usuário no sistema com base nos dados fornecidos.
     *
     * @param request Um objeto EditRequestDto contendo as informações a serem editadas do usuário.
     * @return ResponseEntity indicando que as informações do usuário foram editadas com sucesso ou uma resposta de erro se ocorrer algum problema durante a edição.
     */
    public ResponseEntity<?> editUser(UserRequestDto request) {

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
        fieldsToValidate.put("idUser", request.getIdUser());

        for (Map.Entry<String, String> entry : fieldsToValidate.entrySet()) {
            ResponseEntity<StandardResponse> responseError = Utils.validateField(entry.getKey(), entry.getValue());
            if (responseError != null) {
                return responseError;
            }
        }

        User user = userRepository.getUser(request.getIdUser());
        if (user == null) {
            return Utils.generateStandardResponseEntity("Error: User does not exist.", HttpStatus.NOT_FOUND);
        }

        try {
            userRepository.editUser(name, cellphone, email, cpf, rg, password, request.getIdUser());

            return Utils.generateStandardResponseEntity("Success: User edited.", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("Error: The system was unable to edit the user.\nDetails: {}", e.getMessage());
            return Utils.generateStandardResponseEntity("Error: The system was unable to edit the user. Verify if users exists.", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Remove um usuário do sistema com base no ID fornecido.
     *
     * @param request Um objeto DeleteUserRequestDto contendo o ID do usuário a ser removido.
     * @return ResponseEntity indicando que o usuário foi removido com sucesso ou uma resposta de erro se o usuário não for encontrado.
     */
    public ResponseEntity<?> deleteUser(UserRequestDto request)
    {
        User user = userRepository.getUser(request.getIdUser());

        ResponseEntity<?> responseError = Utils.validateField("user", user);
        if (responseError != null) {
            return responseError;
        }

        userRepository.deleteUser(request.getIdUser());

        return Utils.generateStandardResponseEntity("Success: User has been deleted.", HttpStatus.FOUND);
    }

    /**
     * Retorna o nome do usuário com base no ID fornecido.
     *
     * @param id O ID do usuário.
     * @return ResponseEntity contendo o nome do usuário, ou uma resposta de erro se o usuário não for encontrado.
     */
    public ResponseEntity<?> getUserName(String id) {
        User user = userRepository.getUser(id);

        if (user == null)
        {
            return Utils.generateStandardResponseEntity("Error: User does not exist.", HttpStatus.NOT_FOUND);
        }

        UserResponse response = UserResponse.builder()
                .name(user.getName())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
