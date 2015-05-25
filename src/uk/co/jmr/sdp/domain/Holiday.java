package uk.co.jmr.sdp.domain;

import java.util.Date;

public class Holiday {
	private Date date;
	private String description;
	private long id = -1;

	public Holiday() {

		super();
	}

	public Holiday(Date date, String description) {

		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		this.date = date;
		this.description = description;
	}

	public Date getDate() {

		return date;
	}

	public void setDate(Date date) {

		this.date = date;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}
}