package com.github.ibspoof.dse.transformer.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;
import com.fasterxml.jackson.dataformat.smile.SmileParser;

public class SmileMapper {

    private static ObjectMapper smileMapper = null;

    public static ObjectMapper getMapper() {

        if (smileMapper != null) {
            return smileMapper;
        }

        SmileFactory smileFactory = new SmileFactory();
        smileFactory.configure(SmileGenerator.Feature.CHECK_SHARED_NAMES, true);
        smileFactory.configure(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES, true);
        smileFactory.configure(SmileGenerator.Feature.ENCODE_BINARY_AS_7BIT, true);
        smileFactory.configure(SmileGenerator.Feature.WRITE_HEADER, true);
        smileFactory.configure(SmileGenerator.Feature.WRITE_END_MARKER, false);
        smileFactory.configure(SmileParser.Feature.REQUIRE_HEADER, false);

        smileMapper = new ObjectMapper(smileFactory);

        return smileMapper;
    }
}
