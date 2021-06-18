package goodsmash.cucumber.steps;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import goodsmash.cucumber.common.TestContext;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Quand;
import io.cucumber.java.fr.Soit;
import mic.poulet.goodsmash.smash.dao.CharacterDao;
import mic.poulet.goodsmash.smash.model.character.Character;
import mic.poulet.goodsmash.smash.model.character.CharacterWeight;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class CharacterStep {
	@Value("${api-smash.base-path}")
	private String basePath;

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final CharacterDao characterDao;
	private final TestContext context;
	private final MockMvc mvc;

	public CharacterStep(CharacterDao characterDao, TestContext context, MockMvc mvc) {
		this.characterDao = characterDao;
		this.context = context;
		this.mvc = mvc;
	}

	////////////////////////////////////////////////////////////////////////////
	/////////////////////////////    WS     ////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	@Alors("il récupère le(s) personnage(s) suivant(s) :")
	public void getCharacter(Map<String, String> fapam) throws Exception {
		ResultActions result = context.getResult()
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		checkFapamDetailDto(result, fapam);
	}

	@Quand("il tente de récupérer le personnage {word}")
	public void getFapam(String name) throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(basePath+ "/smash/characters/Lucas")
				.contentType(MediaType.APPLICATION_JSON);

		context.setResult(mvc.perform(request)
				.andDo(print()));
	}

	private void checkFapamDetailDto(ResultActions result, Map<String, String> map) throws Exception {
		/*try {
			String uuid = getFapamUuid(result, map, 0);

			check(result, uuid, "issuer", map, RestResponseStep::uuidTransform);
			check(result, uuid, "issuer.uuid", map, RestResponseStep::uuidTransform);
			check(result, uuid, "issuer.iban", map, s -> s);

			check(result, uuid, "document", map, RestResponseStep::uuidTransform);
			check(result, uuid, "creationDate", map, s -> s);
			check(result, uuid, "cancelDate", map, s -> s);
			check(result, uuid, "status", map, s -> s);
			check(result, uuid, "reference", map, s -> s);
			check(result, uuid, "label", map, s -> s);

			check(result, uuid, "receiver.uuid", map, RestResponseStep::uuidTransform);
			check(result, uuid, "receiver.ibanValue", map, s -> s);
			check(result, uuid, "receiver.ibanDocument", map, RestResponseStep::uuidTransform);

			check(result, uuid, "payment.value", map, Double::valueOf);
			check(result, uuid, "payment.accountingDate", map, s -> s);
			check(result, uuid, "payment.plannedDate", map, s -> s);

			check(result, uuid, "amount", map, Double::valueOf);

			map.entrySet()
					.stream()
					.filter(entry -> entry.getKey()
							.contains("[*]"))
					.forEach(LambdaException.catchConsumer(
							entry -> checkTab(result, uuid, entry.getKey(), entry.getValue())));
		} catch (LambdaException e) {
			e.relance(Exception.class);
		}*/
	}

	////////////////////////////////////////////////////////////////////////////
	/////////////////////////////    BDD    ////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	@Soit("le(s) personnage(s) suivant(s) :")
	public void initCharacter(List<Map<String, String>> characters) {
		characters.forEach(map -> {
			Character character = new Character();
			Optional.of("id")
					.map(map::get)
					.map(Long::valueOf)
					.ifPresent(character::setId);
			Optional.of("name")
					.map(map::get)
					.ifPresent(character::setName);
			Optional.of("weight")
					.map(map::get)
					.map(CharacterWeight::valueOf)
					.ifPresent(character::setCharacterWeight);
			Optional.of("floaty")
					.map(map::get)
					.map(Boolean::valueOf)
					.ifPresent(character::setFloaty);
			Optional.of("skill_rating")
					.map(map::get)
					.map(Integer::valueOf)
					.ifPresent(character::setSkillRating);
			Optional.of("tier")
					.map(map::get)
					.ifPresent(character::setTier);

			characterDao.save(character);
		});
	}

	@Alors("il existe {int} personnages(s) en base")
	public void charactersExist(int nbCharacters) {
		assertEquals(nbCharacters, characterDao.count());
	}

	/*@Transactional
	@Alors("le(s) personnage(s) suivant(s) existe(nt) en base :")
	public void checkCharacters(List<Map<String, String>> characters) {
		characters.forEach(map -> {
			Optional<Fapam> optional;
			String fapamValue = map.get("uuid");
			String fapamUuid = Pattern.compile("\\[.+]")
					.matcher(fapamValue)
					.matches() ? (String) context.get(fapamValue) : fapamValue;

			if (fapamUuid == null) {
				optional = findByIssuerAndReference(map);
				optional.map(Fapam::getUuid)
						.map(UUID::toString)
						.ifPresent(uuid -> context.put(fapamValue, uuid));
			} else {
				optional = Optional.of(fapamUuid)
						.map(UUID::fromString)
						.flatMap(characterDao::findByUuid);
			}

			assertFalse(optional.isEmpty());

			var fapam = optional.get();

			check("issuer.uuid", fapam.getIssuer()
					.getUuid(), map, UUID::fromString);
			check("issuer.iban", fapam.getIssuer().getIban(), map);

			check("status", fapam.getStatus(), map, FapamStatus::valueOf);
			check("document", fapam.getDocument(), map, UUID::fromString);
			check("creationDate", fapam.getCreationDate(), map,
				(String date) -> LocalDateTime.parse(date, DATE_TIME_FORMATTER));
			check("sentDate", fapam.getSentDate(), map,
				(String date) -> LocalDateTime.parse(date, DATE_TIME_FORMATTER));
			check("reference", fapam.getReference(), map);
			check("label", fapam.getLabel(), map);

			String mandate = map.get("mandate");
			if (StringUtils.isNotBlank(mandate)) {
				assertNotNull(fapam.getMandate());
				UUID mandateUuid = mandate.matches("\\[.*]") ? (UUID) context.get(mandate) : UUID.fromString(mandate);
				assertEquals(mandateUuid, fapam.getMandate().getUuid());
			}
			check("receiver.uuid", fapam.getReceiver().getUuid(), map, UUID::fromString);
			check("receiver.ibanValue", fapam.getReceiver().getIbanValue(), map);
			check("receiver.ibanDocument", fapam.getReceiver().getIbanDocument(), map, UUID::fromString);
			checkEmails(map.get("receiver.uuidMails"), fapam.getReceiver());

			Payment payment = optional.map(Fapam::getPayment).orElseGet(Payment::new);

			check("payment.value", payment.getValue(), map, BigInteger::new);
			check("payment.commission", payment.getCommission(), map, BigInteger::new);
			check("payment.accountingDate", payment.getAccountingDate(), map,
					(String date) -> LocalDate.parse(date, DATE_FORMATTER));
			check("payment.plannedDate", payment.getPlannedDate(), map,
					(String date) -> LocalDate.parse(date, DATE_FORMATTER));
			check("payment.paymentDate", payment.getPaymentDate(), map,
					(String date) -> LocalDateTime.parse(date, DATE_TIME_FORMATTER));
			check("payment.transactionUuid", fapam.getPayment().getTransactionUuid(), map, UUID::fromString);
			check("payment.paiementRecurrentUuid", fapam.getPayment().getPaiementRecurrentUuid(), map, UUID::fromString);
			check("payment.prelevementProgrammeUuid", fapam.getPayment().getPrelevementProgrammeUuid(), map, UUID::fromString);
		});
	}*/

	private void check(String expectedKey, String actual, Map<String, String> map) {
		check(expectedKey, actual, map, s -> s);
	}

	private <T> void check(String expectedKey, T actual, Map<String, String> map, Function<String, T> transform) {
		if (!map.containsKey(expectedKey)) {
			return;
		}

		Optional.of(expectedKey)
				.map(map::get)
				.map(transform)
				.ifPresentOrElse(expected -> assertEquals(expectedKey, expected, actual),
						() -> assertNull(expectedKey, actual));
	}
}
