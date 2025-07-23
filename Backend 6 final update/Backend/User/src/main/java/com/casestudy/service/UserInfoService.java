package com.casestudy.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService{

	@Autowired
	UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel availUser=userrepo.findByUsername(username);
		if (availUser==null) {
			return null;
		}
		String user=availUser.getUsername();
		String pass=availUser.getPassword();
		return new User(user, pass,new ArrayList<>());
	}

}
