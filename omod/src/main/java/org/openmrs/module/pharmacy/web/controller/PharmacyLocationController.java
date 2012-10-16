package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class PharmacyLocationController {

    public boolean checked;

    private static final Log log = LogFactory.getLog(PharmacyLocationController.class);

    public PharmacyService service;

    private UserContext userService;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/locationSetter")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        response.setContentType("application/json");
        String drop = request.getParameter("drop");


        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int size = listUsers.size();
        JSONArray jsons = new JSONArray();


        try {

            if (drop != null) {

                jsons.put("" + request.getSession().getAttribute("location"));

            } else {


                if (size > 1) {

                    if (request.getSession().getAttribute("location") != null)
                        jsons.put("" + request.getSession().getAttribute("location"));
                    else
                        jsons.put("none");

                } else if (size == 1) {

                    jsons.put("" + listUsers.get(0).getLocation());

                } else
                    jsons.put("none");

            }
            response.getWriter().print(jsons);

            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/locationSetter")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);

        userService = Context.getUserContext();
        response.setContentType("application/json");
        try {

            String location = request.getParameter("locationsVal");
            if (location != null) {

                if (request.getSession().getAttribute("location") == null) {
                    request.getSession(true).setAttribute("location", location);
                    response.getWriter().print(service.setPharmacyLocation(location));
                } else {


                    request.getSession(false).removeAttribute("location");

                    request.getSession(false).setAttribute("location", location);
                    response.getWriter().print(service.setPharmacyLocation(location));


                }

            }

            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }
}
