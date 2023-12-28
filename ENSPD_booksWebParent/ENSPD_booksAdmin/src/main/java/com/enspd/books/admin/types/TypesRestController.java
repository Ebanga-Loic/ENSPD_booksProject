package com.enspd.books.admin.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enspd.books.common.entity.Filieres;
import com.enspd.books.common.entity.Types;

@RestController
public class TypesRestController {
	@Autowired
	private TypesService service;

	@PostMapping("/types/check_unique")
	public String checkUnique(@Param("id") Integer id, @Param("name") String name) {
		return service.checkUnique(id, name);
	}

	@GetMapping("/types/{id}/filieres")
	public List<FilieresDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer typeId)
			throws TypesNotFoundRestException {
		List<FilieresDTO> listFilieres = new ArrayList<>();

		try {
			Types type = service.get(typeId);
			Set<Filieres> filieres = type.getFilieres();

			for (Filieres filiere : filieres) {
				FilieresDTO dto = new FilieresDTO(filiere.getId(), filiere.getName());
				listFilieres.add(dto);
			}

			return listFilieres;
		} catch (TypesNotFoundException e) {
			throw new TypesNotFoundRestException();
		}
	}
}
