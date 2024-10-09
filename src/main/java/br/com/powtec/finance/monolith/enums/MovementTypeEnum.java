package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum MovementTypeEnum {
  ASSET_RETURN("ASSET_RETURN"),
  ASSET_MOVEMENT("ASSET_MOVEMENT");

  private String name;

  MovementTypeEnum(String name) {
    this.name = name;
  }
}
