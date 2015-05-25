package uk.co.jmr.sdp.domain;

import java.util.HashSet;
import java.util.Set;

public class Feature {

	private long id;
	private String featureName;
	private Set<Role> roles = new HashSet<Role>();

	private String description;

	public String getDescription() {

		return description;
	}

	protected void setDescription(String description) {

		this.description = description;
	}

	protected Feature() {

		super();
		this.id = -1;
	}

	public Feature(String featureName) {

		super();
		this.id = -1;
		this.featureName = featureName;
	}

	public Feature(String featureName, String description) {

		super();
		this.id = -1;
		this.featureName = featureName;
		this.description = description;
	}

	public long getId() {

		return id;
	}

	public String getFeatureName() {

		return featureName;
	}

	public Set<Role> getRoles() {

		return roles;
	}

	protected void setId(long id) {

		this.id = id;
	}

	protected void setFeatureName(String featureName) {

		this.featureName = featureName;
	}

	protected void setRoles(Set<Role> roles) {

		this.roles = roles;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((featureName == null) ? 0 : featureName.hashCode());
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
		Feature other = (Feature) obj;
		return (this.id == other.id && this.featureName.equals(other.featureName));
	}

	@Override
	public String toString() {

		return "Feature [id=" + id + ", featureName=" + featureName + "]";
	}

	public boolean addToRoles(Role role) {

		return this.roles.add(role);
	}

	public boolean removeFromRoles(Role role) {

		return this.roles.remove(role);
	}

	public boolean hasRole(Role role) {

		return this.roles.contains(role);
	}

}
