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
public class Sucursal {
  
  private String CodigoSucursal;
  private String Direccion;
  private String NumeroTelefonico;
  private List <Cliente> listaDeClientes;
  protected List<Hotel>listaDeHotel;
  protected List<Vuelo>listaDeVuelos;
}

