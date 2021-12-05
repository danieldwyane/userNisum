package com.user.config;

import java.util.ArrayList;
import java.util.List;

import com.user.model.Users;
import com.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository rep;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<Users> user = rep.findByEmail(email);
		if (!user.isEmpty()) {
			return new User(user.get(0).getEmail(), user.get(0).getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}

}