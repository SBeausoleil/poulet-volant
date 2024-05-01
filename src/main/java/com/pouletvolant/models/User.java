package com.pouletvolant.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = User.TABLE, indexes = @Index(columnList = "email", unique = true))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class User implements UserDetails {
	private static final long serialVersionUID = 6814230742102157648L;
	public static final String TABLE = "users";

	public static enum Type {
		STUDENT(Type.VALUE_STUDENT), INTERNSHIP_MANAGER(Type.VALUE_INTERNSHIP_MANAGER), EMPLOYER(Type.VALUE_EMPLOYER);

		public static final String VALUE_STUDENT = "STUDENT";
		public static final String VALUE_INTERNSHIP_MANAGER = "INTERNSHIP_MANAGER";
		public static final String VALUE_EMPLOYER = "EMPLOYER";

		public final String value;

		Type(String value) {
			// force equality between name of enum instance, and value of constant
			if (!this.name().equals(value))
				throw new IllegalArgumentException("Incorrect use of User.Type!");
			this.value = value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", insertable = false, updatable = false)
	protected Type type;
	private String firstName;
	private String lastName;
	@Column(name = "email")
	protected String email;
	protected String phoneNumber;
	@JsonIgnore
	@Column(name = "password_hash")
	private String passwordHash;

	@JsonIgnore
	@Transient
	private ArrayList<SimpleGrantedAuthority> authority;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private CVFile cv;
	
	@JsonIgnore
	@Transient
	@OneToMany(mappedBy = "editeur", fetch = FetchType.EAGER)
	private List<Formulaire> formulaires;
	
	@JsonIgnore
	@Transient
	@OneToMany(mappedBy = "destinataire", fetch = FetchType.EAGER)
	private List<Notification> notifications;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<MandatoryRedirect> mandatoryRedirects;

	protected User(Type type, String firstName, String lastName, String email, String phoneNumber,
			String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.passwordHash = passwordHash;
		this.email = email;
		this.phoneNumber = phoneNumber;

		this.type = type;
		this.authority = new ArrayList<>(1);
		authority.add(new SimpleGrantedAuthority("ROLE_" + type.name()));
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return passwordHash;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return this.email;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@Override
	@JsonIgnore
	public List<SimpleGrantedAuthority> getAuthorities() {
		return authority;
	}

	public String getName() {
		return firstName + " " + lastName;
	}
}
