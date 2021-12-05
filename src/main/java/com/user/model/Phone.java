package com.user.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="PHONE")
public class Phone implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
    @Column(name = "NUMBER")
	private String number;
	
    @Column(name = "CITY_CODE")
    private String citycode;
    
    @Column(name = "COUNTRY_CODE")
    private String countrycode;
	
    @ManyToOne()
	@JoinColumn(name = "ID_USER", referencedColumnName = "ID", nullable = false)
	@JsonBackReference
	private Users user;
}
