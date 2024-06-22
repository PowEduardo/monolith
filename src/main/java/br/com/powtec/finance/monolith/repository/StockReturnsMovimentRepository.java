package br.com.powtec.finance.monolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.StockReturnsMovimentModel;

@Repository
public interface StockReturnsMovimentRepository
    extends JpaRepository<MovimentModel, Long>, JpaSpecificationExecutor<StockReturnsMovimentModel> {

}
