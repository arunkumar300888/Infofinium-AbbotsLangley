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

import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.service.RoleService;

@Controller
@RequestMapping(value = "/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/goShowRoles", method = RequestMethod.GET)
	public String showAllRoles(Model model, HttpSession session) {

		// List<Role> roleLists=roleService.findAllRoles();
		List<Role> roleLists = getRoleList();
		model.addAttribute("roleLists", roleLists);
		model.addAttribute("title", "Role Lists");
		return "showRoles";
	}

	@RequestMapping(value = "/goCreateNewRole", method = RequestMethod.GET)
	public String gocreateNew(@RequestParam("id") long roleId, Model model, HttpSession session) {

		model.addAttribute("title", "Role Creation");
		model.addAttribute("roleId", roleId);
		if (roleId != -1) {
			Role role = roleService.findRoleById(roleId);
			if (role != null) {
				model.addAttribute("title", "Role Updation");
				model.addAttribute("roleName", role.getRoleName());
			}
		}
		return "createRole";
	}

	@RequestMapping(value = "/goDeleteRole", method = RequestMethod.GET)
	public String goDeleteExistingRole(@RequestParam("id") long roleId, Model model, HttpSession session) {

		Role roleToDelete = roleService.findRoleById(roleId);
		roleToDelete.setIsActive('N');
		roleService.saveRole(roleToDelete);
		model.addAttribute("title", "Role Lists");
		model.addAttribute("roleLists", getRoleList());
		return "showRoles";
	}

	@RequestMapping(value = "/goUnDeleteRole", method = RequestMethod.GET)
	public String goUnDeleteExistingRole(@RequestParam("id") long roleId, Model model, HttpSession session) {

		Role roleToUnDelete = roleService.findRoleById(roleId);
		roleToUnDelete.setIsActive('Y');
		roleService.saveRole(roleToUnDelete);
		model.addAttribute("title", "Role Lists");
		model.addAttribute("roleLists", getRoleList());
		return "showRoles";
	}

	private List<Role> getRoleList() {

		// List<Role> updateRoleLists=roleService.findAllRoles();
		List<Role> updateRoleLists = roleService.findAllRolesWithInActive();
		return updateRoleLists;
	}

	@RequestMapping(value = "/createRole", method = RequestMethod.POST)
	public String gocreateRole(@RequestParam("roleId") long oldRoleId, @RequestParam("roleName") String roleName, Model model,
		HttpSession session) {

		roleName = WordUtils.capitalizeFully(roleName).trim();
		Role checkRoleExists = roleService.findRoleByRoleName(roleName);
		if (checkRoleExists != null) {
			if (checkRoleExists.getIsActive() == 'N') {
				model.addAttribute("title", "Inactive Role Exists");
				model.addAttribute("result", "Please use a different role name");
				model.addAttribute("roleName", roleName);
				model.addAttribute("roleId", oldRoleId);

			}

			model.addAttribute("title", "Role Creation");
			model.addAttribute("roleName", roleName);
			model.addAttribute("result", "Role already exists");
			model.addAttribute("roleId", oldRoleId);
			return "createRole";
		}

		if (oldRoleId != -1) {
			Role roleUpdate = roleService.findRoleById(oldRoleId);
			roleUpdate.setRoleName(roleName);
			roleService.saveRole(roleUpdate);
			model.addAttribute("title", "Role Lists");
			model.addAttribute("roleLists", getRoleList());
			return "showRoles";
		}

		Role role = new Role(roleName, 'Y');
		roleService.saveRole(role);
		model.addAttribute("title", "Role Creation");
		model.addAttribute("roleId", oldRoleId);
		model.addAttribute("result", "Role created successfully");
		// model.addAttribute("roleLists", getRoleList());
		return "createRole";
	}
	
	
	
	
	
	
}
