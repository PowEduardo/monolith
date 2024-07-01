package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum AssetReturnsOperationEnum {

  DIVIDEND("DIVIDEND"),
  JCP("JCP"),
  REFUND("REFUND"),
  AMORTIZATION("AMORTIZATION");

  private String name;

  AssetReturnsOperationEnum(String name) {
    this.name = name;
  }
}
