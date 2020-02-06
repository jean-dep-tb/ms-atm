package spring.boot.webflu.ms.atm.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring.boot.webflu.ms.atm.app.documents.Atm;
import spring.boot.webflu.ms.atm.app.documents.TipoOperacionAtm;
import spring.boot.webflu.ms.atm.app.service.AtmService;
import spring.boot.webflu.ms.atm.app.client.ProductoBancoClient;
import spring.boot.webflu.ms.atm.app.client.ProductoCreditoClient;
import spring.boot.webflu.ms.atm.app.client.operacionCuentaBanco;
import spring.boot.webflu.ms.atm.app.client.operacionCuentaCredito;
import spring.boot.webflu.ms.atm.app.dao.atmDao;
import spring.boot.webflu.ms.atm.app.dto.Cuenta;
import spring.boot.webflu.ms.atm.app.dto.OperacionCuentaBanco;
import spring.boot.webflu.ms.atm.app.dto.OperacionCuentaCredito;
import spring.boot.webflu.ms.atm.app.exception.RequestException;

@Service
public class AtmServiceImpl implements AtmService {

	@Autowired
	private ProductoBancoClient productoBancoClient;
	
	@Autowired
	private operacionCuentaBanco operacionCuentaBancoCliente;
	
	@Autowired
	private ProductoCreditoClient productoCreditoClient;
	
	@Autowired
	private operacionCuentaCredito operacionCuentaCredito;
	
	@Autowired
	public atmDao atmDao;
	
	@Override
	public Flux<Atm> findAllAtm()
	{
	return atmDao.findAll();
	
	}
	@Override
	public Mono<Atm> findByIdAtm(String id)
	{
		return atmDao.findById(id);
	
	}
	
	@Override
	public Mono<Atm> saveAtm(Atm atm) {
		return atmDao.save(atm);
	}
	
	@Override
	public Mono<Void> deleteAtm(Atm atm) {
		return atmDao.delete(atm);
	}
	
	//OPARACIONDE DE BANCO
	
