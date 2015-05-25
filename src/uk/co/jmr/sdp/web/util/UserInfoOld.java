/*
 * package uk.co.jmr.sdp.web.util;
 * 
 * import java.util.HashSet; import java.util.Set;
 * 
 * import uk.co.jmr.sdp.domain.GroupRole; import uk.co.jmr.sdp.domain.Role;
 * import uk.co.jmr.sdp.domain.User;
 * 
 * import com.ardhika.wfar.WfUserInfo;
 * 
 * public class UserInfo implements WfUserInfo {
 * 
 * private String userName; private Set<String> roles = new HashSet<String>();
 * private Set<String> groupRoles = new HashSet<String>(); private String
 * emailId;
 * 
 * public UserInfo(User user){ this.userName = user.getUserName(); this.emailId
 * = user.getEmailId(); Set<Role> rs = user.getRoles(); for (Role r : rs) {
 * if(r.getIsActive()=='Y') this.roles.add(r.getRoleName()); } Set<GroupRole>
 * grs = user.getGroupRoles(); for (GroupRole gr : grs) {
 * if(gr.getGroup().getIsActive()=='Y' && gr.getRole().getIsActive()=='Y')
 * this.groupRoles
 * .add(gr.getRole().getRoleName()+"."+gr.getGroup().getGroupName()); } }
 * 
 * @Override public String getUserName() { return this.userName; }
 * 
 * @Override public Set<String> getRoles() { return this.roles; }
 * 
 * @Override public String getActiveRole() { // TODO Auto-generated method stub
 * return null; }
 * 
 * @Override public String getActiveGroup() { // TODO Auto-generated method stub
 * return null; }
 * 
 * @Override public String toString() { return "UserInfo [userName=" + userName
 * + ", roles=" + roles + "]"; }
 * 
 * @Override public String getEmailId() { return this.emailId; }
 * 
 * @Override public Set<String> getGroupRoles() { return this.groupRoles; }
 * 
 * 
 * 
 * }
 */