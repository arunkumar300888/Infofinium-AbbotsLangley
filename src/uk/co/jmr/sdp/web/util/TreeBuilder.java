package uk.co.jmr.sdp.web.util;

import uk.co.jmr.sdp.domain.ds.DMNode;

public class TreeBuilder {

	public String build(DMNode root) {

		String buf = "";
		for (DMNode node : root.getChildren()) {
			buf = buf + ConvertNode(node);
		}
		return buf;
	}

	private String ConvertNode(DMNode node) {

		String buf = "<li> <img src='resources/images/folder.gif' />" + "<span path='" + node.getPath() + "'>" + node.getName()
			+ "</span>";
		if (node.getChildren().size() > 0) {
			buf = buf + "<ul>";
			for (DMNode child : node.getChildren()) {
				buf = buf + ConvertNode(child);
			}
			buf = buf + "</ul>";
		}
		buf = buf + "</li>";
		return buf;
	}

}
