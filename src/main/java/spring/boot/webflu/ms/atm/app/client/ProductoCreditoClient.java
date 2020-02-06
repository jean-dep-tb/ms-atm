package spring.boot.webflu.ms.atm.app.client;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.atm.app.dto.Cuenta;
import spring.boot.webflu.ms.atm.app.dto.OperacionCuentaCredito;

@Service
public class ProductoCreditoClient {

	private static final Logger log = LoggerFactory.getLogger(Cuenta.class);
	
	@Autowired
	@Qualifier("creditoBanco")
	private WebClient productoCreditoClient;

	public Mono<Cuenta> buscarCuentaCredito(String cuenta_origen,String codigo_bancario) {
		
		log.info("Actualizando: cuenta origen --> deposito credito : "+ cuenta_origen + " cod_bancario: " + codigo_bancario);
		
		Map<String, String> pathVariable = new HashMap<String,String>();
		pathVariable.put("numero_cuenta",cuenta_origen);
		pathVariable.put("codigo_bancario",codigo_bancario);
		
		return productoCreditoClient.get()
				.uri("/numero_cuenta/{numero_cuenta}/{codigo_bancario}",pathVariable)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Cuenta.class);
		
	}
	
}
