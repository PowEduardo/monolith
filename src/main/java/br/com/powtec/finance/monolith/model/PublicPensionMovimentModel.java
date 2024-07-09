package br.com.powtec.finance.monolith.model;

import br.com.powtec.finance.monolith.enums.AssetOperationEnum;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_moviments_public_pension")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublicPensionMovimentModel extends MovimentModel {
  AssetOperationEnum operation;
}
