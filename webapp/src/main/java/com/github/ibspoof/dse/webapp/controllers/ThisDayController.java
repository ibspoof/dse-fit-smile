package com.github.ibspoof.dse.webapp.controllers;

import com.datastax.driver.mapping.Result;
import com.github.ibspoof.dse.webapp.models.ThisDay;
import com.github.ibspoof.dse.webapp.repositories.OnThisDayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/thisday")
public class ThisDayController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ThisDayController.class);

    @Autowired
    private OnThisDayRepository onThisDayRepository;

    @RequestMapping(value = "/{month}/{day}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ThisDay> get(@PathVariable String month, @PathVariable String day) {

        String date = month + "-" + day;
        Result<ThisDay> rs = onThisDayRepository.getAllByDay(date);
        return rs.all();
    }


    @RequestMapping(value = "/{month}/{day}/{year}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ThisDay> getByDateAndYear(@PathVariable String month, @PathVariable String day, @PathVariable Integer year) {

        String date = month + "-" + day;

        Result<ThisDay> rs = onThisDayRepository.getByDayAndYear(date, year);
        return rs.all();
    }

}