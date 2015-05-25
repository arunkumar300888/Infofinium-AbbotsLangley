package uk.co.jmr.sdp.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.jmr.sdp.domain.Feature;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.service.FeatureService;
import uk.co.jmr.sdp.service.RoleService;

@Controller
@RequestMapping(value = "/features")
public class FeatureController {
	@Autowired
	private FeatureService featureService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/goShowFeatures", method = RequestMethod.GET)
	public String showAllRoles(Model model, HttpSession session) {

		List<Feature> featureLists = featureService.listAllFeatures();
		model.addAttribute("featureLists", featureLists);
		model.addAttribute("title", "Feature Lists");
		return "showFeatures";
	}

	private void showFeatureInfo(long featureId, Model model) {

		List<Role> roleLists = roleService.findAllRoles();
		Feature feature = featureService.findFeatureById(featureId);
		Set<Role> featureRoleLists = feature.getRoles();
		model.addAttribute("title", "Access Roles");
		model.addAttribute("roleLists", roleLists);
		model.addAttribute("featureRoleLists", featureRoleLists);
		model.addAttribute("featureName", feature.getFeatureName());
		model.addAttribute("description", feature.getDescription());
		model.addAttribute("featureId", feature.getId());
	}

	@RequestMapping(value = "/showAddNewRoleToFeature", method = RequestMethod.GET)
	public String showAddNew(@RequestParam("id") long featureId, Model model, HttpSession session) {

		showFeatureInfo(featureId, model);
		return "addNewRoleToFeature";
	}

	@RequestMapping(value = "/goAddRoleToFeature", method = RequestMethod.GET)
	public String addRoleToFeature(@RequestParam("roleId") long roleId, @RequestParam("featureId") long featureId, Model model) {

		featureService.addAccessForRole(featureId, roleId);
		showFeatureInfo(featureId, model);
		return "addNewRoleToFeature";
	}

	@RequestMapping(value = "/goDeleteRoleToFeature", method = RequestMethod.GET)
	public String deleteRoleToFeature(@RequestParam("roleId") long roleId, @RequestParam("featureId") long featureId, Model model) {

		featureService.removeAccessForRole(featureId, roleId);
		showFeatureInfo(featureId, model);
		return "addNewRoleToFeature";
	}
}
