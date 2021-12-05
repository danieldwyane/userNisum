package com.user.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.user.config.JwtTokenUtil;
import com.user.dto.UserRequest;
import com.user.exception.UserException;
import com.user.model.Phone;
import com.user.model.Users;
import com.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository rep;

	@Value("${regex.email}")
	private String regexEmail;

	@Value("${regex.password}")
	private String regexPassword;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public List<Users> findAll() {
		return rep.findAll();
	}

	public ResponseEntity<?> create(UserRequest userRequest) {
		List<Users> userEmailExist = rep.findByEmail(userRequest.getEmail());
		if (!userEmailExist.isEmpty())
			return new ResponseEntity<>(new UserException("El correo ya está registrado"), HttpStatus.CONFLICT);
		if (!validateRegex(userRequest.getEmail(), regexEmail))
			return new ResponseEntity<>(new UserException("La dirección de correo no es válido"),
					HttpStatus.BAD_REQUEST);
		if (!validateRegex(userRequest.getPassword(), regexPassword))
			return new ResponseEntity<>(new UserException(
					"El password debe tener un dígito, una minúscula, una mayúscula, no tener espacios en blanco, un caracter especial y al menos 8 caracteres"),
					HttpStatus.BAD_REQUEST);
		Users userNew = rep.save(cloneToUser(userRequest));
		return new ResponseEntity<>(userNew, HttpStatus.CREATED);
	}

	public Users cloneToUser(UserRequest userRequest) {
		Users user = new Users();
		user.setId(UUID.randomUUID());
		user.setEmail(userRequest.getEmail());
		user.setName(userRequest.getName());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setPhones(userRequest.getPhones());
		user.setCreated(new Date());
		user.setLastLogin(new Date());
		user.setIsActive(true);
		user.setToken(jwtTokenUtil.getJWTToken(userRequest.getEmail()));
		for (Phone phone : user.getPhones()) {
			phone.setUser(user);
		}
		return user;
	}

	public Boolean validateRegex(String value, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	public void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}