/*
 * package com.ardhika.wfar.driver;
 * 
 * import java.util.HashSet; import java.util.Set;
 * 
 * import uk.co.jmr.sdp.domain.Group; import uk.co.jmr.sdp.domain.GroupRole;
 * import uk.co.jmr.sdp.domain.Role; import uk.co.jmr.sdp.domain.User;
 * 
 * import com.ardhika.wfar.WfUserInfo;
 * 
 * public class UserInfoRepoOld { public static WfUserInfo UI1 = new
 * WfUserInfo() {
 * 
 * @Override public String getUserName() { return "kanaga"; }
 * 
 * @Override public Set<String> getRoles() { Set<String> roles = new
 * HashSet<String>(); roles.add("Reviewer"); roles.add("Checker"); return roles;
 * }
 * 
 * @Override public Set<String> getGroupRoles() { Set<String> groupRoles = new
 * HashSet<String>(); groupRoles.add("Reviewer.HealthSafety"); //
 * groupRoles.add("Development"); return groupRoles; }
 * 
 * @Override public String getActiveRole() { return null; }
 * 
 * @Override public String getActiveGroup() { return null; }
 * 
 * 
 * @Override public String getEmailId() { return "kanaga@gmail.com"; }
 * 
 * @Override public Set<GroupRole> getGroupRoles(User user) { return
 * user.getGroupRoles(); }
 * 
 * @Override public Set<Group> getGroups(User user) { Set<GroupRole> grprls =
 * user.getGroupRoles(); Set<Group> groups = new HashSet<Group>(); for(GroupRole
 * gr:grprls){ groups.add(gr.getGroup()); } return groups; }
 * 
 * @Override public Set<User> getUsersFromGroupAndRole(Group group, Role role) {
 * 
 * 
 * return null; }
 * 
 * @Override public Set<User> getUsersFromGroup(Group group) { return null; }
 * 
 * };
 * 
 * public static WfUserInfo UI2 = new WfUserInfo() {
 * 
 * @Override public String getUserName() { return "viswa"; }
 * 
 * @Override public Set<String> getRoles() { Set<String> roles = new
 * HashSet<String>(); roles.add("Reviewer"); roles.add("Publisher"); return
 * roles; }
 * 
 * @Override public String getActiveRole() { return null; }
 * 
 * 
 * @Override public String getActiveGroup() { return null; }
 * 
 * @Override public String getEmailId() { return "viswa@gmail.com"; }
 * 
 * @Override public Set<String> getGroupRoles() { // TODO Auto-generated method
 * stub return null; } };
 * 
 * public static WfUserInfo UI3 = new WfUserInfo() {
 * 
 * @Override public String getUserName() { return "vaish"; }
 * 
 * @Override public Set<String> getRoles() { Set<String> roles = new
 * HashSet<String>(); roles.add("Publisher"); roles.add("Approver");
 * roles.add("Reviewer"); return roles; }
 * 
 * @Override public String getActiveRole() { return null; }
 * 
 * 
 * @Override public String getActiveGroup() { return null; }
 * 
 * @Override public String getEmailId() { return "vaish@gmail.com"; }
 * 
 * @Override public Set<String> getGroupRoles() { // TODO Auto-generated method
 * stub return null; }
 * 
 * }; public static WfUserInfo UI4 = new WfUserInfo() {
 * 
 * @Override public String getUserName() { return "archana"; }
 * 
 * @Override public Set<String> getRoles() { Set<String> roles = new
 * HashSet<String>(); roles.add("Approver"); return roles; }
 * 
 * @Override public String getActiveRole() { return null; }
 * 
 * @Override public String getActiveGroup() { return null; }
 * 
 * @Override public String getEmailId() { return "archana@gmail.com"; }
 * 
 * @Override public Set<String> getGroupRoles() { // TODO Auto-generated method
 * stub return null; }
 * 
 * }; public static WfUserInfo UI5 = new WfUserInfo() {
 * 
 * @Override public String getUserName() { return "ravi"; }
 * 
 * @Override public Set<String> getRoles() { Set<String> roles = new
 * HashSet<String>(); roles.add("Reviewer"); roles.add("Publisher"); return
 * roles; }
 * 
 * @Override public String getActiveRole() { return null; }
 * 
 * @Override public String getActiveGroup() { return null; }
 * 
 * @Override public String getEmailId() { return "ravi@gmail.com"; }
 * 
 * @Override public Set<String> getGroupRoles() { // TODO Auto-generated method
 * stub return null; }
 * 
 * }; public static WfUserInfo UI6 = new WfUserInfo() {
 * 
 * @Override public String getUserName() { return "prem"; }
 * 
 * @Override public Set<String> getRoles() { Set<String> roles = new
 * HashSet<String>(); roles.add("Assigner"); return roles; }
 * 
 * @Override public String getActiveRole() { return null; }
 * 
 * 
 * @Override public String getActiveGroup() { return null; }
 * 
 * @Override public String getEmailId() { return "prem@gmail.com"; }
 * 
 * @Override public Set<String> getGroupRoles() { // TODO Auto-generated method
 * stub return null; } };
 * 
 * }
 */