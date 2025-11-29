package com.example.hyperativa_back_end.services;

import com.example.hyperativa_back_end.dtos.beanio.CardRecord;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PositionalFileService {

    public List<CardRecord> parseCards(String base64Content) {
        // Decode Base64 string into bytes
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content);

        // Create reader from decoded bytes
        Reader reader = new InputStreamReader(new ByteArrayInputStream(decodedBytes));

        // Load BeanIO mapping and create reader
        StreamFactory factory = StreamFactory.newInstance();
        factory.loadResource("card-mapping.xml");
        BeanReader beanReader = factory.createReader("cardStream", reader);

        // Read records
//        TODO finish
        Object record;
        List<CardRecord> cards = new ArrayList<>();
        while ((record = beanReader.read()) != null) {
            if (record instanceof CardRecord card) {
                cards.add(card);
            }
        }

        beanReader.close();
        return cards;
    }

}