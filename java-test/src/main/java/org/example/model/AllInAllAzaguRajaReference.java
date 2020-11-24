package org.example.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AllInAllAzaguRajaReference {

  @NotNull
  @Size(max = 80)
  private String code;

  @Size(max = 80)
  private String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
