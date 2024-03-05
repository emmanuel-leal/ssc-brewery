package guru.sfg.brewery.web.controllers.api;


import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeer() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                .header("Api-Key", "jose").header("Api-secret", "Calamardo"))
                 .andExpect(status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("jose","Calamardo")))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBeerHttpBasicUserRole() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user","password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerHttpBasicCustomarRole() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("scott","tiger")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerNoAuth() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerBadCreds() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .header("Api-Key", "jose").header("Api-secret", "Calamardos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerParams() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311?apikey=jose&apisecret=Calamardo"))
                .andExpect(status().isOk());
    }

    // es lo mismo que el metodo de arriba
    @Test
    void deleteBeerParamsAndHeader() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                        .param("apikey","jose").param("apisecret", "Calamardo"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCredsParams() throws Exception{
        mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311?apikey=jose&apisecret=Calamardos"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception{
        mockMvc.perform(get("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception{
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }
}