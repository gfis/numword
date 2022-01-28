/*  Spelling of numbers in Morse code - language independant,
    output a number of dots and hpyhens
    @(#) $Id: MorseSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2016-02-15: copied from MorseSpeller, and from xtrans.misc.MorseCodeTransformator
*/
/*
 * Copyright 2016 Dr. Georg Fischer <punctum at punctum dot kom>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.teherba.numword;
import  org.teherba.numword.BaseSpeller;
import  java.io.File;
import  java.io.PrintWriter;
import  java.util.HashMap;
import  org.apache.logging.log4j.Logger;
import  org.apache.logging.log4j.LogManager;

/** Spells numbers in Morse code.
 *  @author Dr. Georg Fischer
 */
public class MorseSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: MorseSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /** No-args Constructor
     */
    public MorseSpeller() {
        super();
        setIso639("morse");
        setDescription("Morse Code");
        setMaxLog(1); // arbitrary length limitation
        setSeparator(false);
    } // Constructor()

    /** Gets the codes for the dots and hyphens of a Morse character.
     *  @param ch character to be shown (a decimal digit)
     *  @return a string of 5 dots and hyphens, or null if the character
     *  cannot be mapped.
     */
    private String getMorseCode(char ch) {
        String result = null;
        switch (ch) {
            default : result = null;    break;
            case '1': result = ".----"; break;
            case '2': result = "..---"; break;
            case '3': result = "...--"; break;
            case '4': result = "....-"; break;
            case '5': result = "....."; break;
            case '6': result = "-...."; break;
            case '7': result = "--..."; break;
            case '8': result = "---.."; break;
            case '9': result = "----."; break;
            case '0': result = "-----"; break;
        } // switch ch
        return result;
    } // getMorseCode

    /** Needed since method is <em>abstract</em> in {@link BaseSpeller}.
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
    } // spellTuple

    /** Returns the Morse code for a number string.
     *  @param number a sequence of decimal digits
     *  @return a series of dots and hyphens
     */
    public String spellCardinal(String number) {
        StringBuffer result = new StringBuffer(1024);
        int pos = 0;
        while (pos < number.length()) {
            result.append(' ');
            result.append(getMorseCode(number.charAt(pos ++)));
        } // while pos
        return result.toString().substring(1);
    } // spellCardinal

} // MorseSpeller
