/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package co.edu.udec.software.poo.principal;

import co.edu.udec.software.poo.entidades.Cliente;
import co.edu.udec.software.poo.entidades.Facturacliente;
import co.edu.udec.software.poo.entidades.Hotel;
import co.edu.udec.software.poo.entidades.HotelPK;
import co.edu.udec.software.poo.entidades.Plazadisponible;
import co.edu.udec.software.poo.entidades.Regimenhospedaje;
import co.edu.udec.software.poo.entidades.Sucursale;
import co.edu.udec.software.poo.entidades.Vuelo;

/**
 *
 * @author JEIFER ALCALA
 */
public class Viajes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
     Cliente c1 = new Cliente();
        c1.setNombre("Pedro");
        System.out.println("Este es el primer usuario de la agencia: " + c1.getNombre());

        Facturacliente f1 = new Facturacliente();
        f1.setDescuento((float) 0.5);
        double descuento = f1.getDescuento()*10;
        System.out.println("Usted tiene un descuento por el valor de: " + descuento+"%");

        Hotel h1 = new Hotel();
        h1.setNombre("Yere Confort");
        System.out.println("El nombre del Hotel es: " + h1.getNombre());

        HotelPK k1 = new HotelPK();
        k1.setIdHoteles(1);
        System.out.println("Aquí se muestra información de HotelPK: " + k1.getIdHoteles());

        Plazadisponible p1 = new Plazadisponible();
        p1.setPlazaDisponiblecol(25);
        System.out.println("Estas son las plazas disponibles: " + p1.getPlazaDisponiblecol());

        Regimenhospedaje r1 = new Regimenhospedaje();
        r1.setTipoPension("Media pensión");
        System.out.println("Este es el tipo de pensión: " + r1.getTipoPension());

        Sucursale s1 = new Sucursale();
        s1.setNroTelefonico("3001010101");
        System.out.println("Este es el número telefónico de la sucursal; " + s1.getNroTelefonico());

        Vuelo v1 = new Vuelo();
        v1.setDestino("Santa Marta");
        System.out.println("El destino del vuelo es: " + v1.getDestino());
    
    }
    
}
