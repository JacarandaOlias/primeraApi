package com.jacaranda.primeraPrueba.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jacaranda.primeraPrueba.exception.element.DatabaseException;
import com.jacaranda.primeraPrueba.model.User;
import com.jacaranda.primeraPrueba.repository.RepositoryUser;

@Service
public class ServiceUser {
	private final RepositoryUser repositoryUser;

	public ServiceUser(RepositoryUser repositoryUser) {
		super();
		this.repositoryUser = repositoryUser;
	}

	public User createElement(User user) {
		try {
			return repositoryUser.save(user);
		} catch (DataAccessException e) {
			throw new DatabaseException("No se pudo guardar el usuario en la base de datos", e);
		}
	}
}
