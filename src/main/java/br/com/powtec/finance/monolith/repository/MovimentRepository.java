package br.com.powtec.finance.monolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.StockMovimentModel;

@Repository
public interface MovimentRepository
                extends JpaRepository<MovimentModel, Long>, JpaSpecificationExecutor<StockMovimentModel> {
}
