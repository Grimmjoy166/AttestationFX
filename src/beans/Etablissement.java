/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Sinponzakra
 */
@Entity
public class Etablissement {
    @Id
    @GeneratedValue
    private int id;
    private String nom;
    private String type;
    private String region;
    @OneToMany(mappedBy = "etablissement")
    private List<Etudiant> etudiants;
    @OneToMany(mappedBy = "etablissement")
    private List<Employe> employes;

    public Etablissement() {
    }

    public Etablissement(int id, String nom, String type, String region) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.region = region;
    }

    public Etablissement(String nom, String type, String region) {
        this.nom = nom;
        this.type = type;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
    
    

    @Override
    public String toString() {
        return this.nom;
    }
    
    
    
    
    
    
    
}
