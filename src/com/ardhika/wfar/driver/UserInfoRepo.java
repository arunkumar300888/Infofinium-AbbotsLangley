package com.ardhika.wfar.driver;

import java.util.HashSet;
import java.util.Set;

import uk.co.jmr.sdp.domain.Group;
import uk.co.jmr.sdp.domain.GroupRole;
import uk.co.jmr.sdp.domain.Role;
import uk.co.jmr.sdp.domain.User;

import com.ardhika.wfar.WfUserInfo;

public class UserInfoRepo {

	public static WfUserInfo UI1 = new WfUserInfo() {

		@Override
		public String getUserName() {

			return "kanaga";
		}

		@Override
		public Set<String> getRoles() {

			Set<String> roles = new HashSet<String>();
			roles.add("Reviewer");
			roles.add("Checker");
			return roles;
		}

		@Override
		public Set<String> getGroupRoles() {

			Set<String> groupRoles = new HashSet<String>();
			groupRoles.add("Reviewer.HealthSafety");
			return groupRoles;
		}

		@Override
		public String getActiveRole() {

			return null;
		}

		@Override
		public String getActiveGroup() {

			return null;
		}

		@Override
		public String getEmailId() {

			return "kanaga@gmail.com";
		}

		@Override
		public long getUserId() {

			return 1;
		}
	};

	public static WfUserInfo UI2 = new WfUserInfo() {

		@Override
		public String getUserName() {

			return "viswa";
		}

		@Override
		public Set<String> getRoles() {

			Set<String> roles = new HashSet<String>();
			roles.add("Reviewer");
			roles.add("Publisher");
			return roles;
		}

		@Override
		public String getActiveRole() {

			return null;
		}

		@Override
		public String getActiveGroup() {

			return null;
		}

		@Override
		public String getEmailId() {

			return "viswa@gmail.com";
		}

		@Override
		public Set<String> getGroupRoles() {

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getUserId() {

			return 2;
		}
	};

	public static WfUserInfo UI3 = new WfUserInfo() {

		@Override
		public String getUserName() {

			return "vaish";
		}

		@Override
		public Set<String> getRoles() {

			Set<String> roles = new HashSet<String>();
			roles.add("Publisher");
			roles.add("Approver");
			roles.add("Reviewer");
			return roles;
		}

		@Override
		public String getActiveRole() {

			return null;
		}

		@Override
		public String getActiveGroup() {

			return null;
		}

		@Override
		public String getEmailId() {

			return "vaish@gmail.com";
		}

		@Override
		public Set<String> getGroupRoles() {

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getUserId() {

			return 3;
		}
	};

	public static WfUserInfo UI4 = new WfUserInfo() {

		@Override
		public String getUserName() {

			return "archana";
		}

		@Override
		public Set<String> getRoles() {

			Set<String> roles = new HashSet<String>();
			roles.add("Approver");
			return roles;
		}

		@Override
		public String getActiveRole() {

			return null;
		}

		@Override
		public String getActiveGroup() {

			return null;
		}

		@Override
		public String getEmailId() {

			return "archana@gmail.com";
		}

		@Override
		public Set<String> getGroupRoles() {

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getUserId() {

			return 4;
		}
	};

	public static WfUserInfo UI5 = new WfUserInfo() {

		@Override
		public String getUserName() {

			return "ravi";
		}

		@Override
		public Set<String> getRoles() {

			Set<String> roles = new HashSet<String>();
			roles.add("Reviewer");
			roles.add("Publisher");
			return roles;
		}

		@Override
		public String getActiveRole() {

			return null;
		}

		@Override
		public String getActiveGroup() {

			return null;
		}

		@Override
		public String getEmailId() {

			return "ravi@gmail.com";
		}

		@Override
		public Set<String> getGroupRoles() {

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getUserId() {

			return 5;
		}

	};

	public static WfUserInfo UI6 = new WfUserInfo() {

		@Override
		public String getUserName() {

			return "prem";
		}

		@Override
		public Set<String> getRoles() {

			Set<String> roles = new HashSet<String>();
			roles.add("Assigner");
			return roles;
		}

		@Override
		public String getActiveRole() {

			return null;
		}

		@Override
		public String getActiveGroup() {

			return null;
		}

		@Override
		public String getEmailId() {

			return "prem@gmail.com";
		}

		@Override
		public Set<String> getGroupRoles() {

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getUserId() {

			return 6;
		}

	};

}
