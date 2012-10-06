package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class barcode {

    private static final Log log = LogFactory.getLog(barcode.class);



    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/barcode")
    public  synchronized void pageLoad(ModelMap map) {

    }

}
