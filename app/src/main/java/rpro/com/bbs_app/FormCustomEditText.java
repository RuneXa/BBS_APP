package rpro.com.bbs_app;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;

/**
 * Created by Richie on 04/01/2017.
 */
public class FormCustomEditText extends AutoCompleteTextView {
    public FormCustomEditText(Context context) {
        super(context);
    }

    public FormCustomEditText(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public FormCustomEditText(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getAdapter() != null) {
            showDropDown();
            performFiltering(getText(), 0);
        } else if(getAdapter() != null) {
            String str = getText().toString();
            ListAdapter listAdapter = getAdapter();
            if(str.length() > 0){
                for(int i = 0; i < listAdapter.getCount(); i++) {
                    String temp = listAdapter.getItem(i).toString();
                    if(str.compareToIgnoreCase(temp) == 0) { //TODO: Create AutoComplete Logic
                        setText(temp);
                        return;
                    } else {
                        setText("");
                    }
                }
            } else {
                setText("");
            }
        }
    }

}
