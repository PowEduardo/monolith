package br.com.powtec.finance.monolith.model;

import br.com.powtec.finance.monolith.enums.IndexerEnum;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_asset_fixed_income")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetFixedIncomeModel extends AssetModel {

  private IndexerEnum indexer;
  private Double interestRate;
}
