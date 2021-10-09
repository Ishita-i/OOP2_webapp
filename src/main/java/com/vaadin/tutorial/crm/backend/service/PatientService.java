package com.vaadin.tutorial.crm.backend.service;

import com.vaadin.tutorial.crm.backend.entity.Company;
import com.vaadin.tutorial.crm.backend.entity.Patient;
import com.vaadin.tutorial.crm.backend.repository.CompanyRepository;
import com.vaadin.tutorial.crm.backend.repository.PatientRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PatientService {
    private static final Logger LOGGER = Logger.getLogger(PatientService.class.getName());
    private PatientRepository patientRepository;
    private CompanyRepository companyRepository;

    public PatientService(PatientRepository patientRepository,
                          CompanyRepository companyRepository) {
        this.patientRepository = patientRepository;
        this.companyRepository = companyRepository;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return patientRepository.findAll();
        } else {
            return patientRepository.search(filterText);
        }
    }

    public long count() {
        return patientRepository.count();
    }


    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }

    public void save(Patient patient) {
        if (patient == null) {
            LOGGER.log(Level.SEVERE,
                    "Patient is null. Are you sure you have connected your form to the application?");
            return;
        }
        patientRepository.save(patient);
    }

    @PostConstruct
    public void populateTestData() {
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(
                    Stream.of("SZF", "ASSURIA", "PARSASCO", "SELF RELIANCE", "FATUM", "ONVERZEKERD")
                            .map(Company::new)
                            .collect(Collectors.toList()));
        }

        if (patientRepository.count() == 0) {
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            patientRepository.saveAll(
                    Stream.of("Gabrielle Patel 2-8-2005 8762909 V_8970 9-8-2021 Wanicastraat#2",
                            "Brian Robinson 7-12-1999 7112246 M_3467 7-3-2021 Wanicaweg#2",
                            "Eduardo Haugen 27-2-1998 8237563 M_7612 7-4-2021 tayerbladstraat#9",
                            "Koen Johansen 16-5-2007 8508892 M_8989 28-6-2021 Kwattaweg#89",
                            "Alejandro Macdonald 4-11-1977 8554467 M_5638 12-12-2020 Arsaweg#45",
                            "Angel Karlsson 16-6-1992 8150386 V_7538 3-6-2021 Simanstaart#9",
                            "Yahir Gustavsson 8-9-2003 7660109 M_9999 15-9-2021 Wilhelminastraat#90")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Patient patient = new Patient();
                                patient.setFirstName(split[0]);
                                patient.setLastName(split[1]);
                                patient.setDateOfBirth(split[2]);
                                patient.setPhonenumber(split[3]);
                                patient.setAssurancenr(split[4]);
                                patient.setLastVisited(split[5]);
                                patient.setAddress(split[6]);
                                patient.setCompany(companies.get(r.nextInt(companies.size())));

                                return patient;
                            }).collect(Collectors.toList()));
        }
    }

}
