/*  Spelling of Roman Numbers
    consisting of the letters I V X L C D M
    as used in the Roman Empire
    and thereafter, for example for years on grave stones
    @(#) $Id: RomanSpeller.java 657 2011-03-17 07:56:38Z gfis $
    2012-09-14: zero => empty
    2011-03-14, Dr. Georg Fischer: copied from TlhSpeller

    Maximum number: 2,999.

    Not yet properly implemented:
    - parsing
*/
/*
 * Copyright 2011 Dr. Georg Fischer <punctum at punctum dot kom>
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
import  org.teherba.numword.LatSpeller;

/** Spelling of Roman Numbers
 *  consisting of the letters I V X L C D M
 *  as used in the Roman Empire
 *  and thereafter, for example for years on grave stones.
 *  The digits 1 to 10 are I, II, III, IV, V, VI, VII, VIII, IX, X.
 *  The same pattern of mod 5 and prefixing for mod 5 - 1 is used for
 *  higher powers of 10.
 *  @author Dr. Georg Fischer
 */
public class RomanSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: RomanSpeller.java 657 2011-03-17 07:56:38Z gfis $";

    /** local Latin speller for months, seasons, week days etc. */
    private LatSpeller latSpeller;

    /** Constructor
     */
    public RomanSpeller() {
        super();
        setIso639("roman");
        setDescription("Roman Numbers");
        setMaxLog(4);
        setSeparator(false);
        latSpeller = new LatSpeller();
    } // Constructor

    /** Maps a roman number letter to its integer equivalent
     *  @param letter roman number letter (uppercase or lowercase) I V X L C D M etc.
     *  @return integer value of that letter, or 0 if the letter cannot occur in roman numbers
     */
    private int getEquivalent(char letter) {
        int result = 0;
        switch (Character.toUpperCase(letter)) {
            default:                    break;
            case 'I': result = 1;       break;
            case 'V': result = 5;       break;
            case 'X': result = 10;      break;
            case 'L': result = 50;      break;
            case 'C': result = 100;     break;
            case 'D': result = 500;     break;
            case 'M': result = 1000;    break;
        } // switch
        return result;
    } // getEquivalent

    /** Parses a string, and returns a digit sequence if
     *  it starts with a number word.
     *  Always tries to match a longest prefix.
     *  @param text string to be parsed, should start with roman number letters; space stops the search
     *  @param offset offset in <em>text</em> where to start parsing
     *  @return new offset in <em>text</em> of first particle not recognized,
     *  and sequence of digit characters in global <em>result</em>
     */
    public int parseString(String text, int offset) {
        result.delete(0, result.length()); // clear buffer
        int value = 1; // 0 terminates the loop
        int number = 0; // accumulated number
        int lastValue = 0x7fffffff; // a value higher than any roman number
        while (value > 0 && offset < text.length()) { // try to find a proper digit
            char letter = text.charAt(offset);
            value = getEquivalent(letter);
            if (value > 0) { // this was a proper roman number letter
                if (value > lastValue) { // apply subtraction rule
                    number = number
                            - lastValue // that was added in last loop iteration
                            - lastValue + value; // e.g. IX = -1 + 10
                } else { // apply normal addition rule
                    number += value;
                }
                offset ++;
            } else {
                // break the loop
            }
            lastValue = value;
        } // while proper digits found
        result.append(String.valueOf(number));
        return offset;
    } // parseString(String, int)

    /** Appends the wording for a triple of digits,
     *  plus the remaining power of 1000.
     *  Not used here, but needed because it is <em>abstract</em>.
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
    } // spellTuple

    /** Spells a roman number's digit.
     *  @param digit  the digit to be spelled: 0-9
     *  @param unit10 the character for the next higher power of 10, for example "X"
     *  @param unit5  the character for <em>5 * unit1</em>,          for example "V"
     *  @param unit1  the character for this            power of 10, for example "I"
     */
    public void spellRomanDigit(int digit, char unit10, char unit5, char unit1) {
        if (digit >= 5 && digit < 9) {
            result.append(unit5);
        }
        switch (digit) {
            case 4:
                result.append(unit1);
                result.append(unit5);
                break;
            case 3:
                result.append(unit1);
                // fall thru
            case 2:
                result.append(unit1);
                // fall thru
            case 1:
                result.append(unit1);
                break;
            case 0:
                break;
            case 9:
                result.append(unit1);
                result.append(unit10);
                break;
            case 8:
                result.append(unit1);
                // fall thru
            case 7:
                result.append(unit1);
                // fall thru
            case 6:
                result.append(unit1);
                break;
            case 5:
                break;
        } // switch digit
    } // spellRomanDigit

    /** Returns the word for a number in some language.
     *  This method is the heart of the package.
     *  It assumes the "normal" european numbering system
     *  derived from latin. The entire number is splitted
     *  into triples of digits: hundreds, tens, and ones.
     *  These are spelled in order, joined by some morphemes
     *  like "and", and "s" for plural.
     *  The words for ones, tens, for 10..19 and sometimes
     *  for the hundreds are stored in language specific arrays.
     *  @param number a sequence of digit characters, maybe
     *  interspersed with non-digits (spaces, punctuation).
     *  @return number word
     */
    public String spellCardinal(String number) {
        result.setLength(0);
        StringBuffer buffer = new StringBuffer(1024);
        int position = 0;
        while (position < number.length()) { // remove non-digits
            char ch = number.charAt(position);
            if (Character.isDigit(ch)) {
                buffer.append(ch);
            }
            position ++;
        } // while non-digits
        number = buffer.toString();
        result = new StringBuffer(64);
        realLog = number.length();
        int exp10 = realLog - 1;
        if (number.equals("0")) {
            // empty result
        } else if (realLog <= maxLog) { // number can be spelled in this language
            position = 0;
            while (exp10 >= 0) { // process all digits
                int digit  = Character.digit(number.charAt(position), 10);
                switch (exp10) {
                    case 3:
                        spellRomanDigit(digit, ' ', ' ', 'M');
                        break;
                    case 2:
                        spellRomanDigit(digit, 'M', 'D', 'C');
                        break;
                    case 1:
                        spellRomanDigit(digit, 'C', 'L', 'X');
                        break;
                    case 0:
                        spellRomanDigit(digit, 'X', 'V', 'I');
                        break;
                } // switch
                exp10 --;
                position ++;
            } // while all digits
        } else { // realLog = 0 or too big
            result.append(number + " >= 1");
            for (int pos = 0; pos < maxLog; pos ++) {
                result.append('0');
            } // for int
        }
        return (result.toString());
    } // spellCardinal

    /** Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        return latSpeller.spellMonth(month);
    } // spellMonth(int)

    /** Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -&gt; Spring, 2 -&gt; Summer, 3 -&gt; Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        return latSpeller.spellSeason(season);
    } // spellSaison(int)

    /** Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 (and 0) -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        return latSpeller.spellWeekDay(weekDay);
    } // spellWeekDay(int)

} // RomanSpeller
