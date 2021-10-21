package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.myapplication.callBack.QuaryResponce;
import com.example.myapplication.databinding.ActivityMainBinding;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "Main";
    private String tempIdStrore = "";
    private byte itterator = 0;
    ArrayList<String> idsForQuary = new ArrayList<>();
    private int arrayListSize =0;
    private int delayInMili = 10;
    private int position = 0;
    // DatabaseTableDao databaseTableDao;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firstLevelParsing();
        //when FirstLevel Parsing done we call second level parsing in handeler


        //Old method to write handeler
      /*  Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                secondLevelParsing();
            }
        },delayInMili);*/


        //with lamda
        new Handler().postDelayed(()->runOnUiThread(()->secondLevelParsing()),delayInMili);
        //secondLevelParsing();

    }

    QuaryResponce quaryResponce = new QuaryResponce() {
        @Override
        public void onSuccess(String response) {
            //Here We get responce like this
            //1,MainMenu,1,0,0,0,0,,,0,0;9,System Status,1,0,0,0,0,,0,0;15,Load,1,0,0,0,0,,0,0;25,Bus Voltage,1,0,0,0,0,,0,0;
            //First split responce with ; semicolom then split with , comma
            // String tempResponse = response;
            String tempResponce = "1,MainMenu,1,0,0,0,0,,,0,0;9,System Status,1,0,0,0,0,,0,0;15,Load,1,0,0,0,0,,0,0;25,Bus Voltage,1,0,0,0,0,,0,0;";
            String[] splitedResponce = tempResponce.split(";");
            Integer id;
            String responceName;
            String secondLevelFlages;

            for(int i=0;i<splitedResponce.length;i++){
                String[] valueSpliter = splitedResponce[i].split(",",3);
                id = Integer.valueOf(valueSpliter[0]);
                responceName = valueSpliter[1];
                secondLevelFlages = valueSpliter[2];
            }

        }

        @Override
        public void onFail(Throwable throwable) {

        }
    };

    private void secondLevelParsing() {
        if (position<idsForQuary.size()){
            String request = "";
            request = "type=3&itms=";
            request+=idsForQuary.get(position);
            // sendRequest(request,quaryResponce);

            String tempResponce = "1,MainMenu,1,0,0,0,0,,,0,0;9,System Status,1,0,0,0,0,,0,0;15,Load,1,0,0,0,0,,0,0;25,Bus Voltage,1,0,0,0,0,,0,0;";
            String[] splitedResponce = tempResponce.split(";");
            Integer id=0;
            String responceName="";
            String secondLevelFlages="";

            for(int i=0;i<splitedResponce.length;i++){
                String[] valueSpliter = splitedResponce[i].split(",",3);
                id = Integer.valueOf(valueSpliter[0]);
                responceName = valueSpliter[1];
                secondLevelFlages = valueSpliter[2];
            }

          /*  DatabaseTable databaseTables = new DatabaseTable(id,responceName,"",secondLevelFlages);
            MyDataBase.getInstance(this);
            databaseTableDao.dataInsert(databaseTables);
           */
            Log.d(TAG, "secondLevelParsing: "+quaryResponce);
        }

    }

    private void sendRequest(String request,QuaryResponce quaryResponce) {
        position++;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void firstLevelParsing() {
        try{
            InputStream inputStream = this.getAssets().open("item.png");
            byte[] itemData = IOUtils.toByteArray(inputStream);
            // Log.e(TAG,""+ Arrays.toString(itemData));
            // Reference type data[-123, 0, 1, -123, 0, 9, -123, 0, 15, 1, 0, 25, 0, 0, 26, 0, 0, 27, 0, 0, 23, 0, 0, 24, 0, 0, 29, 0, 0, 30, 0, 0, 32, 0, 0, 33, 0, 0, 35, 0, 0, 36, 2, 0, 39, -124, 0, 16, 1, 12, 25, 0, 12, 26, 0, 0, 40, 0, 0, 41, 0, 5, 63, 0, 0, 42, 0, 8, 120, 0, 8, 121, 0, 0, 43, 0, 0, 44, 0, 0, 45, 0, 8, 122, 0, 8, -7, 0, 0, 46, 0, 0, 47, 0, 0, 48, 0, 11, -79, 0, +22,757 more]

            String store1 = "";
            for(int i=0;i<itemData.length;i+=3){

                store1 = Integer.toBinaryString(itemData[i]);
                Log.d(TAG, ""+store1);

                //-123
                //ref 2 data convert 11111111111111111111111110000101;
                //online convert 1111111110000101;
                //[1, 0, 1, 0, 0, 0, 0, 1] my converted and its already in reverse form because dock refer reverce string;

                //char[] convert = store1.toCharArray();
                char[] firstLevel = new char[8];
                for(int j=0;j<8;j++){
                    char convert;
                    try{
                        convert = store1.charAt(store1.length()-j-1);
                    }
                    catch (StringIndexOutOfBoundsException e){
                        convert = '0';
                    }
                    firstLevel[j] = convert;
                }

                int msb = Byte.toUnsignedInt(itemData[i + 1]);
                int lsb = Byte.toUnsignedInt(itemData[i + 2]);
                int result;
                result = lsb + (msb << 8);
                if(itterator<14){
                    tempIdStrore += result;
                    if (itterator<13){
                        tempIdStrore+=",";
                    }
                    itterator++;
                }
                else{
                    tempIdStrore.charAt(tempIdStrore.length()-1);
                    idsForQuary.add(tempIdStrore);
                    itterator = 0;
                    tempIdStrore="";
                    tempIdStrore+=result+",";
                }

                Log.d(TAG, tempIdStrore);
                Log.d(TAG, ""+result);
                Log.e(TAG, ""+firstLevel.toString());


            }

        }
        catch (Exception e){

        }
        //insert last 15 ids
        idsForQuary.add(tempIdStrore);
        Log.d(TAG, ""+idsForQuary.size());
    }
}