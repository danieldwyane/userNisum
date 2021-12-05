package com.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.user.config.JwtTokenUtil;
import com.user.dto.UserRequest;
import com.user.model.Phone;
import com.user.model.Users;
import com.user.repository.UserRepository;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @InjectMocks
	private UserService userService = new UserService();

	@Mock
	private UserRepository rep;

    @Mock
	private JwtTokenUtil jwtTokenUtil;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private AuthenticationManager authenticationManager;

    @Test
	public void findAllTest() throws Exception {
		List<Users> respExp = new ArrayList<>();
        Users users = new Users();
        users.setId(UUID.randomUUID());
        Mockito.when(rep.findAll()).thenReturn(respExp);
		List<Users> respRet = userService.findAll();
		Assert.assertEquals(respExp, respRet);
	}

    @Test
	public void createTest() throws Exception {
		UserRequest userRequest = new UserRequest();
        userRequest.setEmail("jose@gmail.com");
		userRequest.setPassword("Fede1245$$");
		List<Phone> phoneList = new ArrayList<>();
		Phone phone = new Phone();
		phone.setCitycode("57");
		phone.setCountrycode("57");
		phone.setNumber("65045454");
		phoneList.add(phone);
		userRequest.setPhones(phoneList);
		ReflectionTestUtils.setField(userService, "regexEmail", "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		ReflectionTestUtils.setField(userService, "regexPassword", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
		ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
		ResponseEntity<?> respRet = userService.create(userRequest);
		Assert.assertEquals(HttpStatus.CREATED.value(), respRet.getStatusCodeValue());
	}

	@Test
	public void createTest_EmailExist() throws Exception {
		UserRequest userRequest = new UserRequest();
        userRequest.setEmail("jose@gmail.com");
		userRequest.setPassword("Fede1245$$");
		List<Phone> phoneList = new ArrayList<>();
		Phone phone = new Phone();
		phone.setCitycode("57");
		phone.setCountrycode("57");
		phone.setNumber("65045454");
		phoneList.add(phone);
		userRequest.setPhones(phoneList);
		List<Users> userEmailExist = new ArrayList<>();
		Users users = new Users();
		users.setEmail("jose@gmail.com");
		userEmailExist.add(users);
		ReflectionTestUtils.setField(userService, "regexEmail", "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		ReflectionTestUtils.setField(userService, "regexPassword", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
		ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
		Mockito.when(rep.findByEmail(userRequest.getEmail())).thenReturn(userEmailExist);
		ResponseEntity<?> respRet = userService.create(userRequest);
		Assert.assertEquals(HttpStatus.CONFLICT.value(), respRet.getStatusCodeValue());
	}

	@Test
	public void createTest_EmailInvalid() throws Exception {
		UserRequest userRequest = new UserRequest();
        userRequest.setEmail("josegmail.com");
		userRequest.setPassword("Fede1245$$");
		List<Phone> phoneList = new ArrayList<>();
		Phone phone = new Phone();
		phone.setCitycode("57");
		phone.setCountrycode("57");
		phone.setNumber("65045454");
		phoneList.add(phone);
		userRequest.setPhones(phoneList);
		ReflectionTestUtils.setField(userService, "regexEmail", "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		ReflectionTestUtils.setField(userService, "regexPassword", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
		ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
		ResponseEntity<?> respRet = userService.create(userRequest);
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), respRet.getStatusCodeValue());
	}

	@Test
	public void createTest_PasswordInvalid() throws Exception {
		UserRequest userRequest = new UserRequest();
        userRequest.setEmail("jose@gmail.com");
		userRequest.setPassword("sede1245$$");
		List<Phone> phoneList = new ArrayList<>();
		Phone phone = new Phone();
		phone.setCitycode("57");
		phone.setCountrycode("57");
		phone.setNumber("65045454");
		phoneList.add(phone);
		userRequest.setPhones(phoneList);
		ReflectionTestUtils.setField(userService, "regexEmail", "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
		ReflectionTestUtils.setField(userService, "regexPassword", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
		ReflectionTestUtils.setField(userService, "passwordEncoder", passwordEncoder);
		ResponseEntity<?> respRet = userService.create(userRequest);
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), respRet.getStatusCodeValue());
	}

    @Test
	public void authenticateTest() throws Exception {
        String email = "jose@gmail.com";
        String password = "123456";
		ReflectionTestUtils.setField(userService, "authenticationManager", authenticationManager);
		userService.authenticate(email, password);
	}
}
