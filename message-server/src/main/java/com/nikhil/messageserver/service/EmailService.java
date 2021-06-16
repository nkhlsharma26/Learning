package com.nikhil.messageserver.service;


import com.nikhil.messageserver.Model.OrderDetail;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service("emailService")
public class EmailService implements MessageService{
    private static final String COMMA_DELIMITER = ",";
    private static final String FILE_NAME_WITH_EMAIL_LIST = "../IntraDayEquitiesData/emailList.csv";
    private static final String FILE_WITH_ORDER_DATA = "../IntraDayEquitiesData/BuyData.csv";

    private final Logger logger = LogManager.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.subject}")
    private String subject;

    @Value("${spring.mail.senderName}")
    private String senderName;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    @Scheduled(fixedDelay = 30000)
    public void sendMessage() {
        Map<String, OrderDetail> text = readCsvDataToMap(FILE_WITH_ORDER_DATA);
        if(!text.isEmpty()){
            sendEmail(text);
        }
    }

    private void sendEmail(Map<String, OrderDetail> text) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setSubject(subject);

        List<String> emailList = getCSVData(FILE_NAME_WITH_EMAIL_LIST);
        String[] array = emailList.toArray(new String[0]);
        msg.setTo(array);
        msg.setCc(senderEmail);
        msg.setText("Hi,\r\nI recommend you to buy the following stock.\r\nDetails are as under:\r\n \r\n"
                + text.get("0").toString() + "\r\n \r\nhttp://localhost:4200/ \r\nRegards\r\n" + senderName);

        logger.info("Sending mail to :" + StringUtils.join(array,","));
        mailSender.send(msg);
        File file = new File(FILE_WITH_ORDER_DATA);
        if(file.delete()){
            logger.info("Order details data deleted after sending email.");
        }
        else {
            logger.error("Unable to delete order data. PLease delete manually.");
        }

    }

    private List<String> getCSVData(String fileName) {
        List<String> emailList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                emailList.add(getRecord(scanner.nextLine()));
            }
            logger.info("Data read successfully from emailList.csv");
        }
        catch (FileNotFoundException ex){
            logger.error("File "+ fileName +" was not found.");
        }
        return emailList;
    }

    private String getRecord(String line) {
        String value = null;
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                value = rowScanner.next();
            }
        }
        return value;
    }

    private Map<String, OrderDetail> readCsvDataToMap(String fileName) {
        CSVReader reader;
        Map<String, OrderDetail> csvData = new HashMap<>();
        try {
            reader = new CSVReader(new FileReader(fileName));
            List<String[]> allRows = reader.readAll();
            allRows.remove(0); // remove headers
            for(String[] row : allRows){
                OrderDetail orderDetail = new OrderDetail(row[0], row[1],row[2],row[3], row[4],row[5],row[6],row[7],row[8],row[9],row[10],row[11],row[12],row[13],row[14],row[15],row[16],row[17]);
                csvData.put(row[0],orderDetail);
            }
            reader.close();
        } catch (IOException | CsvException e) {
            logger.info("CSV file not found.");
        }
        return csvData;
    }
}
