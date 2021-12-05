package com.user.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException {

	private String mensaje;

    public UserException() {
	}

	public UserException(String mensaje) {
		this.mensaje = mensaje;
	}

}