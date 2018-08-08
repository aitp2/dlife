package com.aitp.dlife.service.enums;

public enum Status {
          OPEND(1), IN_PROGRESS(2),END(0);


private Integer value;

private Status(Integer value) {
	this.value = value;
}
  public static Status prease(Integer value){
	  for (Status status :values()) {
		if (status.value.equals(value)) {
			return status;
		}
	}
	  return null;
  }
public Integer getValue() {
	return value;
}
public void setValue(Integer value) {
	this.value = value;
}

  
  
}