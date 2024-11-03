package org.example.menu;


import org.example.entity.Car;
import org.example.entity.Company;
import org.example.entity.Person;
import org.example.entity.User;
import org.example.service.CompanyService;
import org.example.service.PersonService;
import org.example.service.serviceImpl.CompanyServiceImpl;
import org.example.service.serviceImpl.PersonServiceImpl;

import java.util.List;
import java.util.Scanner;

public class MenuFunctions {

    Scanner in = new Scanner(System.in);
    PersonService personService = new PersonServiceImpl();
    CompanyService companyService = new CompanyServiceImpl();

    public MenuFunctions() { }

    //---PERSON AND USER---//

    public void addPerson() {
        System.out.println("---Добавление пользователя---");
        Person person = getPersonInfo();
        if (person != null) {
            if (personService.addPerson(person)) {
                System.out.println("---Добавление выполнено!---");
            }
        }
    }

    private Person getPersonInfo() {
        Person person = null;
        System.out.print("Введите имя пользователя: ");
        String name = in.nextLine();
        System.out.print("Введите фамилию пользователя: ");
        String surname = in.nextLine();
        System.out.print("Введите возраст пользователя: ");
        String age = in.nextLine();
        System.out.print("Введите телефон пользователя: ");
        String phone = in.nextLine();
        System.out.print("Введите почту пользователя: ");
        String mail = in.nextLine();
        //if (Validator.correctPerson(name, surname, age, phone, mail)) {
        User user = getUserInfo();
        if (user != null) {
            person = new Person(name, surname, Integer.parseInt(age), phone, mail);
            user.setPerson(person);
            person.setUser(user);
        }
        else {
            System.out.println("Пароль или логин не корректны!");
        }
      /*  }
        else {
            System.out.println("Личные данные не корректны!");
        }*/
        return person;
    }

    private User getUserInfo() {
        User user = null;
        System.out.print("Введите логин пользователя: ");
        String login = in.nextLine();
        System.out.print("Введите пароль пользователя: ");
        String password = in.nextLine();
        //  if(Validator.correctUser(login, password)) {
        if(checkUniqueLogin(login)) {
            user = new User(login, password, "User");
        }
        else {
            System.out.println("Такой логин уже занят!");
        }
        //}
        return user;
    }

    private boolean checkUniqueLogin(String login) {
        boolean isUnique = true;
        for (Person p : getPeople()) {
            if (p.getUser().getLogin().equals(login)) {
                isUnique = false;
            }
        }
        return isUnique;
    }

    public void updatePerson() {
        System.out.println("---Изменение пользователя---");
        showPeople();
        System.out.print("Выберите ID пользователя для изменения: ");
        String id = in.nextLine();
        if (getPersonId(id)) {
            Person person = findPersonById(Integer.parseInt(id));
            if (person != null) {
                changeDataFromPerson(person);
                changeDataFromUser(person);
                if (personService.updatePerson(person)) {
                    System.out.println("---Изменение выполнено!---");
                }
            }
        }
    }

    private Person changeDataFromPerson(Person person) {
        System.out.print("Введите имя пользователя: ");
        String name = in.nextLine();
        System.out.print("Введите фамилию пользователя: ");
        String surname = in.nextLine();
        System.out.print("Введите возраст пользователя: ");
        String age = in.nextLine();
        System.out.print("Введите телефон пользователя: ");
        String phone = in.nextLine();
        System.out.print("Введите почту пользователя: ");
        String mail = in.nextLine();
        //  if (Validator.correctPerson(name, surname, age, phone, mail)) {
        person.setName(name);
        person.setSurname(surname);
        person.setAge(Integer.parseInt(age));
        person.setPhone(phone);
        person.setMail(mail);
       /* }
        else {
            System.out.println("Личные данные не корректны!");
        }*/
        return person;
    }

    private Person changeDataFromUser(Person person) {

        System.out.print("Введите логин пользователя: ");
        String login = in.nextLine();
        System.out.print("Введите пароль пользователя: ");
        String password = in.nextLine();
        //   if(Validator.correctUser(login, password)) {
        person.getUser().setLogin(login);
        person.getUser().setPassword(password);
        // }
        return person;
    }

    public void updateLoginAndPassword(User user) {
        System.out.println("---Изменение логина и пароля---");
        changeDataFromUser(user.getPerson());
        if (personService.updatePerson(user.getPerson())) {
            System.out.println("---Изменение выполнено!---");
        }

    }

    public void deletePerson() {
        System.out.println("---Удаление пользователя---");
        showPeople();
        System.out.print("Выберите ID пользователя для изменения: ");
        String id = in.nextLine();
        if (getPersonId(id)) {
            if (personService.deletePerson(Integer.parseInt(id))) {
                System.out.println("---Удаление выполнено!---");
            }
        }
    }

    private boolean getPersonId(String id) {
        boolean isAppropriateNumber = false;
        //   if (Validator.correctId(id)) {
        if (!(Integer.parseInt(id) < 0) && !(Integer.parseInt(id) > getPeople().size())) {
            isAppropriateNumber = true;
        }
        else {
            System.out.println("Такого ID нет!");
        }
       /* }
        else {
            System.out.println("ID не корректно!");
        }*/
        return isAppropriateNumber;
    }

