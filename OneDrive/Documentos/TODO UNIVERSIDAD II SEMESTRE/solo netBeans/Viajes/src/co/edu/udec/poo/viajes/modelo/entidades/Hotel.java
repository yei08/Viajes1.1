/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;

/**
 *
 * @author JEIFER ALCALA
 */
public class Hotel {
  private String CodigoHotel;
  private String Nombre;
  private String Direccion;
  private String Ciudad;
  private String NumeroTelefonico;
  private List <Cliente> listaDeClientes;
 
  //constructor por defercto
  public Hotel (){
  
  }
  //constructor con parametros
  public Hotel (String CodigoHotel, String Nombre,
  String Direccion){
     this.CodigoHotel = CodigoHotel;
     this.Nombre = Nombre;
     this.Direccion = Direccion;
  } 

    public String getCodigoHotel() {
        return CodigoHotel;
        
    }
 
}
