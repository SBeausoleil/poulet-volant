package com.pouletvolant.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "formulaires")
public class Formulaire {

	public static enum StatutFormulaire {
		TOUR_EMPLOYER, TOUR_STUDENT, FINALISER;
	}
	
	public static enum GenreFormulaire {
		EVALUATION_STUDENT, CONTRAT_EMBAUCHE;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String fileName;

	@Column
	private String formulaire_type;

	@Enumerated
	private StatutFormulaire statutFormulaire;
	
	@Enumerated
	private GenreFormulaire genreFormulaire;
	
	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name = "editeur")
	@ToString.Exclude
	private User editeur;
	
	@Lob
	private byte[] donnee;
	
	public Formulaire(String fileName, String formulaire_type, byte[] donnee, User editeur, StatutFormulaire statutFormulaire) {
		this.donnee = donnee;
		this.editeur = editeur;
		this.fileName = fileName;
		this.formulaire_type = formulaire_type;
		this.statutFormulaire = statutFormulaire;
		this.genreFormulaire = GenreFormulaire.CONTRAT_EMBAUCHE;
	}

}
