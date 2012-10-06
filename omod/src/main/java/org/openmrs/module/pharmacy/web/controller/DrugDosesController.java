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

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDoses")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        List<Dose> List = service.getDoses();
        int size = List.size();
        JSONObject json = new JSONObject();

        try {

            for (int i = 0; i < size; i++) {

                json.accumulate("aaData", getArray(List, i));
            }
            json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
            json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
            json.accumulate("iDisplayStart", 0);
            json.accumulate("iDisplayLength", 10);

            response.setContentType("application/json");

            response.getWriter().print(json);
            response.flushBuffer();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDoses")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();

        String dosesfrequency = request.getParameter("dosesfrequency");
        String dosesdrug = request.getParameter("dosesdrug");
        String dosequantity = request.getParameter("dosequantity");
        String dosesreason = request.getParameter("dosesreason");
        String dosesuuidvoid = request.getParameter("dosesuuidvoid");

        String dosesname = request.getParameter("dosesname");
        String dosesedit = request.getParameter("dosesedit");
        String dosesuuid = request.getParameter("dosesuuid");
        if (dosesuuidvoid == null) {
            dosesdrug = dosesdrug.substring(0, dosesdrug.indexOf("("));

            String uuidvalue = service.getDrugNameByName(dosesdrug).getUuid();

            List<Drug> dname = serviceDrugs.getAllDrugs();
            int dnames = dname.size();
            for (int i = 0; i < dnames; i++) {
                uuid = getString(dname, i, uuidvalue);
                if (uuid != null)
                    break;

            }
        }
        if (dosesedit != null) {
            if (dosesedit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<Dose> list = service.getDoses();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, service.getDrugsByUuid(uuid).getUuid(),
                            service.getDrugFrequencyByName(dosesfrequency).getUuid(), Integer.parseInt(dosequantity));
                    if (found)
                        break;
                }

                if (!found) {

                    Dose dose = new Dose();
                    //dose.setPharmacyDrug(service.getDrugsByUuid(uuid));
                    dose.setFrequency(service.getDrugFrequencyByName(dosesfrequency));
                    dose.setQuantity(Integer.parseInt(dosequantity));

                    service.saveDoses(dose);

                } else { ///inform the user that the entry exi

                }

            } else if (dosesedit.equalsIgnoreCase("true")) {
                Dose dose = new Dose();
                dose = service.getDosesByUuid(dosesuuid);
                if (userService.getAuthenticatedUser().getUserId().equals( dose.getCreator().getUserId())) {

                    //dose.setPharmacyDrug(service.getDrugsByUuid(uuid));
                    dose.setFrequency(service.getDrugFrequencyByName(dosesfrequency));
                    dose.setQuantity(Integer.parseInt(dosequantity));
                }
                service.saveDoses(dose);

            }

        }

        else if (dosesuuidvoid != null) {

            Dose dose = new Dose();
            dose = service.getDosesByUuid(dosesuuidvoid);

            dose.setVoided(true);
            dose.setVoidReason(dosesreason);

            service.saveDoses(dose);

        }

    }

    public synchronized JSONArray getArray(List<Dose> doses, int size) {

        drugStrengthA = new JSONArray();

        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {

            if((rl.getRole().equals("System Developer"))||(rl.getRole().equals("Provider"))||(rl.getRole().equals("	Authenticated "))){

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
        //drugStrengthA.put(service.getDrugsByUuid(doses.get(size).getPharmacyDrug().getUuid()).getDrugName().getDrugName());
        drugStrengthA.put(service.getDrugFrequencyByUuid(doses.get(size).getFrequency().getUuid()).getFrequencyName());
        drugStrengthA.put(doses.get(size).getQuantity());

        if (deletePharmacy) {
            drugStrengthA.put("<a href=#?uuid=" + doses.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            drugStrengthA.put("");

        return drugStrengthA;
    }

    public synchronized boolean getCheck(List<Dose> doses, int size, String uuid, String freq, int quantity) {

        //		if ((doses.get(size).getPharmacyDrug().getUuid().equals(uuid)
        //		        && doses.get(size).getFrequency().getUuid().equalsIgnoreCase(freq) && doses.get(size).getQuantity()
        //		        .equals(quantity))) {
        //
        //			return true;
        //
        //		} else
        return false;

    }

    public synchronized String getString(List<Drug> dname, int size, String text) {

        //		if (dname.get(size).getDrugName().getUuid().equalsIgnoreCase(text)) {
        //
        //			return dname.get(size).getUuid();
        //		}
        return null;
    }

}
