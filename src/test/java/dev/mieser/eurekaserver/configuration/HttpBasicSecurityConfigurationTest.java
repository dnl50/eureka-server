package dev.mieser.eurekaserver.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-http-basic.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HttpBasicSecurityConfigurationTest {

    /*
     * Lists all registers instances. Path does not really matter, as long as it's
     * a valid eureka REST API path.
     */
    private static final String EUREKA_API_PATH = "/eureka/apps";

    @Autowired
    private EurekaSecurityProperties properties;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getReturnsOkWhenCorrectBasicAuthGiven() throws Exception {
        // given
        var correctUsername = properties.getUsername();
        var correctPassword = properties.getPassword();

        // when / then
        mockMvc.perform(get(EUREKA_API_PATH)
                .with(httpBasic(correctUsername, correctPassword))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getReturnsUnauthorizedWhenNoBasicAuthParamsGiven() throws Exception {
        // given / when / then
        mockMvc.perform(get(EUREKA_API_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getReturnsUnauthorizedWhenWrongBasicUsernameGiven() throws Exception {
        // given
        var wrongUsername = properties.getUsername() + "1234";
        var correctPassword = properties.getPassword();

        // when / then
        mockMvc.perform(get(EUREKA_API_PATH)
                .with(httpBasic(wrongUsername, correctPassword))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getReturnsUnauthorizedWhenWrongPasswordGiven() throws Exception {
        // given
        var correctUsername = properties.getUsername();
        var wrongPassword = properties.getPassword() + "1234";

        // when / then
        mockMvc.perform(get(EUREKA_API_PATH)
                .with(httpBasic(correctUsername, wrongPassword))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getReturnsUnauthorizedWhenWrongUsernameAndPasswordGiven() throws Exception {
        // given
        var wrongUsername = properties.getUsername() + "1234";
        var wrongPassword = properties.getPassword() + "1234";

        // when / then
        mockMvc.perform(get(EUREKA_API_PATH)
                .with(httpBasic(wrongUsername, wrongPassword))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
