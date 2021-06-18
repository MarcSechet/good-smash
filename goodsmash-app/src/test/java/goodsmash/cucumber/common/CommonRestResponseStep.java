package goodsmash.cucumber.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import io.cucumber.java.fr.Alors;
import lombok.extern.slf4j.Slf4j;
import mic.poulet.goodsmash.exceptions.LambdaException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
public class CommonRestResponseStep {
	private final TestContext context;

	public CommonRestResponseStep(TestContext context) {
		this.context = context;
	}

	@Alors("la demande réussit")
	public void succeed() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug(context.getResult().andReturn().getResponse().getContentAsString());
		}

		context.getResult().andExpect(status().is2xxSuccessful());
	}

	@Alors("la demande échoue avec un code {int}")
	public void failedWithCode(int code) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug(context.getResult().andReturn().getResponse().getContentAsString());
		}

		context.getResult().andExpect(status().is(code));
	}

	@Alors("le retour indique {string}")
	public void resultWithMessage(String message) throws Exception {
		context.getResult().andExpect(jsonPath("$.message", is(message)));
	}

	/**
	 * Vérifie le contenu d'un header en retour
	 * @param headerName nom du header
	 * @param headerContent contenu du header
	 */
	@Alors("le retour contient le header {string} avec le contenu {string}")
	public void leRetourContientLeHeaderSuivant(String headerName, String headerContent) throws Exception {
		ResultActions result = context.getResult()
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		String header = result.andReturn().getResponse().getHeader(headerName);
		assertEquals(headerContent, header);
	}

	/**
	 * Vérifie qu'un header n'est pas présent dans le retour
	 * @param headerName nom du header
	 */
	@Alors("le retour ne contient pas le header {string}")
	public void leRetourNeContientPasLeHeaderSuivant(String headerName) throws Exception {
		ResultActions result = context.getResult()
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		String header = result.andReturn().getResponse().getHeader(headerName);
		assertNull("Header", header);
	}


	/**
	 * Vérifie le contenu des champs de l'objet de retour d'erreur
	 *
	 * @param fields <table><tbody>
	 *               <tr><td>field</td><td>nom du champ</td></tr>
	 *               <tr><td>value</td><td>valeur du champ</td></tr>
	 *               </tbody></table>
	 */
	@Alors("le retour contient les champs suivants :")
	public void leRetourContientLesChampsSuivants(List<Map<String, String>> fields) throws Exception {
		ResultActions result = context.getResult()
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

		try {
			fields.forEach(LambdaException.catchConsumer(map -> checkField(result, map)));
		} catch (LambdaException e) {
			e.relance(Exception.class);
		}

		result.andExpect(jsonPath("$.fields", hasSize(fields.size())));
	}

	private void checkField(ResultActions result, Map<String, String> map) throws Exception {
		String field = map.get("field");
		result.andExpect(jsonPath("$.fields[*].field", hasItem(field)));

		String path = "$.fields[?(@.field==\"" + field + "\")].value";
		Optional.of("value")
			.map(map::get)
			.ifPresent(LambdaException.catchConsumer(value -> result.andExpect(jsonPath(path, hasItem(value)))));
	}
}
