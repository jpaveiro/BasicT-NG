package com.gjv.basicTapi.usecase;

import com.gjv.basicTapi.dto.UserRequestDto;
import com.gjv.basicTapi.model.StandardResponse;
import com.gjv.basicTapi.repository.UserRepository;
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
        StandardResponse response = StandardResponse.builder()
                .message("Sucess: User registered.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
