package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum AssetOperationEnum {

  BUY("BUY"),
  SELL("SELL"),
  SPLIT("SPLIT"),
  CONTRIBUTION("CONTRIBUTION"),
  PORTABILITY("PORTABILITY"),
  CREDIT_ENTRY("CREDIT_ENTRY"),
  DEPOSIT("DEPOSIT"),
  JAM("JAM"),
  WITHDRAW("WITHDRAW");

  private String name;

  AssetOperationEnum(String name) {
    this.name = name;
  }
}
