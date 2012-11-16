package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Authorized("Manage Pharmacy")
public class HomeController {

    private static final Log log = LogFactory.getLog(HomeController.class);
    private ContainerFactory containerFactory;
    private String[][] encdata;
    private ConceptService conceptService;
    private PharmacyService service;
    private String patientID = null;
    private String prescriber = null;
    private String pharmacyUser = null;
    private String question;
    private String question_ans;
    private boolean morethanOne = false;
    private String questionTwo;
    private String question_ansTwo;
    private String questionThree;
    private String question_ansThree;
    private String date;
    private String nextVisitDate;
    private String noOfMonths;
    private ArrayList<String> drugDispensed;
    private ArrayList<ArrayList<String>> drugAll;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size;
    private JSONParser parser;
    private PharmacyEncounter pEncounter;

    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/home")
    public synchronized void pageLoad(ModelMap map) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/home")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        conceptService = Context.getConceptService();

        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        size = pharmacyLocationUsersByUserName.size();


        if (size > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (size == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();


        }

        String jsonText = request.getParameter("values");

        parser = new JSONParser();
        String[][] obsObs = ArrayObs(parser, jsonText);
        String[][] obsEnc = ArrayEnc(parser, jsonText);
        String[][] obsExtra = ArrayExtra(parser, jsonText);


        pEncounter = new PharmacyEncounter();


        for (int i = 1; i < obsEnc.length; i++) {

            if (obsEnc[i][1].equals("2")) {

                pEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(obsEnc[i][2])));

                patientID = obsEnc[i][2];

            } else if (obsEnc[i][1].equals("3")) {
                pEncounter.setEncounter(Context.getEncounterService().getEncounterType(obsEnc[i][2]));
            } else if (obsEnc[i][1].equals("4")) {
                pEncounter.setDateTime(DateHelper(obsEnc[i][2]));


            } else if (obsEnc[i][1].equals("5")) {
                nextVisitDate = obsEnc[i][2];

            } else if (obsEnc[i][1].equals("6")) {


                noOfMonths = obsEnc[i][2];

            } else if (obsEnc[i][1].equals("0.1")) {
                pEncounter.setFormName("Psychiatry Prescription");
            }


