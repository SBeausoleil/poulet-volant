package com.pouletvolant.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.pouletvolant.models.User;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new AuditAwareImplementation();
	}
	
	private static class AuditAwareImplementation implements AuditorAware<Long> {

		@Override
		public Optional<Long> getCurrentAuditor() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication == null || !authentication.isAuthenticated())
				return Optional.empty();
			User user = (User) authentication.getPrincipal();
			return Optional.ofNullable(user.getId());
		}
	}
}