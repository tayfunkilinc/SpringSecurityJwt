package com.tpe.controller;

import com.tpe.dto.LoginUser;
import com.tpe.dto.UserDTO;
import com.tpe.security.JWTUtils;
import com.tpe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;


    //userı kaydetme:register
    //request:http://localhost:8080/register + POST + body
    //response:mesaj+201
    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO){

        userService.saveUser(userDTO);

        return new ResponseEntity<>("User is registered successfully...", HttpStatus.CREATED);
    }

    //localhost:8080/login + JSON + POST
    //response:token
    @RequestMapping("/login")
    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody LoginUser loginUser){

        //manager securitynin default filtresini kullanarak
        //username ve password doğrulamasını yapar
        //eğer geçerli ise geriye authenticate olmuş kullanıcıyı
        //authentication nesnesi olarak döndürür
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUserName(),loginUser.getPassword()));

        String token= jwtUtils.generateToken(authentication);
        return new ResponseEntity<>(token,HttpStatus.CREATED);//201
    }


    //örnek bir servis yazalım
    //localhost:8080/goodbye + GET
    @RequestMapping("/goodbye")
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    public ResponseEntity<String> goodbye(){
        return ResponseEntity.ok("Goodbye Spring Security:)");
    }
}