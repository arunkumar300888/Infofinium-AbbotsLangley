package uk.co.jmr.sdp.domain;

public class Doctype implements Comparable<Doctype> {

	private long id;
	private String doctypeName;
	private String description;
	private char isActive;
	private String version;
	
	public char getIsActive() {

		return isActive;
	}

	public void setIsActive(char isActive) {

		this.isActive = isActive;
	}

	private boolean workflow;
	private String abbreviation;

	public String getAbbreviation() {

		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {

		this.abbreviation = abbreviation;
	}

	public boolean isWorkflow() {

		return workflow;
	}

	public void setWorkflow(boolean workflow) {

		this.workflow = workflow;
	}

	public Doctype(String doctypeName, String description, String abbreviation, boolean workflow) {

		this.id = -1;
		this.doctypeName = doctypeName;
		this.description = description;
		this.abbreviation = abbreviation;
		this.workflow = workflow;
	}

	public Doctype(String doctypeName, String description, String abbreviation, boolean workflow, char isActive,String version) {

		this.id = -1;
		this.doctypeName = doctypeName;
		this.description = description;
		this.abbreviation = abbreviation;
		this.workflow = workflow;
		this.isActive = isActive;
		this.version=version;
	}

	public Doctype(String doctypeName, String description, boolean workflow) {

		this.id = -1;
		this.doctypeName = doctypeName;
		this.description = description;
		this.workflow = workflow;
	}

	protected Doctype() {

		this.id = -1;
	}

	public Doctype(String doctypeName, String description) {

		this.id = -1;
		this.doctypeName = doctypeName;
		this.description = description;
	}

	public long getId() {

		return id;
	}

	public String getDoctypeName() {

		return doctypeName;
	}

	public String getDescription() {

		return description;
	}

	protected void setId(long id) {

		this.id = id;
	}

	// Change Protected to Public For Admin Doc Type to Update

	// protected void setDoctypeName(String doctypeName) {
	public void setDoctypeName(String doctypeName) {

		this.doctypeName = doctypeName;
	}

	// protected void setDescription(String description) {
	public void setDescription(String description) {

		this.description = description;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((doctypeName == null) ? 0 : doctypeName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctype other = (Doctype) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (doctypeName == null) {
			if (other.doctypeName != null)
				return false;
		}
		else if (!doctypeName.equals(other.doctypeName))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Doctype [id=" + id + ", doctypeName=" + doctypeName + ", description=" + description + "]";
	}

	@Override
	public int compareTo(Doctype o) {

		// TODO Auto-generated method stub
		return this.getDoctypeName().compareTo(o.getDoctypeName());
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
