package tacos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tacos.Ingredient.Type;

@WebMvcTest
public class DesignTacoControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	private List<Ingredient> ingredients;
	private Taco design;

	@BeforeEach
	public void setup() {
		ingredients = Arrays.asList(
				new Ingredient("FLTO", "pszenna", Type.WRAP),
				new Ingredient("COTO", "kukurydziana", Type.WRAP),
				new Ingredient("GRBF", "mielona wołowina", Type.PROTEIN),
				new Ingredient("CARN", "kawałki mięsa", Type.PROTEIN),
				new Ingredient("TMTO", "pomidory", Type.VEGGIES),
				new Ingredient("LETC", "sałata", Type.VEGGIES),
				new Ingredient("CHED", "cheddar", Type.CHEESE),
				new Ingredient("JACK", "Monterey Jack", Type.CHEESE),
				new Ingredient("SLSA", "salsa", Type.SAUCE),
				new Ingredient("SRCR", "śmietana", Type.SAUCE)
		);
		
		design = new Taco();
		design.setName("My taco");
		design.setIngredients(Arrays.asList("FLTO", "GRBF", "LETC", "CHED", "SLSA"));
	}
	
	@Test
	public void testShowDesignPage() throws Exception {
		mockMvc.perform(get("/design"))
				.andExpect(status().isOk())
				.andExpect(view().name("design"))
				.andExpect(model().attribute("wrap", ingredients.subList(0, 2)));
	}
	
	@Test
	public void testProcessDesign() throws Exception {
		mockMvc.perform(post("/design").content("name=Test+Taco&ingredients=FLTO,GRBF,CHED")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().stringValues("Location", "/orders/current"));
			
	}
}
