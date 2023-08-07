package com.example.userservice.controller;

import com.example.userservice.dto.APIResponsDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("{steam-id}")
    public ResponseEntity<APIResponsDto> getUser(@PathVariable("steam-id") String steamId) {
        APIResponsDto apiResponsDto = userService.getUserBySteamId(steamId);
        return new ResponseEntity<>(apiResponsDto, HttpStatus.OK);
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<UserDto> loginUser(@PathVariable String email, @PathVariable String password) {
        return userService.loginUser(email,password);
    }
}
