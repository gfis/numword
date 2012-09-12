/*  Spelling of numbers in Klingon (tlhIngan-Hol),
    spoken in StarTrek movies (Paramount Pictures)
    @(#) $Id: TlhSpeller.java 657 2011-03-17 07:56:38Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2005-07-21, Georg Fischer: copied from FinSpeller
    
    From the book: "The Klingon Dictionary" by Marc Okrand,
    Pocket Books, New York / London / Toronto / Sydney,
    first printing January 1992, ISBN 0-671-74559-X
    
    The numbers are rather regular, like in English,
    except for special words for 10**4 and 10**5.
    
    Maximum number: 999,999,999.
    
    Not yet properly implemented:
    - netlh = 10**4 and bIp = 10**5
    - parsing

*/
/*
 * Copyright 2006 Dr. Georg Fischer <punctum at punctum dot kom>
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
/**
 *  Spelling of numbers in Klingon (tlhIngan-Hol)
 *  @author Dr. Georg Fischer
 */
public class TlhSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: TlhSpeller.java 657 2011-03-17 07:56:38Z gfis $";
    
    /**
     *  Constructor
     */
    public TlhSpeller() {
        super();
        setIso639("tlh");
        setDescription("Klingon (tlhIngan-Hol)");
        setMaxLog(9);
        setSeparator(true);

        wordN = new String[]
        { "pagh"            // 0
        , "wa\'"            // 1
        , "cha\'"           // 2
        , "wej"             // 3
        , "loS"             // 4
        , "vagh"            // 5
        , "jav"             // 6
        , "Soch"            // 7
        , "chorgh"          // 8
        , "Hut"             // 9
        };
        // wordN0 = new String[] - postfix with 10* = "maH"
        // word1N = new String[] - prefix  with 10+ = "wa\'maH "

        setMorphem("h1", "vatlh");
        setMorphem("t1", "SaD");
        setMorphem("t2", "SanID");
        setMorphem("k1", "maH");    // 10**1
        setMorphem("k4", "netlh");  // 10**4
        setMorphem("k5", "bIp");    // 10**5
        setMorphem("m1", "\'uy\'");
        setMorphem("p0", " ");
        enumerateMorphems();
    }
    
    /**
     *  Appends the wording for a triple of digits, 
     *  plus the remaining power of 1000
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
        // hundreds
        switch (digitN00) {
            case 0:
                break;
            default:
                putWord(wordN[digitN00]);
                if (logTuple < 1) {
                    append("vatlh");    // 10**2
                }
                else {
                    append("bIp");      // 10**5
                }
                if (digitN0 > 0 || digitN > 0) {
                    // append(" ");
                }
                break;
        } // switch 100
        
        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0);
                }
                else 
                if (digitN >= 1) { // 0..9
                    spellN(digitN);
                }
                break; 
            default:
                {
                    spellN(digitN0);
                    if (logTuple < 1) {
                        append("maH");  // 10**1
                    }
                    else {
                        append("netlh");    // 10**4
                    }
                    if (digitN > 0) {
                        spellN(digitN);
                    }
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    append("SaD");  //  don't use variant "SanID"
                    append(" ");
                    break;
                default:
                    append("\'uy\'");   // 10**6
                    append (" ");
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple
    
} // TlhSpeller
