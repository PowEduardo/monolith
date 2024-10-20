package br.com.powtec.finance.monolith.model;

import java.time.YearMonth;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "tb_credit_card_statement")
public class CreditCardStatementModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private YearMonth referenceMonth;
  private Double discounts;
  private Double value;
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  private CreditCardModel card;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "statement")
  private List<CreditCardInstallmentModel> installments;
  private Boolean paid;
}
