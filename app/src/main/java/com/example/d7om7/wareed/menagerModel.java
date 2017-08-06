package com.example.d7om7.wareed;

import java.util.ArrayList;

/**
 * Created by d7om7 on 8/1/2017.
 */

public class menagerModel {
    static Donor donor=null;

    public void creatDonor(){
if (donor==null) {
    donor = new Donor("ahmad", "0552777608", 0, "makkah", "O+", "0", "0", "1", new ArrayList<String>());
    donor.setRequestBlood("54666757546");
    donor.getRequestBlood(0);
}
    }

}
