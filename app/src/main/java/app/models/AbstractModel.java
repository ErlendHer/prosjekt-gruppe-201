package app.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractModel {

	protected Integer id;
	
	public AbstractModel() {
		this.id = null;
	}
	
	public AbstractModel(ResultSet rs) throws SQLException {
		this.id = rs.getInt(1);
	}
	
	public Integer getId() {
		return this.id;
	}
}
