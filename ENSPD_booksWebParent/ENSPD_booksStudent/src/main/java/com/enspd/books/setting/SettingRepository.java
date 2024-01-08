package com.enspd.books.setting;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enspd.books.common.entity.Setting;
import com.enspd.books.common.entity.SettingFiliere;

public interface SettingRepository extends CrudRepository<Setting, String> {
	public List<Setting> findByFiliere(SettingFiliere filiere);
}
