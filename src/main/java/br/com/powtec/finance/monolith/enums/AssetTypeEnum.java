package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum AssetTypeEnum {

  STOCK("STOCK"),
  REIT("REIT"),
  DIRECT_TREASURE("DIRECT_TREASURE"),
  PENSION("PENSION"),
  PUBLIC_PENSION("PUBLIC_PENSION"),
  FIXED_INCOME("FIXED_INCOME");

  private String name;

  AssetTypeEnum(String name) {
    this.name = name;
  }
}
