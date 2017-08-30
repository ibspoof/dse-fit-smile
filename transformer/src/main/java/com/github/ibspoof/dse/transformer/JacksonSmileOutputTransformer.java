package com.github.ibspoof.dse.transformer;

import com.datastax.bdp.search.solr.FieldOutputTransformer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ibspoof.dse.transformer.mappers.SmileMapper;
import com.github.ibspoof.dse.transformer.pojos.onthisday.Page;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.StoredFieldVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class JacksonSmileOutputTransformer extends FieldOutputTransformer {

    private static final Logger logger = LoggerFactory.getLogger(JacksonSmileOutputTransformer.class);

    private ObjectMapper smileMapper = SmileMapper.getMapper();
    private ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public void stringField(FieldInfo fieldInfo,
                            String value,
                            StoredFieldVisitor visitor,
                            DocumentHelper helper) throws IOException {

        logger.error("name: " + fieldInfo.name + ", value: " + value);

        if (fieldInfo.name.equals("pages")) {

            try {

                JavaType type = smileMapper.getTypeFactory().constructCollectionType(List.class, Page.class);
                List<Page> pages = smileMapper.readValue(value.getBytes(), type);

                FieldInfo pagesJson = helper.getFieldInfo("pages_json");

                String jsonInString = jsonMapper.writeValueAsString(pages);

                logger.error("Json String {}", jsonInString);

                visitor.stringField(pagesJson, jsonInString.getBytes());

            } catch (IOException e) {
                logger.error(fieldInfo.name + " " + e.getMessage());
                throw e;
            }
        }
    }

}
