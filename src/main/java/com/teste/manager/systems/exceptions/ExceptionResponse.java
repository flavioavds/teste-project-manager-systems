package com.teste.manager.systems.exceptions;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Date timestamp;
	private Integer status;
	private String message;
	private String details;

}
