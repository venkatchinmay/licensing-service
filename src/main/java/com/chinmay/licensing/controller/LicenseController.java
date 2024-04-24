package com.chinmay.licensing.controller;

import com.chinmay.licensing.model.License;
import com.chinmay.licensing.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value ="/v1/organisation/{organisationId}/license")
public class LicenseController {

 @Autowired
 LicenseService licenseService;
@PostMapping
 public ResponseEntity<Object> createLicense(@RequestBody License license,
                                             @PathVariable(value = "organisationId", required = true) String organisationId){
  license.setOrganizationId(organisationId);
  License response = licenseService.createLicense(license);
  return ResponseEntity.status(HttpStatus.CREATED).body(response);
 }

 @GetMapping("/{licenseId}")
 public ResponseEntity<Object> getLicense(@PathVariable(value = "organisationId", required = true) String organisationId,
                        @PathVariable(value= "licenseId", required= true) String licenseId,
                                          @RequestHeader(value= "Accept-Language", required= false) Locale locale){
  License license = licenseService.getLicense(organisationId,licenseId,locale);
  // optional code - spring HATEOS
  license.add(linkTo(
          methodOn(LicenseController.class)
          .getLicense(organisationId,license.getLicenseId(),locale))
          .withSelfRel(),
          linkTo(methodOn(LicenseController.class)
                  .createLicense(license,license.getLicenseId()))
                  .withRel("createLicense"),
          linkTo(methodOn(LicenseController.class)
                  .updateLicense(license,license.getLicenseId()))
                  .withRel("updateLicense"),
          linkTo(methodOn(LicenseController.class)
                  .deleteLicense(organisationId,license.getLicenseId(),locale))
                  .withRel("deleteLicense"));
  return ResponseEntity.ok(license);
 }

 @PutMapping
  public ResponseEntity<Object> updateLicense(@RequestBody License license,
                            @PathVariable(value = "organisationId", required = true) String organisationId){
  license.setOrganizationId(organisationId);
  License response = licenseService.updateLicense(license);
  return ResponseEntity.ok(response);

 }

 @DeleteMapping("/{licenseId}")
  public ResponseEntity<Object> deleteLicense(@PathVariable(value = "organisationId", required = true) String organisationId,
                            @PathVariable(value= "licenseId", required= true) String licenseId,
 @RequestHeader(value= "Accept-Language", required= false) Locale locale){
   String response = licenseService.deleteLicense(organisationId,licenseId,locale);
   return ResponseEntity.ok(response);
 }


}
