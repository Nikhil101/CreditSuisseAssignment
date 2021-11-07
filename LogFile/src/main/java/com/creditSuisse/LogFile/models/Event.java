package com.creditSuisse.LogFile.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name="Events")
@AllArgsConstructor

public class Event {

	@Id
	private String eventId;
	  private long timestamp;
	  private String type;
	  private String host;
	  private boolean longEventFlag;
	  
	public Event() {
		super();
	}
	  
}
