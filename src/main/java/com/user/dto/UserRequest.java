package com.user.dto;

import java.io.Serializable;
import java.util.List;

import com.user.model.Phone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	
    private String email;
    
    private String password;

	private List<Phone> phones;
}
