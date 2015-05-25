package uk.co.jmr.sdp.domain.ds;

import java.util.ArrayList;
import java.util.List;

public class DMNode {

	private String name;
	private String path;
	private String uuid;
	private List<DMNode> children = new ArrayList<DMNode>();

	public DMNode(String name, String path, String uuid) {

		this.name = name;
		this.path = path;
		this.uuid = uuid;
	}

	public String getName() {

		return name;
	}

	public String getPath() {

		return path;
	}

	public String getUuid() {

		return uuid;
	}

	public List<DMNode> getChildren() {

		return children;
	}

	@Override
	public String toString() {

		return "DMNode [" + path + " - " + name + "]" + ":" + children.size();
	}

	public void print() {

		//System.out.println(this);
		for (DMNode child : children) {

			child.print();
		}
	}

}
