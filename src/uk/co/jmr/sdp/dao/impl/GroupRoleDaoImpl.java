package uk.co.jmr.sdp.dao.impl;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import uk.co.jmr.sdp.dao.GroupRoleDao;
import uk.co.jmr.sdp.dao.UserDao;
import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

@Repository("groupRoleDao")
public class GroupRoleDaoImpl implements GroupRoleDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private UserDao userDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupRole> findAllGroupRoles() {

		return (List<GroupRole>) this.hibernateTemplate.find("from GroupRole");
	}

	@Override
	public GroupRole findGroupRoleById(long id) {

		return this.hibernateTemplate.get(GroupRole.class, id);
	}

	@Override
	public void saveGroupRole(GroupRole groupRole) {

		this.hibernateTemplate.save(groupRole);
	}

	@Override
	public Set<Role> getRoles(Group group) {

		Set<GroupRole> grproles = group.getGroupRoles();
		Set<Role> roles = new HashSet<Role>();
		for (GroupRole gr : grproles) {
			roles.add(gr.getRole());
		}
		return roles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersFromGroupRole(GroupRole groupRole) {

		// Set<GroupRole> grproles = group.getGroupRoles();
		String query = "from User as u where " + groupRole.getId() + " in(select g.id from u.groupRoles as g)";
		//System.out.println("query-> " + query);
		return (List<User>) hibernateTemplate.find(query);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GroupRole findGroupRole(Group group, Role role) {

		String query = "from GroupRole as s where s.group=? and s.role=?";
		List result = this.hibernateTemplate.find(query, group, role);
		if (result.size() >= 1)
			return (GroupRole) result.get(0);
		return null;
	}

	// Unused Below Codes
	@Override
	public List<User> findUsersFromGroup(Group group) {

		Set<GroupRole> grproles = group.getGroupRoles();
		StringBuffer sb = new StringBuffer();
		for (GroupRole g : grproles) {
			sb.append(g.getId());
			sb.append(",");
		}
		int len = sb.length();
		sb.deleteCharAt(len - 1);
		//System.out.println("sb-> " + sb);
		// String query =
		// "from User as u where u.groupRoles in(select gr.id from GroupRole as gr where gr.group="+group.getId()+")";
		// String query =
		// "from u.groupRoles as u where u.id in( select gr.id from GroupRole as gr where gr.group="+group.getId()+")";
		String query = "select g.id from User.groupRoles as g";
		List lst = this.hibernateTemplate.find(query);
		//System.out.println("lst-> " + lst);
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Set<User> getUsersFromGroupSample(Group group) {

		Set<GroupRole> grproles = group.getGroupRoles();
		final StringBuffer sb = new StringBuffer();
		for (GroupRole g : grproles) {
			sb.append(g.getId());
			sb.append(",");
		}
		// String query =
		// "select user_id from user_grouprole where grouprole_id in () ";
		int len = sb.length();
		sb.deleteCharAt(len - 1);
		//System.out.println("String buffer-> " + sb);
		List usersList = hibernateTemplate.execute(new HibernateCallback<List>() {
			public List doInHibernate(Session s) throws HibernateException, SQLException {

				SQLQuery sql = s.createSQLQuery("select user_id from user_grouprole where grouprole_id in(" + sb + ")");
				//System.out.println("**** " + sql.list());
				return sql.list();
			}
		});

		//System.out.println("usersList-> " + usersList);
		Set<User> users = new HashSet<User>(usersList);
		users.addAll(usersList);

		//System.out.println(users);
		return users;
	}

	@Override
	public Set<GroupRole> getGroupRolesForUser(User user) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupRole getgroupRole(Group group, Role role) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> getUsersFromRoleAndGroup(Role role, Group group) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean haveRoleInGroup(Role role, Group group) {

		// TODO Auto-generated method stub
		return false;
	}

}
