package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class CategoryNamesController {

    private static final Log log = LogFactory.getLog(CategoryNamesController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private JSONArray supplierNames;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/categoryName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);
        List<PharmacyCategory> list = service.getPharmacyCategory();
        int size = list.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {
                        jsons.put("" + getDropDown(list, i));
                    }

                    response.getWriter().print(jsons);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    json.accumulate("aaData", getArray(list, i));

                }

                if (!json.has("aaData")) {

                    datad2 = new JSONArray();
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

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/categoryName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        String categoryName = request.getParameter("categoryname");
        String description = request.getParameter("description");
        String edit = request.getParameter("categoryedit");
        String uuid = request.getParameter("categoryuuid");
        String uuidvoid = request.getParameter("categoryuuidvoid");
        String reason = request.getParameter("categoryreason");
        userService = Context.getUserContext();
        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<PharmacyCategory> list = service.getPharmacyCategory();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, categoryName);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacyCategory pharmacyCategory = new PharmacyCategory();

                    pharmacyCategory.setName(categoryName);
                    pharmacyCategory.setDescription(description);

                    service.savePharmacyCategory(pharmacyCategory);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                PharmacyCategory categoryNamee = new PharmacyCategory();

                categoryNamee = service.getPharmacyCategoryByUuid(uuid);

                // saving/updating a record
                categoryNamee.setName(categoryName);//(drugName);
                categoryNamee.setDescription(description);
                service.savePharmacyCategory(categoryNamee);

            }

        } else if (uuidvoid != null) {

            PharmacyCategory categoryNamee = new PharmacyCategory();

            categoryNamee = service.getPharmacyCategoryByUuid(uuidvoid);

            categoryNamee.setVoided(true);
            categoryNamee.setVoidReason(reason);

            service.savePharmacyCategory(categoryNamee);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyCategory> categoryNamee, int size) {

        supplierNames = new JSONArray();

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

            supplierNames.put("edit");
            editPharmacy = false;
        } else
            supplierNames.put("");
        supplierNames.put("");
        supplierNames.put(categoryNamee.get(size).getUuid());
        supplierNames.put(categoryNamee.get(size).getName());
        supplierNames.put(categoryNamee.get(size).getDescription());
        if (deletePharmacy) {
            supplierNames.put("void");
            deletePharmacy = false;
        } else
            supplierNames.put("");

        return supplierNames;
    }

    public synchronized String getDropDown(List<PharmacyCategory> categoryNamee, int size) {

        return categoryNamee.get(size).getName();
    }

    public synchronized boolean getCheck(List<PharmacyCategory> categoryNamee, int size, String names) {
        if (categoryNamee.get(size).getName().equalsIgnoreCase(names)) {

            return true;

        } else
            return false;

    }
}
