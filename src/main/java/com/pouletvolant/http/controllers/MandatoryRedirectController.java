package com.pouletvolant.http.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pouletvolant.models.User;
import com.pouletvolant.repositories.MandatoryRedirectRepository;
import com.pouletvolant.security.CurrentUser;
import com.pouletvolant.util.ResponseUtil;

@Controller
@RequestMapping("/api/mandatory-redirect")
public class MandatoryRedirectController {

	@Autowired
	private MandatoryRedirectRepository redirectRepo;
	
	@Autowired
	private ResponseUtil responseUtil;

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@CurrentUser User user, @RequestParam long id) {
		int originalSize = user.getMandatoryRedirects().size();
		user.setMandatoryRedirects(user.getMandatoryRedirects().stream().filter((redirect) -> {
			return redirect.getId() == id;
		}).collect(Collectors.toList()));

		if (user.getMandatoryRedirects().size() != originalSize) {
			redirectRepo.deleteById(id);
			return new ResponseEntity<Void>(responseUtil.insertUserUpdateInstruction(user, null), HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
	}
}
