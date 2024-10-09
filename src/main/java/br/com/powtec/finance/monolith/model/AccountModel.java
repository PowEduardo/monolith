package br.com.powtec.finance.monolith.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@Entity(name = "tb_account")
public class AccountModel {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String bank;
  private String number;
  private String branch;
  @Column(name = "isPrimary")
  private Boolean primary;
  private LocalDate createDate;
  @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "account", fetch = FetchType.LAZY, orphanRemoval = false)
  private List<MovementModel> movements;
} 
