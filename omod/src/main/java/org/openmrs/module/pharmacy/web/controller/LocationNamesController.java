package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
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
public class LocationNamesController {

    private static final Log log = LogFactory.getLog(LocationNamesController.class);

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

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/locationName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {


        service = Context.getService(PharmacyService.class);
        List<PharmacyLocations> list = service.getPharmacyLocations();




        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        String drug = request.getParameter("drug");
        String user = request.getParameter("user");
        int psize;



        int size = list.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            jsons.put("" + getDropDown(list, i));
                        }

                    } else
                        jsons.put("" + null);

                    response.getWriter().print(jsons);
                } else if (drop.equalsIgnoreCase("total")) {

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


            }

            else if (user != null) {




                List<User> p= Context.getUserService().getUsers(user, Context.getUserService().getRoles(), true);

                psize=p.size();


                JSONArray temp = new JSONArray();
                for (int i = 0; i < psize; i++) {


                    temp.put(p.get(i).getUsername() );


                }

                response.getWriter().print(temp);
            }
            else {


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
                    json.accumulate("aaData", datad2);

                }
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            }
            response.flushBuffer();

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/locationName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String locationName = request.getParameter("locationname");//description
        String description = request.getParameter("description");//description
        String edit = request.getParameter("locationeedit");
        String uuid = request.getParameter("locationuuid");
        String uuidvoid = request.getParameter("locationuuidvoid");
        String reason = request.getParameter("locationreason");

        String username = request.getParameter("username");
        userService = Context.getUserContext();
        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {
                //check for same entry before saving
                List<PharmacyLocations> list = service.getPharmacyLocations();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, locationName);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacyLocations pharmacyLocations = new PharmacyLocations();
                    pharmacyLocations.setName(locationName);
                    pharmacyLocations.setDescription(description);

                    service.savePharmacyLocations(pharmacyLocations);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                PharmacyLocations pharmacyLocations = new PharmacyLocations();

                pharmacyLocations = service.getPharmacyLocationsByUuid(uuid);

                if (userService.getAuthenticatedUser().getUserId().equals( pharmacyLocations.getCreator().getUserId())) {

                    // saving/updating a record
                    pharmacyLocations.setName(locationName);
                    pharmacyLocations.setDescription(description);

                    service.savePharmacyLocations(pharmacyLocations);
                }
            }

        }

        else if (uuidvoid != null) {

            PharmacyLocations pharmacyLocations = new PharmacyLocations();

            pharmacyLocations = service.getPharmacyLocationsByUuid(uuidvoid);

            pharmacyLocations.setVoided(true);
            pharmacyLocations.setVoidReason(reason);

            service.savePharmacyLocations(pharmacyLocations);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyLocations> pharmacyLocations, int size) {

        drugNamess = new JSONArray();
        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {

            if((rl.equals("System Developer"))||(rl.equals("Provider"))){

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
        drugNamess.put(pharmacyLocations.get(size).getUuid());
        drugNamess.put(pharmacyLocations.get(size).getName());
        drugNamess.put(pharmacyLocations.get(size).getDescription());

        if (deletePharmacy) {
            drugNamess.put("void");
            deletePharmacy = false;
        } else
            drugNamess.put("");
        return drugNamess;
    }

    public synchronized String getDropDown(List<PharmacyLocations> pharmacyLocations, int size) {

        return pharmacyLocations.get(size).getName();
    }

    public synchronized Integer getCheckSize(List<PharmacyStore> pharmacyStore, int size, String name) {

        if ((pharmacyStore.get(size).getDrugs().getName().equals(name))) {

            return pharmacyStore.get(size).getQuantity();

        } else
            return 0;

    }

    public boolean getCheck(List<PharmacyLocations> pharmacyLocations, int size, String drugNamess) {
        if (pharmacyLocations.get(size).getName().equalsIgnoreCase(drugNamess)) {

            return true;

        } else
            return false;

    }
}
