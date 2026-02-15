package com.jacaranda.primeraPrueba.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jacaranda.primeraPrueba.model.User;
import com.jacaranda.primeraPrueba.repository.RepositoryUser;

@Service
public class ServiceUserAuth implements UserDetailsService {

	private final RepositoryUser repositoryUser;

	public ServiceUserAuth(RepositoryUser repositoryUser) {
		super();
		this.repositoryUser = repositoryUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = (User) this.repositoryUser.findById(username).orElse(null);
			
			if (user == null) {
				throw new UsernameNotFoundException("Usuario no encontrado");
			}else {
				return user;
			}
		}catch (Exception e) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}

	}
	
	
}
