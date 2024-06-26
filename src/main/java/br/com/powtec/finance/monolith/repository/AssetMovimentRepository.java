package br.com.powtec.finance.monolith.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.MovimentModel;
import br.com.powtec.finance.monolith.model.AssetMovimentModel;

@Repository
public interface AssetMovimentRepository
        extends JpaRepository<MovimentModel, Long>, JpaSpecificationExecutor<AssetMovimentModel> {
    List<AssetMovimentModel> findAllByAssetId(Long id);
}
