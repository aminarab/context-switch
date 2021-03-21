package ir.amin.contextswitch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

public class DozerManager<T> {

	private DozerBeanMapper mapper = new DozerBeanMapper();

	public DozerManager() {
		// setMapping();
	}

	private void setXMLMapping() {
		List<String> mappingFiles = new ArrayList<String>();
		mappingFiles.add("dozer-mappings.xml");
		mapper.setMappingFiles(mappingFiles);
	}

	@SuppressWarnings("unchecked")
	public T clone(T reference) {
		for (String idName : getIdNames((Class<T>) reference.getClass())) {			
			excludeFieldByName((Class<T>) reference.getClass(), idName);
		}
		return (T) mapper.map(reference, reference.getClass());
	}

	private void excludeFieldByName(final Class<T> cls, final String fieldName) {
		BeanMappingBuilder skipIdBuilder = new BeanMappingBuilder() {
			@Override
			protected void configure() {
				mapping(cls, cls).exclude(fieldName);
			}
		};
		mapper.addMapping(skipIdBuilder);
	}

	public List<String> getIdNames(Class<T> cls){
		List<String> idNames = new ArrayList<String>();
		Field[] fields = cls.getDeclaredFields();

		  for(Field field : fields){
		      Id annotation = field.getAnnotation(Id.class);
		      if( annotation != null ){
		          idNames.add(field.getName());
		      }
		  }
		return idNames; 
	}
}
