package uk.co.jmr.sdp.domain;

public class UserSession {

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof UserSession) {

			return this.id == ((UserSession) obj).getId();

		}

		return false;
	}

	private long id;
	private String sessionid;
	private String ipaddress;
	private String browser;
	private String deviceos;
	private String version;

	public UserSession(long id, String sessionid, String ipaddress, String browser, String deviceos, String version) {

		super();
		this.id = id;
		this.sessionid = sessionid;
		this.ipaddress = ipaddress;
		this.browser = browser;
		this.deviceos = deviceos;
		this.version = version;
	}

	public UserSession() {

		super();
		// TODO Auto-generated constructor stub
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public String getIpaddress() {

		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {

		this.ipaddress = ipaddress;
	}

	public String getBrowser() {

		return browser;
	}

	public String getSessionid() {

		return sessionid;
	}

	public void setSessionid(String sessionid) {

		this.sessionid = sessionid;
	}

	public void setBrowser(String browser) {

		this.browser = browser;
	}

	public String getDeviceos() {

		return deviceos;
	}

	public void setDeviceos(String deviceos) {

		this.deviceos = deviceos;
	}

}
