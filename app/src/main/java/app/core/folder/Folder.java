package app.core.folder;

import java.util.ArrayList;

public class Folder {
  private String name;
  private int parentID;
  private int folderID;

  private ArrayList<Folder> children;
  private Folder parent;

  public Folder(String name, int folderID, int parentID) {
    this.name = name;
    this.folderID = folderID;
    this.parentID = parentID;

    children = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public int getParentID() {
    return parentID;
  }

  public int getFolderID() {
    return folderID;
  }

  @Override
  public String toString() {
    return "Folder [folderID=" + folderID + ", name=" + name + ", parentID=" + parentID + "]";
  }

  public void addChild(Folder folder) {
    this.children.add(folder);
  }

  public void setParent(Folder folder) {
    parent = folder;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParentID(int parentID) {
    this.parentID = parentID;
  }

  public void setFolderID(int folderID) {
    this.folderID = folderID;
  }

  public ArrayList<Folder> getChildren() {
    return this.children;
  }

  public void setChildren(ArrayList<Folder> children) {
    this.children = children;
  }

  public Folder getParent() {
    return this.parent;
  }
}