            pEncounter.setLocation(service.getPharmacyLocationsByName(locationVal));


        }

        //save encounter


        boolean drugsSave = false;
        boolean obsSave = false;
        boolean dontSave = false;
        boolean save = false;

        List<PharmacyOrders> pharmacyOrders = new ArrayList<PharmacyOrders>();

        List<PharmacyObs> pharmacyObs = new ArrayList<PharmacyObs>();

        List<PharmacyExtra> pharmacyExt = new ArrayList<PharmacyExtra>();

        drugAll = new ArrayList<ArrayList<String>>();


        PharmacyOrders ppharmacyOrders = null;
        PharmacyObs ppharmacyObs = null;
        PharmacyExtra pharmacyExtra = null;

        for (int i = 0; i < obsObs.length; i++) {


            if (obsObs[i][1] != null) {
                if (obsObs[i][1].equals("1114")) {
                    ppharmacyObs = new PharmacyObs();

                    obsSave = true;
                }


                if (obsObs[i][1].equals("90900")) {

                    if (obsObs[i][2] != null | !obsObs[i][2].isEmpty()) {
                        ppharmacyOrders = new PharmacyOrders();
                        drugDispensed = new ArrayList<String>();
                        obsSave = false;
                        drugsSave = true;
                        dontSave = false;
                    }

                }


                if (obsObs[i][1].equals("9090")) {

                    pharmacyExtra = new PharmacyExtra();
                    dontSave = true;
                }

                if (obsObs[i][1].equals("909002")) {

                    save = true;
                }


            }


            if (dontSave) {

                if (!obsObs[i][2].isEmpty()) {
                    if (obsObs[i][1].equals("9090")) {


                        //if true
                        if (obsObs[i][2].equals("1966")) {
                            if (obsObs[i][1].equals("9090")) {

                                question = "If on Zyprexa were they referred for lipid panel and RBS";
                                question_ans = obsObs[i][2];
                                pharmacyExtra.setQuestion(service.getPharmacyGeneralVariablesByName(question));
                                pharmacyExtra.setQuestionAns(Integer.parseInt(question_ans));
                                pharmacyExt.add(pharmacyExtra);
                            }

                        } else {

                            morethanOne = true;

                            if (obsObs[i][1].equals("9090")) {
                                question = "If on Zyprexa were they referred for lipid panel and RBS";
                                question_ans = obsObs[i][2];
                                pharmacyExtra.setQuestion(service.getPharmacyGeneralVariablesByName(question));
                                pharmacyExtra.setQuestionAns(Integer.parseInt(question_ans));
                                pharmacyExt.add(pharmacyExtra);
                            } else if (obsObs[i][1].equals("9091")) {
                                questionTwo = "Did patient fast prior to blood sugar check";
                                question_ansTwo = obsObs[i][2];
                                pharmacyExtra.setQuestion(service.getPharmacyGeneralVariablesByName(questionTwo));
                                pharmacyExtra.setQuestionAns(Integer.parseInt(question_ansTwo));
                                pharmacyExt.add(pharmacyExtra);
                            } else if (obsObs[i][1].equals("9092")) {
                                questionThree = "Did patient fast prior to lipid panel";
                                question_ansThree = obsObs[i][2];
                                pharmacyExtra.setQuestion(service.getPharmacyGeneralVariablesByName(questionThree));

                                pharmacyExtra.setQuestionAns(Integer.parseInt(question_ansThree));
                                pharmacyExt.add(pharmacyExtra);
                            }


                        }

                    }


                }
            }


            if (obsSave) {

                if (!obsObs[i][2].isEmpty()) {
                    if (obsObs[i][1].equals("1114")) {
                        //check datatype of numericpharmacyObs enterered by the user
                        if (obsObs[i][0].equals("2")) {

                            ppharmacyObs.setConcept(obsObs[i][1]);
                            ppharmacyObs.setValueNumeric(Double.parseDouble(obsObs[i][2]));


                            if (!obsExtra[0][1].equals("null")) {


                                ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(obsExtra[0][1]).getUuid());
                            }

                            ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                            if (patientID != null)
                                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                            ppharmacyObs.setPharmacyEncounter(pEncounter);

                            ppharmacyObs.setValueCoded(0);
                            ppharmacyObs.setValueCodedName(null);
                            ppharmacyObs.setValueDatetime(null);
                            ppharmacyObs.setValueGroupId(null);
                            ppharmacyObs.setValueModifier(null);
                            ppharmacyObs.setValueText(null);
                            ppharmacyObs.setValueBoolen(false);
                            ppharmacyObs.setComment(null);

                            ppharmacyObs.setDateStarted(null);
                            ppharmacyObs.setDateStopped(null);


                            pharmacyObs.add(ppharmacyObs);

                        } else if (obsObs[i][0].equals("44")) {


                            date = obsObs[i][2];
                        } else if (obsObs[i][0].equals("3")) {

                            ppharmacyObs.setConcept(obsObs[i][1]);
                            ppharmacyObs.setValueDatetime(DateHelper(date));
                            ppharmacyObs.setValueNumeric(Double.parseDouble(obsObs[i][2]));
                            ppharmacyObs.setValueText("TG");


                            if (!obsExtra[0][1].equals("null")) {
                                ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(obsExtra[0][1]).getUuid());

                            }

//							  
                            ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                            if (patientID != null)
                                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                            ppharmacyObs.setPharmacyEncounter(pEncounter);

                            ppharmacyObs.setValueCoded(0);
                            ppharmacyObs.setValueCodedName(null);
                            ppharmacyObs.setValueGroupId(null);
                            ppharmacyObs.setValueModifier(null);
                            ppharmacyObs.setValueBoolen(false);
                            ppharmacyObs.setComment(null);

                            ppharmacyObs.setDateStarted(null);
                            ppharmacyObs.setDateStopped(null);


                            pharmacyObs.add(ppharmacyObs);
                        } else if (obsObs[i][0].equals("4")) {
                            ppharmacyObs.setConcept(obsObs[i][1]);
                            ppharmacyObs.setValueDatetime(DateHelper(date));
                            ppharmacyObs.setValueNumeric(Double.parseDouble(obsObs[i][2]));
                            ppharmacyObs.setValueText("TCHOL");


                            if (!obsExtra[0][1].equals("null")) {


                                ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(obsExtra[0][1]).getUuid());

                            }

                            ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                            if (patientID != null)
                                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                            ppharmacyObs.setPharmacyEncounter(pEncounter);

                            ppharmacyObs.setValueCoded(0);
                            ppharmacyObs.setValueCodedName(null);
                            ppharmacyObs.setValueGroupId(null);
                            ppharmacyObs.setValueModifier(null);
                            ppharmacyObs.setValueBoolen(false);
                            ppharmacyObs.setComment(null);

                            ppharmacyObs.setDateStarted(null);
                            ppharmacyObs.setDateStopped(null);


                            pharmacyObs.add(ppharmacyObs);
                        } else if (obsObs[i][0].equals("5")) {
                            ppharmacyObs.setConcept(obsObs[i][1]);
                            ppharmacyObs.setValueDatetime(DateHelper(date));
                            ppharmacyObs.setValueNumeric(Double.parseDouble(obsObs[i][2]));
                            ppharmacyObs.setValueText("LDL");


                            if (!obsExtra[0][1].equals("null")) {

                                ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(obsExtra[0][1]).getUuid());

                            }
//							  
                            ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                            if (patientID != null)
                                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                            ppharmacyObs.setPharmacyEncounter(pEncounter);

                            ppharmacyObs.setValueCoded(0);
                            ppharmacyObs.setValueCodedName(null);
                            ppharmacyObs.setValueGroupId(null);
                            ppharmacyObs.setValueModifier(null);
                            ppharmacyObs.setValueBoolen(false);
                            ppharmacyObs.setComment(null);

                            ppharmacyObs.setDateStarted(null);
                            ppharmacyObs.setDateStopped(null);


                            pharmacyObs.add(ppharmacyObs);
                        } else if (obsObs[i][0].equals("6")) {
                            ppharmacyObs.setConcept(obsObs[i][1]);
                            ppharmacyObs.setValueDatetime(DateHelper(date));
                            ppharmacyObs.setValueNumeric(Double.parseDouble(obsObs[i][2]));
                            ppharmacyObs.setValueText("HDL");


                            if (!obsExtra[0][1].equals("null")) {

                                ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(obsExtra[0][1]).getUuid());

                            }
                            ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));
                            if (patientID != null)
                                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                            ppharmacyObs.setPharmacyEncounter(pEncounter);

                            ppharmacyObs.setValueCoded(0);
                            ppharmacyObs.setValueCodedName(null);
                            ppharmacyObs.setValueGroupId(null);
                            ppharmacyObs.setValueModifier(null);
                            ppharmacyObs.setValueBoolen(false);
                            ppharmacyObs.setComment(null);

                            ppharmacyObs.setDateStarted(null);
                            ppharmacyObs.setDateStopped(null);


                            pharmacyObs.add(ppharmacyObs);
                        }


                    }
                }

            }


            if (drugsSave) {

                if (obsObs[i][1].equals("90900")) {


                    if (!obsObs[i][2].isEmpty())
                        ppharmacyOrders.setConcept(obsObs[i][2]);
                    else
                        ppharmacyOrders.setConcept(null);


                } else if (obsObs[i][1].equals("Change")) {


                    if (!obsObs[i][2].isEmpty()) {


                        if (obsObs[i][2].equals("101")) {

                            ppharmacyOrders.setChangeId("DOWN");
                        } else if (obsObs[i][2].equals("102")) {

                            ppharmacyOrders.setChangeId("UP");
                        } else
                            ppharmacyOrders.setChangeId(obsObs[i][2]);

                    } else
                        ppharmacyOrders.setChangeId(null);


                } else if (obsObs[i][1].equals("1900")) {


                    if (!obsObs[i][1].isEmpty())
                        ppharmacyOrders.setDoseId(obsObs[i][1]);
                    else
                        ppharmacyOrders.setDoseId(null);


                    if (!obsObs[i][2].isEmpty()) {


                        drugDispensed.add("" + obsObs[i][2].substring(obsObs[i][2].indexOf("#") + 1));
                    } else
                        ppharmacyOrders.setDrugId(null);


                } else if (obsObs[i][1].equals("7463")) {

                    if (!obsObs[i][2].isEmpty()) {


                        ppharmacyObs = new PharmacyObs();
                        ppharmacyObs.setConcept(obsObs[i][1]);
                        ppharmacyObs.setValueCoded(Integer.parseInt(obsObs[i][2]));


                        if (!prescriber.equals("null")) {


                            ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(prescriber).getUuid());
                        }

                        ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                        if (patientID != null)
                            ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                        ppharmacyObs.setPharmacyEncounter(pEncounter);

                        ppharmacyObs.setValueNumeric(null);
                        ppharmacyObs.setValueCodedName(null);
                        ppharmacyObs.setValueDatetime(null);
                        ppharmacyObs.setValueGroupId(null);
                        ppharmacyObs.setValueModifier(null);
                        ppharmacyObs.setValueText(null);
                        ppharmacyObs.setValueBoolen(false);
                        ppharmacyObs.setComment(null);

                        ppharmacyObs.setDateStarted(null);
                        ppharmacyObs.setDateStopped(null);


                        pharmacyObs.add(ppharmacyObs);


                    }


                } else if (obsObs[i][1].equals("1896")) {

                    if (!obsObs[i][2].isEmpty()) {
                        if (Integer.parseInt(obsObs[i][2]) == 5622) {


                        } else {
                            ppharmacyObs = new PharmacyObs();
                            ppharmacyObs.setConcept(obsObs[i][1]);
                            ppharmacyObs.setValueCoded(Integer.parseInt(obsObs[i][2]));


                            if (!prescriber.equals("null")) {


                                ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(prescriber).getUuid());
                            }

                            ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                            if (patientID != null)
                                ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                            ppharmacyObs.setPharmacyEncounter(pEncounter);

                            ppharmacyObs.setValueNumeric(null);
                            ppharmacyObs.setValueCodedName(null);
                            ppharmacyObs.setValueDatetime(null);
                            ppharmacyObs.setValueGroupId(null);
                            ppharmacyObs.setValueModifier(null);
                            ppharmacyObs.setValueText(null);
                            ppharmacyObs.setValueBoolen(false);
                            ppharmacyObs.setComment(null);

                            ppharmacyObs.setDateStarted(null);
                            ppharmacyObs.setDateStopped(null);


                            pharmacyObs.add(ppharmacyObs);
                        }

                    }


                } else if (obsObs[i][1].equals("1896B")) {

                    if (!obsObs[i][2].isEmpty()) {

                        ppharmacyObs = new PharmacyObs();
                        ppharmacyObs.setConcept("1896");
                        ppharmacyObs.setValueCoded(5622);


                        if (!prescriber.equals("null")) {


                            ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(prescriber).getUuid());
                        }

                        ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                        if (patientID != null)
                            ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                        ppharmacyObs.setPharmacyEncounter(pEncounter);

                        ppharmacyObs.setValueNumeric(null);
                        ppharmacyObs.setValueCodedName(null);
                        ppharmacyObs.setValueDatetime(null);
                        ppharmacyObs.setValueGroupId(null);
                        ppharmacyObs.setValueModifier(null);
                        ppharmacyObs.setValueText(obsObs[i][2]);
                        ppharmacyObs.setValueBoolen(false);
                        ppharmacyObs.setComment(null);

                        ppharmacyObs.setDateStarted(null);
                        ppharmacyObs.setDateStopped(null);


                        pharmacyObs.add(ppharmacyObs);


                    }


                } else if (obsObs[i][1].equals("Indication")) {

                    if (!obsObs[i][2].isEmpty()) {
                        if (obsObs[i][2] == "5622") {

                        } else
                            ppharmacyOrders.setIndicationId(obsObs[i][2]);

                    } else
                        ppharmacyOrders.setIndicationId(null);
                } else if (obsObs[i][1].equals("IndicationB")) {

                    if (!obsObs[i][2].isEmpty()) {

                        ppharmacyOrders.setIndicationId("5622");
                        ppharmacyOrders.setValueText(obsObs[i][2]);


                    } else
                        ppharmacyOrders.setIndicationId(null);
                } else if (obsObs[i][1].equals("1897")) {

                    if (!obsObs[i][2].isEmpty()) {


                        ppharmacyObs = new PharmacyObs();
                        ppharmacyObs.setConcept(obsObs[i][1]);
                        ppharmacyObs.setValueCoded(Integer.parseInt(obsObs[i][2]));


                        if (!prescriber.equals("null")) {


                            ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(prescriber).getUuid());
                        }

                        ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                        if (patientID != null)
                            ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                        ppharmacyObs.setPharmacyEncounter(pEncounter);

                        ppharmacyObs.setValueNumeric(null);
                        ppharmacyObs.setValueCodedName(null);
                        ppharmacyObs.setValueDatetime(null);
                        ppharmacyObs.setValueGroupId(null);
                        ppharmacyObs.setValueModifier(null);
                        ppharmacyObs.setValueText(null);
                        ppharmacyObs.setValueBoolen(false);
                        ppharmacyObs.setComment(null);

                        ppharmacyObs.setDateStarted(null);
                        ppharmacyObs.setDateStopped(null);


                        pharmacyObs.add(ppharmacyObs);


                    }
                } else if (obsObs[i][1].equals("909001")) {

                    if (!obsObs[i][2].isEmpty()) {

                        drugDispensed.add("" + Integer.parseInt(obsObs[i][2]));
                        ppharmacyOrders.setQuantity(Integer.parseInt(obsObs[i][2]));

                    }
                } else if (obsObs[i][1].equals("909005")) {

                    if (!obsObs[i][2].isEmpty()) {

                        ppharmacyOrders.setAmount(Double.parseDouble(obsObs[i][2]));

                    }

                } else if (obsObs[i][1].equals("909006")) {

                    if (!obsObs[i][2].isEmpty()) {


                        ppharmacyOrders.setReceiptNo(Integer.parseInt(obsObs[i][2]));

                    }
                } else if (obsObs[i][1].equals("909007")) {

                    if (!obsObs[i][2].isEmpty()) {


                        ppharmacyOrders.setWaiverNo(Integer.parseInt(obsObs[i][2]));
                        //ppharmacyOrders.setQuantity(Integer.parseInt(obsObs[i][2]));

                    }
                } else if (obsObs[i][1].equals("909008")) {

                    if (!obsObs[i][2].isEmpty()) {


                        ppharmacyOrders.setInvoiceNo(Integer.parseInt(obsObs[i][2]));
                        //ppharmacyOrders.setQuantity(Integer.parseInt(obsObs[i][2]));

                    }
                } else if (obsObs[i][1].equals("909009")) {

                    if (!obsObs[i][2].isEmpty()) {


                        ppharmacyOrders.setWaiverAmount(Double.parseDouble(obsObs[i][2]));


                    }


                    drugsSave = false;
                    //date nextVisitDate);noOfMonths
                    if (nextVisitDate != null)
                        ppharmacyOrders.setNextVisitDate(DateHelper(nextVisitDate));
                    else
                        ppharmacyOrders.setNextVisitDate(null);

                    if (!noOfMonths.isEmpty()) {


                        ppharmacyOrders.setMonthsNo(Integer.parseInt(noOfMonths));
                    } else
                        ppharmacyOrders.setMonthsNo(0);


                    ppharmacyOrders.setPharmacyEncounter(pEncounter);
                    ppharmacyOrders.setInstructions(null);
                    ppharmacyOrders.setStartDate(null);
                    ppharmacyOrders.setAutoEndDate(null);
                    ppharmacyOrders.setDiscontinuedDate(null);
                    ppharmacyOrders.setDiscontinued(false);
                    ppharmacyOrders.setDiscontinuedReason(null);
                    ppharmacyOrders.setPlan(null);


                    ppharmacyOrders.setDispensed(true);
                    ppharmacyOrders.setPharmacyExtra(pharmacyExtra);
                    drugAll.add(drugDispensed);
                    pharmacyOrders.add(ppharmacyOrders);


                }

                //prescriber=
                else if (obsObs[i][1].equals("909002")) {
                    if (!obsObs[i][2].isEmpty()) {
                        ppharmacyOrders.setPillCount(Integer.parseInt(obsObs[i][2]));


                    }


                } else if (obsObs[i][1].equals("909003")) {

                    prescriber = obsObs[i][2];


                } else if (obsObs[i][1].equals("909004")) {
                    pharmacyUser = obsObs[i][2];
                }


            }

        }


        service.savePharmacyEncounter(pEncounter);
        service.savePharmacyObs(pharmacyObs);
        service.savePharmacyOrders(pharmacyOrders);


        for (int i = 0; i < drugAll.size(); i++) {
            PharmacyStore pharmacyStore = new PharmacyStore();


            pharmacyStore = service.getPharmacyInventoryByDrugUuid(Context.getConceptService().getDrug(drugAll.get(i).get(0)), service.getPharmacyLocationsByName(locationVal).getUuid());
            if (pharmacyStore != null && pharmacyStore.getQuantity() > Integer.parseInt(drugAll.get(i).get(1))) {
                pharmacyStore.setQuantity((pharmacyStore.getQuantity() - Integer.parseInt(drugAll.get(i).get(1))));
                service.savePharmacyInventory(pharmacyStore);


            }

            //use the id of the drug and get the set inventory set in drug dispense setting that will give you the inventory uuid
            //get the uuid and substract the quantity

        }


    }


    public synchronized String[][] ArrayEnc(JSONParser parser, String jsonText) {

        String[][] encdata = null;


        try {


            Object obj = parser.parse(jsonText);
            JSONArray array = (JSONArray) obj;


            encdata = new String[7][3];

            for (int i = 0; i < array.size(); i++) {

                String value = ArrayDataOne(array.get(i).toString());
                String name = value.substring(0, value.indexOf("#"));
                if (name.equals("QD")) {
                    //For quantity dispensed
                } else if (name.equals("PD")) {
                    //for pill count
                    // System.out.println("PD="+ value);

                } else if (name.equals("prescriber")) {
                    //For Prescriber
                } else if (name.equals("memberDet")) {
                    //for memberDet

                } else {


                    if (i <= 6) {

                        encdata[i][0] = value.substring(value.indexOf("#"), value.indexOf("#|")).substring(1);
                        encdata[i][1] = value.substring(0, value.indexOf("#"));

                        encdata[i][2] = value.substring(value.indexOf("#|")).substring(2);


                    }


                }


            }

        } catch (Exception e) {
            log.error("Error generated", e);
        }

        return encdata;
    }


    public synchronized String[][] ArrayObs(JSONParser parser, String jsonText) {
        String[][] obsdata = null;


        try {


            Object obj = parser.parse(jsonText);
            JSONArray array = (JSONArray) obj;

            obsdata = new String[array.size()][3];

            for (int i = 0; i < array.size(); i++) {

                String value = ArrayDataOne(array.get(i).toString());


                String name = value.substring(0, value.indexOf("#"));
                if (name.equals("QD")) {
                    //For quantity dispensed


                    obsdata[i][1] = "909001";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                } else if (name.equals("PD")) {

                    obsdata[i][1] = "909002";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                } else if (name.equals("AM")) {
                    //For quantity dispensed

                    obsdata[i][1] = "909005";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                } else if (name.equals("RN")) {

                    obsdata[i][1] = "909006";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                } else if (name.equals("WN")) {

                    obsdata[i][1] = "909007";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);
                } else if (name.equals("IN")) {

                    obsdata[i][1] = "909008";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                } else if (name.equals("WA")) {

                    obsdata[i][1] = "909009";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);
                } else if (name.equals("prescriber")) {

                    obsdata[i][1] = "909003";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                    prescriber = value.substring(value.indexOf("#|")).substring(2);
                } else if (name.equals("memberDet")) {

                    obsdata[i][1] = "909004";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                } else {


                    if (i > 6) {

                        //data type
                        obsdata[i][0] = value.substring(value.indexOf("#"), value.indexOf("#|")).substring(1);

                        //concept id
                        obsdata[i][1] = value.substring(0, value.indexOf("#"));
                        //answer id
                        obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);


                    }


                }


            }

        } catch (Exception e) {
            log.error("Error generated", e);
        }

        return obsdata;
    }

    public synchronized String[][] ArrayExtra(JSONParser parser, String jsonText) {
        String[][] obsExtra = null;


        try {


            Object obj = parser.parse(jsonText);
            JSONArray array = (JSONArray) obj;

            obsExtra = new String[2][2];

            for (int i = 0; i < array.size(); i++) {

                String value = ArrayDataOne(array.get(i).toString());
                String name = value.substring(0, value.indexOf("#"));
                //System.out.println("value="+ value);

                if (name.equals("prescriber")) {

                    obsExtra[0][0] = "909003";
                    obsExtra[0][1] = value.substring(value.indexOf("#|")).substring(2);
                } else if (name.equals("memberDet")) {

                    obsExtra[1][0] = "909004";
                    obsExtra[1][1] = value.substring(value.indexOf("#|")).substring(2);

                }


            }

        } catch (Exception e) {
            log.error("Error generated", e);
        }

        return obsExtra;
    }


    public synchronized String ArrayDataOne(String jsonText) {

        String value = "";
        JSONParser parser = new JSONParser();


        containerFactory = new ContainerFactory() {
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };


        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();

                if (entry.getValue().toString().contains("|"))
                    value += "#" + entry.getValue().toString().substring(entry.getValue().toString().indexOf("|"));
                else
                    value += "#|" + entry.getValue().toString();
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        return value.substring(2);

    }

    public synchronized Date DateHelper(String outgoingexpire) {

        Date date2 = null;
        try {
            if (outgoingexpire != null) {
                if (outgoingexpire.length() < 10) {
                    date2 = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);

                } else
                    date2 = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire.substring(0, 10));


            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }
        return date2;
    }

}
