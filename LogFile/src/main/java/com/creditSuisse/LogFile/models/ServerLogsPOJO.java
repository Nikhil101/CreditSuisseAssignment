package com.creditSuisse.LogFile.models;



import lombok.Data;


@Data
public class ServerLogsPOJO {

	private String id;
	private String state;
	private String type;
	private String host;
	private Long timestamp;
	
}
