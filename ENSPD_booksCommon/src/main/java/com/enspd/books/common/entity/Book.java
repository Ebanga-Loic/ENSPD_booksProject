package com.enspd.books.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, length = 256, nullable = false)
	private String name;

	@Column(length = 256, nullable = false)
	private String auteur;

	@Column(length = 4096, nullable = false, name = "full_description")
	private String fullDescription;

	@Column(name = "created_time")
	private Date createdTime;

	@Column(name = "updated_time")
	private Date updatedTime;

	private boolean enabled;

	@Column(name = "in_stock")
	private boolean inStock;

	@ManyToOne
	@JoinColumn(name = "filiere_id")
	private Filieres filieres;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private Types types;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BookDetail> details = new ArrayList<>();

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

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public Filieres getFilieres() {
		return filieres;
	}

	public void setFilieres(Filieres filieres) {
		this.filieres = filieres;
	}

	public Types getTypes() {
		return types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	public List<BookDetail> getDetails() {
		return details;
	}

	public void setDetails(List<BookDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + "]";
	}

	public void addDetail(String name, String value) {
		this.details.add(new BookDetail(name, value, this));
	}

	public void addDetail(Integer id, String name, String value) {
		this.details.add(new BookDetail(id, name, value, this));
	}

}
