package gavin.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import gavin.app.BaseActivity;
import gavin.demo.R;


public class CustomWidgetActivity extends BaseActivity
{
    private boolean needInvoice;
    private RadioGroup  invoiceTypeRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);

        TextView textView = findView(R.id.MarqueeText);
        textView.setSelected(true);

        invoiceTypeRadioGroup = findView(R.id.invoice_type);
        CheckBox needInvoiceCheckBox = findView(R.id.need_invoice);
        needInvoiceCheckBox.setChecked(true);
    }


    public void toggleInvoice(View view)
    {
        needInvoice = ((CheckBox) view).isChecked();
        if (needInvoice)
        {
            showToast("需要发票");
            invoiceTypeRadioGroup.setVisibility(View.VISIBLE);
        }
        else
        {
            showToast("不需要发票");
            invoiceTypeRadioGroup.setVisibility(View.GONE);
        }
    }


    private void setInvoiceType(int type)
    {
        if(type > 0 && type <= 3)
        {
            int invoiceTypeRedioIds[] = {0, R.id.radio0, R.id.radio1, R.id.radio2};
            RadioGroup invoiceTypeRadioGroup = (RadioGroup)findViewById(R.id.invoice_type);
            invoiceTypeRadioGroup.check(invoiceTypeRedioIds[type]);
        }
    }

    public int getInvoiceType()
    {
        int radioButtonID = invoiceTypeRadioGroup.getCheckedRadioButtonId();
        View radioButton = invoiceTypeRadioGroup.findViewById(radioButtonID);
        int index = invoiceTypeRadioGroup.indexOfChild(radioButton);
        return index+1;
    }
}
