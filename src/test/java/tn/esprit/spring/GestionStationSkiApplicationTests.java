package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.spring.repositories.*;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
class GestionStationSkiApplicationTests {
	@MockBean
	private ICourseRepository courseRepository;
	@MockBean
	private IInstructorRepository instructorRepository;
	@MockBean
	private IPisteRepository pisteRepository;
	@MockBean
	private IRegistrationRepository registrationRepository;
	@MockBean
	private ISkierRepository skierRepository;
	@MockBean
	private ISubscriptionRepository subscriptionRepository;

	@Test
	void contextLoads() {
	}

}
