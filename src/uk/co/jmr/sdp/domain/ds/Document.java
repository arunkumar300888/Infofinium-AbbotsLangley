package uk.co.jmr.sdp.domain.ds;

public class Document {
	private String id = "";
	private String name = "";
	private String description = "";
	private String url = "";
	private String createDate = "";
	private String title = "";
	private String homeFolder = "";
	private String path = "";

	public String getId() {

		return id;
	}

	public void setId(String id) {

		if (id == null)
			this.id = "";
		else
			this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		if (name == null)
			this.name = "";
		else
			this.name = name;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		if (description == null || description.equalsIgnoreCase("null"))
			this.description = "";
		else
			this.description = description;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		if (url == null)
			this.url = "";
		else
			this.url = url;
	}

	public String getCreateDate() {

		return createDate.substring(0, 10) + " " + createDate.subSequence(10, 16);
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		if (title == null)
			this.title = "";
		else
			this.title = title;
	}

	public String getHomeFolder() {

		return homeFolder;
	}

	public void setHomeFolder(String homeFolder) {

		if (homeFolder == null)
			this.homeFolder = "";
		else
			this.homeFolder = homeFolder;
	}

	public String getPath() {

		return path;
	}

	public void setPath(String path) {

		if (path == null)
			this.path = "";
		else
			this.path = path;
	}

	@Override
	public String toString() {

		return "Document [id=" + id + ", name=" + name + ", description=" + description + ", url=" + url + ", createDate="
			+ createDate + ", title=" + title + ", homeFolder=" + homeFolder + ", path=" + path + "]";
	}

}
