package com.pouletvolant.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pouletvolant.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	default User findByUsername(String username) { return findByEmail(username); }
	
	@Query(value = "select * from users u where u.type = 'student'", nativeQuery = true)
	List<User> findAllStudents();
}
