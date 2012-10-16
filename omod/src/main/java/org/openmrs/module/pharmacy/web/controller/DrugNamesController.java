package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.DrugName;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugNamesController {

    private static final Log log = LogFactory.getLog(DrugNamesController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private JSONArray drugNamess;

    private ConceptService serviceDrugs;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);

        List<DrugName> list = service.getDrugName();
        int size = list.size();


        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    List<Drug> list2 = serviceDrugs.getAllDrugs();

                    int size2 = list2.size();

                    for (int i = 0; i < size2; i++) {
                        jsons.put("" + getDropDown(list2, i));
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

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String drugName = request.getParameter("namename");
        String edit = request.getParameter("nameedit");
        String uuid = request.getParameter("nameuuid");
        String uuidvoid = request.getParameter("nameuuidvoid");
        String reason = request.getParameter("namereason");
        userService = Context.getUserContext();
        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<DrugName> list = service.getDrugName();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, drugName);
                    if (found)
                        break;
                }

                if (!found) {

                    DrugName drugNamee = new DrugName();
                    drugNamee.setDrugName(drugName);

                    service.saveDrugName(drugNamee);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                DrugName drugNamee = new DrugName();

                drugNamee = service.getDrugNameByUuid(uuid);
                if (userService.getAuthenticatedUser().getUserId().equals(drugNamee.getCreator().getUserId())) {

                    // saving/updating a record
                    drugNamee.setDrugName(drugName);//(drugName);

                    service.saveDrugName(drugNamee);
                }
            }

        } else if (uuidvoid != null) {

            DrugName drugNamee = new DrugName();

            drugNamee = service.getDrugNameByUuid(uuidvoid);

            drugNamee.setVoided(true);
            drugNamee.setVoidReason(reason);

            service.saveDrugName(drugNamee);

        }

    }

    public JSONArray getArray(List<DrugName> drugNames, int size) {

        drugNamess = new JSONArray();
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

            drugNamess.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
            editPharmacy = false;
        } else
            drugNamess.put("");

        drugNamess.put(drugNames.get(size).getUuid());
        drugNamess.put(drugNames.get(size).getDrugName());

        if (deletePharmacy) {
            drugNamess.put("<a href=#?uuid=" + drugNames.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            drugNamess.put("");

        return drugNamess;
    }

    public String getDropDown(List<Drug> drug, int size) {

        return drug.get(size).getName();

    }

    public boolean getCheck(List<DrugName> drugNames, int size, String drugNamess) {
        if (drugNames.get(size).getDrugName().equalsIgnoreCase(drugNamess)) {

            return true;

        } else
            return false;

    }
}
