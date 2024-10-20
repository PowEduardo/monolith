package br.com.powtec.finance.monolith.model;

import br.com.powtec.finance.monolith.enums.EntryTypeEnum;
import br.com.powtec.finance.monolith.model.movement.CreditCardMovementModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_credit_card_installment")
public class CreditCardInstallmentModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Integer installment;
  private Double value;
  @Enumerated(EnumType.STRING)
  private EntryTypeEnum entryType;
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private CreditCardStatementModel statement;
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
  private CreditCardMovementModel movement;
}
