package vn.edu.ntu.chauhoang.content_provider_59cntt3;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SmsFragment extends Fragment
{
    ListView lvdsSMS;
    ArrayList<String> dsSMS = new ArrayList<>();
    ArrayAdapter<String> smsadapter;
    boolean Duocphepdocsms = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sms, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Duocphepdocsms = ((MainActivity)getActivity()).duocphepdocSMS;
        lvdsSMS = view.findViewById(R.id.dsSMS);
        smsadapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dsSMS);
        lvdsSMS.setAdapter(smsadapter);
        //if(Duocphepdocsms == true)
            hienthiSMS();
    }

    public void hienthiSMS()
    {
        Bundle data = getArguments();
        String strtungay = data.getString(ChooseFragment.KEY_TU_NGAY);
        String strdenngay = data.getString(ChooseFragment.KEY_DEN_NGAY);
        Date tungay = Date.valueOf(strtungay); //Chuyển chuỗi ngày thành đối tượng Date
        Date denngay = Date.valueOf(strdenngay);
        dsSMS.clear();
        //Lấy Uri
        Uri uriSMS = Telephony.Sms.Inbox.CONTENT_URI;
        ContentResolver resolver = getActivity().getContentResolver();

        Cursor cursor = resolver.query(uriSMS, new String[]{"address","date","body"}
        ,null,null,null);

        if(cursor != null)
        {
            int addressCol = cursor.getColumnIndex("address");
            int dateCol = cursor.getColumnIndex("date");
            int bodyCol = cursor.getColumnIndex("body");
            //3 cột để kiểm tra
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            while (cursor.moveToNext())
            {
                //address -> Text
                //date -> Interger -> Long
                //Body -> Text
                long s = cursor.getLong(dateCol);
                Date date = new Date(s);
                if (date.compareTo(tungay) == 1 && date.compareTo(denngay) == -1)
                {
                    String address = cursor.getString(addressCol);
                    String body = cursor.getString(bodyCol);
                    String sms = address + "\n" + dateFormat.format(date) + "\n" + body;
                    dsSMS.add(sms);
                }
            }
        }
        smsadapter.notifyDataSetChanged();
    }
}