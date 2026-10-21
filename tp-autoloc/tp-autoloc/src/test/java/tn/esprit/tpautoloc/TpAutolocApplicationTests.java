package tn.esprit.tpautoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.repository.IClientRepository;
import java.time.LocalDate;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import java.math.BigDecimal;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;
import tn.esprit.tpautoloc.repository.IEquipementRepository;
import tn.esprit.tpautoloc.repository.IEmployeRepository;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.service.IVehiculeService;


@SpringBootTest
class TpAutolocApplicationTests {

    @Autowired //Spring injecte lui-même l’implémentation de IAgenceRepository. Tu n’écris pas new
    private IAgenceRepository agenceRepository;
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private IVehiculeRepository vehiculeRepository;
    @Autowired
    private IEquipementRepository equipementRepository;

    @Autowired
    private IEmployeRepository employeRepository;

    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Autowired
    private IAgenceService agenceService;
    @Autowired
    private IClientService clientService;
    @Autowired
    private IVehiculeService vehiculeService;

    @Test
    void contextLoads() {
    }

    @Test
    void testAgenceRepository() {

        long nombreAvant = agenceRepository.count();

        Agence agence = new Agence();
        agence.setNom("Agence4");
        agence.setAdresse("djerba");

        //save
        Agence saved = agenceRepository.save(agence); //Elle retourne l’objet enregistré, avec l’id généré.
        System.out.println("Agence enregistrée : " + saved);
        assertNotNull(saved); // saved existe (n’est pas null) : save() a bien renvoyé un objet
        assertNotNull(saved.getIdAgence()); // verifier l’id n’est pas null : la BD a généré un id, donc l’insertion a réussi
        assertEquals("Agence4", saved.getNom()); // verifie valeur attendue = valeur réelle


        // findAll :Afficher l’objet retourné par save().
        List<Agence> agences = agenceRepository.findAll();
        System.out.println("Toutes les agences : " + agences);
        assertFalse(agences.isEmpty()); //  verifie la condition est false :isEmpty() doit être faux, donc la liste contient des agences

        // findById
        Optional<Agence> trouvee = agenceRepository.findById(saved.getIdAgence());
        /* Si l’agence n’est pas trouvée, findById renvoie un Optional.empty(), c’est-à-dire un Optional vide. Elle ne renvoie jamais null, et ne lance pas d’exception.
        * Un Optional est une boîte : soit la boîte contient une agence,
soit la boîte est vide.*/


        System.out.println("Agence trouvée : " + trouvee);
        assertTrue(trouvee.isPresent()); // verifie la condition est true
        assertEquals(saved.getIdAgence(), trouvee.get().getIdAgence()); //les deux ids sont égaux:on a retrouvé la bonne agence


        // existsById
        boolean existe = agenceRepository.existsById(saved.getIdAgence());
        System.out.println("Existe ? " + existe);
        assertTrue(existe);


        // count
        System.out.println("Nombre d'agences : " + agenceRepository.count());
        assertEquals(nombreAvant + 1, agenceRepository.count());
        // deleteById

        agenceRepository.deleteById(saved.getIdAgence());
        System.out.println("Existe encore ? " + agenceRepository.existsById(saved.getIdAgence()));
        assertFalse(agenceRepository.existsById(saved.getIdAgence()));
        assertTrue(agenceRepository.findById(saved.getIdAgence()).isEmpty());
        assertEquals(nombreAvant, agenceRepository.count());
    }

    @Test
    void testClientRepository() {

        long nombreAvant = clientRepository.count();

        // Création de deux clients (sans toucher à "reservations")
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.benali@gmail.com");
        c1.setTelephone("20123456");
        c1.setNumPermis("P12345");
        c1.setDateInscription(LocalDate.of(2026, 10, 15));

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sara");
        c2.setEmail("sara.trabelsi@gmail.com");
        c2.setTelephone("98765432");
        c2.setNumPermis("P67890");
        c2.setDateInscription(LocalDate.of(2026, 10, 15));

        // save
        Client saved1 = clientRepository.save(c1);
        Client saved2 = clientRepository.save(c2);
        System.out.println("Client 1 enregistré : " + saved1.getIdClient() + " " + saved1.getNom());
        System.out.println("Client 2 enregistré : " + saved2.getIdClient() + " " + saved2.getNom());
        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());
        assertNotEquals(saved1.getIdClient(), saved2.getIdClient());
        assertEquals("Ben Ali", saved1.getNom());
        assertEquals("Trabelsi", saved2.getNom());

