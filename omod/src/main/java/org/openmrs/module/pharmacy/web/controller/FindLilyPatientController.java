package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.parser.ContainerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FindLilyPatientController {

    private static final Log log = LogFactory.getLog(FindLilyPatientController.class);

    private ContainerFactory containerFactory;

    @RequestMapping("module/pharmacy/findLilyPatient")
    public synchronized void pageLoad(ModelMap map) {


    }

}
