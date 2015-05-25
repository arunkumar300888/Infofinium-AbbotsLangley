package uk.co.jmr.sdp.domain;

public class Discipline {
	private long id;
	private String disciplineName;

	protected Discipline() {

		super();
		this.id = -1;
	}

	public Discipline(String disciplineName) {

		super();
		this.id = -1;
		this.disciplineName = disciplineName;
	}

	public long getId() {

		return id;
	}

	public String getDisciplineName() {

		return disciplineName;
	}

	protected void setId(long id) {

		this.id = id;
	}

	protected void setDisciplineName(String DisciplineName) {

		this.disciplineName = DisciplineName;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((disciplineName == null) ? 0 : disciplineName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Role))
			return false;
		Discipline other = (Discipline) obj;
		return (this.id == other.id && this.disciplineName.equals(other.disciplineName));
	}

	@Override
	public String toString() {

		return "Discipline [id=" + id + ", disciplineName=" + disciplineName + "]";
	}

}
