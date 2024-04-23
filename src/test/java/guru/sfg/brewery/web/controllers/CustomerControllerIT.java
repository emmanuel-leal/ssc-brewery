package guru.sfg.brewery.web.controllers;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomerControllerIT extends BaseIT{

    @ParameterizedTest(name = "#{index} with [{arguments}]")
    @MethodSource("guru.sfg.brewery.web.controllers.CustomerControllerIT#getStreamAdminCustomer")
    void testListCustomersAuth(String user,String pwd) throws Exception{
        mockMvc.perform(get("/customers")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic(user,pwd)))
                .andExpect(status().isOk());
    }

    @Test
    void testListCustomersNoAuth() throws Exception{
        mockMvc.perform(get("/customers")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testListCustomersNoLoggedIn() throws Exception{
        mockMvc.perform(get("/customers"))
                .andExpect(status().isUnauthorized());
    }
}
