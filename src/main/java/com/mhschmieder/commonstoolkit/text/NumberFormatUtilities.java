/**
 * MIT License
 *
 * Copyright (c) 2020, 2021 Mark Schmieder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is part of the CommonsToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * CommonsToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/commonstoolkit
 */
package com.mhschmieder.commonstoolkit.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * This is a utility class for methods related to common use of Number Format
 * functionality. Many of these methods are placeholders for legacy code,
 * where we would prefer to eventually move to Number Converters instead.
 */
public final class NumberFormatUtilities {

    // The constructor is disabled, as this is a static utilities class.
    private NumberFormatUtilities() {}

    @SuppressWarnings("nls")
    public static NumberFormat getUniquefierNumberFormat( final Locale locale ) {
        final NumberFormat uniquefierNumberFormat = NumberFormat.getNumberInstance( locale );

        // :NOTE: The choice of three digits for name uniqueness, is to allow
        // for table row sorting in numeric order by forcing leading zeroes. Any
        // more than three digits might cause a comma or other locale-specific
        // delimiter to be inserted.
        if ( uniquefierNumberFormat instanceof DecimalFormat ) {
            final DecimalFormat decimalFormat = ( DecimalFormat ) uniquefierNumberFormat;
            decimalFormat.applyPattern( "_000" );
            decimalFormat.setDecimalSeparatorAlwaysShown( false );
        }

        return uniquefierNumberFormat;
    }

    public static NumberFormat getUnitDecoratedDecimalFormat( final String numericFormatterPattern,
                                                              final String measurementUnitString,
                                                              final Locale locale ) {
        // Use a decimal formatter that defaults to integers or floating point
        // when possible, determined by the supplied numeric formatter pattern.
        // :NOTE: Not all locales support decimal formatting. In such cases, we
        // also forego units as we don't want to have to defer the expensive
        // creation of the number formatter to the callback methods, where we
        // could alternately format the number with the unit but no localization
        // of number representation. This should be revisited for commonality.
        final NumberFormat numberFormat = NumberFormat.getNumberInstance( locale );
        if ( numberFormat instanceof DecimalFormat ) {
            final DecimalFormat decimalFormat = ( DecimalFormat ) numberFormat;
            final String augmentedPattern = ( ( measurementUnitString != null )
                    && !measurementUnitString.trim().isEmpty() )
                        ? numericFormatterPattern + measurementUnitString
                        : numericFormatterPattern;
            decimalFormat.applyPattern( augmentedPattern );
            decimalFormat.setDecimalSeparatorAlwaysShown( false );
        }

        return numberFormat;
    }

}// class NumberFormatUtilities