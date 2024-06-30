package br.com.powtec.finance.monolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.MovimentModel;

@Repository
public interface MovimentRepository<T extends MovimentModel>
        extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
