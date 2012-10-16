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

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/maxminName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        String drug = request.getParameter("drug");
        service = Context.getService(PharmacyService.class);
        List<DrugMaxMin> list = service.getDrugMaxMin();
        int size = list.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("total")) {

                    List<PharmacyStore> listSize = service.getPharmacyInventory();
                    int sizeList = listSize.size();

                    if (size != 0) {
                        for (int i = 0; i < sizeList; i++) {
                            int val = getCheckSize(listSize, i, drug);
                            if (val == 0) {

                            } else {
                                jsons.put("" + val);
                            }
                            if (val != 0)
                                break;
                        }
                    } else
                        jsons.put("" + null);

                    response.getWriter().print(jsons);
                }

            } else {

                if (size != 0) {
                    for (int i = 0; i < size; i++) {

                        json.accumulate("aaData", getArray(list, i));

                    }

                }
                if (!json.has("aaData")) {

                    datad2 = new JSONArray();
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    json.accumulate("aaData", datad2);

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
        List<Drug> dname = serviceDrugs.getAllDrugs();
        int dnames = dname.size();
        for (int i = 0; i < dnames; i++) {
            druguuid = getString(dname, i, drug);
            if (druguuid != null)
                break;

        }

        List<DrugMaxMin> list = service.getDrugMaxMin();
        int size = list.size();

        /*  if (size == 0) {
            DrugMaxMin drugMaxMin = new DrugMaxMin();

            drugMaxMin.setMax(Integer.parseInt(max));
            drugMaxMin.setMin(Integer.parseInt(min));
            drugMaxMin.setDrug(serviceDrugs.getDrugByUuid(druguuid));
            service.saveDrugMaxMin(drugMaxMin);


        }*/

        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {
                //check for same entry before saving
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, druguuid);
                    if (found)
                        break;
                }

                if (!found) {
                    DrugMaxMin drugMaxMin = new DrugMaxMin();

                    drugMaxMin.setMax(Integer.parseInt(max));
                    drugMaxMin.setMin(Integer.parseInt(min));
                    drugMaxMin.setDrug(serviceDrugs.getDrugByUuid(druguuid));
                    service.saveDrugMaxMin(drugMaxMin);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                DrugMaxMin drugMaxMin = new DrugMaxMin();

                drugMaxMin = service.getDrugMaxMinByUuid(uuid);

                if (userService.getAuthenticatedUser().getUserId().equals(drugMaxMin.getCreator().getUserId())) {

                    // saving/updating a record
                    drugMaxMin.setMax(Integer.parseInt(max));
                    drugMaxMin.setMin(Integer.parseInt(min));
                    drugMaxMin.setDrug(serviceDrugs.getDrugByUuid(druguuid));

                    service.saveDrugMaxMin(drugMaxMin);
                }
            }

        } else if (uuidvoid != null) {

            DrugMaxMin drugMaxMin = new DrugMaxMin();

            drugMaxMin = service.getDrugMaxMinByUuid(uuidvoid);

            drugMaxMin.setVoided(true);
            drugMaxMin.setVoidReason(reason);

            service.saveDrugMaxMin(drugMaxMin);

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
