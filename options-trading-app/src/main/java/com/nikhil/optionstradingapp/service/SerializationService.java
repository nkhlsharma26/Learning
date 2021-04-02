package com.nikhil.optionstradingapp.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SerializationService <T>{

    private Logger logger = LogManager.getLogger(SerializationService.class);
    private T t;

    public void serializeObject(T t, String fileName) {
        try {
            FileOutputStream fileOutputStream
                    = new FileOutputStream( fileName+".txt");
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(t);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch (IOException ex){
            logger.error("Exception occured while serialization : "+t.getClass());
            logger.error(ex.getStackTrace());
            System.out.println("Unable to serialize object. Please check logs for more details.");
        }

    }

    public T deserializeObject(String fileName) {
        T t = null;
        try {
            FileInputStream fileInputStream
                    = new FileInputStream(fileName + ".txt");
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            t = (T) objectInputStream.readObject();
            objectInputStream.close();
        }catch (ClassNotFoundException | IOException ex){
            logger.error("Exception occured while serialization : "+t.getClass());
            logger.error(ex.getStackTrace());
            System.out.println("Unable to serialize object. Please check logs for more details.");
        }
        return t;
    }
}
