package dev.cryss.springboot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dev.cryss.springboot.model.Telefone;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {
	
	@Query("select t from Telefone t where t.pessoa.id = ?1")
	public List<Telefone> getTelefones(Long idpessoa);
	
	
}
