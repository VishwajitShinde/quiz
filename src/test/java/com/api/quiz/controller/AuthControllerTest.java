package com.api.quiz.controller;

import com.api.quiz.payload.request.LoginRequest;
import com.api.quiz.payload.response.JwtResponse;
import com.api.quiz.repository.RoleRepository;
import com.api.quiz.repository.UserRepository;
import com.api.quiz.security.jwt.JwtUtils;
import com.api.quiz.security.services.UserDetailsServiceImpl;
import com.api.quiz.service.AuthService;
import com.api.quiz.util.MockDataLoader;
import io.jsonwebtoken.lang.Assert;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource( locations = { "classpath:application-test.properties", "classpath:data/mockData.json", "classpath:ssl-server.jks" })
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("test")
public class AuthControllerTest {



	@Autowired
	private MockMvc mockMvc;

	/*
	@Autowired
	private ObjectMapper objectMapper;
	*/

	/*
	@MockBean
	AuthService authService;

	Map<String, Object> data;
	Map<String, Object> responseDataWithoutToken;

	@BeforeEach
	public void setUp() {
		data = MockDataLoader.loadDataFromFile( "data/mockData.json", Map.class );
		responseDataWithoutToken = (Map<String, Object>) ((Map<String, Object>)data.get("user1")).get("response");
	}

	@Test
	public void TestSignIn_success() throws Exception {
		String loginInfo = "{\"emailOrMobile\":\"dhamma4u@gmail.com\",\"password\":\"Password1!\"}";
		LoginRequest loginRequest = MockDataLoader.loadJsonDataFromString(loginInfo, LoginRequest.class);

		Map<String, Object> responseData =  new LinkedHashMap<>(responseDataWithoutToken);
		responseData.put("accessToken", "dummy-jwt-token");
		JwtResponse response = (JwtResponse) MockDataLoader.convertData( responseData, JwtResponse.class);

		Mockito.when(authService.authenticateOrSignInUser(Mockito.any())).thenReturn( response );

		ResultActions result = mockMvc.perform( post("/api/auth/signin" )
				.contentType("application/json")
				.content(loginInfo) )
				.andExpect(status().isOk())
				.andDo(print());

		Assert.notNull(result, "/api/auth/signin : Api Response " );
	}
*/

}