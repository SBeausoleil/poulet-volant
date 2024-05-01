package com.pouletvolant.services;

import java.util.List;

import com.pouletvolant.models.Formulaire;

public interface FormulaireService {

	Formulaire getOne(String fileName);

	void deleteFormulaire(long id);

	List<Formulaire> getAllFormulaire();
}
