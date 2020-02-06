package spring.boot.webflu.ms.atm.app.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.atm.app.documents.Atm;

public interface AtmService {

	Flux<Atm> findAllAtm();
	Mono<Atm> findByIdAtm(String id);
	Mono<Atm> saveAtm(Atm atm);
	Mono<Void> deleteAtm(Atm cliente);
	
	//OPERACIONES DE CAJERO
	
	Mono<Atm> atmDeposito(Atm atm);
	Mono<Atm> atmRetiro(Atm atm);
	Mono<Atm> atmTransferencia(Atm atm);
	Mono<Atm> atmPagoCredito(Atm atm);

}
