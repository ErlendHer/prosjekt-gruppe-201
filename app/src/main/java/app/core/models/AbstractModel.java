package app.core.models;

/**
 * The Class AbstractModel represents the base model for Objects fetched from the Database.
 */
public abstract class AbstractModel {

  protected Integer id;

  /**
   * Instantiates a new abstract model.
   */
  public AbstractModel() {
    this.id = 0;
  }

  /**
   * Instantiates a new abstract model.
   *
   * @param id the id
   */
  public AbstractModel(Integer id) {
    this.id = id;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(Integer id) {
    this.id = id;
  }
}
