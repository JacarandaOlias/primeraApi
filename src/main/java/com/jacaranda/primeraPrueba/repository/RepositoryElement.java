package com.jacaranda.primeraPrueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.primeraPrueba.model.Element;

public interface RepositoryElement extends JpaRepository<Element, Integer> {

}
