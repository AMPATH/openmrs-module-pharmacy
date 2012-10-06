/**
 *
 */
package org.openmrs.module.pharmacy.ext;

import org.openmrs.annotation.Authorized;
import org.openmrs.module.web.extension.AdministrationSectionExt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ampath Developers
 */
public class PharmacyAdminExt extends AdministrationSectionExt {

    /*
      * (non-Javadoc)
      *
      * @see org.openmrs.module.web.extension.AdministrationSectionExt#getLinks()
      */
    @Override
    @Authorized("Manage Pharmacy")
    public Map<String, String> getLinks() {
        Map<String, String> map = new HashMap<String, String>();

        map.put("module/pharmacy/home.form", "Pharmacy");

        return map;

    }

    /*
      * (non-Javadoc)
      *
      * @see org.openmrs.module.web.extension.AdministrationSectionExt#getTitle()
      */
    @Override
    @Authorized("Manage Pharmacy")
    public String getTitle() {
        return "Pharmacy Module";
    }

}
