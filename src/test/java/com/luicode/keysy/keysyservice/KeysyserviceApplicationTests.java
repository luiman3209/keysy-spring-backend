package com.luicode.keysy.keysyservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luicode.keysy.keysyservice.dtos.CommonResponse;
import com.luicode.keysy.keysyservice.dtos.GetAllPasswordsResponse;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.utils.DbInit;
import com.luicode.keysy.keysyservice.utils.JwtTestUtils;
import com.luicode.keysy.keysyservice.utils.MessageUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = KeysyserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(value = {"classpath:application-test.properties"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KeysyserviceApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected MessageUtils messageUtils;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected DbInit dbInit;

	@Autowired
	protected JwtTestUtils jwtTestUtils;

	private String jwtToken;
	private String headerTokenValue;

	@BeforeAll
	void setUp() {
		jwtToken = jwtTestUtils.generateTestToken(DbInit.TEST_USER_EMAIL);
		headerTokenValue = "Bearer " + jwtToken;
		dbInit.initData();
	}

	private static final String PASSWORDS = "/passwords";

	private MockHttpServletRequestBuilder getPasswords() {
		return get(PASSWORDS)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", headerTokenValue);
	}

	private MockHttpServletRequestBuilder addPassword() {
		return post(PASSWORDS)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", headerTokenValue);
	}

	@Test
	@Order(1)
	void getPasswordsSuccess() throws Exception {

		String respContent = mockMvc
				.perform(getPasswords())
				.andExpect(status().is(HttpStatus.OK.value()))
				.andDo(print())
				.andReturn().getResponse().getContentAsString();

		GetAllPasswordsResponse resp = objectMapper.readValue(respContent, GetAllPasswordsResponse.class);
		assertNotNull(resp);
		assertEquals(1, resp.getPasswords().size());
	}

	@Test
	@Order(2)
	void addPasswordSuccess() throws Exception {
		PasswordEntryRequest addPasswordReqest =  PasswordEntryRequest.builder()
				.entryName("test")
				.username("johndoe32")
				.password("pa!ssword3232")
				.build();
		String respContent = mockMvc
				.perform(addPassword().content(objectMapper.writeValueAsString(addPasswordReqest)))
				.andExpect(status().is(HttpStatus.OK.value()))
				.andDo(print())
				.andReturn().getResponse().getContentAsString();

		CommonResponse resp = objectMapper.readValue(respContent, CommonResponse.class);
		assertNotNull(resp);
		assertEquals(messageUtils.getMessage("password.added.successfully"), resp.getMessage());
	}

}
