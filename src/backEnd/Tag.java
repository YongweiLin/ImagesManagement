package backEnd;

/** A Tag object for the Image */
public class Tag {
  private String name;

  /**
   * Create a new Tag with name name. Record this tag into tagBase.
   *
   * @param name the name of this Tag.
   */
  public Tag(String name) {
    this.name = name;
    /* Keep a record of all the tag in the tag base, so that tags persist when we close and reopen
    the program. */
    TagManager.writePersist(this.name, "phase1/src/tagBase.txt");
  }

  /**
   * Return this tag's name.
   *
   * @return the name of this tag.
   */
  public String getName() {
    return this.name;
  }
}
