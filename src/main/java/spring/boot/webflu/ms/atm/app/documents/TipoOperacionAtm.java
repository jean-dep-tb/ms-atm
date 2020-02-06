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

@Document(collection ="TipoOperacion")
public class TipoOperacionAtm {

	@NotEmpty
	private String idTipo;
	@NotEmpty
	private String descripcion;
	
	public TipoOperacionAtm(String idTipo,String descripcion) {
		
		this.idTipo = idTipo;
		this.descripcion = descripcion;
		
	}
	
	public TipoOperacionAtm() {
		
	}

	
	
}










