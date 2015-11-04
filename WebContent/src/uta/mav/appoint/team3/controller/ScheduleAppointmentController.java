package uta.mav.appoint.team3.controller;

import java.sql.SQLException;

import uta.mav.appoint.beans.Appointment;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.team3fall.util.Util;

/**
 * Controller to schedule appointment
 * @author SDP Team 3
 *
 */
public class ScheduleAppointmentController {
	
	public static void scheduleAppointment(String phoneNumber, String appointmentId, String studentId, 
			String description, String appType, String pName, String duration, String start, String email) throws SQLException{
		
		Appointment a = new Appointment();
		a.setStudentPhoneNumber(phoneNumber);
		a.setAppointmentId(Integer.parseInt(appointmentId));
		a.setStudentId(studentId);
		a.setDescription(description);
		a.setAppointmentType(appType);
		a.setPname(pName);
		a.setDescription(description);
		int d = Integer.parseInt(duration);
		String[] parts = (start).split(" ");
		a.setAdvisingDate(parts[3] + "-" + Util.convertDate(parts[1]) + "-" + parts[2]);
		parts = parts[4].split(":");
		a.setAdvisingStartTime(parts[0] + ":" + parts[1]);
		a.setAdvisingEndTime(Util.addTime(parts[0],parts[1],d));
		
		DatabaseManager dbm = new DatabaseManager();
		Boolean result = dbm.createAppointment(a,email);
		if (result == true){
			String sub = "Appointment set for " + a.getAdvisingDate();
			String mess = ",\nAn appointment has been set for " + a.getAdvisingDate()
			+ " at " + a.getAdvisingStartTime() + " - " + a.getAdvisingEndTime()
			+ "\nAppoint ID: " + a.getAppointmentId();
			Email newMail = new Email(sub,mess,email);
			newMail.sendMail();
		}
	}
}
