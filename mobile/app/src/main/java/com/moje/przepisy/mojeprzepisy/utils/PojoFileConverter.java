package com.moje.przepisy.mojeprzepisy.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PojoFileConverter {
    private Context context;
    private String fileName;
    private List<?> pojoList;
    private PojoJsonConverter pojoJsonConverter = new PojoJsonConverter();

    public PojoFileConverter(Context context){
        this.context = context;
    }

    public String getPojoListFromFile(String fileName) {
        try {
            FileInputStream fileToRead = context.openFileInput(fileName);
            StringBuffer fileToReadBuffer = new StringBuffer();
            BufferedReader dataIO = new BufferedReader(new InputStreamReader(fileToRead));

            String pojoList;
            while ((pojoList = dataIO.readLine()) != null){
                fileToReadBuffer.append(pojoList +"\n");
            }

            dataIO.close();
            fileToRead.close();
            return fileToReadBuffer.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> void addPojoListToFile(String fileName, List<T> pojoList) {
        this.fileName = fileName;
        this.pojoList = pojoList;
        new BackgroundSaveItemsToFileActions().execute();
    }

    private class BackgroundSaveItemsToFileActions extends AsyncTask<Void, Void, Void> {

        public BackgroundSaveItemsToFileActions() {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                FileOutputStream fileWithData;
                try {
                    fileWithData = (context.openFileOutput(fileName, Context.MODE_PRIVATE));
                    try {
                        fileWithData.write(pojoJsonConverter.convertPojoToJson(pojoList).getBytes());
                        fileWithData.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(context, "DODANE", Toast.LENGTH_SHORT).show();
        }
    }
}
