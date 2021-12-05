package com.user.controller;

import java.util.List;

import com.user.config.JwtTokenUtil;
import com.user.dto.UserRequest;
import com.user.dto.UserToken;
import com.user.model.Users;
import com.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping()
	public List<Users> findAll() {
		return service.findAll();
	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {
		return service.create(userRequest);
	}

	@PostMapping("/login")
	public UserToken login(@RequestParam("user") String email, @RequestParam("password") String pwd) throws Exception {
		try {
			service.authenticate(email, pwd);
		} catch (Exception e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		String token = jwtTokenUtil.getJWTToken(email);
		UserToken userToken = new UserToken();
		userToken.setUser(email);
		userToken.setToken(token);
		return userToken;
	}

}
