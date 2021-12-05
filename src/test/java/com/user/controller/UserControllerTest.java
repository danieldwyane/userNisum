package com.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.user.config.JwtTokenUtil;
import com.user.dto.UserRequest;
import com.user.dto.UserToken;
import com.user.model.Users;
import com.user.service.UserService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @InjectMocks
	private UserController userController = new UserController();

    @Mock
	private UserService userService = new UserService();

    @Mock
	private JwtTokenUtil jwtTokenUtil;

    @Test
	public void findAllTest() throws Exception {
		List<Users> respExp = new ArrayList<>();
        Users users = new Users();
        users.setId(UUID.randomUUID());
        Mockito.when(userService.findAll()).thenReturn(respExp);
		List<Users> respRet = userController.findAll();
		Assert.assertEquals(respExp, respRet);
	}

    @Test
	public void createTest() throws Exception {
		UserRequest userRequest = new UserRequest();
        userRequest.setEmail("jose@gmail.com");
        Mockito.when(userService.create(userRequest)).thenReturn(new ResponseEntity(userRequest, HttpStatus.CREATED));
		ResponseEntity<?> respRet = userController.create(userRequest);
		Assert.assertEquals(HttpStatus.CREATED.value(), respRet.getStatusCodeValue());
	}

    @Test
	public void loginTest() throws Exception {
        String email = "jose@gmail.com";
        String password = "123456";
		UserToken respRet = userController.login(email, password);
		Assert.assertEquals(email, respRet.getUser());
	}
}
