package uk.co.jmr.sdp.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.jmr.sdp.domain.Doctype;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.service.GroupService;

@Controller
@RequestMapping(value = "/groups")
public class GroupController {

	@Autowired
	private GroupService grpGroupService;

	@RequestMapping(value = "/goShowGroups", method = RequestMethod.GET)
	public String showAllGroups(Model model, HttpSession session) {

		List<Group> groupLists = grpGroupService.findFirstLevelGroups();
		model.addAttribute("groupLists", groupLists);
		model.addAttribute("title", "Group Lists");
		return "showGroups";
	}

	@RequestMapping(value = "/goCreateNewGroup", method = RequestMethod.GET)
	public String gocreateNew(@RequestParam("id") long groupId, Model model, HttpSession session) {

		model.addAttribute("title", "Group Creation");
		model.addAttribute("groupId", groupId);
		if (groupId != -1) {
			Group group = grpGroupService.findGroupById(groupId);
			if (group != null) {
				model.addAttribute("title", "Update Group");
				model.addAttribute("groupName", group.getGroupName());
			}
		}
		return "createGroup";
	}

	private List<Group> getGroupList() {

		List<Group> updateGroupLists = grpGroupService.findFirstLevelGroups();
		return updateGroupLists;
	}

	@RequestMapping(value = "/createGroup", method = RequestMethod.POST)
	public String gocreateDocType(@RequestParam("groupId") long oldGroupId, @RequestParam("groupName") String groupName,
		Model model, HttpSession session) {

		groupName = WordUtils.capitalizeFully(groupName).trim();
		Group checkGrpupExists = grpGroupService.findGroupByName(groupName);
		if (checkGrpupExists != null) {
			model.addAttribute("title", "Group Creation");
			model.addAttribute("groupName", groupName);
			model.addAttribute("result", "Group already exists");
			model.addAttribute("groupId", oldGroupId);
			
			return "createGroup";
		}

		if (oldGroupId != -1) {
			// System.out.println("InSide Update");
			Group grpUpdate = grpGroupService.findGroupById(oldGroupId);
			grpUpdate.setGroupName(groupName);
			grpGroupService.saveGroup(grpUpdate);
			model.addAttribute("title", "Group Lists");
			model.addAttribute("groupLists", getGroupList());
			return "showGroups";
		}

		Group parentGroup = null;
		Group group = new Group(groupName, parentGroup, 'Y');
		grpGroupService.saveGroup(group);
		model.addAttribute("title", "Group Lists");
		model.addAttribute("groupLists", getGroupList());
		model.addAttribute("result", "Group Created successfully");
		model.addAttribute("groupId",oldGroupId);
		return "createGroup";
	}

}
