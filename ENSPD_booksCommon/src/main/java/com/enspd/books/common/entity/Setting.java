package com.enspd.books.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "settings")
public class Setting {
	@Id
	@Column(name = "`key`", nullable = false, length = 128)
	private String key;

	@Column(nullable = false, length = 1024)
	private String value;

	@Enumerated(EnumType.STRING)
	@Column(length = 45, nullable = false)
	private SettingFiliere filiere;

	public Setting() {

	}

	public Setting(String key) {
		this.key = key;
	}

	public Setting(String key, String value, SettingFiliere filiere) {
		this.key = key;
		this.value = value;
		this.filiere = filiere;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SettingFiliere getFiliere() {
		return filiere;
	}

	public void setFiliere(SettingFiliere category) {
		this.filiere = category;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Setting other = (Setting) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Setting [key=" + key + ", value=" + value + "]";
	}

}
