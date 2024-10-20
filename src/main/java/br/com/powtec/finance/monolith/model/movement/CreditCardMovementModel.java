package br.com.powtec.finance.monolith.model.movement;

import java.util.List;

import br.com.powtec.finance.monolith.model.CreditCardInstallmentModel;
import br.com.powtec.finance.monolith.model.CreditCardModel;
import br.com.powtec.finance.monolith.model.MovementModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_movements_credit_card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardMovementModel extends MovementModel {
  private Integer installment;
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  private CreditCardModel card;
  private Boolean paid;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "movement")
  private List<CreditCardInstallmentModel> installments;
}
