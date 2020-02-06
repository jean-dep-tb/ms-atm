package spring.boot.webflu.ms.atm.app.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Flux;
import spring.boot.webflu.ms.atm.app.documents.TipoOperacion;

@Getter
@Setter

public class OperacionCuentaCredito {

	
	private String dni;
	
	private String codigoBanco;
	private String cuenta_origen;
	private String numeroCuenta;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaOperacion;
	
	private TipoOperacion tipoOperacion;
	
	private Double montoPago;
	

	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}



	@Override
	public String toString() {
		return "OperacionCuentaCredito [dni=" + dni + ", codigoBanco=" + codigoBanco + ", numeroCuenta=" + numeroCuenta
				+ ", fechaOperacion=" + fechaOperacion + ", tipoOperacion=" + tipoOperacion + ", montoPago=" + montoPago
				+ "]";
	}
	
	
	
}










