package uk.co.jmr.webforms.db.pojo;

public class ArrayItems implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Arrays arrays;
	private int rank;
	private String value;

	public ArrayItems() {

		super();
		this.id = -1L;
	}

	public ArrayItems(long id, Arrays arrays, int rank, String value) {

		this.id = id;
		this.arrays = arrays;
		this.rank = rank;
		this.value = value;
	}

	public Arrays getArrays() {

		return this.arrays;
	}

	public void setArrays(Arrays arrays) {

		this.arrays = arrays;
	}

	public long getId() {

		return this.id;
	}

	public void setId(long id) {

		this.id = id;
	}

	public int getRank() {

		return this.rank;
	}

	public void setRank(int rank) {

		this.rank = rank;
	}

	public String getValue() {

		return this.value;
	}

	public void setValue(String value) {

		this.value = value;
	}
}
