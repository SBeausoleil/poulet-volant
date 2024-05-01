package com.pouletvolant.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.CVFile;

@Repository
public interface CVFileRepository extends JpaRepository<CVFile, Long> {

	CVFile findByFileName(String fileName);
	
    @Query(value = "SELECT * FROM cvfile WHERE user_id = :id", nativeQuery=true)
	CVFile findUserCVById(@Param("id") long id);

}