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
import org.openmrs.module.pharmacy.model.Dose;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugDosesController {

    private static final Log log = LogFactory.getLog(DrugDosesController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private String uuid;

    private boolean found = false;

    private ConceptService serviceDrugs;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;
    private int size, size2;
    private List<Dose> doseList = service.getDoses();
    private JSONObject jsonObject;
    private List<Drug> allDrugs;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDoses")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        doseList = service.getDoses();
        size = doseList.size();
        jsonObject = new JSONObject();

        try {

            for (int i = 0; i < size; i++) {

                jsonObject.accumulate("aaData", getArray(doseList, i));
            }
            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 10);

            response.setContentType("application/jsonObject");

            response.getWriter().print(jsonObject);
            response.flushBuffer();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDoses")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();

        String dosesFrequency = request.getParameter("dosesFrequency");
        String dosesDrug = request.getParameter("dosesDrug");
        String doseQuantity = request.getParameter("doseQuantity");
        String dosesReason = request.getParameter("dosesReason");
        String dosesUuidVoid = request.getParameter("dosesUuidVoid");

        String dosesName = request.getParameter("dosesName");
        String dosesEdit = request.getParameter("dosesEdit");
        String dosesUuid = request.getParameter("dosesUuid");
        if (dosesUuidVoid == null) {
            dosesDrug = dosesDrug.substring(0, dosesDrug.indexOf("("));

            String uuidValue = service.getDrugNameByName(dosesDrug).getUuid();

            allDrugs = serviceDrugs.getAllDrugs();
            size2 = allDrugs.size();

        }
        if (dosesEdit != null) {
            if (dosesEdit.equalsIgnoreCase("false")) {


            } else if (dosesEdit.equalsIgnoreCase("true")) {

            }

        } else if (dosesUuidVoid != null) {

            Dose dose = new Dose();
            dose = service.getDosesByUuid(dosesUuidVoid);

            dose.setVoided(true);
            dose.setVoidReason(dosesReason);

            service.saveDoses(dose);

        }

    }

    public synchronized JSONArray getArray(List<Dose> doses, int size) {

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
        drugStrengthA.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
        drugStrengthA.put(doses.get(size).getUuid());
        drugStrengthA.put(service.getDrugFrequencyByUuid(doses.get(size).getFrequency().getUuid()).getFrequencyName());
        drugStrengthA.put(doses.get(size).getQuantity());

        if (deletePharmacy) {
            drugStrengthA.put("<a href=#?uuid=" + doses.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            drugStrengthA.put("");

        return drugStrengthA;
    }


}
