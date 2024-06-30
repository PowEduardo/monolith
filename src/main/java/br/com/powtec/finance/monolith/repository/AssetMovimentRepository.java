package br.com.powtec.finance.monolith.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.AssetMovimentModel;

@Repository("assetMovimentRepository")
public interface AssetMovimentRepository
        extends MovimentRepository<AssetMovimentModel> {
    List<AssetMovimentModel> findAllByAssetId(Long id);
}
