package com.enspd.books.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enspd.books.common.entity.Setting;
import com.enspd.books.common.entity.SettingFiliere;

@Service
public class SettingService {
	@Autowired
	private SettingRepository repo;

	public List<Setting> getGeneralSettings() {
		return repo.findByFiliere(SettingFiliere.GENERAL);
	}

	public EmailSettingBag getEmailSettings() {
		List<Setting> settings = repo.findByFiliere(SettingFiliere.MAIL_SERVER);
		settings.addAll(repo.findByFiliere(SettingFiliere.MAIL_TEMPLATES));

		return new EmailSettingBag(settings);
	}

}
