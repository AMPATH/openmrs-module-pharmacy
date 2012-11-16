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


    public PharmacyService service;

    private boolean found = false;

    private JSONArray supplierNames;


    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray jsonArray;

    private JSONObject jsonObject;

    private int size;


    private List<PharmacyCategory> pharmacyCategoryList;

    private PharmacyCategory pharmacyCategoryObject;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/categoryName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {


        //get parameters
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");


        service = Context.getService(PharmacyService.class);
        pharmacyCategoryList = service.getPharmacyCategory();
        userService = Context.getUserContext();

        int size = pharmacyCategoryList.size();

        jsonObject = new JSONObject();


        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {
                        jsonArray.put("" + getDropDown(pharmacyCategoryList, i));
                    }

                    response.getWriter().print(jsonArray);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArray(pharmacyCategoryList, i));

                }

                if (!jsonObject.has("aaData")) {

                    jsonArray = new JSONArray();
                    jsonArray.put("None");
                    jsonArray.put("None");
                    jsonArray.put("None");
                    jsonArray.put("None");

                    jsonArray.put("None");
                    jsonArray.put("None");

                    jsonObject.accumulate("aaData", jsonArray);

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);


                response.getWriter().print(jsonObject);
            }
            response.flushBuffer();

        } catch (Exception e) {

            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/categoryName")
    public synchronized void pageLoadPost(HttpServletRequest request, HttpServletResponse response) {


        // get parameters
        String categoryName = request.getParameter("categoryname");
        String description = request.getParameter("description");
        String categoryEdit = request.getParameter("categoryedit");
        String categoryUuid = request.getParameter("categoryuuid");
        String categoryUuidVoid = request.getParameter("categoryuuidvoid");
        String categoryReason = request.getParameter("categoryreason");


        //openmrs constants
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);

        if (categoryEdit != null) {
            //making a new entry
            if (categoryEdit.equalsIgnoreCase("false")) {


                pharmacyCategoryList = service.getPharmacyCategory();
                size = pharmacyCategoryList.size();

                //check if there is an entry before saving
                for (int i = 0; i < size; i++) {

                    found = getCheck(pharmacyCategoryList, i, categoryName);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacyCategory pharmacyCategory = new PharmacyCategory();

                    pharmacyCategory.setName(categoryName);
                    pharmacyCategory.setDescription(description);

                    service.savePharmacyCategory(pharmacyCategory);

                } else //do code to display to the user that there is a duplicate entry
                {

                }

            }   // Editing an existing entry
            else if (categoryEdit.equalsIgnoreCase("true"))

            {

                pharmacyCategoryObject = service.getPharmacyCategoryByUuid(categoryUuid);

                // saving/updating a record
                pharmacyCategoryObject.setName(categoryName);
                pharmacyCategoryObject.setDescription(description);
                service.savePharmacyCategory(pharmacyCategoryObject);

            }

        } else if (categoryUuidVoid != null) {     // user voiding an entry


            pharmacyCategoryObject = service.getPharmacyCategoryByUuid(categoryUuidVoid);

            pharmacyCategoryObject.setVoided(true);
            pharmacyCategoryObject.setVoidReason(categoryReason);

            service.savePharmacyCategory(pharmacyCategoryObject);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyCategory> pharmacyCategoryList1, int size) {

        supplierNames = new JSONArray();

        // check the user permission

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
        supplierNames.put(pharmacyCategoryList1.get(size).getUuid());
        supplierNames.put(pharmacyCategoryList1.get(size).getName());
        supplierNames.put(pharmacyCategoryList1.get(size).getDescription());
        if (deletePharmacy) {
            supplierNames.put("void");
            deletePharmacy = false;
        } else
            supplierNames.put("");

        return supplierNames;
    }

    public synchronized String getDropDown(List<PharmacyCategory> pharmacyCategories, int size) {

        return pharmacyCategories.get(size).getName();
    }

    public synchronized boolean getCheck(List<PharmacyCategory> pharmacyCategories, int size, String names) {
        if (pharmacyCategories.get(size).getName().equalsIgnoreCase(names)) {

            return true;

        } else
            return false;

    }
}
