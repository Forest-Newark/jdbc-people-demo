package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by doug on 4/6/17.
 */
@Controller
public class PeopleController {

    @Autowired
    PeopleRepository repo;

    @RequestMapping("/")
    public String listPeople(Model model){

        model.addAttribute("people", repo.listPeople());

        return "index";
    }

    @RequestMapping("/delete")
    public String deletePerson(Integer personId){

        // delete this schmuck
        repo.deletePerson(personId);

        return "redirect:/";
    }

    @RequestMapping("/person")
    public String addEditPerson(Model model, Integer personId){

        Person person;
        if(personId != null){
            person = repo.getPerson(personId);
        } else {
            person = new Person();
        }

        model.addAttribute("person", person);

        return "personForm";
    }

    @RequestMapping("/savePerson")
    public String savePerson(Person person){

        if(person.getPersonId() == null) {
            repo.insertPerson(person);
        } else {
            repo.updatePerson(person);
        }

        return "redirect:/";

    }

}
