package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PeopleRepository {

    @Autowired
    JdbcTemplate template;

    public List<Person> listPeople(){

        return template.query(
                "SELECT * FROM person ORDER BY personid",
                (rs, i) -> new Person(
                        rs.getInt("personid"),
                        rs.getString("title"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname"),
                        rs.getString("suffix")
                ));
    }

    public void deletePerson(Integer personId) {
        template.update("DELETE FROM personphone WHERE personid = ?", personId);
        template.update("DELETE FROM password WHERE personid = ?", personId);
        template.update("DELETE FROM emailaddress WHERE personid = ?", personId);
        template.update("DELETE FROM personaddress WHERE personid = ?", personId);
        template.update("DELETE FROM person WHERE personid = ?", personId);
    }

    public void insertPerson(Person person){
        template.update(
                "INSERT INTO person " +
                        "(title, " +
                        "firstname, " +
                        "middlename, " +
                        "lastname, " +
                        "suffix) " +
                        "VALUES (?, ?, ?, ?, ?)",
                person.getTitle(),
                person.getFirstName(),
                person.getMiddleName(),
                person.getLastName(),
                person.getSuffix());

    }

    public Person getPerson(Integer personId) {
        return template.queryForObject(
                "SELECT * FROM person WHERE personid = ?",
                new Object[]{personId},
                (rs, i) -> new Person(
                        rs.getInt("personid"),
                        rs.getString("title"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname"),
                        rs.getString("suffix")
                ));
    }

    public void updatePerson(Person person) {
        template.update(
                "UPDATE person SET " +
                        "title = ?, " +
                        "firstname = ?, " +
                        "middlename = ?, " +
                        "lastname = ?, " +
                        "suffix = ?" +
                        "WHERE personid = ?",
                person.getTitle(),
                person.getFirstName(),
                person.getMiddleName(),
                person.getLastName(),
                person.getSuffix(),
                person.getPersonId());
    }
}
