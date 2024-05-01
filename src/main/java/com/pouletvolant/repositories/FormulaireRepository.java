package com.pouletvolant.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.Formulaire;

@Repository
public interface FormulaireRepository extends JpaRepository<Formulaire, Long> {

	Formulaire findByFileName(String fileName);
	
}
