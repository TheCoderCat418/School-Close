/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.thecodercat418.school;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author ellio
 */
public class School {

    public static ArrayList<String> IDS = new ArrayList<>();
    static OkHttpClient ohc = new OkHttpClient();
    static Gson gson = new Gson();

    public static void main(String[] args) {
        while (true) {
            try {
                   ArrayList<closing> aa = findNewClosings();
                for (int i = 0; i<aa.size(); i++) {
                    String data = "{\"content\":null,\"embeds\":[{\"title\":\"" + aa.get(i).Name1[0] + " HAS CLOSED!\",\"description\":\"" + aa.get(i).Name1[0] + " is " + aa.get(i).Status1[0] + " and " + aa.get(i).Status2[0]  + ".\\n\\nCity: " + aa.get(i).City[0] + "\",\"color\":16711680}],\"attachments\":[]}";
                    pushUpdate(data);
                    System.out.println("Finished closing " + i+1 + "/" + aa.size());
                    Thread.sleep(2000);
                }
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println("Waiting...");
                Thread.sleep(300000);
            } catch (InterruptedException ex) {
                Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static ArrayList<closing> findNewClosings() throws IOException {
        root r = CheckNewData();
        ArrayList<closing> list = new ArrayList<>();
        for (closing close : r.Closing) {
            if (IDS.indexOf(close.ID[0]) == -1) {
                if (close.State[0].equals("IL")) {
                    if (close.EntityTypeCode[0].equals("1") || close.EntityTypeCode[0].equals("2")) {
                        System.out.println("Found Closare. Name: " + close.Name1[0]);
                        IDS.add(close.ID[0]);
                        list.add(close);
                    }
                }
            }
        }
        return list;
    }

    public static root CheckNewData() throws IOException {
        Request r = new Request.Builder().url("https://media.psg.nexstardigital.net/WGNR/closings/closings.json").get().build();
        Response re = ohc.newCall(r).execute();
        if(re.isSuccessful()){
            System.out.println("Running Json...");
        root r2 = gson.fromJson(re.body().string(), root.class);
        System.out.println("Done!");
        return r2;
        }else{
            System.out.println(re.code());
            return null;
        }
        
    }

    public static void pushUpdate(String json) {
        try {System.out.println("Pushing Update...");
            RequestBody rb = RequestBody.create(json, MediaType.get("application/json"));
            Request request = new Request.Builder().url("https://discord.com/api/webhooks/1195155487046520862/a636MF89sGiMGJYjKyt4cpFFZ3WDd7E8-xr3vm7Y8WNGeNV9a1sdzbNTCSjFy_kr1lE2")
                    .post(rb)
                    .build();
            Response r = ohc.newCall(request).execute();
        } catch (IOException ex) {
            Logger.getLogger(School.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
