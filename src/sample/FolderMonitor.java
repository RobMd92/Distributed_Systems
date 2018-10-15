package sample;

public interface FolderMonitor {
    boolean isEOF();  // have we have reached the end of the file being read?
    String[] getNames();  // returns the names of all the files in folder
    boolean openFile(String name);  // opens a file called name
    byte getB();   // Gets a byte from the currently open file
    boolean closeFile(String name);  // closes the open file
    boolean isChange();	// has a new file(s) has been added?
}