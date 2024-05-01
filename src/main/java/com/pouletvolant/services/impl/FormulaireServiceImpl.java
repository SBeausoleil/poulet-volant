package com.pouletvolant.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pouletvolant.exceptions.FileStorageException;
import com.pouletvolant.exceptions.MyFileNotFoundException;
import com.pouletvolant.models.Formulaire;
import com.pouletvolant.repositories.FormulaireRepository;
import com.pouletvolant.services.FormulaireService;

@Transactional
@Service(value = "dbFormulaireService")
public class FormulaireServiceImpl implements FormulaireService {

	@Autowired
	private FormulaireRepository formulaireRepository;

	@Override
	public Formulaire getOne(String fileName) {
		try {
			return formulaireRepository.findByFileName(fileName);
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found with name " + fileName, e);
		}
	}

	@Override
	public void deleteFormulaire(long id) {
		try {
			formulaireRepository.deleteById(id);
		} catch (Exception e) {
			throw new FileStorageException("Could not delete file. Please try again!", e);
		}
	}

	@Override
	public List<Formulaire> getAllFormulaire() {
		try {
			List<Formulaire> list = new ArrayList<>();
			formulaireRepository.findAll().iterator().forEachRemaining(list::add);
			return list;
		} catch (Exception e) {
			throw new MyFileNotFoundException("File(s) not found", e);
		}
	}

}
