package com.bank.controller;


import com.bank.config.jwt.JwtUtils;
import com.bank.dao.UserDAO;
import com.bank.model.Role;
import com.bank.model.User;
import com.bank.model.UserRole;
import com.bank.payload.request.LoginForm;
import com.bank.payload.request.SignUpForm;
import com.bank.payload.response.ResponseLogin;
import com.bank.payload.response.ResponseMessage;
import com.bank.repository.RoleRepo;
import com.bank.repository.UserRepo;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000" , allowedHeaders = "*")
@RestController
@RequestMapping(path = "/auth")
public class LoginController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseMessage> register( @Valid  @RequestBody SignUpForm signUpForm) {
        ResponseMessage response = new ResponseMessage();

        if(userRepo.existsByUsername(signUpForm.getUsername())) {
            response.setMessage("Error: " + signUpForm.getUsername() +" Username already taken");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(userRepo.existsByEmail(signUpForm.getEmail())) {
            response.setMessage("Error: " + signUpForm.getEmail() +" email already taken");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String password = passwordEncoder.encode(signUpForm.getPassword());
        User user = new User(signUpForm.getUsername(),password,signUpForm.getFirstname(),signUpForm.getLastname(), signUpForm.getEmail());

        Set<String> stringRoles = signUpForm.getRoles();
        Set<UserRole> userRoles = new HashSet<>();

        stringRoles.forEach(roleName -> {
            Role role = roleRepo.findByName(roleName).orElseThrow(() -> new RuntimeException("Error: Role not found"));
            userRoles.add(new UserRole(user,role));
        });

        user.setUserRoles(userRoles);
        user.setAccount(accountService.createAccount()); // kullanicinin hesabini olusturup ekledik

        userRepo.save(user);

        response.setMessage("User registered successfully");
        response.setSuccess(true);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ResponseLogin> login(@Valid @RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        Authentication authentication = authenticationManager
                                                    .authenticate(new UsernamePasswordAuthenticationToken(username,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        UserDAO userDAO = userService.getUserDAO(user);

        return ResponseEntity.ok(new ResponseLogin(userDAO, jwt));
    }


}
