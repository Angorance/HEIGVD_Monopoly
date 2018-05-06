package bdfh.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Class used to convert Java Objects into Json, and String Json
 * into Java Objects.
 *
 * @author Héléna Line Reymond
 * @version 1.0
 */
public class JsonDataMapper {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Convert a String Json into a Java Object.
	 *
	 * @param json          Json text to convert into a Java Object
	 * @param objectClass   Class of the Object to get
	 *
	 * @return  the Java Object got
	 * @throws IOException
	 */
	public static <T> T fromJson(String json, Class<T> objectClass) throws IOException {
		return objectMapper.readValue(json, objectClass);
	}
	
	/**
	 * Convert a Java Object into a String Json
	 * @param object    Object to convert into Json
	 *
	 * @return  the Json String got
	 * @throws JsonProcessingException
	 */
	public static String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}