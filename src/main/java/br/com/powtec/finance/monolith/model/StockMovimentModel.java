package br.com.powtec.finance.monolith.model;

import br.com.powtec.finance.monolith.enums.StockOperationEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_moviments_stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockMovimentModel extends MovimentModel {

  Integer amount;
  String ticker;
  @Enumerated(EnumType.STRING)
  StockOperationEnum operation;

}
