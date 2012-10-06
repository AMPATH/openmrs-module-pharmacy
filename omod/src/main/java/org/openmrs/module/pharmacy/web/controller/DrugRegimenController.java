package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.Regimen;
import org.openmrs.module.pharmacy.model.RegimenCombination;
import org.openmrs.module.pharmacy.model.RegimenNames;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugRegimenController {

    private static final Log log = LogFactory.getLog(DrugRegimenController.class);

    private JSONArray data;

    public PharmacyService service;

    String drop = null;

    private RegimenCombination regimenCombination;

    private boolean found = false;

    private boolean foundOptionYes = false;

    private boolean foundOptionNo = false;

    private RegimenNames regimenNames;

    private RegimenCombination combination;
    private RegimenCombination option1Combi;
    private RegimenCombination option2Combi;

    private RegimenCombination combination1;

    private RegimenCombination combination2;

    private boolean dataone = true;

    private RegimenCombination newcombination1;

    private boolean datatwo = true;

    private RegimenCombination newcombination2;

    private RegimenCombination newcombination3;

    private boolean datathree = true;

    private boolean datafour = true;

    private RegimenCombination combination3;

    private RegimenCombination newcombination4;

    private ConceptService serviceDrugs;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;
    private boolean datafive;
    private RegimenCombination newOption2;
    private RegimenCombination newOption1;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugRegimen")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);




        serviceDrugs = Context.getConceptService();
        List<Regimen> list = service.getRegimen();
        regimenCombination = new RegimenCombination();
        regimenNames = new RegimenNames();
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
            // drugs
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugRegimen")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        serviceDrugs = Context.getConceptService();
        String regimennamecomplete = request.getParameter("regimennamecomplete");
        String complete = request.getParameter("complete");
        userService = Context.getUserContext();
        //regimen options
        dataone = true;
        datatwo = true;
        datathree = true;

        datafour = true;
        datafive = true;
        found = false;
        String regimendrug1 = request.getParameter("regimendrug1");
        String regimendrug2 = request.getParameter("regimendrug2");
        String regimendrug3 = request.getParameter("regimendrug3");
        String regimendrug4 = request.getParameter("regimendrug4");



        String option1 = request.getParameter("option1");
        String option2 = request.getParameter("option2");
        String category = request.getParameter("category");


        String regimenreason = request.getParameter("regimenreason");
        String regimenuuidvoid = request.getParameter("regimenuuidvoid");

        String regimenedit = request.getParameter("regimenedit");
        String regimenuuid = request.getParameter("regimenuuid");
        String optionss = request.getParameter("optionss");

        //check for same entry before saving
        List<Regimen> list = service.getRegimen();
        List<RegimenCombination> list2 = service.getRegimenCombination();
        int size = list.size();
        int size2 = list2.size();
        if (regimenuuidvoid == null) {
            for (int i = 0; i < size; i++) {

                if (optionss.equalsIgnoreCase("Yes")) {
                    foundOptionYes = getCheck(list, i, regimennamecomplete);

                } else if (optionss.equalsIgnoreCase("No")) {
                    found = getCheck(list, i, regimennamecomplete);

                }

                if (found)
                    break;
                if (foundOptionYes)
                    break;
            }

        }
        if (regimenedit != null) {
            if (regimenedit.equalsIgnoreCase("false")) {

                if (!optionss.isEmpty()) {
                    if ((!foundOptionYes)&& optionss.equalsIgnoreCase("Yes")) {

                        Regimen regimen = new Regimen();

                        regimen.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));

                        if (!complete.equals("-1"))
                            regimen.setDrugName(serviceDrugs.getDrugByNameOrId(complete));
                        else
                            regimen.setDrugName(null);

                        regimen.setCombination(true);
                        regimen.setCategory(category);
                        service.saveRegimen(regimen);
                        //	    		    	    		if((!regimennameoption.equals("-1")&&!regimendrug1.equals("-1")&&!regimendrug2.equals("-1")&&!regimendrug3.equals("-1"))){

                        combination = new RegimenCombination();
                        combination1 = new RegimenCombination();
                        combination2 = new RegimenCombination();
                        combination3 = new RegimenCombination();

                        combination.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));

                        String check = service.getRegimenNamesByName(regimennamecomplete).getUuid();
                        List<Regimen> regimenn = service.getRegimen();
                        int sizee = regimenn.size();

                        for (int r = 0; r < sizee; r++) {
                            String get = getUuid(regimenn, r, check);

                            if (get != null) {
                                combination.setPharmacyRegimen(regimen);
                                combination1.setPharmacyRegimen(regimen);
                                combination2.setPharmacyRegimen(regimen);
                                combination3.setPharmacyRegimen(regimen);
                            }
                        }
                        if (!regimendrug1.equals("-1"))
                            combination.setDrugName(serviceDrugs.getDrugByNameOrId(regimendrug1));
                        else
                            combination.setDrugName(null);

                        combination1.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));
                        //combination1.setPharmacyRegimen(service.getRegimenByUuid(service.getRegimenNamesByName(regimennamecomplete).getUuid()));
                        if (!regimendrug2.equals("-1"))
                            combination1.setDrugName(serviceDrugs.getDrugByNameOrId(regimendrug2));
                        else
                            combination1.setDrugName(null);

                        combination2.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));

                        //combination2.setPharmacyRegimen(service.getRegimenByUuid(service.getRegimenNamesByName(regimennamecomplete).getUuid()));
                        if (!regimendrug3.equals("-1"))
                            combination2.setDrugName(serviceDrugs.getDrugByNameOrId(regimendrug3));
                        else
                            combination2.setDrugName(null);





                        if(option1!=null && option2!=null){

                            option1Combi = new RegimenCombination();
                            option2Combi = new RegimenCombination();


                            option1Combi.setPharmacyRegimen(regimen);
                            option2Combi.setPharmacyRegimen(regimen);

                            option1Combi.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));

                            option1Combi.setDrugName(serviceDrugs.getDrugByNameOrId(option1));
                            option1Combi.setOptions(true);
                            option2Combi.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));

                            option2Combi.setDrugName(serviceDrugs.getDrugByNameOrId(option2));
                            option2Combi.setOptions(true);


                        }


                        service.saveRegimenCombination(combination2);//
                        service.saveRegimenCombination(combination);//
                        service.saveRegimenCombination(combination1);//
                        service.saveRegimenCombination(option1Combi);//
                        service.saveRegimenCombination(option2Combi);//



                        //	    		    			}

                    }

                    else if ((!found)&&optionss.equalsIgnoreCase("No")) {

                        Regimen regimen = new Regimen();

                        regimen.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));
                        if (!complete.equals("-1"))
                            regimen.setDrugName(serviceDrugs.getDrugByNameOrId(complete));
                        else
                            regimen.setDrugName(null);

                        regimen.setCategory(category);
                        regimen.setCombination(false);

                        service.saveRegimen(regimen);

                    }

                }

            } else if (regimenedit.equalsIgnoreCase("true")) {

                if (!optionss.isEmpty()) {

                    if ((foundOptionYes)&&optionss.equalsIgnoreCase("Yes")) {

                        List<RegimenCombination> combinat = service.getRegimenCombination();
                        int sizee = combinat.size();
                        if (sizee == 0) {
                            newOption1 = new RegimenCombination();
                            newOption2 = new RegimenCombination();


                            newcombination3 = new RegimenCombination();

                            newcombination2 = new RegimenCombination();
                            newcombination1 = new RegimenCombination();

                        }
                        for (int r = 0; r < sizee; r++) {

                            String get = getRegimenUuid(combinat, r, regimenuuid);




                            if (get != null) {


                                if(combinat.get(r).getOptions()){
                                    if (datafour) {
                                        option1Combi = new RegimenCombination();

                                        option1Combi = service.getRegimenCombinationByUuid(get);
                                        datafour = false;
                                        continue;
                                    }
                                    if (datafive) {
                                        option2Combi = new RegimenCombination();

                                        option2Combi = service.getRegimenCombinationByUuid(get);
                                        datafive = false;
                                        continue;
                                    }


                                } else{

                                    if (dataone) {
                                        newcombination1 = new RegimenCombination();

                                        newcombination1 = service.getRegimenCombinationByUuid(get);
                                        dataone = false;
                                        continue;
                                    }
                                    if (datatwo) {
                                        newcombination2 = new RegimenCombination();

                                        newcombination2 = service.getRegimenCombinationByUuid(get);
                                        datatwo = false;
                                        continue;
                                    }
                                    if (datathree) {
                                        newcombination3 = new RegimenCombination();

                                        newcombination3 = service.getRegimenCombinationByUuid(get);
                                        datathree = false;
                                        continue;
                                    }
                                }




                            } else {

                                if (dataone) {
                                    newcombination1 = new RegimenCombination();

                                    dataone = false;
                                    continue;
                                }
                                if (datatwo) {
                                    newcombination2 = new RegimenCombination();

                                    datatwo = false;
                                    continue;
                                }
                                if (datathree) {
                                    newcombination3 = new RegimenCombination();

                                    datathree = false;
                                    continue;
                                }
                                if (datafour) {
                                    option1Combi = new RegimenCombination();

                                    option1Combi = service.getRegimenCombinationByUuid(get);
                                    datafour = false;
                                    continue;
                                }
                                if (datafive) {
                                    option2Combi = new RegimenCombination();

                                    option2Combi = service.getRegimenCombinationByUuid(get);
                                    datafive = false;
                                    continue;
                                }

                            }

                        }

                        Regimen regimen = new Regimen();

                        regimen = service.getRegimenByUuid(regimenuuid);//

                        // if (userService.getAuthenticatedUser().getUserId() == regimen.getCreator().getUserId()) {





                        //regimen.setRegimenNames(service.getRegimenNamesByName(regimennamecomplete));
                        if (!complete.equals("-1"))
                            regimen.setDrugName(serviceDrugs.getDrugByNameOrId(complete));
                        else
                            regimen.setDrugName(null);

                        regimen.setCombination(true);

                        newcombination1.setRegimenNames(regimen.getRegimenNames());

                        String check = regimen.getRegimenNames().getUuid();
                        List<Regimen> regimenn = service.getRegimen();
                        int sze = regimenn.size();

                        for (int r = 0; r < sze; r++) {
                            String get = getUuid(regimenn, r, check);

                            if (get != null) {
                                newcombination1.setPharmacyRegimen(regimen);
                                newcombination2.setPharmacyRegimen(regimen);
                                newcombination3.setPharmacyRegimen(regimen);

                            }
                        }
                        if (!regimendrug1.equals("-1"))
                            newcombination1.setDrugName(serviceDrugs.getDrugByNameOrId(regimendrug1));
                        else
                            newcombination1.setDrugName(null);

                        newcombination2.setRegimenNames(regimen.getRegimenNames());
                        //combination1.setPharmacyRegimen(service.getRegimenByUuid(service.getRegimenNamesByName(regimennamecomplete).getUuid()));
                        if (!regimendrug2.equals("-1"))
                            newcombination2.setDrugName(serviceDrugs.getDrugByNameOrId(regimendrug2));
                        else
                            newcombination2.setDrugName(null);

                        newcombination3.setRegimenNames(regimen.getRegimenNames());
                        //combination2.setPharmacyRegimen(service.getRegimenByUuid(service.getRegimenNamesByName(regimennamecomplete).getUuid()));
                        if (!regimendrug3.equals("-1"))
                            newcombination3.setDrugName(serviceDrugs.getDrugByNameOrId(regimendrug3));
                        else
                            newcombination3.setDrugName(null);






                        if(option1!=null && option2!=null){

                            option1Combi = new RegimenCombination();
                            option2Combi = new RegimenCombination();


                            option1Combi.setPharmacyRegimen(regimen);
                            option2Combi.setPharmacyRegimen(regimen);

                            option1Combi.setRegimenNames(regimen.getRegimenNames());

                            option1Combi.setDrugName(serviceDrugs.getDrugByNameOrId(option1));
                            option1Combi.setOptions(true);
                            option2Combi.setRegimenNames(regimen.getRegimenNames());

                            option2Combi.setDrugName(serviceDrugs.getDrugByNameOrId(option2));
                            option2Combi.setOptions(true);


                        }


                        service.saveRegimenCombination(newcombination1);//
                        service.saveRegimenCombination(newcombination2);//
                        service.saveRegimenCombination(newcombination3);//
                        service.saveRegimenCombination(option1Combi);//
                        service.saveRegimenCombination(option2Combi);//

                        service.saveRegimen(regimen);
                        //}
                    }

                    else if ((found)&&optionss.equalsIgnoreCase("No")) {

                        Regimen regimen = new Regimen();

                        regimen = service.getRegimenByUuid(regimenuuid);//

                        regimen.setRegimenNames(regimen.getRegimenNames());
                        if (!complete.equals("-1"))
                            regimen.setDrugName(serviceDrugs.getDrugByNameOrId(complete));
                        else
                            regimen.setDrugName(null);









                        if (regimen.getCombination()) {
                            regimen.setCombination(false);
                            List<RegimenCombination> listcheck = service.getRegimenCombination();
                            int number = listcheck.size();

                            for (int i = 0; i < number; i++) {

                                String uuid = getDrug(listcheck, i, regimen.getRegimenNames().getRegimenName());


                                if (uuid != null) {


                                    RegimenCombination combination = new RegimenCombination();

                                    combination = service.getRegimenCombinationByUuid(uuid);

                                    combination.setVoided(true);
                                    combination.setVoidReason("");

                                    service.saveRegimenCombination(combination);//

                                }
                            }

                        }

                        service.saveRegimen(regimen);

                    }

                }

            }

        }

        else if (regimenuuidvoid != null) {

            Regimen regimen = new Regimen();

            regimen = service.getRegimenByUuid(regimenuuidvoid);

            regimen.setVoided(true);
            regimen.setVoidReason(regimenreason);

            service.saveRegimen(regimen);
            if (service.getRegimenByUuid(regimenuuidvoid).getCombination()) {

                List<RegimenCombination> listcheck = service.getRegimenCombination();
                int number = listcheck.size();

                for (int i = 0; i < number; i++) {

                    String uuid = getDrug(listcheck, i, regimenuuidvoid);

                    if (uuid != null) {

                        RegimenCombination combination = new RegimenCombination();

                        combination = service.getRegimenCombinationByUuid(uuid);

                        combination.setVoided(true);
                        combination.setVoidReason(regimenreason);

                        service.saveRegimenCombination(combination);//

                    }
                }

            }

        }

    }

    public synchronized JSONArray getArray(List<Regimen> regimen, int size) {

        data = new JSONArray();


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

            data.put("edit");
            editPharmacy = false;
        } else
            data.put("");

        data.put("");
        data.put(regimen.get(size).getUuid());
        data.put(regimen.get(size).getRegimenNames().getRegimenName());
        if (regimen.get(size).getDrugName() != null)
            data.put(regimen.get(size).getDrugName().getName());
        else
            data.put("None");



        if (regimen.get(size).getCombination()) {
            String uuid = regimen.get(size).getRegimenNames().getRegimenName();

            List<RegimenCombination> combi = service.getRegimenCombination();
            int combinu = combi.size();

            for (int i = 0; i < combinu; i++) {

                String found = getDrug(combi, i, uuid);
                if (found != null) {


                    if(combi.get(i).getOptions()){

                        if (combi.get(i).getDrugName() != null) {
                            data.put(combi.get(i).getDrugName().getName());

                        } else
                            data.put("None");

                    }
                    else{

                        if (combi.get(i).getDrugName() != null) {
                            data.put(combi.get(i).getDrugName().getName());

                        } else
                            data.put("None");
                    }



                }

            }

        } else {
            data.put("None");
            data.put("None");
            data.put("None");
            data.put("None");
            data.put("None");
        }

        if (deletePharmacy) {
            data.put("void");
            deletePharmacy = false;
        } else
            data.put("");

        return data;
    }

    public synchronized String getDropDown(List<Regimen> regimen, int size) {

        return regimen.get(size).getRegimenNames().getRegimenName();
    }

    public synchronized boolean getCheck(List<Regimen> regimen, int size, String name) {
        if (regimen.get(size).getRegimenNames().getRegimenName().equalsIgnoreCase(name)) {
            return true;

        } else
            return false;

    }

    public synchronized boolean getCheck2(List<RegimenCombination> regimen, int size, String name) {
        if (regimen.get(size).getRegimenNames().getRegimenName().equalsIgnoreCase(name)) {

            return true;

        } else
            return false;

    }

    public synchronized String getDrug(List<RegimenCombination> regimenCombination, int size, String uuid) {
        service = Context.getService(PharmacyService.class);
        if (regimenCombination.get(size).getRegimenNames().getRegimenName().equalsIgnoreCase(uuid)) {

            return regimenCombination.get(size).getUuid();

        } else
            return null;

    }

    public synchronized String getUuid(List<Regimen> regimen, int size, String name) {
        service = Context.getService(PharmacyService.class);
        if (regimen.get(size).getRegimenNames().getUuid().equalsIgnoreCase(name)) {

            return regimen.get(size).getUuid();

        } else
            return null;

    }

    public synchronized String getRegimenUuid(List<RegimenCombination> regimen, int size, String name) {
        service = Context.getService(PharmacyService.class);
        if (regimen.get(size).getRegimenNames().getUuid()
                .equalsIgnoreCase(service.getRegimenByUuid(name).getRegimenNames().getUuid())) {

            return regimen.get(size).getUuid();

        } else
            return null;

    }

}
