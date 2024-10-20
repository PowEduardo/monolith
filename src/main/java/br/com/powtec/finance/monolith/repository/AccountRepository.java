package br.com.powtec.finance.monolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.AccountModel;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long>, JpaSpecificationExecutor<AccountModel>{
}
