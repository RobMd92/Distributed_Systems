package sample;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Observable;


public class SharedImpl extends Observable implements FolderMonitor  {
    private OutputStream outputStream;
    private DataInputStream inputStream;
    private File Shared = new File("Shared");
    private File Local = new File("Local");
    private String[] oldSongs;



    private static SharedImpl sInstance;

    private SharedImpl(){

    }
    public static SharedImpl getInstance() {
        if (sInstance == null) {
            sInstance = new SharedImpl();
        }
        return sInstance;
    }
    @Override
    public boolean isEOF() {
        try {
            if (inputStream.available() > 0)

                return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFile(null);
        return true;


    }

    @Override
    public String[] getNames() {


        ArrayList<File> listOfFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(Shared.listFiles())));


        String[] strArray = new String[listOfFiles.size()];
//        oldSongs = new String[listOfFiles.size()];


        for (int i = 0; i < listOfFiles.size(); i++) {
            strArray[i] = listOfFiles.get(i).getName();


        }
        return strArray;
    }

    @Override
    public boolean openFile(String name1) {


        File[] matchingFiles = Shared.listFiles((dir, name) -> name.startsWith(name1));
        assert matchingFiles != null;

        String choice = matchingFiles[0].toString();
        System.out.println(choice);


        try {
            inputStream = new DataInputStream(new FileInputStream(new File(choice)));
            System.out.println("true");
            downloadFile(name1);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public byte getB() {
        try {
            return inputStream.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -100;
    }

    @Override
    public boolean closeFile(String name) {

        try {
            inputStream.close();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;
    }


    @Override
    public boolean isChange() {
        String[] newSongs = getNames();
        if (!Arrays.equals(newSongs, oldSongs)) {
            setChanged();
            notifyObservers();
            oldSongs = newSongs;

            return true;
        }
        return false;
    }

    private void downloadFile(String name1) {

        try {
            outputStream = new DataOutputStream(new FileOutputStream(new File(String.valueOf(Local + "/" + name1))));
            while (!isEOF()) {

                outputStream.write(getB());

            }
            closeFile(null);
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }
}
