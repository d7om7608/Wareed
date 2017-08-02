package com.example.d7om7.wareed;

import java.util.ArrayList;

/**
 * Created by d7om7 on 8/1/2017.
 */

public class menagerModel {
    static Donor donor=null;

    public void creatDonor(){
if (donor==null) {
    donor = new Donor("ahmad", "0552777608", 0, "makkah", "O+", "0", "0", 1, new ArrayList<RequestBlood>());
    RequestBlood requestBlood = new RequestBlood("khaled", 125467, 3, "عمليه جراحيه", "A+", "makkah", "king khaled", "1438/11/1", 1, donor.getUserID(), 2);
    donor.requestBlood.add(requestBlood);
}
    }

}
