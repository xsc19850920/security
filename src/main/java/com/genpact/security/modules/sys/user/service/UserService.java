package com.genpact.security.modules.sys.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.genpact.security.modules.sys.user.dao.UserRepository;
import com.genpact.security.modules.sys.user.entity.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public User getUserById(int id) {
		return userRepository.findOne(id);
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find the user '" + username + "'");
		}
		return user;
	}
}
