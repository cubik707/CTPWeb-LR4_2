package org.example.service;

import org.example.entity.Company;
import java.util.List;

public interface CompanyService {
    boolean addCompany(Company company);
    boolean updateCompany(Company company);
    boolean deleteCompany(int id);
    List<Company> showCompanies();
    Company findCompanyById(int id);
    Company findCompanyByName(String name);
}

