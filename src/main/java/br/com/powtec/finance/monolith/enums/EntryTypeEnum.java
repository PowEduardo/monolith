package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum EntryTypeEnum {
  DISCOUNT("DISCOUNT"),
  INSTALLMENT("INSTALLMENT");

  private String name;

  EntryTypeEnum(String name) {
    this.name = name;
  }
}
