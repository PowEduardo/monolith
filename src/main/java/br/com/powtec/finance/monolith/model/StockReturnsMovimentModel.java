package br.com.powtec.finance.monolith.model;

import br.com.powtec.finance.monolith.enums.StockReturnsOperationEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_moviments_stock_returns")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockReturnsMovimentModel extends MovimentModel {

  private Integer amount;
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  private AssetModel stock;
  @Enumerated(EnumType.STRING)
  private StockReturnsOperationEnum operation;
  private Double unitValue;
}
