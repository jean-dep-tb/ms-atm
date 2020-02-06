package spring.boot.webflu.ms.atm.app.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import spring.boot.webflu.ms.atm.app.documents.Atm;

public interface atmDao extends ReactiveMongoRepository<Atm, String> {


	
}
