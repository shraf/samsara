/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara.constants;

/**
 *
 * @author eldee
 */
public class consts {
    enum City{cairo("القاهرة"),giza("الجيزة"),qalyobia("القليوبية"),alexandria("اسكندرية"),behira("البحيرة"),matrouh("مطروح"),dumiat("دمياط"),daqahlia("دقهلية"),kafr_elsheikh("كفر الشيخ"),elgharbia("الغربية")
   ,mnofia("المنوفية"),sharqya("الشرقية"),port_said("بور سعيد"),ismailia("اسماعلية"),suez("السويس"),ganoub_sinai("جنوب سينا"),shamal_sinai("شمال سينا"),bni_sweif("بنى سويف"),asyuit("اسيوط"),elmenia("المنيا"),sohag("سوهاج"),qena("قنا"),luxor("الاقصر"),aswan("اسوان");
        private String city;
       
        private City(String city){
            this.city=city;
        }
        
        public boolean contains(String city){
       for (City c:City.values())
           if(city.equals(c.city))
               return true;
       return false;
   }
    
    };
    
   

}
