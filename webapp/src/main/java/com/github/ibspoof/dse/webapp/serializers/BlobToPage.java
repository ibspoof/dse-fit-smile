package com.github.ibspoof.dse.webapp.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.ibspoof.dse.webapp.controllers.ThisDayController;
import com.github.ibspoof.dse.webapp.mappers.SmileMapper;
import com.github.ibspoof.dse.webapp.pojos.onthisday.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class BlobToPage extends JsonSerializer<ByteBuffer> {

    private static final Logger logger = LoggerFactory.getLogger(ThisDayController.class);

    private ObjectMapper smileMapper = SmileMapper.getMapper();

    @Override
    public void serialize(ByteBuffer blob, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {

        List<Page> page = null;

        JavaType type = smileMapper.getTypeFactory().constructCollectionType(List.class, Page.class);

        try {
            page = smileMapper.readValue(blob.array(), type);
        } catch (Exception e) {
            logger.error("Unable to parse JSON", e);
        }

        jsonGenerator.writeObject(page);
    }
}