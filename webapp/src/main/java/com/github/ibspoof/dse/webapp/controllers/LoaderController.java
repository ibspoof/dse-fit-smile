package com.github.ibspoof.dse.webapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ibspoof.dse.webapp.mappers.SmileMapper;
import com.github.ibspoof.dse.webapp.models.ThisDay;
import com.github.ibspoof.dse.webapp.pojos.onthisday.Event;
import com.github.ibspoof.dse.webapp.pojos.onthisday.OnThisDay;
import com.github.ibspoof.dse.webapp.repositories.OnThisDayRepository;
import com.github.ibspoof.dse.webapp.views.responses.SuccessResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/loader")
public class LoaderController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoaderController.class);

    private final String BASE_URL = "https://en.wikipedia.org/api/rest_v1/feed/onthisday/events/{month}/{day}";

    private ObjectMapper mapper = new ObjectMapper();
    private ObjectMapper smileMapper = SmileMapper.getMapper();

    @Autowired
    private OnThisDayRepository onThisDayRepository;

    @RequestMapping(value = "/{month}/{day}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse get(@PathVariable String month, @PathVariable String day) throws Exception {

        HttpResponse<String> createCoreResponse = Unirest.get(BASE_URL)
                .routeParam("month", month)
                .routeParam("day", day)
                .asString();

        logger.warn(createCoreResponse.getBody());

        OnThisDay onThisDay;
        try {
            onThisDay = mapper.readValue(createCoreResponse.getBody(), OnThisDay.class);
        } catch (IOException e) {
            throw new Exception("Unable to parse JSON");
        }

        for (Event event : onThisDay.getEvents()) {

            ThisDay thisDay = new ThisDay();
            thisDay.setDate(month + "-" + day);
            thisDay.setYear(event.getYear());
            thisDay.setTitle(event.getText());
            thisDay.setPages(smileMapper.writeValueAsBytes(event.getPages()));

            if (!onThisDayRepository.save(thisDay)) {
                throw new Exception("Unable to save things");
            }
        }

        return new SuccessResponse();
    }

}