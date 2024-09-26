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
public class AssetMovementDTO extends MovementDTO {

  AssetDTO asset;
  Double amount;
  AssetOperationEnum operation;
  Double unitValue;
  LocalDate dueDate;

}
