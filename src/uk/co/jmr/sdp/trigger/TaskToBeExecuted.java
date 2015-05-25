package uk.co.jmr.sdp.trigger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import uk.co.jmr.sdp.domain.KeepAlive;
import uk.co.jmr.sdp.domain.TenancyForm;
import uk.co.jmr.sdp.domain.TransactionDetails;
import uk.co.jmr.sdp.domain.User;
import uk.co.jmr.sdp.service.KeepAliveService;
import uk.co.jmr.sdp.service.TenancyFormService;
import uk.co.jmr.sdp.service.TransactionDetailService;
import uk.co.jmr.sdp.service.UserService;



public class TaskToBeExecuted {
	
	@Autowired
	private KeepAliveService keepAliveService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private TenancyFormService tenancyFormService;
	@Autowired
	private UserService userService;
	
	
	
	public void keepAppAlive() {
		//System.out.println("inside trigger");
		
		List<KeepAlive> keepAlives=keepAliveService.findAllKeepAlive();
		if(!keepAlives.isEmpty()){
			for(KeepAlive keepAlive:keepAlives){
				keepAlive.setLastPacketReceived(new Date());
				keepAlive.setMessage("KeepAlive");
				keepAliveService.save(keepAlive);
				break;
			}
		}else{
			KeepAlive keepAlive2=new KeepAlive(new Date(), "KeepAlive");
			keepAliveService.save(keepAlive2);
		}
			
	}
	
	
	public void rentDueReminder(){
		
		try{
		List<TenancyForm> tenancyForms=tenancyFormService.findAllTenancy();
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		
		
		
		for(TenancyForm tenancyForm:tenancyForms){
			
			Date currentDate=new Date();
			Calendar calCurrent=Calendar.getInstance();
			calCurrent.setTime(currentDate);
			
			TransactionDetails transactionDetails1=transactionDetailService.findTransactionDetialsByTenancyAndTransactionType(tenancyForm, "D");
			Date startDate=null;
			
			if(transactionDetails1==null)
			startDate= tenancyForm.getTenancyStartedDate();
			else{
				startDate=transactionDetails1.getTransactiondate();
			}
			
			
			
			Calendar cal=Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, tenancyForm.getRentDueDate());
			
			int monthCurrent=calCurrent.get(calCurrent.MONTH);
			int nextMonth=cal.get(cal.MONTH);
			if( ++monthCurrent == nextMonth){
			
			Double annualRent=Double.parseDouble(tenancyForm.getPropertyDetailsForm().getAnnualRent());
			
			
			int days=daysBetween(startDate, cal.getTime());
			
			double perDayRent=annualRent/365;
			perDayRent=perDayRent*days;
			String rent1=String.valueOf(perDayRent);
		//	String[] rentArray=rent1.split("\\.");
			int rent=Integer.parseInt(rent1.split("\\.")[0]);
			String finalPerDayRent=String.valueOf(rent);
			User newUser=userService.findUserByNameAndEmail(tenancyForm.getTenantFirstName(), tenancyForm.getTenantEmailAddress());
			long userId=newUser!=null? newUser.getId() : null;
			TransactionDetails transactionDetails2=transactionDetailService.findTransactionDetialsByPaymentTypeAndRentDueDate(cal.getTime(),tenancyForm);
		if(transactionDetails2==null){	
		transactionDetails2=new TransactionDetails(tenancyForm, tenancyForm.getPropertyDetailsForm(), "D", cal.getTime(), finalPerDayRent, "n", userId,"");
		transactionDetailService.save(transactionDetails2);
		}
		}
		}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	private int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
	
}

