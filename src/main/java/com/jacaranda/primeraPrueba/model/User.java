package com.jacaranda.primeraPrueba.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	
	private String password;
	
	@Column(name="role")
	private String rol;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pasword) {
		this.password = pasword;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
		        new SimpleGrantedAuthority("ROLE_" + this.rol));
	}

	

	
}