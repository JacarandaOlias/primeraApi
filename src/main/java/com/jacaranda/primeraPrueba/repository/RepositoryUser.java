package com.jacaranda.primeraPrueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.primeraPrueba.model.User;

public interface RepositoryUser extends JpaRepository<User, String> {

}
