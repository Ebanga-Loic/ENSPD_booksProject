package com.enspd.books.admin.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Setting;
import com.enspd.books.common.entity.SettingFiliere;

@Service
public class SettingService {
	@Autowired
	private SettingRepository repo;

	public List<Setting> listAllSettings() {
		return (List<Setting>) repo.findAll();
	}

	public GeneralSettingBag getGeneralSettings() {
		List<Setting> settings = new ArrayList<>();

		List<Setting> generalSettings = repo.findByFiliere(SettingFiliere.GENERAL);

		settings.addAll(generalSettings);

		return new GeneralSettingBag(settings);
	}

	public void saveAll(Iterable<Setting> settings) {
		repo.saveAll(settings);
	}

	public List<Setting> getMailServerSettings() {
		return repo.findByFiliere(SettingFiliere.MAIL_SERVER);
	}

	public List<Setting> getMailTemplateSettings() {
		return repo.findByFiliere(SettingFiliere.MAIL_TEMPLATES);
	}
}
