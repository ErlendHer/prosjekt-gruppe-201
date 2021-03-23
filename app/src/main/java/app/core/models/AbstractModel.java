package app.core.models;

public abstract class AbstractModel {

	protected Integer id;
	
	public AbstractModel() {
		this.id = 0;
	}
	
	public AbstractModel(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
}
