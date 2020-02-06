package spring.boot.webflu.ms.atm.app.documents;


import java.util.Date;

import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

@Document(collection ="atmBanco")
public class Atm {
	

	@Id
	@NotEmpty
	private String dni;
	@NotEmpty
	private String codigo_atm_banco; //SAVER SI PERTENECE A LA MISMA RED DE CAJEROS
	@NotEmpty
	private String cuenta_origen;
	@NotEmpty
	private String cuenta_destino;
	@NotEmpty
	private String codigo_bancario_destino;
	@NotEmpty
	private String codigo_bancario_origen;
	@NotEmpty
	private Date fechaOperacion;
	@NotEmpty
	private TipoOperacionAtm tipoOperacion;
	@NotEmpty
	private Double comisionInterbancaria=0.0;
	@NotEmpty
	private Double soles;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}

	public Atm(String dni,String codigo_atm_banco, String cuenta_origen,
			String cuenta_destino, String codigo_bancario_destino,
			String codigo_bancario_origen,Date fechaOperacion,
			TipoOperacionAtm tipoOperacion, Double comisionInterbancaria, Double soles) {
		this.dni = dni;
		this.codigo_atm_banco = codigo_atm_banco;
		this.cuenta_origen = cuenta_origen;
		this.cuenta_destino = cuenta_destino;
		this.codigo_bancario_destino = codigo_bancario_destino;
		this.codigo_bancario_origen = codigo_bancario_origen;
		this.fechaOperacion = fechaOperacion;
		this.tipoOperacion = tipoOperacion;
		this.comisionInterbancaria = comisionInterbancaria;
		this.soles = soles;
	}
	
	
	
	
}