	@Override
	public Mono<Atm> atmDeposito(Atm atm) {
		
		Mono<Atm> operacion = Mono.just(atm);
		
		return operacion.flatMap(a -> {
			
			TipoOperacionAtm tipo = new TipoOperacionAtm();
			tipo.setIdTipo("1");
			tipo.setDescripcion("deposito");
			
			atm.setTipoOperacion(tipo);
			
			//BUSCAR EL NUMERO DE CUENTA
			Mono<Cuenta> operacion1 = productoBancoClient.findByNumeroCuenta(atm.getCuenta_origen(),atm.getCodigo_bancario_origen());
			
			return operacion1.defaultIfEmpty(new Cuenta()).flatMap(p->{
				
				if(p.getNumeroCuenta()==null){ //if(p.getNumero_cuenta()==null){
					throw new RequestException("LA CUENTA NO ESTA ASOCIADA AL BANCO");
				}else {
					
					return operacion1.flatMap(op -> {
						
						//VERIFICAR QUE LA CUENTA SEA DEL MISMO BANCO DE ORIGEN
						if(!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_bancario_origen())) { 
							throw new RequestException("LA CUENTA NO PERTENCE AL MISMO BANCO");
						}else {
							//VERIFICAR SI PERTENCE A LA MISMA RED DE BANCOS - COBRAR COMISION
							if (!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_atm_banco())) {
								//CODIGO DE ATM BANCO BCP = 192
								if (atm.getCodigo_atm_banco().equalsIgnoreCase("bcp")) {
									atm.setComisionInterbancaria(0.5);
								}else if(atm.getCodigo_atm_banco().equalsIgnoreCase("bbva")) {
									atm.setComisionInterbancaria(1.0);
								}
								
							}
							
						}
						
						//REGISTRAR LA OPERACION
						
						OperacionCuentaBanco opBanco = new OperacionCuentaBanco();
						opBanco.setDni(a.getDni());
						opBanco.setCodigo_bancario_origen(a.getCodigo_bancario_origen());
						opBanco.setCuenta_origen(a.getCuenta_origen());

						opBanco.setCodigo_bancario_destino(".");
						opBanco.setCuenta_destino(".");

						opBanco.setFechaOperacion(a.getFechaOperacion());//new date()
						opBanco.setMontoPago(a.getSoles());
						//opBanco.setComision(a.getComisionInterbancaria());
						
						//REALIZA EL DEPOSITO Y GRABAR EL MOVIMIENTO				
						Mono<OperacionCuentaBanco> operacion2 = operacionCuentaBancoCliente.despositoBancario(opBanco);
						
						return operacion2.flatMap(o -> {
							if (o.getDni().equals("")) {
								Mono.empty();
								
							}
							return atmDao.save(atm);
							
						});
						
					});
				}
				
			});
			
				
	});
}
		
	@Override
	public Mono<Atm> atmRetiro(Atm atm) {

		Mono<Atm> oper = Mono.just(atm);
		
		return oper.flatMap(a -> {
			
			TipoOperacionAtm tipo = new TipoOperacionAtm();
			tipo.setIdTipo("2");
			tipo.setDescripcion("retiro");
			
			atm.setTipoOperacion(tipo);
			
			//BUSCAR EL NUMERO DE CUENTA
			Mono<Cuenta> operacion1 = productoBancoClient.findByNumeroCuenta(atm.getCuenta_origen(),atm.getCodigo_bancario_origen());
			
			return operacion1.flatMap(op -> {
				
				if (!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_bancario_origen())) {
					throw new RequestException("LA CUENTA NO PERTENCE AL MISMO BANCO");
				} else {
					if (!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_atm_banco())) {

						if (atm.getCodigo_atm_banco().equalsIgnoreCase("bcp")) {
							atm.setComisionInterbancaria(1.50);

						} else if (atm.getCodigo_atm_banco().equalsIgnoreCase("bbva")) {
							atm.setComisionInterbancaria(2.50);

						}
					}
				}
				
				//REGISTRAR LA OPERACION
				
				OperacionCuentaBanco opBanco = new OperacionCuentaBanco();
				opBanco.setDni(a.getDni());
				opBanco.setCodigo_bancario_origen(a.getCodigo_bancario_origen());
				opBanco.setCuenta_origen(a.getCuenta_origen());

				opBanco.setCodigo_bancario_destino(".");
				opBanco.setCuenta_destino(".");

				opBanco.setFechaOperacion(a.getFechaOperacion());//new date()
				opBanco.setMontoPago(a.getSoles());
				opBanco.setComision(a.getComisionInterbancaria());
				
				//REALIZA EL DEPOSITO Y GRABAR EL MOVIMIENTO				
				Mono<OperacionCuentaBanco> operacion2 = operacionCuentaBancoCliente.retiroBancario(opBanco);
				
				return operacion2.flatMap(o -> {
					if (o.getDni().equals("")) {
						Mono.empty();
						
					}
					return atmDao.save(atm);
					
				});
				
			});
			
		});
	
	}
	@Override
	public Mono<Atm> atmTransferencia(Atm atm) {
		
		Mono<Atm> oper = Mono.just(atm);
		
		return oper.flatMap(a -> {
			
			TipoOperacionAtm tipo = new TipoOperacionAtm();
			tipo.setIdTipo("3");
			tipo.setDescripcion("transferiaCuenta");
			
			atm.setTipoOperacion(tipo);
			
			//BUSCAR EL NUMERO DE CUENTA
			Mono<Cuenta> operacion1 = productoBancoClient.findByNumeroCuenta(atm.getCuenta_origen(),atm.getCodigo_bancario_origen());
			
			return operacion1.flatMap(op -> {
				
				if (!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_bancario_origen())) {
					throw new RequestException("LA CUENTA NO PERTENCE AL MISMO BANCO");
				} else {
					if (!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_atm_banco())) {

						if (atm.getCodigo_atm_banco().equalsIgnoreCase("bcp")) {
							atm.setComisionInterbancaria(1.50);

						} else if (atm.getCodigo_atm_banco().equalsIgnoreCase("bbva")) {
							atm.setComisionInterbancaria(2.50);

						}
					}
				}
				
				//REGISTRAR LA OPERACION
				
				OperacionCuentaBanco opBanco = new OperacionCuentaBanco();
				opBanco.setDni(a.getDni());
				opBanco.setCodigo_bancario_origen(a.getCodigo_bancario_origen());
				opBanco.setCuenta_origen(a.getCuenta_origen());
				opBanco.setCodigo_bancario_destino(a.getCodigo_bancario_destino());
				opBanco.setCuenta_destino(a.getCuenta_destino());
				opBanco.setFechaOperacion(a.getFechaOperacion()); //new Date()
				opBanco.setMontoPago(a.getSoles());
				opBanco.setComision(a.getComisionInterbancaria());
				
				//REALIZA EL DEPOSITO Y GRABAR EL MOVIMIENTO				
				Mono<OperacionCuentaBanco> operacion2 = operacionCuentaBancoCliente.transferenciaCuenta(opBanco);
				
				return operacion2.flatMap(o -> {
					if (o.getDni().equals("")) {
						Mono.empty();
						
					}
					return atmDao.save(atm);
					
				});
				
			});
			
		});
		
	}
	
	
	@Override
	public Mono<Atm> atmPagoCredito(Atm atm) {
		
		Mono<Atm> oper = Mono.just(atm);
		
		return oper.flatMap(a -> {
			
			TipoOperacionAtm tipo = new TipoOperacionAtm();
			tipo.setIdTipo("4");
			tipo.setDescripcion("pagoCredito");
			
			atm.setTipoOperacion(tipo);
			
			//BUSCAR EL NUMERO DE CUENTA
			Mono<Cuenta> operacion1 = productoCreditoClient.buscarCuentaCredito(atm.getCuenta_origen(),atm.getCodigo_bancario_origen());
			
			return operacion1.defaultIfEmpty(new Cuenta()).flatMap(p->{
				
				System.out.println("El numero de cuenta es :" + p.getNumeroCuenta());
				System.out.println("La cuenta :" + p.toString());
				
				if(p.getNumeroCuenta()==null){ //if(p.getNumero_cuenta()==null){
					throw new RequestException("LA CUENTA NO EXISTE");
				}else {
					
					return operacion1.flatMap(op -> {
						
						if (!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_bancario_origen())) {
							throw new RequestException("LA CUENTA NO PERTENCE AL MISMO BANCO");
						} else {
							if (!op.getCodigoBanco().equalsIgnoreCase(atm.getCodigo_atm_banco())) {

								if (atm.getCodigo_atm_banco().equalsIgnoreCase("bcp")) {
									atm.setComisionInterbancaria(1.50);

								} else if (atm.getCodigo_atm_banco().equalsIgnoreCase("bbva")) {
									atm.setComisionInterbancaria(2.50);

								}
							}
						}
						
						//REGISTRAR LA OPERACION
						OperacionCuentaCredito cd = new OperacionCuentaCredito();
						cd.setDni(a.getDni());
						cd.setCodigoBanco(a.getCodigo_bancario_origen());
						cd.setNumeroCuenta(a.getCuenta_origen());			
						cd.setFechaOperacion(a.getFechaOperacion());
						cd.setMontoPago(a.getSoles());		
						
						
						//REALIZA EL DEPOSITO Y GRABAR EL MOVIMIENTO				
						Mono<OperacionCuentaCredito> operacion2 = operacionCuentaCredito.operacionPagoCredito(cd);
						
						return operacion2.flatMap(o -> {
							if (o.getDni().equals("")) {
								Mono.empty();
								
							}
							return atmDao.save(atm);
							
						});
						
					});
					
					
					
					
				}
				
			});
			
			
			
			
			
		});
		
	}
	
	
	
}
