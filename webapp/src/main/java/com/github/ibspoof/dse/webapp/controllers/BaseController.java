package com.github.ibspoof.dse.webapp.controllers;

import com.github.ibspoof.dse.webapp.services.CassandraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
abstract class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private CassandraService cassandraService;

}
