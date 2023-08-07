package com.example.userservice.service;

import com.example.userservice.dto.APIResponsDto;
import com.example.userservice.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserDto saveUser(UserDto userDto);
    APIResponsDto getUserBySteamId(String steamId);

    ResponseEntity<UserDto> loginUser(String email, String password);
}
