package br.com.portfolio.client;

import br.com.portfolio.config.FeignSupporterConfig;
import br.com.portfolio.dto.CepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "brasil-api", url = "${url.brasil.api}", configuration = FeignSupporterConfig.class)
public interface BrasilAPIClient {
    @RequestMapping(method = RequestMethod.GET, value = "cep/v1/{cep}")
    CepDTO getCEP(@PathVariable("cep") String cep);
}
