package ir.protelco.pidget.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;

import ir.protelco.pidget.R;
import ir.protelco.pidget.font.FontAdapter;
import ir.protelco.pidget.font.FontType;
import ir.protelco.pidget.parsi.Parsi;
import ir.protelco.pidget.parsi.ParsiUtils;
import ir.protelco.pidget.utils.Utils;

import static ir.protelco.pidget.parsi.ParsiUtils.*;


public class ParsiEditText extends AppCompatEditText {

    private boolean shouldReplaceWithParsiDigits;
    private FontType fontType;
    private boolean shouldHideBottomLine;

    private InputFilter inputFilter ;

    public ParsiEditText(Context context) {
        super(context);

        init(context);
    }

    public ParsiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public ParsiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs , defStyleAttr);
    }


    private void init(Context context) {

        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.ParsiEditText);

        initialize(context,typedArray);

        typedArray.recycle();
    }

    private void init(Context context, AttributeSet attributeSet) {

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ParsiEditText, 0, 0);

        initialize(context,typedArray);

        typedArray.recycle();

    }

    private void init(Context context,AttributeSet attributeSet, int defStyleAttr){

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.ParsiEditText,defStyleAttr,defStyleAttr) ;

        initialize(context,typedArray);

        typedArray.recycle();
    }

    private void initialize(Context context ,TypedArray typedArray){

        shouldReplaceWithParsiDigits = typedArray.getBoolean(R.styleable.ParsiEditText_replaceWithPersianDigits, true);
        fontType = FontType.getType(typedArray.getInt(R.styleable.ParsiEditText_fontAdapterType, 0));
        shouldHideBottomLine = typedArray.getBoolean(R.styleable.ParsiEditText_hideBottomLine, true);

        setTypeface(FontAdapter.getInstance(context).getMatchingTypeface(fontType));

        if (shouldHideBottomLine) {
            getBackground().mutate().setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent), PorterDuff.Mode.SRC_ATOP);
        }

        inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {

                if(Utils.containsDigits(charSequence.toString()) && shouldReplaceWithParsiDigits()){

                    return replaceWithParsiDigits(charSequence.toString()) ;
                }

                return charSequence ;
            }
        } ;

        this.setFilters(new InputFilter[]{inputFilter});
    }

    public boolean shouldReplaceWithParsiDigits() {
        return shouldReplaceWithParsiDigits;
    }

    public void setShouldReplaceWithParsiDigits(boolean shouldReplaceWithParsiDigits) {
        this.shouldReplaceWithParsiDigits = shouldReplaceWithParsiDigits;

    }

    public FontType getFontType() {
        return fontType;
    }

    public void setFontType(FontType fontType) {
        this.fontType = fontType;
    }

    public boolean shouldHideBottomLine() {
        return shouldHideBottomLine;
    }

    public void setShouldHideBottomLine(boolean shouldHideBottomLine) {
        this.shouldHideBottomLine = shouldHideBottomLine;

        if(shouldHideBottomLine){

            getBackground().mutate().setColorFilter(ContextCompat.getColor(getContext(), R.color.transparent), PorterDuff.Mode.SRC_ATOP);
        }
    }

}
