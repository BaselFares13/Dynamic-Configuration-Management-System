package ConfigurationManager;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter<TType> {
	private String id;
    private String name;
    private String type;
    private TType defaultValue;
    private String environment;
    private TType overriddenValue;
    private LocalDateTime lastUpdated;
    
    public Parameter() {
    }
    
    public Parameter(String name, TType defaultValue, String environment) {
    	
    	try {
	        this.defaultValue = defaultValue;
	    } catch (ClassCastException ex) {
	        throw new IllegalArgumentException(
	            "Invalid type for overridden value. Expected: " + defaultValue.getClass().getSimpleName()
	        );
	    }    
    
    	
    	this.id = UUID.randomUUID().toString();
    	this.name = name;
    	this.environment = environment;
    	this.type = detectType(defaultValue);
    	this.lastUpdated = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public TType getDefaultValue() {
        return defaultValue;
    }
    
    public void setDefaultValue(Object value) {
    	try {
	        this.defaultValue = (TType) value;
	    } catch (ClassCastException ex) {
	        throw new IllegalArgumentException(
	            "Invalid type for overridden value. Expected: " + defaultValue.getClass().getSimpleName()
	        );
	    }        
    }

    public String getEnvironment() {
        return environment;
    }

    public TType getOverriddenValue() {
        return overriddenValue;
    }

    public void setOverriddenValue(Object overriddenValue) {
	   try {
	        this.overriddenValue = (TType) overriddenValue;
	    } catch (ClassCastException ex) {
	        throw new IllegalArgumentException(
	            "Invalid type for overridden value. Expected: " + defaultValue.getClass().getSimpleName()
	        );
	    }    
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    private String detectType(Object value) {
        if(value instanceof Number) return "NUMBER";
        if(value instanceof String) return "STRING";
        if(value instanceof Boolean) return "BOOLEAN";
        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
    }
    
    public TType getValue() {
    	if(this.isValueOverridden()) {
    		return this.overriddenValue;
    	}
    	return defaultValue;
    }
    
    public boolean isValueOverridden() {
    	return overriddenValue != null;
    }
}