        // findAll
        List<Client> clients = clientRepository.findAll();
        System.out.println("Liste des clients :");
        clients.forEach(c -> System.out.println(c.getIdClient() + " - " + c.getNom() + " " + c.getPrenom())); //expression lambda)
        assertFalse(clients.isEmpty());
        assertTrue(clients.size() >= 2);

        // findById
        Optional<Client> trouve = clientRepository.findById(saved1.getIdClient());
        System.out.println("Agence trouvée : " + trouve);
        assertTrue(trouve.isPresent());
        assertEquals("Ahmed", trouve.get().getPrenom());

        // existsById : id existant puis inexistant
        assertTrue(clientRepository.existsById(saved1.getIdClient()));
        System.out.println("Id existant : " + clientRepository.existsById(saved1.getIdClient()));

        Long idInexistant = 999999L;
        System.out.println("Id inexistant : " + clientRepository.existsById(idInexistant));
        assertFalse(clientRepository.existsById(idInexistant));

        // count
        System.out.println("Nombre de clients : " + clientRepository.count());
        assertEquals(nombreAvant + 2, clientRepository.count());
    }


    @Test
    void testVehiculeRepository() {

        long nombreAvant = vehiculeRepository.count();


        Vehicule v = new Vehicule();
        v.setImmatriculation("200 TUN 1234");
        v.setMarque("Peugeot");
        v.setModele("208");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("120.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);

        // save
        Vehicule saved = vehiculeRepository.save(v);
        System.out.println("Véhicule enregistré : " + saved.getIdVehicule() + " "
                + saved.getMarque() + " " + saved.getModele());
        assertNotNull(saved.getIdVehicule());
        assertEquals("Peugeot", saved.getMarque());
        assertEquals(CategorieVehicule.CITADINE, saved.getCategorie());
        assertEquals(StatutVehicule.DISPONIBLE, saved.getStatut());


        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("Liste des véhicules :");
        vehicules.forEach(x -> System.out.println(
                x.getIdVehicule() + " - " + x.getMarque() + " " + x.getModele()
                        + " - " + x.getCategorie() + " - " + x.getStatut()
                        + " - " + x.getTarifJournalier() + " DT"));
        assertFalse(vehicules.isEmpty());

        System.out.println("Nombre de véhicules : " + vehiculeRepository.count());
        assertEquals(nombreAvant + 1, vehiculeRepository.count());


        saved.setTarifJournalier(new BigDecimal("150.00")); //BigDecimal est un type Java pour les nombres décimaux exacts, utilisé surtout pour l’argent. Ton attribut tarifJournalier est de ce type, donc tu dois lui donner un BigDecimal (pas un double).
        saved.setStatut(StatutVehicule.LOUE);
        Vehicule modifie = vehiculeRepository.save(saved);
        System.out.println("Après modification : tarif = " + modifie.getTarifJournalier()
                + ", statut = " + modifie.getStatut());
        assertEquals(saved.getIdVehicule(), modifie.getIdVehicule());
        assertEquals(0, new BigDecimal("150.00").compareTo(modifie.getTarifJournalier()));
        /* Le 0 est le résultat attendu de compareTo. Cette méthode compare deux BigDecimal et renvoie un entier :
        -1	la première est plus petite que la seconde
1	la première est plus grande que la seconde
0:les deux valeurs sont égales
         */

        assertEquals(StatutVehicule.LOUE, modifie.getStatut());
        assertEquals(nombreAvant + 1, vehiculeRepository.count());      // pas de doublon



        // deleteById
        vehiculeRepository.deleteById(saved.getIdVehicule());
        System.out.println("Existe encore ? " + vehiculeRepository.existsById(saved.getIdVehicule()));
        assertFalse(vehiculeRepository.existsById(saved.getIdVehicule()));
        assertEquals(nombreAvant, vehiculeRepository.count());
    }



    @Test
    void testEquipementRepository() {

        long nombreAvant = equipementRepository.count();

        Equipement e = new Equipement();
        e.setLibelle("GPS");


        Equipement saved = equipementRepository.save(e);
        System.out.println("Equipement enregistré : " + saved.getIdEquipement() + " - " + saved.getLibelle());
        assertNotNull(saved.getIdEquipement());
        assertEquals("GPS", saved.getLibelle());

        List<Equipement> liste = equipementRepository.findAll();
        System.out.println("Liste des équipements :");
        liste.forEach(x -> System.out.println(x.getIdEquipement() + " - " + x.getLibelle()));
        assertFalse(liste.isEmpty());

        // findById
        Optional<Equipement> trouve = equipementRepository.findById(saved.getIdEquipement());
        System.out.println("Equipement  trouvée : " + trouve);
        assertTrue(trouve.isPresent());

        // existsById
        assertTrue(equipementRepository.existsById(saved.getIdEquipement()));

        // deleteById

        equipementRepository.deleteById(saved.getIdEquipement());
        System.out.println("Existe encore ? " + equipementRepository.existsById(saved.getIdEquipement()));
        assertFalse(equipementRepository.existsById(saved.getIdEquipement()));
        assertEquals(nombreAvant, equipementRepository.count());
    }



    @Test
    void testMaintenanceRepository() {

        long nombreAvant = maintenanceRepository.count();

        Maintenance m = new Maintenance();
        m.setDateDebut(LocalDate.of(2026, 10, 15));
        m.setDateFin(LocalDate.of(2026, 10, 18));
        m.setDescription("Vidange et changement de filtres");

        // save
        Maintenance saved = maintenanceRepository.save(m);
        System.out.println("Maintenance enregistrée : " + saved.getIdMaintenance() + " - " + saved.getDescription());
        assertNotNull(saved.getIdMaintenance());
        assertEquals("Vidange et changement de filtres", saved.getDescription());

        // findAll
        List<Maintenance> liste = maintenanceRepository.findAll();
        System.out.println("Liste des maintenances :");
        liste.forEach(x -> System.out.println(x.getIdMaintenance() + " - " + x.getDateDebut()
                + " -> " + x.getDateFin() + " - " + x.getDescription()));
        assertFalse(liste.isEmpty());

        // findById
        Optional<Maintenance> trouve = maintenanceRepository.findById(saved.getIdMaintenance());
        System.out.println("Maintenance   trouvée : " + trouve);
        assertTrue(trouve.isPresent());
        assertEquals(LocalDate.of(2026, 10, 15), trouve.get().getDateDebut());

        assertTrue(maintenanceRepository.existsById(saved.getIdMaintenance()));

        // deleteById

        maintenanceRepository.deleteById(saved.getIdMaintenance());
        System.out.println("Existe encore ? " + maintenanceRepository.existsById(saved.getIdMaintenance()));
        assertFalse(maintenanceRepository.existsById(saved.getIdMaintenance()));
        assertEquals(nombreAvant, maintenanceRepository.count());
    }


    @Test
    void testEmployeRepository() {

        long nombreAvant = employeRepository.count();

        Employe emp = new Employe();
        emp.setNom("Mansouri");
        emp.setPrenom("Karim");
        emp.setRole(RoleEmploye.AGENT);



        Employe saved = employeRepository.save(emp);
        System.out.println("Employé enregistré : " + saved.getIdEmploye() + " - "
                + saved.getNom() + " " + saved.getPrenom() + " - " + saved.getRole());
        assertNotNull(saved.getIdEmploye());
        assertEquals("Mansouri", saved.getNom());
        assertEquals(RoleEmploye.AGENT, saved.getRole());

        // findAll
        List<Employe> liste = employeRepository.findAll();
        System.out.println("Liste des employés :");
        liste.forEach(x -> System.out.println(x.getIdEmploye() + " - " + x.getNom()
                + " " + x.getPrenom() + " - " + x.getRole()));
        assertFalse(liste.isEmpty());

        // findById
        Optional<Employe> trouve = employeRepository.findById(saved.getIdEmploye());
        System.out.println("Employé trouvé : " + trouve);
        assertTrue(trouve.isPresent());
        assertEquals("Karim", trouve.get().getPrenom());
        assertEquals(RoleEmploye.AGENT, trouve.get().getRole());

        // existsById
        assertTrue(employeRepository.existsById(saved.getIdEmploye()));

        // deleteById

        employeRepository.deleteById(saved.getIdEmploye());
        System.out.println("Existe encore ? " + employeRepository.existsById(saved.getIdEmploye()));
        assertFalse(employeRepository.existsById(saved.getIdEmploye()));
        assertEquals(nombreAvant, employeRepository.count());
    }



    @Test
    void testAgenceService() {
        int nombreAvant = agenceService.findAll().size();

        Agence agence = new Agence();
        agence.setNom("Agence Service");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setVille("Tunis");
        agence.setTelephone("71123456");


        Agence saved = agenceService.create(agence);
        System.out.println("Agence enregistrée : " + saved.getIdAgence() + " - " + saved.getNom());
        assertNotNull(saved.getIdAgence());
        assertEquals("Agence Service", saved.getNom());


        List<Agence> agences = agenceService.findAll();
        System.out.println("Liste des agences :");
        agences.forEach(a -> System.out.println(a.getIdAgence() + " - " + a.getNom()));
        assertFalse(agences.isEmpty());


        Agence trouvee = agenceService.findById(saved.getIdAgence());
        System.out.println("Agence trouvée : " + trouvee.getNom());
        assertEquals(saved.getIdAgence(), trouvee.getIdAgence());


        System.out.println("Nombre d'agences : " + agenceService.findAll().size());
        assertEquals(nombreAvant + 1, agenceService.findAll().size());


        Agence modif = new Agence();
        modif.setNom("Agence Service Modifiée");
        modif.setAdresse("Avenue Habib Bourguiba");
        modif.setVille("Tunis");
        modif.setTelephone("71123456");
        Agence maj = agenceService.update(saved.getIdAgence(), modif);
        assertEquals("Agence Service Modifiée", maj.getNom());

        // deleteById puis vérification

        agenceService.deleteById(saved.getIdAgence());

        assertEquals(nombreAvant, agenceService.findAll().size());
        System.out.println("Agence supprimée, nombre d'agences : " + agenceService.findAll().size());
    }

    @Test
    void testClientService() {
        int nombreAvant = clientService.findAll().size();


        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Ahmed");
        c1.setEmail("ahmed.service@gmail.com");
        c1.setTelephone("20123456");
        c1.setNumPermis("P11111");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Sara");
        c2.setEmail("sara.service@gmail.com");
        c2.setTelephone("98765432");
        c2.setNumPermis("P22222");
        c2.setDateInscription(LocalDate.now());

        Client s1 = clientService.create(c1);
        Client s2 = clientService.create(c2);
        System.out.println("Client 1 enregistré : " + s1.getIdClient() + " " + s1.getNom());
        System.out.println("Client 2 enregistré : " + s2.getIdClient() + " " + s2.getNom());
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());


        List<Client> clients = clientService.findAll();
        System.out.println("Liste des clients :");
        clients.forEach(c -> System.out.println(c.getIdClient() + " - " + c.getNom() + " " + c.getPrenom()));



        Client trouve = clientService.findById(s1.getIdClient());
        System.out.println("Client trouvé : " + trouve.getNom() + " " + trouve.getPrenom());
        assertEquals("Ahmed", trouve.getPrenom());


        System.out.println("Nombre de clients : " + clientService.findAll().size());
        assertEquals(nombreAvant + 2, clientService.findAll().size());


        clientService.deleteById(s1.getIdClient());
        clientService.deleteById(s2.getIdClient());
        System.out.println("Clients supprimés, nombre de clients : " + clientService.findAll().size());
        assertEquals(nombreAvant, clientService.findAll().size());


    }


    @Test
    void testVehiculeService() {
        int nombreAvant = vehiculeService.findAll().size();


        Vehicule v = new Vehicule();
        v.setImmatriculation("201 TUN 5678");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("110.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule saved = vehiculeService.create(v);
        System.out.println("Véhicule enregistré : " + saved.getIdVehicule() + " "
                + saved.getMarque() + " " + saved.getModele());
        assertNotNull(saved.getIdVehicule());
        assertEquals("Renault", saved.getMarque());


        List<Vehicule> vehicules = vehiculeService.findAll();
        System.out.println("Liste des véhicules :");
        vehicules.forEach(x -> System.out.println(x.getIdVehicule() + " - " + x.getMarque()
                + " " + x.getModele() + " - " + x.getCategorie() + " - " + x.getStatut()
                + " - " + x.getTarifJournalier() + " DT"));
        assertFalse(vehicules.isEmpty());


        Vehicule trouve = vehiculeService.findById(saved.getIdVehicule());
        System.out.println("Véhicule trouvé : " + trouve.getMarque() + " " + trouve.getModele());
        assertEquals(saved.getIdVehicule(), trouve.getIdVehicule());


        System.out.println("Nombre de véhicules : " + vehiculeService.findAll().size());
        assertEquals(nombreAvant + 1, vehiculeService.findAll().size());

        // règle métier : tarif négatif refusé
        Vehicule invalide = new Vehicule();
        invalide.setMarque("Test");
        invalide.setTarifJournalier(new BigDecimal("-5"));
        assertThrows(IllegalArgumentException.class, () -> vehiculeService.create(invalide));

        // deleteById puis vérification
        vehiculeService.deleteById(saved.getIdVehicule());

        System.out.println("Véhicule supprimé, nombre de véhicules : " + vehiculeService.findAll().size());
        assertEquals(nombreAvant, vehiculeService.findAll().size());
    }






}