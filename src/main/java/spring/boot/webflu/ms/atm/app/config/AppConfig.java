package spring.boot.webflu.ms.atm.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	@Bean
	@Qualifier("client")
	public WebClient registrarWebClient() {
		return WebClient.create("http://localhost:8020/api/Clientes");
	}
	
	@Bean
	@Qualifier("productoBanco")
	public WebClient registrarWebBancoClient() {
		return WebClient.create("http://localhost:8021/api/ProductoBancario");
	}
	
	@Bean
	@Qualifier("creditoBanco")
	public WebClient registrarWebCreditClient() {
		return WebClient.create("http://localhost:8022/api/ProductoCredito");
	}
	
	@Bean
	@Qualifier("operacionBanco")
	public WebClient registrarWebOperacionBanco() {
		return WebClient.create("http://localhost:8023/api/operacionBancaria");
	}
	
	@Bean
	@Qualifier("operacionCredito")
	public WebClient registrarWebOparacionCredito() {
		return WebClient.create("http://localhost:8024/api/OperCuentasCreditos");
	}

}
