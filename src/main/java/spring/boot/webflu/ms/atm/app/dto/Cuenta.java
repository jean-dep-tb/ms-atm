package spring.boot.webflu.ms.atm.app.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import spring.boot.webflu.ms.atm.app.documents.TipoOperacion;

@Getter
@Setter

public class Cuenta {
	
	private String id;
	private String dni;
	private String numeroCuenta;//*numero_cuenta
	private TipoOperacion tipoProducto;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_afiliacion;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_caducidad;
	private double saldo;
	private String usuario;
	private String clave;
	private String codigoBanco;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fecha_afiliacion() {
		return fecha_afiliacion;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fecha_caducidad() {
		return fecha_caducidad;
	}
	
	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", dni=" + dni + ", numeroCuenta=" + numeroCuenta + ", tipoProducto=" + tipoProducto
				+ ", fecha_afiliacion=" + fecha_afiliacion + ", fecha_caducidad=" + fecha_caducidad + ", saldo=" + saldo
				+ ", usuario=" + usuario + ", clave=" + clave + ", codigoBanco=" + codigoBanco + "]";
	}
	

}










