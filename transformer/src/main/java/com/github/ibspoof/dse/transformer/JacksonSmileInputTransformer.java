package com.github.ibspoof.dse.transformer;

import com.datastax.bdp.search.solr.FieldInputTransformer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ibspoof.dse.transformer.mappers.SmileMapper;
import com.github.ibspoof.dse.transformer.pojos.onthisday.Page;
import org.apache.commons.codec.binary.Hex;
import org.apache.lucene.document.Document;
import org.apache.solr.common.SolrException;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.apache.solr.common.SolrException.ErrorCode.BAD_REQUEST;

public class JacksonSmileInputTransformer extends FieldInputTransformer {

    private static final Logger logger = LoggerFactory.getLogger(JacksonSmileInputTransformer.class);


    private ObjectMapper smileMapper = SmileMapper.getMapper();

    @Override
    public boolean evaluate(String field) {
        return field.equals("pages");
    }

    @Override
    public void addFieldToDocument(SolrCore core, IndexSchema schema, String key, Document doc, SchemaField fieldInfo,
                                   String fieldValue, DocumentHelper helper) throws IOException {

        try {

            SchemaField pagesLang = schema.getFieldOrNull("pages_lang");
            SchemaField pagesPageId = schema.getFieldOrNull("pages_pageid");
            SchemaField pagesTimestamp = schema.getFieldOrNull("pages_timestamp");

            byte[] raw = Hex.decodeHex(fieldValue.toCharArray());

            JavaType type = smileMapper.getTypeFactory().constructCollectionType(List.class, Page.class);

            List<Page> pages = smileMapper.readValue(raw, type);

            logger.trace("Processing Page Cnt {}", pages.size());
            logger.trace("Processing Key: {}", key);

            for (Page page : pages) {
                logger.trace("Page parsing: {}", page.getPageid().toString());

                if (pagesLang != null) {
                    helper.addFieldToDocument(core, schema, key, doc, pagesLang, page.getLang());
                }
                if (pagesPageId != null) {
                    helper.addFieldToDocument(core, schema, key, doc, pagesPageId, page.getPageid().toString());
                }
                if (pagesTimestamp != null) {
                    Long timestamp = Date.from(Instant.parse(page.getTimestamp())).getTime();
                    helper.addFieldToDocument(core, schema, key, doc, pagesTimestamp, timestamp.toString());
                }
            }

        } catch (Exception ex) {
            throw new SolrException(BAD_REQUEST, "Unable to process 'pages' field.", ex);
        }
    }
}