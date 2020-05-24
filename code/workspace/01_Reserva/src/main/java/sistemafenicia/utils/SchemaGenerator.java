package sistemafenicia.utils;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
 
public class SchemaGenerator implements InitializingBean {
 
    private String schema;
    private DataSource dataSource;
 
    public String getSchema() {
        return schema;
    }
 
    public void setSchema(String schema) {
        this.schema = schema;
    }
 
    public DataSource getDataSource() {
        return dataSource;
     }
 
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 
     public void afterPropertiesSet() throws Exception {
    	 JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         jdbcTemplate.execute("CREATE SCHEMA " + schema + " AUTHORIZATION DBA");
    }
}