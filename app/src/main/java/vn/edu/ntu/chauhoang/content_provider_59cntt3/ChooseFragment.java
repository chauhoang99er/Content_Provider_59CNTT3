package vn.edu.ntu.chauhoang.content_provider_59cntt3;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class ChooseFragment extends Fragment implements View.OnClickListener
{
    public static final String KEY_TU_NGAY = "tu-ngay";
    public static final String KEY_DEN_NGAY = "den-ngay";
    EditText edttungay,edtdenngay;
    ImageView imvtungay, imvdenngay;
    ImageButton imBtnhienthi;
    NavController navController;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtdenngay = view.findViewById(R.id.edtdenngay);
        edttungay = view.findViewById(R.id.edttungay);
        imvdenngay = view.findViewById(R.id.imvdenngay);
        imvtungay = view.findViewById(R.id.imvtungay);
        imBtnhienthi = view.findViewById(R.id.imBtnhienthi);
        imvdenngay.setOnClickListener(this);
        imvtungay.setOnClickListener(this);
        imBtnhienthi.setOnClickListener(this);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        ((MainActivity)getActivity()).navcontroller = this.navController;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.imvtungay: thietLapNgay(0);break;
            case R.id.imvdenngay: thietLapNgay(1);break;
            case R.id.imBtnhienthi: hienthisms();break;
        }
    }

    private void thietLapNgay(final int Choose)
    {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                StringBuilder builder = new StringBuilder();
                builder.append(year)
                        .append("-")
                        .append(month)
                        .append("-")
                        .append(dayOfMonth);
                if (Choose == 0)
                    edttungay.setText(builder.toString());
                else
                    edtdenngay.setText(builder.toString());
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),listener
        ,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void hienthisms()
    {
        if (edttungay.getText().toString().length()>0 &&
                edtdenngay.getText().toString().length()>0)
        {
            Bundle data = new Bundle();
            data.putCharSequence(KEY_TU_NGAY, edttungay.getText().toString());
            data.putCharSequence(KEY_DEN_NGAY, edtdenngay.getText().toString());
            navController.navigate(R.id.action_chooseFragment_to_smsFragment,data);
        }
    }
}