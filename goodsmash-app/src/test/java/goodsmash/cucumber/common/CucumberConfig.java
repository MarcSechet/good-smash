package goodsmash.cucumber.common;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Etantdonnéque;
import io.cucumber.spring.CucumberContextConfiguration;
import mic.poulet.goodsmash.GoodsmashApplication;
import mic.poulet.goodsmash.spec.api.CharactersApi;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ContextConfiguration(classes = GoodsmashApplication.class, loader = SpringBootContextLoader.class)
@AutoConfigureMockMvc
@CucumberContextConfiguration
@ComponentScan
@MockBeans({ @MockBean(Clock.class) })
public class CucumberConfig {
	private static final List<String> tableNames = Arrays.asList("character");

	private final EntityManager entityManager;
	private final TestContext context;
	private final Clock clock;

	public CucumberConfig(EntityManager entityManager, TestContext context, Clock clock) {
		this.entityManager = entityManager;
		this.context = context;
		this.clock = clock;
	}

	@Before
	public void init() {
		context.init();

		Clock defaultClock = Clock.fixed(Instant.EPOCH, ZoneId.systemDefault());
		when(clock.instant()).thenReturn(defaultClock.instant());
		when(clock.getZone()).thenReturn(defaultClock.getZone());
	}

	@After
	@Transactional
	public void cleanUp() {
		entityManager.flush();

		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE")
				.executeUpdate();
		tableNames.forEach(tableName -> entityManager.createNativeQuery("TRUNCATE TABLE " + tableName)
				.executeUpdate());
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE")
				.executeUpdate();
	}

	@Etantdonnéque("la date actuelle est le {int}-{int}-{int} à {int}h{int}:{int}")
	public void currentDateIsSet(int day, int month, int year, int hour, int minutes, int seconds) {
		LocalDateTime time = LocalDateTime.of(year, month, day, hour, minutes, seconds);
		when(clock.instant()).thenReturn(time.toInstant(ZoneId.systemDefault()
				.getRules()
				.getOffset(time)));
	}
}
