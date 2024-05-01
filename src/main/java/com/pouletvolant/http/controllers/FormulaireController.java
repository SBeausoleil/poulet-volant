package com.pouletvolant.http.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pouletvolant.exceptions.FileStorageException;
import com.pouletvolant.http.payloads.ApiResponse;
import com.pouletvolant.models.Formulaire;
import com.pouletvolant.models.Formulaire.StatutFormulaire;
import com.pouletvolant.models.User;
import com.pouletvolant.repositories.FormulaireRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.services.FormulaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/formulaires")
public class FormulaireController {
	
	private static final Logger logger = LoggerFactory.getLogger(FormulaireController.class);

	@Autowired
	private FormulaireService formulaireService;

	@Autowired
	private FormulaireRepository formulaireRepository;

	@PostMapping("/uploadFormulaire")
	public ResponseEntity<Void> uploadFormulaire(@CurrentUser User editeur,@NotNull @RequestParam("file") MultipartFile multipartFile) {
		logger.info("En processus d'upload d'un formulaire ...");
		try {
			if ((multipartFile.getOriginalFilename().contains("internship")) == true) {
				Formulaire formulaire = new Formulaire(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes(), editeur, StatutFormulaire.TOUR_EMPLOYER);
				formulaireRepository.save(formulaire);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
				ResponseEntity<Void> response = ResponseEntity.created(location).build();
				return response;
			}
			else if ((multipartFile.getOriginalFilename().contains("employer")) == true) {
				Formulaire formulaire = new Formulaire(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes(), editeur, StatutFormulaire.TOUR_STUDENT);
				formulaireRepository.save(formulaire);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
				ResponseEntity<Void> response = ResponseEntity.created(location).build();
				return response;
			}
			else if ((multipartFile.getOriginalFilename().contains("student")) == true) {
				Formulaire formulaire = new Formulaire(multipartFile.getOriginalFilename(),
						multipartFile.getContentType(), multipartFile.getBytes(), editeur, StatutFormulaire.FINALISER);
				formulaireRepository.save(formulaire);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
				ResponseEntity<Void> response = ResponseEntity.created(location).build();
				return response;
			}
			throw new FileStorageException("Désolé! Le nom du formulaire contient une séquence de route invalide " + multipartFile.getOriginalFilename());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@GetMapping("/downloadFormulaire/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
		logger.info("En processus de download d'un formulaire ...");
		Formulaire formulaire = formulaireService.getOne(fileName);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(formulaire.getFormulaire_type()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + formulaire.getFileName() + "\"")
				.header("Access-Control-Expose-Headers",HttpHeaders.CONTENT_DISPOSITION + "," + HttpHeaders.CONTENT_LENGTH)
				.body(new ByteArrayResource(formulaire.getDonnee()));
	}
	
	@GetMapping("/listeFormulaire")
	public ApiResponse<List<Formulaire>> listAllFormulaire(){
		logger.info("En processus de recherche d'une liste de formulaires ...");
		return new ApiResponse<>(HttpStatus.OK.value(), "", formulaireService.getAllFormulaire());
	}
	
	@DeleteMapping("/deleteFormulaire/{id}")
	public ApiResponse<Void> deleteFormulaire(@PathVariable("id") long id){
		logger.info("En processus de suppression d'un formulaire ...");
		formulaireService.deleteFormulaire(id);
		return new ApiResponse<>(HttpStatus.OK.value(), "", null);
	}
}
