package Serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;


public class JsonSerializer<TType> {
	private String fileName;
	private ObjectMapper mapper;

	
	public JsonSerializer(String fileName) {
		this.fileName = fileName;
		this.mapper = new ObjectMapper();
    	mapper.registerModule(new JavaTimeModule());
	}
	
	public void serialize(TType obj) throws IOException {
	    File file = new File(fileName);
		mapper.writeValue(file, obj);	  
	}
	
	public TType deserialize(TypeReference<TType> typeRef) throws IOException {
	    File file = new File(fileName);
		if (!file.exists() || file.length() == 0) {
	    	return null;
	    }
	    return mapper.readValue(file, typeRef);
	}
	
}
