package com.example.clement.ouestpaul;

import com.example.clement.ouestpaul.lieux.Batiment;
import com.example.clement.ouestpaul.lieux.Lieu;
import com.example.clement.ouestpaul.lieux.Service;
import com.example.clement.ouestpaul.location.Position;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by clement on 23/01/2015.
 */
public class GestionServices {

    private ArrayList<Service> listService;

    public GestionServices() {
        listService = new ArrayList<Service>();
        test();
    }

    private void test() {
        Batiment b1 = new Batiment(1, "RU 1", new Position(43.562248, 1.463193));
        Batiment b2 = new Batiment(2, "RU 2", new Position(43.560967, 1.471973));

        Batiment b3 = new Batiment(3, "U1", new Position(43.560304, 1.470264));
        Batiment b4 = new Batiment(4, "U2", new Position(43.561322, 1.470526));
        Batiment b5 = new Batiment(5, "U3", new Position(43.561961, 1.469963));
        Batiment b6 = new Batiment(6, "U4", new Position(43.562703, 1.469192));

        Service s1 = new Service(1, "Recharge Monéo", new Position(43.562248, 1.463193), b1);
        Service s2 = new Service(2, "Recharge Monéo", new Position(43.560967, 1.471973), b2);

        Service s3 = new Service(1, "Machine à café", new Position(43.560304, 1.470264), b3);
        Service s4 = new Service(2, "Machine à café", new Position(43.561322, 1.470526), b4);
        Service s5 = new Service(1, "Machine à café", new Position(43.561961, 1.469963), b5);
        Service s6 = new Service(2, "Machine à café", new Position(43.562703, 1.469192), b6);

        Service s7 = new Service(1, "Distrib. de boissons", new Position(43.560304, 1.470264), b3);
        Service s8 = new Service(2, "Distrib. de boissons", new Position(43.561322, 1.470526), b4);
        Service s9 = new Service(1, "Distrib. de boissons", new Position(43.561961, 1.469963), b5);
        Service s10 = new Service(2, "Distrib. de boissons", new Position(43.562703, 1.469192), b6);

        Service s11 = new Service(1, "Distrib. de nourritures", new Position(43.560304, 1.470264), b3);
        Service s12 = new Service(2, "Distrib. de nourritures", new Position(43.561322, 1.470526), b4);
        Service s13 = new Service(1, "Distrib. de nourritures", new Position(43.561961, 1.469963), b5);
        Service s14 = new Service(2, "Distrib. de nourritures", new Position(43.562703, 1.469192), b6);

        listService.add(s1);
        listService.add(s2);
        listService.add(s3);
        listService.add(s4);
        listService.add(s5);
        listService.add(s6);
        listService.add(s7);
        listService.add(s8);
        listService.add(s9);
        listService.add(s10);
        listService.add(s11);
        listService.add(s12);
        listService.add(s13);
        listService.add(s14);
    }

    public ArrayList<String> getTypesServices() {
        ArrayList<String> listNoms = new ArrayList<String>();
        for (Service s : listService) {
            if (!listNoms.contains(s.getNom()))
                listNoms.add(s.getNom());
        }
        Collections.sort(listNoms);
        return listNoms;
    }

    public ArrayList<Service> getListServiceByName(String name) {
        ArrayList<Service> listServiceByName = new ArrayList<Service>();
        for (Service s : listService) {
            if (s.getNom().equals(name))
                listServiceByName.add(s);
        }
        return listServiceByName;
    }
}
