package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.DrugStrength;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugStrengthController {

    private static final Log log = LogFactory.getLog(DrugStrengthController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugStrength")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        String drop = request.getParameter("drop");
        List<DrugStrength> list = service.getDrugStrength();
        int size = list.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {

                        jsons.put(getDropDown(list, i));
                    }

                    response.getWriter().print(jsons);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    json.accumulate("aaData", getArray(list, i));

                }
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);

            }
            response.flushBuffer();
            editPharmacy = false;

            deletePharmacy = false;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugStrength")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String strengthreason = request.getParameter("strengthreason");
        String strengthuuidvoid = request.getParameter("strengthuuidvoid");
        userService = Context.getUserContext();
        String strengthname = request.getParameter("strengthname");
        String strengthedit = request.getParameter("strengthedit");
        String strengthuuid = request.getParameter("strengthuuid");

        if (strengthedit != null) {
            if (strengthedit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<DrugStrength> list = service.getDrugStrength();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, strengthname);
                    if (found)
                        break;
                }

                if (!found) {

                    DrugStrength drugStrength = new DrugStrength();
                    drugStrength.setDrugStrength(strengthname);
                    service.saveDrugStrength(drugStrength);

                } else //do code to display to the user
                {

                }

            } else if (strengthedit.equalsIgnoreCase("true")) {
                DrugStrength drugStrength = new DrugStrength();
                drugStrength = service.getDrugStrengthByUuid(strengthuuid);
                if (userService.getAuthenticatedUser().getUserId().equals(drugStrength.getCreator().getUserId())) {

                    // saving/updating a record
                    drugStrength.setDrugStrength(strengthname);

                    service.saveDrugStrength(drugStrength);
                }
            }

        } else if (strengthuuidvoid != null) {

            DrugStrength drugStrength = new DrugStrength();
            drugStrength = service.getDrugStrengthByUuid(strengthuuidvoid);

            drugStrength.setVoided(true);
            drugStrength.setVoidReason(strengthreason);

            service.saveDrugStrength(drugStrength);

        }

    }

    public synchronized JSONArray getArray(List<DrugStrength> drugStrength, int size) {

        drugStrengthA = new JSONArray();
        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {

            if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider")) || (rl.getRole().equals("	Authenticated "))) {

                editPharmacy = true;
                deletePharmacy = true;
            }


            if (rl.hasPrivilege("Edit Pharmacy")) {
                editPharmacy = true;
            }

            if (rl.hasPrivilege("Delete Pharmacy")) {
                deletePharmacy = true;
            }

        }

        if (editPharmacy) {

            drugStrengthA.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
            editPharmacy = false;
        } else
            drugStrengthA.put("");
        drugStrengthA.put(drugStrength.get(size).getUuid());
        drugStrengthA.put(drugStrength.get(size).getDrugStrength());
        if (deletePharmacy) {
            drugStrengthA.put("<a href=#?uuid=" + drugStrength.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            drugStrengthA.put("");

        return drugStrengthA;
    }

    public synchronized String getDropDown(List<DrugStrength> drugStrength, int size) {

        return drugStrength.get(size).getDrugStrength();
    }

    public synchronized boolean getCheck(List<DrugStrength> drugStrength, int size, String name) {
        if (drugStrength.get(size).getDrugStrength().equalsIgnoreCase(name)) {

            return true;

        } else
            return false;

    }
}
