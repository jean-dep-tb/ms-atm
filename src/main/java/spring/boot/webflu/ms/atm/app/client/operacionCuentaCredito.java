package spring.boot.webflu.ms.atm.app.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.atm.app.dto.OperacionCuentaCredito;

@Service
public class operacionCuentaCredito {

	private static final Logger log = LoggerFactory.getLogger(OperacionCuentaCredito.class);
	
	@Autowired
	@Qualifier("operacionCredito")
	private WebClient operacionCreditoClient;
	

	//consumir de la cuenta de op-credito
	public Mono<OperacionCuentaCredito> operacionPagoCredito(OperacionCuentaCredito operacionCuentaCredito) {
		
		System.out.println("opraciones cuentas" + operacionCuentaCredito.toString());
		System.out.println("numero de cuenta -_>>" + operacionCuentaCredito.getNumeroCuenta());
		
		operacionCuentaCredito.setCuenta_origen(operacionCuentaCredito.getNumeroCuenta());
		
		System.out.println(operacionCuentaCredito.getCuenta_origen());
		
		return operacionCreditoClient
				.post()
				   .uri("/pago")
				   .accept(MediaType.APPLICATION_JSON)
				   .contentType(MediaType.APPLICATION_JSON)
				   .body(BodyInserters.fromValue(operacionCuentaCredito))
				   .retrieve()
				   .bodyToMono(OperacionCuentaCredito.class).log();		
		
	}
	
}
