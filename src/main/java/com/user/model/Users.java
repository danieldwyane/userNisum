package com.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="USER")
public class Users implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;

	@Column(name = "CREATED")
    private Date created;

	@Column(name = "MODIFIED")
    private Date modified;

	@Column(name = "LAST_LOGIN")
    private Date lastLogin;

	@Column(name = "TOKEN", length = 1000)
    private String token;

	@Column(name = "IS_ACTIVE")
    private Boolean isActive;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Phone> phones;
}
