package com.totalplay.auth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.totalplay.auth.dao.UserDetailsDao;
import com.totalplay.auth.model.UserDetailsModel;

@Service
public class UDetailService implements UserDetailsService {
	
	@Autowired
	private UserDetailsDao userDetailsDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsModel userdetail = userDetailsDao.getUserByUsername(username);
		return new User(username, userdetail.getSecretk(), buildAuthorities(userdetail.getId()));
	}

	private List<GrantedAuthority> buildAuthorities(Integer id) {
		Set<GrantedAuthority> auths = new HashSet<>();		
		for(String rol: userDetailsDao.getRoles(id)) {
			auths.add(new SimpleGrantedAuthority(rol));
		}		
		return new ArrayList<GrantedAuthority>(auths);
	}
}