    public void showPeople() {
        List<Person> people = getPeople();
        if (people.size() != 0) {
            System.out.format("%10s%20s%20s%10s%20s%30s%20s", "ID |", "Имя |", "Фамилия |", "Возраст |", "Телефон |", "Почта |", "Логин |");
            for (Person p: people) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s%10s%20s%30s%20s", p.getPersonId() + " |", p.getName() + " |",
                        p.getSurname() + " |", p.getAge() + " |",
                        p.getPhone() + " |", p.getMail() + " |", p.getUser().getLogin() + " |");

            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет пользователей!");
        }
    }

    private List<Person> getPeople() {
        List<Person> people = personService.showPeople();
        return people;
    }

    private Person findPersonById(int id) {
        Person person = personService.findPersonById(id);
        return person;
    }

    //---COMPANY---//

    public String addCompany() {
        System.out.println("---Добавление компании---");
        String result = null;
        System.out.print("Введите название компании: ");
        String name = in.nextLine();
        System.out.print("Введите страну происхождения компании: ");
        String country = in.nextLine();
        //  if (Validator.correctCompany(name, country)) {
        Company company = new Company(name, country);
        if (companyService.addCompany(company)) {
            result = "---Добавление выполнено!---";
        }
      /*  }
        else {
            result = "Данные не корректны!";
        }*/
        return result;
    }

    public String updateCompany() {
        String result = null;
        System.out.println("---Изменение компании---");
        showCompanies();
        System.out.print("Выберите ID компании для изменения: ");
        String id = in.nextLine();
        if (getCompanyId(id)) {
            System.out.print("Введите название компании: ");
            String name = in.nextLine();
            System.out.print("Введите страну происхождения компании: ");
            String country = in.nextLine();
            //     if (Validator.correctCompany(name, country)) {
            Company company = companyService.findCompanyById(Integer.parseInt(id));
            company.setCompanyName(name);
            company.setCompanyCountry(country);
            if (companyService.updateCompany(company)) {
                System.out.println("---Изменение выполнено!---");
            }
          /*  }
            else {
                System.out.println("Данные не корректны!");
            }*/
        }

        return result;
    }

    public void deleteCompany() {
        System.out.println("---Удаление компании---");
        showCompanies();
        System.out.print("Введите ID компании для удаления: ");
        String id = in.nextLine();
        if (getCompanyId(id)) {
            if(companyService.deleteCompany(Integer.parseInt(id))) {
                System.out.println("---Удаление выполнено!---");
            }
        }
    }

    private boolean getCompanyId(String id) {
        boolean isAppropriateNumber = false;
        //   if (Validator.correctId(id)) {
        if (!(Integer.parseInt(id) < 0) && !(Integer.parseInt(id) > getCompanies().size())) {
            isAppropriateNumber = true;
        }
        else {
            System.out.println("Такого ID нет!");
        }
       /* }
        else {
            System.out.println("ID не корректно!");
        }*/
        return isAppropriateNumber;
    }

    public void showCompanies() {
        List<Company> companies = getCompanies();
        if (companies.size() != 0) {
            theHeaderForCompany();
            for (Company c: companies) {
                theTableForCompany(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет компаний!");
        }
    }

    private void theTableForCompany(Company c) {
        System.out.println(" ");
        System.out.format("%10s%20s%30s", c.getCompanyId() + " |", c.getCompanyName() + " |", c.getCompanyCountry() + " |");
        System.out.println(" ");
    }

    private void theHeaderForCompany() {
        System.out.format("%10s%20s%30s"," ID |", "Название |", "Страна происхождения |");
    }

    private List<Company> getCompanies() {
        List<Company> companies = companyService.showCompanies();
        return companies;
    }

    public void showOneCompany() {
        Company company = findCompanyByName();
        if (company != null) {
            theHeaderForCompany();
            theTableForCompany(company);
        }
    }

    private Company findCompanyByName() {
        System.out.print("Введите название компании: ");
        String name = in.nextLine();
        boolean isFound = false;
        for (Company c : getCompanies()) {
            if (c.getCompanyName().equals(name)) {
                isFound = true;
            }
        }
        Company company = null;
        if (isFound) {
            company = companyService.findCompanyByName(name);
        }
        else {
            System.out.println("Такой компании не найдено!");
        }
        return company;
    }

    //---CAR---//

    public void addCar() {
        System.out.println("---Добавление машины---");
        showCompanies();
        System.out.print("Выберите ID компании для авто: ");
        String id = in.nextLine();
        Car car = getCarInfo();
        if (car != null) {
            Company company = companyService.findCompanyById(Integer.parseInt(id));
            car.setCompany(company);
            company.addCar(car);
            if (companyService.updateCompany(company)) {
                System.out.println("---Добавление выполнено!---");
            }
        }
    }

    private Car getCarInfo() {
        System.out.print("Введите название: ");
        String name = in.nextLine();
        System.out.print("Введите год создания: ");
        String year = in.nextLine();
        System.out.print("Введите пробег: ");
        String distance = in.nextLine();
        System.out.print("Введите вид топлива: ");
        String fuel = in.nextLine();
        System.out.print("Введите расход: ");
        String fuelConsumption = in.nextLine();
        System.out.print("Введите цену: ");
        String price = in.nextLine();
        // if (Validator.correctCar(name, year, distance, fuel, fuelConsumption, price)) {
        //     if (Validator.correctFuel(fuel)) {
        Car car = new Car();
        car.setName(name);
        car.setYear(Integer.parseInt(year));
        car.setDistance(Integer.parseInt(distance));
        car.setFuel(fuel);
        car.setFuelConsumption(fuelConsumption);
        car.setPrice(Integer.parseInt(price));
        return car;
          /*  }
            else {
                System.out.println("Введите топливо: Бензин или Дизель!");
            }*/
       /* }
        else {
            System.out.println("Данные не корректны!");
        }*/
    }

    public void deleteCar() {
        System.out.println("---Удаление машины---");
        showCars();
        Company company = findCompanyByName();
        if (company != null) {
            System.out.print("Введите название машины: ");
            String name = in.nextLine();
            Car car = findCarInList(company, name);
            if (car != null) {
                company.getCars().remove(car);
                if (companyService.updateCompany(company)) {
                    System.out.println("---Удаление выполнено!---");
                }
            }
        }
    }

    public void updateCar() {
        System.out.println("---Изменение машины---");
        showCars();
        Company company = findCompanyByName();
        if (company != null) {
            System.out.print("Введите название машины: ");
            String nameForChange = in.nextLine();
            Car car = findCarInList(company, nameForChange);
            if (car != null) {
                System.out.print("Введите название: ");
                String name = in.nextLine();
                System.out.print("Введите год создания: ");
                String year = in.nextLine();
                System.out.print("Введите пробег: ");
                String distance = in.nextLine();
                System.out.print("Введите вид топлива: ");
                String fuel = in.nextLine();
                System.out.print("Введите расход: ");
                String fuelConsumption = in.nextLine();
                System.out.print("Введите цену: ");
                String price = in.nextLine();
              /*  if (Validator.correctCar(name, year, distance, fuel, fuelConsumption, price)) {
                    if (Validator.correctFuel(fuel)) {*/
                car.setName(name);
                car.setYear(Integer.parseInt(year));
                car.setDistance(Integer.parseInt(distance));
                car.setFuel(fuel);
                car.setFuelConsumption(fuelConsumption);
                car.setPrice(Integer.parseInt(price));
            }
            else {
                System.out.println("Введите топливо: Бензин или Дизель!");
            }
               /* }
                else {
                    System.out.println("Данные не корректны!");
                }
                if (companyService.updateCompany(company)) {
                    System.out.println("---Изменение выполнено!---");
                }*/
            // }
        }
    }

    public void findCarByName() {
        System.out.print("Введите название машины: ");
        String name = in.nextLine();
        Car car = null;
        for (Company company : getCompanies()) {
            car = findCarInList(company, name);
            if (car != null) {
                theHeaderForCar();
                theTableForCar(car);
            }
        }
        System.out.println(" ");
    }

    private Car findCarInList(Company company, String name) {
        Car car = null;
        if (!company.getCars().isEmpty()) {
            for (Car c : company.getCars()) {
                if (c.getName().equals(name)) {
                    car = c;
                }
            }
            if (car == null) {
                System.out.println("Такой машины не найдено в компании " + company.getCompanyName());
            }

        }
        return car;
    }

    public void showCarsFromOneCompany() {
        showCompanies();
        Company company = findCompanyByName();
        if (company != null) {
            if (!company.getCars().isEmpty()) {
                theHeaderForCar();
                for (Car c : company.getCars()) {
                    theTableForCar(c);
                }
                System.out.println(" ");
            }
            else {
                System.out.println("Компания " + company.getCompanyName() + " не имеет моделей!");
            }
        }
    }

    public void showCars() {
        List<Company> companies = getCompanies();
        if (companies.size() != 0) {
            theHeaderForCar();
            for (Company c: companies) {
                List<Car> cars = c.getCars();
                if (!cars.isEmpty()) {
                    for (Car car : cars) {
                        theTableForCar(car);
                    }
                    System.out.println(" ");
                }

            }
        }
        else {
            System.out.println("Нет пользователей!");
        }
    }

    private void theHeaderForCar() {
        System.out.format("%15s%20s%10s%10s%10s%15s%10s%20s", "ID |", "Название |", "Год |", "Пробег |", "Топливо |",
                "Расход топлива |", "Цена |", " Компания|");
    }

    private void theTableForCar(Car car) {
        System.out.println(" ");
        System.out.format("%15s%20s%10s%10s%10s%16s%10s%20s", car.getCarId() + " |", car.getName() + " |",
                car.getYear() + " |", car.getDistance() + " |", car.getFuel() + " |",
                car.getFuelConsumption() + " |", car.getPrice() + " |", car.getCompany().getCompanyName() + " |");
    }
}
