package br.com.powtec.finance.monolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.MovimentModel;

@Repository
public interface MovimentRepository extends JpaRepository<MovimentModel, Long> {
}
