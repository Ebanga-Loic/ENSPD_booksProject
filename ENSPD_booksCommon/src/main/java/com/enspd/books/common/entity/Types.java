package com.enspd.books.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "types")
public class Types {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 45, unique = true)
	private String name;

	@ManyToMany
	@JoinTable(name = "types_filieres", joinColumns = @JoinColumn(name = "type_id"), inverseJoinColumns = @JoinColumn(name = "filiere_id"))
	private Set<Filieres> filieres = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Filieres> getFilieres() {
		return filieres;
	}

	public void setFilieres(Set<Filieres> filieres) {
		this.filieres = filieres;
	}

}
