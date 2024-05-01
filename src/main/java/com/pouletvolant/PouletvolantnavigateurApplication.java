package com.pouletvolant;

import java.util.TimeZone;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.pouletvolant.models.Address;
import com.pouletvolant.models.Employer;
import com.pouletvolant.models.Offer;
import com.pouletvolant.models.Organization;
import com.pouletvolant.models.User;
import com.pouletvolant.models.UserFactory;
import com.pouletvolant.repositories.AddressRepository;
import com.pouletvolant.repositories.OfferRepository;
import com.pouletvolant.repositories.OrganizationRepository;
import com.pouletvolant.repositories.UserRepository;

@SpringBootApplication
public class PouletvolantnavigateurApplication {

	public static void main(String[] args) {
		SpringApplication.run(PouletvolantnavigateurApplication.class, args);
	}

	@Bean
	ApplicationRunner init(UserFactory userFactory, UserRepository userRepo, OrganizationRepository orgRepo,
			OfferRepository offerRepo, AddressRepository addressRepo) {
		return (args) -> {
			TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
			if (userRepo.count() == 0) {
				userRepo.save(userFactory.newUser(User.Type.STUDENT, "Jeremy", "Tatti", "student@poulet.ca",
						"123-456-7890", "secret"));

				userRepo.save(userFactory.newUser(User.Type.STUDENT, "Jessica", "Lafleur", "jessicalafleur@poulet.ca",
						"123-456-2000", "secret"));

				userRepo.save(userFactory.newUser(User.Type.STUDENT, "Sylvain", "Laroche", "sylvainlaroche@poulet.ca",
						"123-456-3000", "secret"));

				userRepo.save(userFactory.newUser(User.Type.STUDENT, "Stephanie", "Laplante",
						"stephanielaplante@poulet.ca", "123-456-4000", "secret"));

				userRepo.save(userFactory.newUser(User.Type.STUDENT, "Benjamin", "Claveau", "benjaminclaveau@poulet.ca",
						"123-427-4567", "secret"));

				userRepo.save(userFactory.newUser(User.Type.INTERNSHIP_MANAGER, "Samuel", "Beausoleil",
						"gsTeam2@poulet.ca", "438-503-8006", "secret"));

				Employer employer = (Employer) userRepo.save(userFactory.newUser(User.Type.EMPLOYER, "Dominik", "Auger",
						"employeurTeam2@poulet.ca", "438-503-8006", "secret"));

				Organization org = new Organization("CAE", "Compagnie d'apprentissage avionique et médicale", employer);
				orgRepo.save(org);

				Address address = addressRepo.save(new Address("80 rue Albert", "Montreal", "Qc", "Canada", "J5D-9S9"));

				Offer offer1 = new Offer("Offre #1", "Stagiaire Programmeur Java", org, address);
				offerRepo.save(offer1);
				Offer offer2 = new Offer("Offre #2", "Stagiaire Ingénieur électrique", org, address);
				offerRepo.save(offer2);
				Offer offer3 = new Offer("Offre #3", "Stagiaire Ingénieur mécanique", org, address);
				offerRepo.save(offer3);
				Offer offer4 = new Offer("Offre #4", "Stagiaire Programmeur C#/C++", org, address);
				offerRepo.save(offer4);
				Offer offer5 = new Offer("Offre #5", "Stagiaire Technicien réseau", org, address);
				offerRepo.save(offer5);
			}

		};
	}
}
