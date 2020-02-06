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
import spring.boot.webflu.ms.atm.app.dto.OperacionCuentaBanco;

@Service
public class operacionCuentaBanco {

	private static final Logger log = LoggerFactory.getLogger(Cuenta.class);
	
	@Autowired
	@Qualifier("operacionBanco")
	private WebClient operacionBancoClient;
	
	//consumir de la cuenta de banco
	public Mono<OperacionCuentaBanco> despositoBancario(OperacionCuentaBanco operacionBanco) {
		
		System.out.println(operacionBanco.toString());
		
		return operacionBancoClient
					.post()
				   .uri("/deposito")
				   .accept(MediaType.APPLICATION_JSON)
				   .contentType(MediaType.APPLICATION_JSON)
				   .body(BodyInserters.fromValue(operacionBanco))
				   .retrieve()
				   .bodyToMono(OperacionCuentaBanco.class).log();		
		
	}
	
	//consumir de la cuenta de banco
	//public Mono<CurrentAccount> retiroBancario(OperationCurrentAccount op) {
//	public Mono<Cuenta> retiroBancario(String cuenta_origen,Double monto,Double comision,String codigo_bancario_destino) {
//		
//		log.info("Actualizando: cuenta origen --> retiro bancario : "+ cuenta_origen 
//				+ " monto : " + monto + " comision : " + comision + " banco de destino " + codigo_bancario_destino);
//		
//		//.uri("/retiro/{numero_cuenta}/{monto}/{comision}")
//		
//		
//		Map<String, String> pathVariable = new HashMap<String,String>();
//		pathVariable.put("numero_cuenta",cuenta_origen);
//		pathVariable.put("monto",Double.toString(monto));//Casteamos la cantidad para envia en el map
//		pathVariable.put("comision",Double.toString(comision));
//		pathVariable.put("codigo_bancario",codigo_bancario_destino);
//		
//		return productoBancoClient
//					.put()
//				   .uri("/retiro/{numero_cuenta}/{monto}/{comision}/{codigo_bancario}",pathVariable)
//				   .accept(MediaType.APPLICATION_JSON)
//				   .contentType(MediaType.APPLICATION_JSON)
//				   .retrieve()
//				   .bodyToMono(Cuenta.class).log();		
//		
//	}
	
	public Mono<OperacionCuentaBanco> retiroBancario(OperacionCuentaBanco operacionBanco) {
		
		System.out.println(operacionBanco.toString());
		
		return operacionBancoClient
					.post()
				   .uri("/retiro")
				   .accept(MediaType.APPLICATION_JSON)
				   .contentType(MediaType.APPLICATION_JSON)
				   .body(BodyInserters.fromValue(operacionBanco))
				   .retrieve()
				   .bodyToMono(OperacionCuentaBanco.class).log();		
		
	}
	
	public Mono<OperacionCuentaBanco> transferenciaCuenta(OperacionCuentaBanco operacionBanco) {
		
		System.out.println(operacionBanco.toString());
		
		return operacionBancoClient
					.post()
				   .uri("/cuentaACuenta")
				   .accept(MediaType.APPLICATION_JSON)
				   .contentType(MediaType.APPLICATION_JSON)
				   .body(BodyInserters.fromValue(operacionBanco))
				   .retrieve()
				   .bodyToMono(OperacionCuentaBanco.class).log();		
		
	}
	
	
}
