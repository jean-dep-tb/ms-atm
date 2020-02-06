package spring.boot.webflu.ms.atm.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import reactor.core.publisher.Flux;
import spring.boot.webflu.ms.atm.app.documents.Atm;
import spring.boot.webflu.ms.atm.app.documents.TipoOperacionAtm;
import spring.boot.webflu.ms.atm.app.service.AtmService;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class SpringBootWebfluMsAtmApplication implements CommandLineRunner{
	
	@Autowired
	private AtmService atmService;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluMsAtmApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluMsAtmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("atmBanco").subscribe();
		
		TipoOperacionAtm retiroBanco = new TipoOperacionAtm("1","retiroBanco");
		TipoOperacionAtm despositoBanco = new TipoOperacionAtm("2","despositoBanco");
		TipoOperacionAtm retiroCredito = new TipoOperacionAtm("3","retiroCredito");
		TipoOperacionAtm depositoCredito = new TipoOperacionAtm("4","depositoCredito");
		
		
		Flux.just(
				new Atm("47305710","191", "900001","900002","bcp","bcp",new Date(),retiroBanco,1.0,2000.0),
				new Atm("47305711","191", "900001","900002","bcp","bcp",new Date(),despositoBanco,2.0,3000.0),
				new Atm("47305712","999", "100001","100002","bbva","bbva",new Date(),retiroCredito,3.0,4000.0),
				new Atm("47305713","999", "100001","100002","bbva","bbva",new Date(),depositoCredito,4.0,5000.0)
				
		).flatMap(atm -> atmService.saveAtm(atm))
			.subscribe(atm -> log.info("insert"+ atm.getCodigo_atm_banco() + atm.getCodigo_bancario_destino() + atm.getCodigo_bancario_origen()));
		
	}

}
