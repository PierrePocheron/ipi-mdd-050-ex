package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    //@Autowired
    //private CommercialRepository commercialRepository;


    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long countEmployes(){
        Long nbEmployes = employeRepository.count();
        return  nbEmployes;
    }

    //Gerer erreur 404 - 409 - 400
    //Recherche un employé par son id
    //Retourne un employé
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employe findById(@PathVariable (value = "id") Long id){
        Optional<Employe> employe = employeRepository.findById(id);
        if(employe.isEmpty()){
            //Erreur404
        }
        return employe.get();
    }


    //Recherche un employé par son matricule
    //Retourne un employé
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"matricule"})
    public Employe findByMatricule(@RequestParam(value = "matricule") String matricule){
        Employe employe = employeRepository.findByMatricule(matricule);
        return employe;
    }


    //Recherche un tous les employés + Option de trie
    //Retourne une liste d'employés
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"page"})

    public Page<Employe> listEmployes(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortProperty") String sortProperty,
            @RequestParam("sortDirection") String sortDirection){
        return employeRepository.findAll(PageRequest.of(page,size, Sort.Direction.fromString(sortDirection), sortProperty));

    }


    //Création d'un Employe
    //Retourne un Employe
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Employe createEmploye(@RequestBody Employe employe)
    {
        return employeRepository.save(employe);
    }

    //Modification d'un Employe
    //Retourne un Employe
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Employe updateEmploye(@RequestBody Employe employe)
    {
        return employeRepository.save(employe);
    }

    //Suppression d'un Employe
    //Retourne rien (seulement un confirmation de suppression code Http:204)
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//204
    public void deleteEmploye(@PathVariable Long id){
        employeRepository.deleteById(id);
    }



}
