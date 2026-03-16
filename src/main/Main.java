package main;

import java.util.List;
import java.util.Map;

import ConfigurationManager.Parameter;
import ConfigurationManager.ParameterManager;
import ConfigurationManager.VersionHistory;
import UserManager.Role;
import UserManager.User;

public class Main {
	
	public static void printAllParameters(Map<String, List<Parameter<?>>> parameters) {
	    if (parameters.isEmpty()) {
	        System.out.println("No parameters available.");
	        return;
	    }

	    for (Map.Entry<String, List<Parameter<?>>> entry : parameters.entrySet()) {
	        String environment = entry.getKey();
	        List<Parameter<?>> list = entry.getValue();

	        System.out.println("Environment: " + environment);
	        for (Parameter<?> p : list) {
	            System.out.println("  Name: " + p.getName());
	            System.out.println("  Type: " + p.getType());
	            System.out.println("  Default Value: " + p.getDefaultValue());
	            System.out.println("  Overridden Value: " + p.getOverriddenValue());
	            System.out.println("  Last Updated: " + p.getLastUpdated());
	            System.out.println("  Environment: " + p.getEnvironment());
	            System.out.println("  ----------------------");
	        }
	        System.out.println("-------------------------------------------------");
	    }
	}
	
	public static void printAllHistory(List<VersionHistory<?>> history) {
	    if (history.isEmpty()) {
	        System.out.println("No history available.");
	        return;
	    }

	    for (VersionHistory<?> p : history) {
	    	System.out.println("  Environment: " + p.getEnvironment());
            System.out.println("  Parameter Name: " + p.getParameterName());
            System.out.println("  New Value: " + p.getNewValue());
            System.out.println("  Old Value: " + p.getOldValue());
            System.out.println("  Changed By: " + p.getChangedBy());
            System.out.println("  ----------------------");
	    }
	    System.out.println("-------------------------------------------------");
	}
	
	public static void main (String[] args) {
		
		User user = new User("Basel", Role.ADMIN);
		
		String parametersFileName = "C:\\Users\\HP\\Documents\\workspace.RELEASE\\DynamicConfigurationManagementSystem\\src\\main\\parameters.txt";
		String historyFileName = "C:\\Users\\HP\\Documents\\workspace.RELEASE\\DynamicConfigurationManagementSystem\\src\\main\\history.txt";
		
		ParameterManager pm = new ParameterManager(user, parametersFileName, historyFileName);
		
//		pm.addParameter("111","NUMBER" , 10, "DEV");
//		pm.addParameter("333", "BOOLEAN" , true, "DEV");
//		pm.overrideValue("111", "DEV", 12);
//		pm.addParameter("222","STRING" , "Max", "OP");
//		pm.updateParameter("111",777, "DEV");
//		pm.deleteParameter("222", "OP");
//		pm.deleteParameter("111", "DEV");
		
//		pm.saveChanges();
		
//		pm.deleteParameter("222", "OP");
//		pm.saveChanges();
		
//		pm.updateParameter("111", "90", "DEV");
//		pm.saveChanges();
		
//		pm.updateParameter("222", "Min", "OP");
//		pm.overrideValue("222", "OP", "Min");
//		pm.saveChanges();
		
		
//		printAllParameters(pm.getParameters());
//		System.out.println(pm.export("DEV"));
		System.out.println("-------------------------------------------------");
//		printAllHistory(pm.getHistory());
//		
//		for(Parameter<?> p : pm.searchByEnvironment("DEV")) {
//			System.out.println("  Name: " + p.getName());
//            System.out.println("  Type: " + p.getType());
//            System.out.println("  Default Value: " + p.getDefaultValue());
//            System.out.println("  Overridden Value: " + p.getOverriddenValue());
//            System.out.println("  Last Updated: " + p.getLastUpdated());
//            System.out.println("  Environment: " + p.getEnvironment());
//            System.out.println("  ----------------------");
//		}
		
//		System.out.println("-------------------------------------------------");
//		
//		for(Parameter<?> p : pm.searchByName("2")) {
//			System.out.println("  Name: " + p.getName());
//            System.out.println("  Type: " + p.getType());
//            System.out.println("  Default Value: " + p.getDefaultValue());
//            System.out.println("  Overridden Value: " + p.getOverriddenValue());
//            System.out.println("  Last Updated: " + p.getLastUpdated());
//            System.out.println("  Environment: " + p.getEnvironment());
//            System.out.println("  ----------------------");
//		}
//
//		System.out.println("-------------------------------------------------");
//		
//		for(Parameter<?> p : pm.searchByType("STRING")) {
//			System.out.println("  Name: " + p.getName());
//            System.out.println("  Type: " + p.getType());
//            System.out.println("  Default Value: " + p.getDefaultValue());
//            System.out.println("  Overridden Value: " + p.getOverriddenValue());
//            System.out.println("  Last Updated: " + p.getLastUpdated());
//            System.out.println("  Environment: " + p.getEnvironment());
//            System.out.println("  ----------------------");
//		}
	}
}
