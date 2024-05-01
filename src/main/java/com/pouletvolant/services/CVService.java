package com.pouletvolant.services;

import java.util.List;

import com.pouletvolant.models.CVFile;

public interface CVService {


	CVFile getOne(String fileName);
	
	CVFile getCVUser(long id);

	void deleteCV(long id);

	List<CVFile> getAllCV();
	
}
