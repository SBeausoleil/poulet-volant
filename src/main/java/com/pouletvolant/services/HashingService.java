package com.pouletvolant.services;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import lombok.Data;
import lombok.NoArgsConstructor;

@Primary
@Service
@Data
@NoArgsConstructor
public class HashingService implements PasswordEncoder {
	public static final int DEFAULT_N_ITERATIONS = 5;
	public static final int DEFAULT_MEMORY_USAGE = 1024 * 256; // MiB
	public static final int DEFAULT_PARALELLISM = 8;

	private int nIterations = DEFAULT_N_ITERATIONS;
	private int memoryUsage = DEFAULT_MEMORY_USAGE;
	private int parallelism = DEFAULT_PARALELLISM;

	private Argon2 argon = Argon2Factory.create(Argon2Types.ARGON2id);

	public HashingService(int nIterations, int memoryUsage, int parallelism) {
		this.nIterations = nIterations;
		this.memoryUsage = memoryUsage;
		this.parallelism = parallelism;
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return argon.hash(nIterations, memoryUsage, parallelism, rawPassword.toString().toCharArray());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return argon.verify(encodedPassword, rawPassword.toString().toCharArray());
	}
}
