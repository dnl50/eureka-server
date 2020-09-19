package dev.mieser.eurekaserver.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DefaultSecurityConfigurationTest {

    /*
     * Lists all registers instances. Path does not really matter, as long as it's
     * a valid eureka REST API path.
     */
    private static final String EUREKA_API_PATH = "/eureka/apps";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void defaultSecurityConfigurationAllowsAllRequests() throws Exception {
        // given / when / then
        mockMvc.perform(get(EUREKA_API_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
