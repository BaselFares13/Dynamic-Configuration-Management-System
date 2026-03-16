package ConfigurationManager;

import  UserManager.User;
import java.time.LocalDateTime;

public class VersionHistory<T> {
    private String parameterName;
    private String environment;
    private T oldValue;
    private T newValue;
    private User changedBy;
    private LocalDateTime timestamp;
    
    public VersionHistory() {}
    
    public VersionHistory(String parameterName, String environment,
    		T oldValue, T newValue, User changedBy, LocalDateTime timestamp) {
    	this.parameterName = parameterName;
    	this.environment =  environment;
    	this.changedBy = changedBy;
    	this.oldValue = oldValue;
    	this.newValue = newValue;
    	this.timestamp = timestamp;
    }
    
    public String getParameterName() {
        return parameterName;
    }

    public String getEnvironment() {
        return environment;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
}
