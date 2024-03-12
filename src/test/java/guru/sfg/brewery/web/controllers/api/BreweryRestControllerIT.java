package guru.sfg.brewery.web.controllers.api;


import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@SpringBootTest
public class BreweryRestControllerIT extends BaseIT {



    @Test
    void getBreweryHttpBasicAdminRole() throws Exception{
        mockMvc.perform(get("/brewery/breweries")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("jose","Calamardo")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getBreweryHttpBasicCustomarRole() throws Exception{
        mockMvc.perform(get("/brewery/breweries")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("scott","tiger")))
                .andExpect(status().isOk());
    }


    @Test
    void getBreweryNoAuth() throws Exception{
        mockMvc.perform(delete("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void getBreweriesJSOMNoAuth() throws Exception{
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getBreweriesJSONCustomerRole() throws Exception{
        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("scott","tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void getBreweriesJSONAdminRole() throws Exception{
        mockMvc.perform(get("/brewery/api/v1/breweries")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("jose","Calamardo")))
                .andExpect(status().isForbidden());
    }

}