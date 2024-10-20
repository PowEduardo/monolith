package br.com.powtec.finance.monolith.model.dto;

import java.time.LocalDate;

import br.com.powtec.finance.monolith.enums.AssetOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AssetMovimentDTO extends MovimentDTO {

  AssetDTO asset;
  Double amount;
  AssetOperationEnum operation;
  Double unitValue;
  LocalDate dueDate;

}
