package com.jacaranda.primeraPrueba.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
    public Element createElement(Element element) {
        try {
            return elementRepository.save(element);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error: Could not save the element.", e);
        }
    }

    // READ (All)
    public List<Element> getAllElements() {
        return elementRepository.findAll();
    }

    // READ (One)
    public Element getElementById(Integer id) {
        return elementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Element not found with ID: " + id));
    }

    // UPDATE
    public Element updateElement(Integer id, Element elementDetails) {
        Element existingElement = getElementById(id); 
        
        try {
            existingElement.setName(elementDetails.getName());
            existingElement.setValue(elementDetails.getValue());
            return elementRepository.save(existingElement);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error: Could not update the element.", e);
        }
    }

    // DELETE
    public void deleteElement(Integer id) {
        if (!elementRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Element not found with ID: " + id);
        }
        try {
            elementRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error: Could not connect to the server.", e);
        }
    }
}
