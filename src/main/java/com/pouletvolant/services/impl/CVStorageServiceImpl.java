package com.pouletvolant.services.impl;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pouletvolant.exceptions.FileStorageException;
import com.pouletvolant.exceptions.MyFileNotFoundException;
import com.pouletvolant.models.CVFile;
import com.pouletvolant.repositories.CVFileRepository;
import com.pouletvolant.services.CVService;

@Transactional
@Service(value = "cvService")
public class CVStorageServiceImpl implements CVService {

	@Autowired
	private CVFileRepository cvFileRepository;

	@Override
	public CVFile getOne(String fileName) {
		try {
			return cvFileRepository.findByFileName(fileName);
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found with name " + fileName, e);
		}
	}

	@Override
	public CVFile getCVUser(long id) {
		try {
			CVFile optionalFile = cvFileRepository.findUserCVById(id);
			if(optionalFile != null) {
				return optionalFile;
			}
			return null;
		} catch (Exception e) {
			throw new MyFileNotFoundException("No file found for this user " + id, e);
		}
	}

	@Override
	public void deleteCV(long id) {
		try {
			cvFileRepository.deleteById(id);
		} catch (Exception e) {
			throw new FileStorageException("Could not delete file. Please try again!", e);
		}
	}

	@Override
	public List<CVFile> getAllCV() {
		try {
			List<CVFile> list = new ArrayList<>();
			cvFileRepository.findAll().iterator().forEachRemaining(list::add);
			return list;
		} catch (Exception e) {
			throw new MyFileNotFoundException("File(s) not found", e);
		}
	}
}