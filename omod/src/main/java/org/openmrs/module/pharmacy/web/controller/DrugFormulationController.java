package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.DrugFormulation;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugFormulationController {

    private static final Log log = LogFactory.getLog(DrugFormulationController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;
    private List<DrugFormulation> drugFormulationList;
    private List<DrugFormulation> drugFormulationList1;
    private int size;
    private JSONObject jsonObject;
    private JSONArray jsonArray;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugFormulation")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String uuid = request.getParameter("uuid");
        String drop = request.getParameter("drop");

        service = Context.getService(PharmacyService.class);
        drugFormulationList = service.getDrugFormulation();
        size = drugFormulationList.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();


        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {

                        jsonArray.put("" + getDropDown(drugFormulationList, i));
                    }

                    response.getWriter().print(jsonArray);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArray(drugFormulationList, i));

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);

                response.getWriter().print(jsonObject);
            }
            response.flushBuffer();
        } catch (Exception e) {
            // TODO Auto-generated catch block

        }

        //log.info(jsonObject);

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugFormulation")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String formulationName = request.getParameter("formulationname");
        String edit = request.getParameter("edit");
        String uuid = request.getParameter("uuid");
        String uuidvoid = request.getParameter("uuidvoid");
        String reason = request.getParameter("reason");
        userService = Context.getUserContext();

        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                drugFormulationList = service.getDrugFormulation();
                size = drugFormulationList.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(drugFormulationList, i, formulationName);
                    if (found)
                        break;
                }

                if (!found) {

                    DrugFormulation drugFormulation = new DrugFormulation();
                    drugFormulation.setFormulation(formulationName);

                    service.saveDrugFormulation(drugFormulation);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {

                DrugFormulation drugFormulation = new DrugFormulation();

                drugFormulation = service.getDrugFormulationByUuid(uuid);
                if (userService.getAuthenticatedUser().getUserId().equals(drugFormulation.getCreator().getUserId())) {

                    // saving/updating a record
                    drugFormulation.setFormulation(formulationName);

                    service.saveDrugFormulation(drugFormulation);
                }
            }

        } else if (uuidvoid != null) {

            DrugFormulation drugFormulation = new DrugFormulation();

            drugFormulation = service.getDrugFormulationByUuid(uuidvoid);

            drugFormulation.setVoided(true);
            drugFormulation.setVoidReason(reason);

            service.saveDrugFormulation(drugFormulation);

        }

    }

    public synchronized JSONArray getArray(List<DrugFormulation> drugFormulation, int size) {

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
        drugStrengthA.put(drugFormulation.get(size).getUuid());
        drugStrengthA.put(drugFormulation.get(size).getFormulation());
        if (deletePharmacy) {
            drugStrengthA.put("<a href=#?uuid=" + drugFormulation.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            drugStrengthA.put("");

        return drugStrengthA;
    }

    public synchronized String getDropDown(List<DrugFormulation> drugFormulation, int size) {

        return drugFormulation.get(size).getFormulation();
    }

    public synchronized boolean getCheck(List<DrugFormulation> drugFormulation, int size, String formulationName) {
        if (drugFormulation.get(size).getFormulation().equalsIgnoreCase(formulationName)) {

            return true;

        } else
            return false;

    }
}
