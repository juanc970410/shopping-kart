/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.webappsintro.controller;

import edu.eci.pdsw.stubs.servicesfacadestub.Producto;
import edu.eci.pdsw.stubs.servicesfacadestub.ServicesFacade;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author hcadavid
 */
@SessionScoped
@ManagedBean(name = "ShoppingKartBackingBean")
public class ShoppingKartBackingBean {
     private List<Producto> productos;
     private Producto selectedProduct;
     private List<Producto> selectedProducts = new ArrayList<Producto>();
     private int cantidad;
     private HashMap<Integer, Integer> cantidades = new HashMap<Integer,Integer>();
     private int pagar = 0;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad=cantidad;
    }

    public HashMap<Integer, Integer> getCantidades() {
        return cantidades;
    }

    public void setCantidades(HashMap<Integer, Integer> cantidades) {
        this.cantidades = cantidades;
    }
     
    public List<Producto> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Producto> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public Producto getSelectedProduct() {
        
        return selectedProduct;
    }

    public void setSelectedProduct(Producto selectedProduct) {
        
        this.selectedProduct = selectedProduct;
        
        
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
     
    public List<Producto> getProductos(){
        productos = ServicesFacade.getInstance().getProductos();
        return productos;
    }
    
    public void add(){
        if(this.cantidad>0){
            if(!cantidades.containsKey(this.selectedProduct.getId())){
                
                this.selectedProducts.add(this.selectedProduct);
                this.cantidades.put(this.selectedProduct.getId(), this.cantidad);
            }
            else{
                
                int temp = this.cantidades.get(this.selectedProduct.getId())+this.cantidad;
                this.cantidades.replace(this.selectedProduct.getId(),temp);
            }
        }
        else if(this.cantidad<0 && cantidades.containsKey(this.selectedProduct.getId())){
            
            if((this.cantidad+this.cantidades.get(this.selectedProduct.getId()))<=0){
                this.selectedProducts.remove(this.selectedProduct);
                this.cantidades.remove(this.selectedProduct.getId());
            }
            else if((this.cantidad+this.cantidades.get(this.selectedProduct.getId()))>0){
                int temp = this.cantidades.get(this.selectedProduct.getId())+this.cantidad;
                this.cantidades.replace(this.selectedProduct.getId(),temp);
            }
        }
    }
    public int getPagar() {
        
        return pagar;
    }

    public void setPagar(int pagar) {
        this.pagar = pagar;
    }
    public void buy(){
        int valor = 0;
        
        if (cantidades.isEmpty()){
            valor = 0;
        }
        else{
            for(int i=0; i<selectedProducts.size();i++){
                Producto pd= selectedProducts.get(i);
                valor+=(pd.getPrecioEnPesos()*cantidades.get(pd.getId()));
            }
        }
        this.pagar=valor;
    }
}
