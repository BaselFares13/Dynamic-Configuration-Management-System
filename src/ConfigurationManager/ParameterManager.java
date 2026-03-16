package ConfigurationManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import UserManager.Role;
import UserManager.User;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import Serialization.JsonSerializer;
import com.fasterxml.jackson.core.type.TypeReference;


public class ParameterManager {
	private Map<String, List<Parameter<?>>> parameters;
	private List<VersionHistory<?>> history;
	private User user;
	private JsonSerializer<Map<String, List<Parameter<?>>>> parametersSerializer;
	private JsonSerializer<List<VersionHistory<?>>> historySerializer;
	
	public ParameterManager(User user, String parametersFileName, String historyFileName) {

	    this.user = user;
	    this.parameters = new HashMap<>();
	    this.history = new ArrayList<>();

	    try {
	        parametersSerializer = new JsonSerializer<>(parametersFileName);
	        historySerializer = new JsonSerializer<>(historyFileName);

	        Map<String, List<Parameter<?>>> loadedParams =
	                parametersSerializer.deserialize(
	                        new TypeReference<Map<String, List<Parameter<?>>>>() {});

	        List<VersionHistory<?>> loadedHistory =
	                historySerializer.deserialize(
	                        new TypeReference<List<VersionHistory<?>>>() {});

	        if (loadedParams != null) {
	            this.parameters = loadedParams;
	        }

	        if (loadedHistory != null) {
	            this.history = loadedHistory;
	        }

	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	    }
	}
	
	public Map<String, List<Parameter<?>>> getParameters() {
		return this.parameters;
	}
	
	public List<VersionHistory<?>> getHistory() {
		return this.history;
	}
	
	public void addParameter(String parameterName, String parameterType,
			Object value, String environment) {
		
		if(this.user.getRole() != Role.ADMIN)
			return;
		
		try {
			Parameter<?> param = createParameter(parameterName, parameterType,
					value, environment);
			
			if(param == null) {
			    throw new NullPointerException("Parameter creation failed, there is a problem in the inserted type");
			}
			
			if(!parameters.containsKey(param.getEnvironment())) {
				List<Parameter<?>> list = new ArrayList<Parameter<?>>();
				list.add(param);
				parameters.put(environment, list);
			} else {
				parameters.get(param.getEnvironment()).add(param);
			}
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
 	}
	
	private Parameter<?> createParameter(String parameterName, String parameterType,
			Object value, String environment) {
		if(parameterType.equalsIgnoreCase("STRING")) {
			Parameter<String> param = new Parameter<String>(parameterName, 
					(String)value, environment);		
			return param;	 
		} else if(parameterType.equalsIgnoreCase("NUMBER")) {
			Parameter<Integer> param = new Parameter<Integer>(parameterName, 
					(Integer)value, environment);		
			return param;
		} else if(parameterType.equalsIgnoreCase("BOOLEAN")) {
			Parameter<Boolean> param = new Parameter<Boolean>(parameterName, 
					(Boolean)value, environment);
			return param;
		} 
		
		return null;
	}
	
	public void overrideValue(String parameterName,
			String environment, Object value) {
		if(this.user.getRole() != Role.ADMIN && this.user.getRole() != Role.SERVICE)
			return;
		
		List<Parameter<?>> list = parameters.get(environment);
		if(list == null) 
			return;
		
		try {
			for(Parameter<?> p : list) {
				if(p.getName().equalsIgnoreCase(parameterName)) {
					
					history.add(new VersionHistory<>(p.getName(), p.getEnvironment(),
							p.getValue(), value, user, LocalDateTime.now()));
					
					p.setOverriddenValue(value);
					p.setLastUpdated(LocalDateTime.now());
					
					break;
				}
			}
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void updateParameter(String parameterName,
			Object value, String environment) {
		if(this.user.getRole() != Role.ADMIN)
			return;
		
		List<Parameter<?>> list = parameters.get(environment);;
		if(list == null) 
			return;
		try {
			for(Parameter<?> p : list) {
				if(p.getName().equalsIgnoreCase(parameterName)) {
				
					history.add(new VersionHistory<>(p.getName(), p.getEnvironment(),
							p.getValue(), value, user, LocalDateTime.now()));
			
					
					p.setDefaultValue(value);
					p.setLastUpdated(LocalDateTime.now());
					p.setOverriddenValue(null);
					break;
				}
			}
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public void deleteParameter(String parameterName,
			String environment) {
		if(this.user.getRole() != Role.ADMIN)
			return;
		
		List<Parameter<?>> list = parameters.get(environment);
		if(list == null) return;
		
		list.removeIf(p -> p.getName().equalsIgnoreCase(parameterName))	;
	}
	
	public List<Parameter<?>> searchByEnvironment(String environment) {
	    return parameters.getOrDefault(environment, new ArrayList<>());
	}
	
	public List<Parameter<?>> searchByName(String name) {
	    List<Parameter<?>> result = new ArrayList<>();

	    for (List<Parameter<?>> list : parameters.values()) {
	        for (Parameter<?> p : list) {
	            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
	                result.add(p);
	            }
	        }
	    }

	    return result;
	}
	
	public List<Parameter<?>> searchByType(String type) {
	    List<Parameter<?>> result = new ArrayList<>();

	    for (List<Parameter<?>> list : parameters.values()) {
	        for (Parameter<?> p : list) {
	            if (p.getType().equalsIgnoreCase(type)) {
	                result.add(p);
	            }
	        }
	    }

	    return result;
	}
	
	public String export(String environment) {
	    String json = "";
	    Map<String, List<Parameter<?>>> map = new HashMap<>();
		try {
			map.put(environment, parameters.get(environment));
			
	    	ObjectMapper mapper = new ObjectMapper();
	    	mapper.registerModule(new JavaTimeModule());
	    	json = mapper
	    	        .writerWithDefaultPrettyPrinter()
	    	        .writeValueAsString(map);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return json;
	}
	
	public void saveChanges() {		
		try {
			this.parametersSerializer.serialize(parameters);
			this.historySerializer.serialize(history);
	    } catch(Exception ex) {
	    	System.out.println(ex.getMessage());
	    }
	}
}
