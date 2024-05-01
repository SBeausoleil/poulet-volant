package com.pouletvolant.http.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pouletvolant.exceptions.FileStorageException;
import com.pouletvolant.http.payloads.ApiResponse;
import com.pouletvolant.models.CVFile;
import com.pouletvolant.models.MandatoryRedirect;
import com.pouletvolant.models.User;
import com.pouletvolant.repositories.CVFileRepository;
import com.pouletvolant.repositories.MandatoryRedirectRepository;
import com.pouletvolant.repositories.UserRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.services.CVService;
import com.pouletvolant.util.ResponseUtil;

@RestController
@RequestMapping("/cvfiles")
public class CVController {

	private static final Logger logger = LoggerFactory.getLogger(CVController.class);

	@Autowired
	private CVService cvService;

	@Autowired
	private CVFileRepository cvRepository;

	@Autowired
	private MandatoryRedirectRepository redirectRepository;

	@Autowired
	private ResponseUtil responseUtil;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/upload/cv")
	public ResponseEntity<Void> uploadCv(@CurrentUser User user,
			@NotNull @RequestParam("file") MultipartFile multipartFile) {
		if (cvService.getCVUser(user.getId()) == null) {
			try {
				if (multipartFile.getOriginalFilename().contains(".pdf")) {
					if (user.getType() == User.Type.STUDENT) {
						CVFile fileEntity = new CVFile(multipartFile.getOriginalFilename(),
								multipartFile.getContentType(), multipartFile.getBytes(), user);
						cvRepository.save(fileEntity);
						URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

						user.setMandatoryRedirects(user.getMandatoryRedirects().stream().filter((redirect) -> {
							if (redirect.getUrl().equals("/cv")) {
								redirectRepository.delete(redirect);
								return false;
							}
							return true;
						}).collect(Collectors.toList()));
						ResponseEntity<Void> response = ResponseEntity.created(location)
								.headers(responseUtil.insertUserUpdateInstruction(user, null)).build();

						return response;
					}
				}
				throw new FileStorageException(
						"Sorry! Filename contains invalid path sequence " + multipartFile.getOriginalFilename());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
		logger.info("En processus de download....");
		CVFile dbFile = cvService.getOne(fileName);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.header("Access-Control-Expose-Headers",
						HttpHeaders.CONTENT_DISPOSITION + "," + HttpHeaders.CONTENT_LENGTH)
				.body(new ByteArrayResource(dbFile.getData()));
	}

	@GetMapping("/studentCV")
	public ApiResponse<CVFile> getStudentFile(@CurrentUser User currentUser) {
		logger.info("En processus de recherche d'un fichier...");
		return new ApiResponse<>(HttpStatus.OK.value(), "", cvService.getCVUser(currentUser.getId()));
	}

	@GetMapping("/has_cv")
	public ResponseEntity<Boolean> hasCv(@CurrentUser User currentUser) {
		logger.info("hasCv(): " + currentUser.getName() + " #" + currentUser.getId());
		return ResponseEntity.ok(cvService.getCVUser(currentUser.getId()) != null);
	}

	@GetMapping
	public ApiResponse<List<CVFile>> listAllStudentFile() {
		logger.info("En processus de recherche d'une liste de fichiers....");
		return new ApiResponse<>(HttpStatus.OK.value(), "", cvService.getAllCV());
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteFile(@PathVariable("id") long id, @CurrentUser User currentUser) {
		logger.info("En processus de suppression d'un fichier....");
		CVFile cv = cvRepository.findById(id).get();
		User student = cv.getUser();
		cvService.deleteCV(id);
		if (cvService.getCVUser(student.getId()) == null) {
			MandatoryRedirect redirect = new MandatoryRedirect("/cv", student);
			student.getMandatoryRedirects().add(redirect);
			redirectRepository.save(redirect);
		}

		if (student.getId() == currentUser.getId()) {
			ResponseEntity<Void> response = ResponseEntity.ok()
					.headers(responseUtil.insertUserUpdateInstruction(student, null)).build();
			return response;
		}
		return ResponseEntity.ok().build();
	}

}
