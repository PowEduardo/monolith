package br.com.powtec.finance.monolith.model;

import br.com.powtec.finance.monolith.enums.StockReturnsOperationEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
  private String ticker;
  @Enumerated(EnumType.STRING)
  private StockReturnsOperationEnum operation;
  private Double unitValue;
}
