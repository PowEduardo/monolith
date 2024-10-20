package br.com.powtec.finance.monolith.model;

import java.time.LocalDate;

import br.com.powtec.finance.monolith.enums.MovementTypeEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_movements")
public class MovementModel {

  LocalDate date;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "tp")
  @Enumerated(EnumType.STRING)
  MovementTypeEnum type;
  @Column(name = "vl")
  Double value;
  @Column(length = 100)
  String description;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, optional = true)
  AccountModel account;
}
