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
import org.openmrs.module.pharmacy.model.DrugMaxMin;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class MaxMinController {

    private static final Log log = LogFactory.getLog(MaxMinController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private JSONArray drugNamess;

    private String originalbindrug;

    private String drugstrength;

    private String drugunit;

    private String formulation;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;

    private ConceptService serviceDrugs;

    private String druguuid;
    private List<DrugMaxMin> drugMaxMin;
    private int size;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private List<PharmacyStore> pharmacyInventory;
    private int sizeList;
    private List<Drug> allDrugs;
    private DrugMaxMin maxMin;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/maxminName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        String drug = request.getParameter("drug");
        service = Context.getService(PharmacyService.class);
        drugMaxMin = service.getDrugMaxMin();
        size = drugMaxMin.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("total")) {

                    pharmacyInventory = service.getPharmacyInventory();
                    sizeList = pharmacyInventory.size();

                    if (size != 0) {
                        for (int i = 0; i < sizeList; i++) {
                            int val = getCheckSize(pharmacyInventory, i, drug);
                            if (val == 0) {

                            } else {
                                jsonArray.put("" + val);
                            }
                            if (val != 0)
                                break;
                        }
                    } else
                        jsonArray.put("" + null);

                    response.getWriter().print(jsonArray);
                }

            } else {

                if (size != 0) {
                    for (int i = 0; i < size; i++) {

                        jsonObject.accumulate("aaData", getArray(drugMaxMin, i));

                    }

                }
                if (!jsonObject.has("aaData")) {

                    datad2 = new JSONArray();
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    jsonObject.accumulate("aaData", datad2);

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
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/maxminName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String max = request.getParameter("max");//description
        String min = request.getParameter("min");//description
        String edit = request.getParameter("maxmineedit");
        String uuid = request.getParameter("maxminuuid");
        String uuidvoid = request.getParameter("maxminuuidvoid");
        String reason = request.getParameter("maxminreason");
        String drug = request.getParameter("maxmindrug");
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();

        service = Context.getService(PharmacyService.class);
        allDrugs = serviceDrugs.getAllDrugs();
        size = allDrugs.size();
        for (int i = 0; i < size; i++) {
            druguuid = getString(allDrugs, i, drug);
            if (druguuid != null)
                break;

        }

        drugMaxMin = service.getDrugMaxMin();
        int size = drugMaxMin.size();


        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {
                //check for same entry before saving
                for (int i = 0; i < size; i++) {

                    found = getCheck(drugMaxMin, i, druguuid);
                    if (found)
                        break;
                }

                if (!found) {
                    maxMin = new DrugMaxMin();

                    maxMin.setMax(Integer.parseInt(max));
                    maxMin.setMin(Integer.parseInt(min));
                    maxMin.setDrug(serviceDrugs.getDrugByUuid(druguuid));
                    service.saveDrugMaxMin(maxMin);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                maxMin = new DrugMaxMin();

                maxMin = service.getDrugMaxMinByUuid(uuid);

                if (userService.getAuthenticatedUser().getUserId().equals(maxMin.getCreator().getUserId())) {

                    // saving/updating a record
                    maxMin.setMax(Integer.parseInt(max));
                    maxMin.setMin(Integer.parseInt(min));
                    maxMin.setDrug(serviceDrugs.getDrugByUuid(druguuid));

                    service.saveDrugMaxMin(maxMin);
                }
            }

        } else if (uuidvoid != null) {

            maxMin = new DrugMaxMin();

            maxMin = service.getDrugMaxMinByUuid(uuidvoid);

            maxMin.setVoided(true);
            maxMin.setVoidReason(reason);

            service.saveDrugMaxMin(maxMin);

        }

    }

    public synchronized JSONArray getArray(List<DrugMaxMin> drugMaxMin, int size) {

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

            drugNamess.put("edit");
            editPharmacy = false;
        } else
            drugNamess.put("");


        drugNamess.put("");
        drugNamess.put(drugMaxMin.get(size).getUuid());
        drugNamess.put(drugMaxMin.get(size).getDrug().getName());
        drugNamess.put(drugMaxMin.get(size).getMax());
        drugNamess.put(drugMaxMin.get(size).getMin());

        if (deletePharmacy) {
            drugNamess.put("void");
            deletePharmacy = false;
        } else
            drugNamess.put("");

        return drugNamess;
    }

    public synchronized Integer getCheckSize(List<PharmacyStore> pharmacyStore, int size, String name) {

        if ((pharmacyStore.get(size).getDrugs().getName().equals(name))) {

            return pharmacyStore.get(size).getQuantity();

        } else
            return 0;

    }

    public synchronized boolean getCheck(List<DrugMaxMin> drugMaxMin, int size, String uuid) {
        if (drugMaxMin.get(size).getDrug().getUuid().toString().equalsIgnoreCase(uuid)) {

            return true;

        } else
            return false;

    }

    public synchronized String getString(List<Drug> dname, int size, String text) {

        if ((dname.get(size).getName().equalsIgnoreCase(text))) {

            return dname.get(size).getUuid();
        }
        return null;
    }
}
