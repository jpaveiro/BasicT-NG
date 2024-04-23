package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.LoginRequestDto;
import com.gjv.basicTapi.dto.UserRequestDto;
import com.gjv.basicTapi.model.StandardResponse;
import com.gjv.basicTapi.model.User;
import com.gjv.basicTapi.model.UserResponse;
import com.gjv.basicTapi.repository.UserRepository;
import com.gjv.basicTapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> setUser(UserRequestDto request)
    {
        if (request.getName() == null || request.getCellphone() == null ||
        request.getEmail() == null || request.getCpf() == null ||
        request.getRg() == null || request.getPassword() == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("Error: You must fill in all fields.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String id = Utils.generateId();
        String name = Utils.checkName(request.getName());
        String cellphone = request.getCellphone();
        String email = request.getEmail();
        String cpf = Utils.checkCpf(request.getCpf());
        String rg = Utils.checkRg(request.getRg());
        String password = Utils.hashPassword(request.getPassword());

        if (name == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("The entered name field is invalid.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (cellphone == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("The entered cellphone field is invalid.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (email == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("The entered email field is invalid.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (cpf == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("The entered cpf field is invalid.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (rg == null)
        {
            StandardResponse response = StandardResponse.builder()
                    .message("The entered rg field is invalid.")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        userRepository.setUser(
                id,
                name,
                cellphone,
                email,
                cpf,
                rg,
                password
        );

        StandardResponse response = StandardResponse.builder()
                .message("Sucess: User registered.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
