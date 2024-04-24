package com.chinmay.licensing.service;

import com.chinmay.licensing.configuration.ServiceConfig;
import com.chinmay.licensing.model.License;
import com.chinmay.licensing.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig serviceConfig;

    public License getLicense(String organisationId, String licenseId, Locale locale){
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organisationId,licenseId);
        if(null == license){
            throw new IllegalArgumentException(
                    String.format(messageSource.getMessage("license.search.error.message",
                            null,locale),licenseId, organisationId));
        }
        return license.withComment(serviceConfig.getProperty());
    }

    public License createLicense(License license){
        licenseRepository.save(license);
        return license.withComment(serviceConfig.getProperty());
    }

    public License updateLicense(License license){
         licenseRepository.save(license);
         return license.withComment(serviceConfig.getProperty());
    }

    public String deleteLicense(String organisationId,String licenseId, Locale locale){
        License license = new License();
        license.setLicenseId(licenseId);
        license.setOrganizationId(organisationId);
        licenseRepository.delete(license);
        return String.format(messageSource.getMessage("license.delete.message",null,locale),licenseId,organisationId);
    }


}
