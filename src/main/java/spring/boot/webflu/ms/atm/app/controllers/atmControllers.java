package spring.boot.webflu.ms.atm.app.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.atm.app.documents.Atm;
import spring.boot.webflu.ms.atm.app.service.AtmService;

@RequestMapping("/api/atm")
@RestController
public class atmControllers {

	@Autowired
	private AtmService bancoService;

	@GetMapping
	public Mono<ResponseEntity<Flux<Atm>>> findAll() {
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				.body(bancoService.findAllAtm())

		);
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Atm>> viewId(@PathVariable String id) {
		return bancoService.findByIdAtm(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping
	public Mono<Atm> update(@RequestBody Atm atm) {
		System.out.println(atm.toString());
		return bancoService.saveAtm(atm);
	}
	
	@PostMapping
	public Mono<Atm> guardar(@RequestBody Atm atm) {
		return bancoService.saveAtm(atm);
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		return bancoService.findByIdAtm(id)
				.flatMap(s -> {
			return bancoService.deleteAtm(s).
					then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
	
	//OPERACIONES DEL CAJERO
	
	@PostMapping("/deposito")
	public Mono<Atm> depositoBancario(@RequestBody Atm atm) {
			return bancoService.atmDeposito(atm);
	}
	
	@PostMapping("/retiro")
	public Mono<Atm> repositoBancario(@RequestBody Atm atm) {
		return bancoService.atmRetiro(atm);

	}
	
	@PostMapping("/transferenciaBanco")
	public Mono<Atm> guardarMovTransf(@RequestBody Atm atm) {
			return bancoService.atmTransferencia(atm);
	}
	
	@PostMapping("/pagoCuentaCredito")
	public Mono<Atm> guardarMovPagos(@RequestBody Atm atm) {
			return bancoService.atmPagoCredito(atm);

	}
	
	
	
}


