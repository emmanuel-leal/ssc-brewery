package guru.sfg.brewery.web.controllers.api;


import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.controllers.BaseIT;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@SpringBootTest
public class BeerRestControllerIT extends BaseIT {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests{

        public Beer beerToDelete(){
            Random rand = new Random();

            return beerRepository.saveAndFlush(Beer.builder()
                    .beerName("Delete Me Beer")
                    .beerStyle(BeerStyleEnum.IPA)
                    .minOnHand(12)
                    .quantityToBrew(200)
                    .upc(String.valueOf(rand.nextInt(99999999)))
                    .build());
        }

        @Test
        void deleteBeer() throws Exception{
            mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                            .header("Api-Key", "jose").header("Api-secret", "Calamardo"))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerHttpBasic() throws Exception{
            mockMvc.perform(delete("/api/v1/beer/"+ beerToDelete().getId())
                            .with(httpBasic("jose","Calamardo")))
                    .andExpect(status().isNoContent());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception{
            mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                            .with(httpBasic("user","password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicCustomarRole() throws Exception{
            mockMvc.perform(delete("/api/v1/beer/97df0c39-90c4-4ae0-b663-453e8e19c311")
                            .with(httpBasic("scott","tiger")))
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
    }



    @Test
    void findBeers() throws Exception{
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception{
        Beer beer = beerRepository.findAll().get(0);

        mockMvc.perform(get("/api/v1/beer/"+beer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception{
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerFormADMIN() throws Exception {
        mockMvc.perform(get("/beers").param("beerName","")
                .with(httpBasic("spring","password")))
                .andExpect(status().isOk());
    }
}