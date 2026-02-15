package com.jacaranda.primeraPrueba.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.jacaranda.primeraPrueba.dto.ElementRequest;
import com.jacaranda.primeraPrueba.exception.element.DatabaseException;
import com.jacaranda.primeraPrueba.exception.element.ElementNotFoundException;
import com.jacaranda.primeraPrueba.exception.element.InvalidElementException;
import com.jacaranda.primeraPrueba.model.Element;
import com.jacaranda.primeraPrueba.repository.RepositoryElement;

@Service
public class ServiceElement {

	private RepositoryElement elementRepository;

	public ServiceElement(RepositoryElement elementRepository) {
		super();
		this.elementRepository = elementRepository;
	}

	// CREATE
	public Element createElement(ElementRequest elementRequest) {
		Element element = new Element();
		element.setName(elementRequest.name());
		element.setValue(elementRequest.value());
		try {
			return elementRepository.save(element);
		} catch (DataAccessException e) {
			throw new DatabaseException("No se pudo guardar el elemento en la base de datos:"+ e.getMessage(), e);
		}
	}

	// READ (All)
	public List<Element> getAllElements() {
		try {
			return elementRepository.findAll();
		} catch (DataAccessException e) {
			throw new DatabaseException("No se pudo acceder a la base de datos", e);
		}
	}

	// READ (One)
	public Element getElementById(Integer id) {
		return elementRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
	}

	// UPDATE
	public Element updateElement(Integer id, Element element) {
		if (id != element.getId()) {
			throw new InvalidElementException("Datos inválidos");
		}

		Element existingElement = elementRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));

		try {
			existingElement.setName(element.getName());
			existingElement.setValue(element.getValue());
			return elementRepository.save(existingElement);
		} catch (DataAccessException e) {
			throw new DatabaseException("No se pudo actualizar el elemento con ID " + id, e);
		}
	}

	// DELETE
	public void deleteElement(Integer id) {
		if (!elementRepository.existsById(id)) {
			throw new ElementNotFoundException(id);
		}
		try {
			elementRepository.deleteById(id);
		} catch (DataAccessException e) {
			throw new DatabaseException("No se pudo borrar el elemento con ID " + id, e);
		}
	}
}
