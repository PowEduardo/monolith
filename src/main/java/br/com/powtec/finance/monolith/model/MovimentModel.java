package br.com.powtec.finance.monolith.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
@Table(name = "tb_moviments")
public class MovimentModel {

  Date date;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "tp")
  String type;
  @Column(name = "vl")
  Double value;
}
