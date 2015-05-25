package com.ardhika.wfar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ardhika.wfar.util.Mailer;

public class WfMail extends WfNode {

	private String assignee = "S:SYSTEM";
	private String subject;
	private String message;
	private Set<String> recipients = new HashSet<String>();

	public WfMail(String name, String subject, String message) {

		super(name, WfNodeType.WF_MAIL_NODE);
		this.subject = subject;
		this.message = message;
	}

	@Override
	public String getAssignee() {

		return assignee;
	}

	public void setAssignee(String assignee) {

		this.assignee = assignee;
	}

	public String getSubject() {

		return subject;
	}

	public void setSubject(String subject) {

		this.subject = subject;
	}

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public Set<String> getRecipients() {

		return recipients;
	}

	public void setRecipients(Set<String> recipients) {

		this.recipients = recipients;
	}

	public void addToRecipients(String recipient) {

		recipients.add(recipient);
	}

	void process(WfCase owningCase) {

		// List<String> toList = new ArrayList<String>();
		Set<String> toList = new HashSet<String>();

		//System.out.println("-----------------------------");
		for (WfStep step : owningCase.getSteps()) {
			//System.out.println(step + "@!@@@@@@@@@@@@@@@@@@@@");
			if (step.getStatus() == "WF_STEP_COMPLETED") {
				String assignee = step.getAssignee();
				if (this.getRecipients().contains(assignee)) {
					toList.add(step.getEmailId());
					//System.out.println("Email:" + step.getEmailId());
				}
				else if (assignee.trim().substring(0, 2).equalsIgnoreCase("U:")) {
					String assigneeName = assignee.trim().substring(2);
					if (assigneeName.equalsIgnoreCase(owningCase.getCreator()) && this.getRecipients().contains("U:{CREATOR}")) {
						toList.add(step.getEmailId());
						//System.out.println("Email:" + step.getEmailId());
					}

				}

			}
		}
		//System.out.println("----Sending Mail to----");
		for (String mailid : toList)
			System.out.println(mailid);
		int length = toList.size();
		//System.out.println("Size:" + length);
		if (toList.size() <= 0)
			return;
		// Call mail function here
		Mailer.doMail(this.getSubject(), this.getMessage(), toList);
	}

	protected WfMail() {

	}
}
