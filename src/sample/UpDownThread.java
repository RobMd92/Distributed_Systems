package sample;

import java.io.*;

public class UpDownThread extends Thread {
    private String from;
    private String to;

    public UpDownThread(String from, String to) {
        this.from = from;
        this.to = to;

        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        try {
            DataInputStream inputStream = new DataInputStream(new FileInputStream(new File(from)));
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(new File(to)));

            while (inputStream.available() > 0) {
                outputStream.write(inputStream.readByte());
            }

            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
