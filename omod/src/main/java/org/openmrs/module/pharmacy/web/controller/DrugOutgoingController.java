package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class DrugOutgoingController {

    private static final Log log = LogFactory.getLog(DrugOutgoingController.class);

    private JSONArray data;

    private JSONArray datad;

    private boolean found = false;



    private boolean exit = false;


    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;


    //       Global    variables that are use in both Get and Post
    private List<PharmacyStoreOutgoing> listStoreOutgoing;

    private String originalbindrug;
    private String uuidfilter = null;

    private String reload = null;
    private String filterDrug = null;

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private LocationService serviceLocation;
    private ConceptService serviceDrugs;
    private List<Drug> allDrugs;

    private String userLocation = null;

    private UserContext userService;
    public PharmacyService service;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int userLocationsize, sizeOfOutgoingEntries, sizeForAllDrugs;




    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugOutgoing")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        /// get openmts specific components
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();
        serviceLocation = Context.getLocationService();
        serviceDrugs = Context.getConceptService();


        /*
        Get the user logged in and Get a list locations that user has been assigned to
        In UI there is an options to assign users to locations
        Assign the size of the list to a variable size
        */


        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        userLocationsize = pharmacyLocationUsersByUserName.size();


        /*
        check if the users location is greater than 1 or only one
        If its one it means that the user can only manage one location hence get that location name
        In the UI if a user that has more than one locations assigned to access the module, they must choose one location and the location is set to session
        So if the size if greater than one we ge the location from session
        */
        userLocation = getUserLocation(userLocationsize, request);


        // reload variable is to reload the datatable may be after saving an entry
        reload = request.getParameter("reload");

        //filterDrug variable  is for filtering entries in the datatable
        filterDrug = request.getParameter("sSearch");

        // get the whole list of outgoing entries made for display
        listStoreOutgoing = service.getPharmacyStoreOutgoing();
        sizeOfOutgoingEntries = listStoreOutgoing.size();


        // objects for returning back the response
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        response.setContentType("application/jsonObject");
        //check on the filter for datatables


        /*
        * Always when the datatable is being refreshed there is a parameter for filter that its always there so to check if the
        * user as assigned a value to it that they want to filter records to be of the given type , I check  if its size is greater than 2
        * If its greater than two the user as given a filter parameter if not its the default filter variable
        *
        * the objective of this code is to get the uuid of the drug selected by the user for filtering the datatable
        *
        * */
        if (filterDrug.length() > 2) {
            originalbindrug = filterDrug;
            uuidfilter = getUuidFilter();
        }
        try {


            /*
            * Checks if the users wants to reload
            * */
            if (reload != null) {

                for (int i = 0; i < sizeOfOutgoingEntries; i++) {
                    jsonObject.accumulate("aaData", getArrayDialog(listStoreOutgoing, i));
                }

            } else {

                /*
                *    loop through the size of outgoing entries  and add the details of that outgoing entry to the jsonArray object
                *
                *
                * */
                for (int i = 0; i < sizeOfOutgoingEntries; i++) {
                    // get only location that the user has selected
                    if (listStoreOutgoing.get(i).getDestination().getName().equalsIgnoreCase(userLocation)) {
                        jsonArray = getArray(listStoreOutgoing, i, userLocation);
                        if (jsonArray != null)
                            jsonObject.accumulate("aaData", jsonArray);

                    }
                    if (exit)
                        break;
                    data = new JSONArray();
                }
                if (!jsonObject.has("aaData")) {
                    jsonObject.accumulate("aaData", null);
                }

            }
            exit = false;
            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 10);

            response.getWriter().print(jsonObject);

            response.flushBuffer();

        } catch (Exception e) {

            log.error("Error generated", e);
        }

    }


    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugOutgoing")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        //POST specific variable
        String outgoingdrug = request.getParameter("outgoingdrug");

        String outgoingquantityin = request.getParameter("outgoingquantityin");
        String outgoingnumber = request.getParameter("outgoingnumber");
        String outgoingmax = request.getParameter("outgoingmax");
        String outgoingmin = request.getParameter("outgoingmin");

        String outgoingbatch = request.getParameter("outgoingbatch");
        String outgoings11 = request.getParameter("outgoings11");
        String requisition = request.getParameter("requisition");

        String issued = request.getParameter("issued");

        String authorized = request.getParameter("authorized");

        String answers = request.getParameter("answers");


        String outgoingexpire = request.getParameter("outgoingexpire");

        String outgoingreason = request.getParameter("outgoingreason");
        String outgoinguuidvoid = request.getParameter("outgoinguuidvoid");

        String outgoinguuidextra = request.getParameter("outgoinguuidextra");

        String destination = request.getParameter("destination");
        String location = request.getParameter("location");
        String supplier = request.getParameter("supplierout");
        String transactions = request.getParameter("transactions");
        String deliveryno = request.getParameter("delivery");

        String outgoingedit = request.getParameter("outgoingedit");
        String outgoinguuid = request.getParameter("outgoinguuid");
        String outgoingcom = request.getParameter("outgoingcom");
        DrugTransactions drugTransactions;
        PharmacyStore pharmacyStore;

        List<PharmacyStoreOutgoing> pharmacyStoreOutgoing1;
        PharmacyStoreOutgoing pharmacyStoreOutgoing;


        listStoreOutgoing = new ArrayList<PharmacyStoreOutgoing>();

        originalbindrug = outgoingdrug;

        //Arrays of drug Ids that the user has given the approving a large number of drugs for outgoing purposes
        String[] drugId = request.getParameterValues("drugId");


        String[] drugQ = request.getParameterValues("quantity");


        String[] quantityToGive = request.getParameterValues("quantityToGive");




        // openmrs specific contstants


        service = Context.getService(PharmacyService.class);
        serviceLocation = Context.getLocationService();
        userService = Context.getUserContext();
        serviceDrugs = Context.getConceptService();


        /*
        Get the user logged in and Get a list locations that user has been assigned to
        In UI there is an options to assign users to locations
        Assign the size of the list to a variable size
        */


        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        userLocationsize = pharmacyLocationUsersByUserName.size();


        /*
        check if the users location is greater than 1 or only one
        If its one it means that the user can only manage one location hence get that location name
        In the UI if a user that has more than one locations assigned to access the module, they must choose one location and the location is set to session
        So if the size if greater than one we ge the location from session
        */

        userLocation = getUserLocation(userLocationsize, request);


        /*
        * from the user interface the users selects from teh drop down a drug that will filter the drugs show in the database
        * once they select we need to get the uuid of the drug given this code does provide us with that functionality
        * */
        if (outgoinguuidvoid == null) {

            uuidfilter = getUuidFilter();


        }

        /*
        * variable outgoingEdit checks when the user wants to edit an entry or not, the default will always be false and this will be when they
        * are making a new entry
        *
        *
        * */


        if (outgoingedit != null) {


            /*
           *  when outgoingedit is false it means they are doing a new entry to the system of an outgoing request
           *
           * */

            if (outgoingedit.equalsIgnoreCase("false")) {

                pharmacyStoreOutgoing1 = service.getPharmacyStoreOutgoing();

                pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuidfilter));

                service.savePharmacyStoreOutgoing(outgoingEntryNewObject(pharmacyStoreOutgoing, outgoingquantityin, outgoingmax, transactions,
                        outgoingmin, outgoingbatch, outgoings11, outgoingexpire, destination, supplier));
            } else if (outgoingedit.equalsIgnoreCase("true")) {

                /*
                * this is called when a user wants to edit an entry
                * The use has to be authenticated so that to only allow people who made the record to edit
                *
                * its not be done to allow supers user to do  the edit it only has to be done by the user who did create the entry
                *
                * */

                pharmacyStoreOutgoing = new PharmacyStoreOutgoing();

                // get the entry the user wants to edit using the outgoinguuid
                pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(outgoinguuid);
                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStoreOutgoing.getCreator().getUserId())) {


                    /*
                   * getPharmacyStoreOutgoingObject method call is for creation of editing   object of type PharamacyStoreOutgoing
                   * the method takes in a class type of PharmacyStoreOutgoing and returns the same type for database updating
                   *
                   * */


                    service.savePharmacyStoreOutgoing(getPharmacyStoreOutgoingObject(pharmacyStoreOutgoing, outgoingquantityin, transactions, supplier, outgoingbatch, outgoings11, outgoingexpire, destination, location));

                }
            }
            // this is executed when the user wants to void/delete and entry from the database
        } else if (outgoinguuidvoid != null) {

            /*
           * getPharmacyStoreOutgoingObject is a method that takes in parameters from the use and also an object of type PharmacyStoreOutgoingObject
           * this will return an object that has been assigned data ready for updating int the database;
           *
           *
           * */

            pharmacyStoreOutgoing = new PharmacyStoreOutgoing();

            service.savePharmacyStoreOutgoing(getPharmacyStoreOutgoingObjectVoid(pharmacyStoreOutgoing, outgoinguuidvoid, outgoingreason));

        } else if (outgoinguuidextra != null) {


            /* outGoingExtraDetailsForNewEntry method is executed when a user needs to approve a whole list of outgoing request.
            *
           * we get from the user an arrray for drugs selected for approval we iterate through each of those in order to approve each entry at a time
           *
           * */
            outGoingExtraDetailsForNewEntry(false, drugId, quantityToGive);

        }

    }

    /*    getArray is a method that is called to create the JsonArray for the consumption by the datatables
   *     so the jsonarray is entailed by the content form the PharmacyStoreOutgoing table,
   *     its also the one responsible for filtering the results in the users interface
   *
   *    it returns a JSONArray object
   *
   * */
    public synchronized JSONArray getArray(List<PharmacyStoreOutgoing> pharmacyStore, int size, String location) {

        /*
        * filterDrug variable checks to ensure that the user has assigned a value to it for filtering the datatables
        * by default its less that 2 in length but the moment its greater that 2 then it meets the criteria of a filter
        * */

        if (filterDrug.length() > 2) {

            /*
            * With the drug filter the user has selected is the same as what we want to add in the array for display in the database
            *
            * */
            if (uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid())) {
                /*
               *
               * Only display the once that has been selected  and also for the location the user is currently viewing
               * */
                if ((pharmacyStore.get(size).getDestination().getName().equalsIgnoreCase(location))
                        && (!pharmacyStore.get(size).getApproved())) {

                    data = new JSONArray();

                    /*
                    * check the roles and permission and grant them to do specific tasks in the UI e.g Edit and delete entries
                    * */
                    Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                    for (Role rl : xvc) {

                        if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider"))
                                || (rl.getRole().equals("	Authenticated "))) {

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

                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                        editPharmacy = false;
                    } else
                        data.put("");

                    data.put("");
                    data.put(pharmacyStore.get(size).getUuid());
                    data.put(pharmacyStore.get(size).getDrugs().getName());
                    data.put(pharmacyStore.get(size).getQuantityIn());

                    data.put(pharmacyStore.get(size).getLocation().getName());
                    data.put(pharmacyStore.get(size).getDestination().getName());

                    data.put(pharmacyStore.get(size).getTransaction().getName());

                    if (pharmacyStore.get(size).getSupplier() == null) {

                        data.put("pending");
                    } else
                        data.put(pharmacyStore.get(size).getSupplier().getName());

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getS11());
                    data.put(pharmacyStore.get(size).getExpireDate());
                    data.put(pharmacyStore.get(size).getDeliveryNo());

                    data.put("");
                    if (pharmacyStore.get(size).getApproved()) {

                        data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                    } else
                        data.put("Approve");

                    if (deletePharmacy) {
                        data.put("Delete");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    data.put(pharmacyStore.get(size).getStatus());
                    data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
                    data.put(pharmacyStore.get(size).getRequested().getUsername());
                    data.put("<input id=\"" + pharmacyStore.get(size).getUuid() + "\" style=\"width: 40px; height: 20px;\"  type=\"text\" name=\"val\"  >");

                    return data;
                }
            } else {
                return null;
            }

        } else {
            /*
           * this is the default to add everything in PharmacyStoreOutgoing for display
           *  Only display the once that has been selected  and also for the location the user is currently viewing
           * */

            if ((pharmacyStore.get(size).getDestination().getName().equalsIgnoreCase(location))
                    && (!pharmacyStore.get(size).getApproved())) {

                data = new JSONArray();
                /*
               * check the roles and permission and grant them to do specific tasks in the UI e.g Edit and delete entries
               * */
                Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                for (Role rl : xvc) {

                    if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider"))
                            || (rl.getRole().equals("	Authenticated "))) {

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
                data.put(pharmacyStore.get(size).getUuid());
                data.put(pharmacyStore.get(size).getDrugs().getName());
                data.put(pharmacyStore.get(size).getQuantityIn());

                data.put(pharmacyStore.get(size).getLocation().getName());
                data.put(pharmacyStore.get(size).getDestination().getName());

                data.put(pharmacyStore.get(size).getTransaction().getName());

                if (pharmacyStore.get(size).getSupplier() == null) {

                    data.put("pending");
                } else
                    data.put(pharmacyStore.get(size).getSupplier().getName());

                data.put(pharmacyStore.get(size).getBatchNo());
                data.put(pharmacyStore.get(size).getS11());
                data.put(pharmacyStore.get(size).getExpireDate());
                data.put(pharmacyStore.get(size).getDeliveryNo());

                data.put("");
                if (pharmacyStore.get(size).getApproved()) {

                    data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                } else
                    data.put("Approve");

                if (deletePharmacy) {
                    data.put("Delete");
                    deletePharmacy = false;
                } else
                    data.put("");

                data.put(pharmacyStore.get(size).getStatus());
                data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
                data.put(pharmacyStore.get(size).getRequested().getUsername());
                data.put("<input id=\"" + pharmacyStore.get(size).getUuid() + "\" style=\"width: 40px; height: 20px;\"  type=\"text\" name=\"val\"  >");

                return data;
            }
        }
        return null;
    }

    /*
   *   getArrayDialog method is responsible for details of a drug with the current quantity of that drug
   *   this is for the purpose of informing the user in summary
   *   parameters are pharmacyStore object and an integer for the current item to be added to the JSONArray for display
   *   returns JSONArray object
   * */
    public synchronized JSONArray getArrayDialog(List<PharmacyStoreOutgoing> pharmacyStore, int size) {

        datad = new JSONArray();

        datad.put(pharmacyStore.get(size).getDrugs().getName());
        datad.put(pharmacyStore.get(size).getQuantityIn());
        datad.put("");
        datad.put("");

        return datad;
    }


    /*getString is used to compare drugs and if found it returns the uuid
   * */


    public synchronized String getString(List<Drug> dname, int size, String text) {

        if ((dname.get(size).getName().equalsIgnoreCase(text))) {

            return dname.get(size).getUuid();
        }
        return null;


    }

    /*
   * Method to return the name of the location that has been assigned to the user who has logged in
   *
   *
   * */
    public String getUserLocation(int userLocationsize, HttpServletRequest request) {


        if (userLocationsize > 1) {
            userLocation = request.getSession().getAttribute("location").toString();

        } else {
            userLocation = pharmacyLocationUsersByUserName.get(0).getLocation();

        }

        return userLocation;
    }


    /**
     * outgoingEntryNewObject method create an outgoing object from the variables the user passes from the ui
     * <p/>
     * The parameters to this method are from the UI and  it return an object of type    PharmacyStoreOutgoing
     */


    public PharmacyStoreOutgoing outgoingEntryNewObject(PharmacyStoreOutgoing pharmacyStoreOutgoing, String outgoingquantityin, String outgoingmax, String transactions,
                                                        String outgoingmin, String outgoingbatch, String outgoings11, String outgoingexpire, String destination, String supplier
    ) {


        pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuidfilter));

        pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(outgoingquantityin));

        if (outgoingmax != null) {

            pharmacyStoreOutgoing.setMaxLevel(Integer.parseInt(outgoingmax));

        } else if (outgoingmax == null) {
            pharmacyStoreOutgoing.setMaxLevel(0);
        }

        if (outgoingmin != null) {
            pharmacyStoreOutgoing.setMinLevel(Integer.parseInt(outgoingmin));

        } else if (outgoingmin == null) {
            pharmacyStoreOutgoing.setMinLevel(0);
        }

        if (outgoingbatch != null) {
            pharmacyStoreOutgoing.setBatchNo(Integer.parseInt(outgoingbatch));

        } else if (outgoingbatch == null) {
            pharmacyStoreOutgoing.setBatchNo(0);
        }
        if (outgoings11 != null) {
            pharmacyStoreOutgoing.setS11(Integer.parseInt(outgoings11));

        } else if (outgoings11 == null) {
            pharmacyStoreOutgoing.setS11(0);
        }

        Date date = null;
        try {
            if (outgoingexpire != null) {
                date = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);
            }
        } catch (ParseException e) {

            log.error("Error generated", e);
        }

        pharmacyStoreOutgoing.setExpireDate(date);
        serviceLocation = Context.getLocationService();

        pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
        pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(userLocation));

        pharmacyStoreOutgoing.setChangeReason(null);

        if (supplier == null) {
            pharmacyStoreOutgoing.setSupplier(null);

        } else
            pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));

        pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));


        return pharmacyStoreOutgoing;

    }

    /*
   *
   * Method responsible for giving the uuid of the drug selected when a user chooses one from the interface
   * This where the system gets the uuid for that drug
   * */
    public String getUuidFilter() {


        allDrugs = serviceDrugs.getAllDrugs();
        sizeForAllDrugs = allDrugs.size();
        for (int i = 0; i < sizeForAllDrugs; i++) {
            uuidfilter = getString(allDrugs, i, originalbindrug);
            if (uuidfilter != null)
                break;
        }

        return uuidfilter;
    }
    /*
   * getPharmacyStoreOutgoingObject is a method that takes in parameters from the use and also an object of type PharmacyStoreOutgoingObject
   * this will return an object that has been assigned data ready for updating int the database;
   *
   *
   * */

    public PharmacyStoreOutgoing getPharmacyStoreOutgoingObject(PharmacyStoreOutgoing pharmacyStoreOutgoing, String outgoingquantityin, String transactions, String supplier, String outgoingbatch, String outgoings11,
                                                                String outgoingexpire, String destination, String location) {

        // pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuidfilter));


        pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(outgoingquantityin));

        pharmacyStoreOutgoing.setMaxLevel(0);


        pharmacyStoreOutgoing.setMinLevel(0);

        if (outgoingbatch != null) {
            pharmacyStoreOutgoing.setBatchNo(Integer.parseInt(outgoingbatch));

        } else if (outgoingbatch == null) {
            pharmacyStoreOutgoing.setBatchNo(0);
        }

        if (outgoings11 != null) {
            pharmacyStoreOutgoing.setS11(Integer.parseInt(outgoings11));

        } else if (outgoings11 == null) {
            pharmacyStoreOutgoing.setS11(0);
        }

        Date date = null;
        try {
            if (outgoingexpire != null) {
                date = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);
            }
        } catch (ParseException e) {

            log.error("Error generated", e);
        }
        pharmacyStoreOutgoing.setExpireDate(date);
        serviceLocation = Context.getLocationService();

        pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
        pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(location));

        pharmacyStoreOutgoing.setChangeReason(null);

        if (supplier == null) {
            pharmacyStoreOutgoing.setSupplier(null);

        } else
            pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));

        pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));
        return pharmacyStoreOutgoing;

    }

    /*
   *    getPharmacyStoreOutgoingObjectVoid is a method that is responsible for  create a void object of type PharamacyStoreOutgoing
   *    returns PharamacyStoreOutgoing objects
   *
   * */
    public PharmacyStoreOutgoing getPharmacyStoreOutgoingObjectVoid(PharmacyStoreOutgoing pharmacyStoreOutgoingVoid, String outgoinguuidvoid, String outgoingreason) {


        pharmacyStoreOutgoingVoid = service.getPharmacyStoreOutgoingByUuid(outgoinguuidvoid);
        pharmacyStoreOutgoingVoid.setVoided(true);
        pharmacyStoreOutgoingVoid.setVoidReason(outgoingreason);
        return pharmacyStoreOutgoingVoid;
    }

    /*     outGoingExtraDetailsForNewEntry is a method to us when the users has approved the system.
   *       this method will take in parameters from the UI and the outcome is changes added to the database
   * */
    public void outGoingExtraDetailsForNewEntry(boolean canSave, String[] drugId, String[] quantityToGive) {
        PharmacyStoreApproved pharmacyStoreApproved;
        PharmacyStoreIncoming PharmacyStoreIncoming;
        List<DrugTransactions> listDrugTransactions = new ArrayList<DrugTransactions>();
        List<PharmacyStoreApproved> listStoreApproved = new ArrayList<PharmacyStoreApproved>();
        String outgoinguuidextra;
        DrugTransactions drugTransactions;
        PharmacyStore pharmacyStore;
        PharmacyStoreOutgoing pharmacyStoreOutgoing;

        /* outGoingExtraDetailsForNewEntry method is executed when a user needs to approve a whole list of outgoing request.
        *
       * we get from the user an arrray for drugs selected for approval we iterate through each of those in order to approve each entry at a time
       *
       * */
        for (int y = 0; y < drugId.length; y++) {


            pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
            pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(drugId[y]);

            pharmacyStore = new PharmacyStore();

            pharmacyStore = service.getDrugDispenseSettingsByLocation(service.getPharmacyLocationsByName(userLocation)).getInventoryId();

           // Check if there is enough drugs in the store for approving quantity to give
           // This is to ensure that we have enough in store
            if (Integer.parseInt(quantityToGive[y]) <= pharmacyStore.getQuantity()) {
                canSave = true;
                int num;

                if (Integer.parseInt(quantityToGive[y]) == pharmacyStoreOutgoing.getQuantityIn()) {

                    pharmacyStoreOutgoing.setApproved(true);
                    pharmacyStoreOutgoing.setQuantityIn(0);
                    num = (pharmacyStore.getQuantity() - Integer.parseInt(quantityToGive[y]));
                    System.out.println(pharmacyStore.getQuantity() + "===pharmacyStore.getS11()pharmacyStore+==" + num + "===.getS11()pharmacyStore.getS11()" + Integer.parseInt(quantityToGive[y]));

                    pharmacyStore.setQuantity(num);
                    service.savePharmacyInventory(pharmacyStore);

                } else {

                    if (Integer.parseInt(quantityToGive[y]) < pharmacyStoreOutgoing.getQuantityIn()) {
                        pharmacyStoreOutgoing.setQuantityIn((pharmacyStoreOutgoing.getQuantityIn() - Integer.parseInt(quantityToGive[y])));
                    }

                    System.out.println(pharmacyStore.getQuantity() + "===pharmacyStore.getS11()pharmacyStore.getS11()pharmacyStore.getS11()" + Integer.parseInt(quantityToGive[y]));
                    num = (pharmacyStore.getQuantity() - Integer.parseInt(quantityToGive[y]));


                    pharmacyStore.setQuantity(num);
                    service.savePharmacyInventory(pharmacyStore);

                }

                pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));

                pharmacyStoreOutgoing.setIssued(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));

                listStoreOutgoing.add(pharmacyStoreOutgoing);
                drugTransactions = new DrugTransactions();

                drugTransactions.setDrugs(pharmacyStoreOutgoing.getDrugs());
                drugTransactions.setQuantityIn(0);
                drugTransactions.setQuantityOut(Integer.parseInt(quantityToGive[y]));
                drugTransactions.setexpireDate(pharmacyStore.getExpireDate());
                drugTransactions.setComment("Give out");

                drugTransactions.setLocation(service.getPharmacyLocationsByName(userLocation).getUuid());

                listDrugTransactions.add(drugTransactions);
                pharmacyStoreApproved = new PharmacyStoreApproved();

                pharmacyStoreApproved.setDrugs(pharmacyStoreOutgoing.getDrugs());
                pharmacyStoreApproved.setQuantityIn(Integer.parseInt(quantityToGive[y]));
                pharmacyStoreApproved.setCategory(pharmacyStoreOutgoing.getCategory());
                pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getDestination());

                pharmacyStoreApproved.setLocation(pharmacyStoreOutgoing.getDestination());
                pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getLocation());
                pharmacyStoreApproved.setTransaction(pharmacyStoreOutgoing.getTransaction());
                pharmacyStoreApproved.setIncoming(pharmacyStoreOutgoing.getIncoming());

                pharmacyStoreApproved.setOutgoing(pharmacyStoreOutgoing);
                pharmacyStoreApproved.setApproved(false);
                pharmacyStoreApproved.setS11(pharmacyStoreOutgoing.getS11());


                pharmacyStoreApproved.setVoided(pharmacyStoreOutgoing.getVoided());
                pharmacyStoreApproved.setMaxLevel(pharmacyStore.getMaxLevel());
                pharmacyStoreApproved.setMinLevel(pharmacyStore.getMinLevel());
                pharmacyStoreApproved.setBatchNo(pharmacyStore.getBatchNo());
                pharmacyStoreApproved.setExpireDate(pharmacyStore.getExpireDate());
                pharmacyStoreApproved.setDeliveryNo(pharmacyStore.getDeliveryNo());
                pharmacyStoreApproved.setRequested(pharmacyStoreOutgoing.getRequested());
                pharmacyStoreApproved.setAuthorized(pharmacyStoreOutgoing.getAuthorized());
                pharmacyStoreApproved.setIssued(pharmacyStoreOutgoing.getissued());

                pharmacyStoreApproved.setStatus("Approved");

                PharmacyStoreIncoming = pharmacyStoreOutgoing.getIncoming();
                PharmacyStoreIncoming.setApproved(true);

                PharmacyStoreIncoming.setStatus("Apprroved");

                service.savePharmacyStoreIncoming(PharmacyStoreIncoming);

                listStoreApproved.add(pharmacyStoreApproved);

            }
        }
        outgoinguuidextra = null;

        if (canSave) {
            service.savePharmacyStoreOutgoing(listStoreOutgoing);
            service.savePharmacyStoreApproved(listStoreApproved);
            service.saveDrugTransactions(listDrugTransactions);
        }


    }

}
