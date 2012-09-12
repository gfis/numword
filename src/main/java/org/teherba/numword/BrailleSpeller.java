/*  Spelling of numbers in Braille notation - language independant,
    output a number of SVG objects 
    @(#) $Id: BrailleSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2009-11-26: copied from IceSpeller

	Uses a Java version of the SVG output program braille.pl of Claus Faerber, 
	c.f. http://commons.wikimedia.org/wiki/File:Braille_A1.svg

	A working example of an SVG file URL-encoded in a data URI is:
	<object  type="image/svg+xml" data="data:text/xml;charset=utf-8,<?xml version=%221.0%22%20standalone=%22no%22?><!DOCTYPE%20svg%20PUBLIC%20%22-//W3C//DTD%20SVG%201.1//EN%22%20%22http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd%22><svg%20width=%2230%22%20height=%2243%22%20version=%221.1%22%20xmlns=%22http://www.w3.org/2000/svg%22><circle%20cx=%228%22%20cy=%228%22%20r=%224%22%20stroke=%22black%22%20stroke-width=%221%22%20fill=%22none%22%20/><circle%20cx=%2220%22%20cy=%228%22%20r=%224%22%20fill=%22black%22%20/><circle%20cx=%228%22%20cy=%2220%22%20r=%224%22%20fill=%22black%22%20/></svg>" />	
	
 # braille.pl is free software; you can redistribute it and/or modify it
 # under the terms of the GNU General Public License as published by the
 # Free Software Foundation; either version 2 of the License, or (at your
 # option) any later version.
 # 
 # This program is distributed in the hope that it will be useful, but
 # WITHOUT ANY WARRANTY; without even the implied warranty of
 # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 # Public License for more details.
 # 
 # You should have received a copy of the GNU General Public License along
 # with this program; if not, write to the Free Software Foundation, Inc.,
 # 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 #
*/
/*
 * Copyright 2009 Dr. Georg Fischer <punctum at punctum dot kom>
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
import 	java.io.File;
import	java.io.PrintWriter;
import 	java.util.HashMap;
import  org.apache.log4j.Logger;

/** Spells numbers in Braille code.
 *  Uses the SVG files created by org.teherba.numword.BrailleSpeller.main.
 *	These are small images with 6 dots with names like braille_0000000.svg.
 *  @author Dr. Georg Fischer
 */
public class BrailleSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: BrailleSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /** No-args Constructor
     */
    public BrailleSpeller() {
        super();
        setIso639("braille");
        setDescription("Braille Code");
        setMaxLog(32); // arbitrary length limitation
        setSeparator(false);
	} // Constructor()
 
	/** Gets the codes for the dots of a Braille character.
 	 *  The positions in the returned string are mapped here as follows:
 	 *  <pre>
	 *  	0 1
 	 *  	2 3
 	 *  	4 5
 	 *   </pre>
 	 *  The usual Braille dot numbering scheme, however, is:
 	 *  <pre>
 	 *  	1 4
	 *  	2 5
 	 *  	3 6
 	 *  </pre>
	 *	@param ch character to be shown (letters and some punctuation)
	 *	@return a string of 6 zeroes or ones, or null if the character
	 *	cannot be mapped.
	 */
	private String getBrailleCode(char ch) {
		String code = null; 
		switch (ch) {	
			case ' ':	code = "000000"; break; //space, could be suppressed in output
			case '1':
			case 'A':	code = "100000"; break;
			case '2':
	  		case 'B':	code = "101000"; break;
	  		case '[':
	  		case ']':
	  		case '{':
	  		case '}':	code = "001111"; break; // Bracket - either opening or closing
			case '3':
  			case 'C':	code = "110000"; break;
  			case '^':	code = "000001"; break; // Capital letter follows
			case ',':	code = "001000"; break;
			case '4':
			case 'D':	code = "110100"; break;
			case '5':
			case 'E':	code = "100100"; break;
			case '!':	code = "001110"; break;
			case '6':
			case 'F':	code = "111000"; break;
			case '7':
		  	case 'G':	code = "111100"; break;
			case '8':
		  	case 'H':	code = "101100"; break;
		  	case '-':	code = "000011"; break;
			case '9':
  			case 'I':	code = "011000"; break;
			case '0':
  			case 'J':	code = "011100"; break;
	  		case 'K':	code = "100010"; break;
  			case 'L':	code = "101010"; break;
  			case 'M':	code = "110010"; break;
  			case 'N':	code = "110110"; break;
  			case '#':	code = "010111"; break; // Number follows
	   		case 'O':	code = "100110"; break;
   			case 'P':	code = "111010"; break;
   			case '.':	code = "001101"; break;
  			case 'Q':	code = "111110"; break;
  			case '?':	code = "001011"; break;
			case '`':	code = "001011"; break; // 'QuoteOpen'
			case '\"':
			case '\'':	code = "000111"; break; // 'QuoteClose'
		//	case '\'':	code = "000100"; break; // Apostrophe  
  			case 'R':	code = "101110"; break;
  			case 'S':	code = "011010"; break;
  			case ';':	code = "001010"; break;
	   		case 'T':	code = "011110"; break;
   			case 'U':	code = "100011"; break;
  			case 'V':	code = "101011"; break;
  			case 'W':	code = "011101"; break;
  			case 'X':	code = "110011"; break;
	  		case 'Y':	code = "110111"; break;
  			case 'Z':	code = "100111"; break;
  			default: // unknown character - yields empty result
  				code = null;
  				break;
		} // switch ch
		return code;
	} // getBrailleCode

	/** Gets an SVG+XML file for a character showing the
	 *	6-dot notation of it's Braille code.
	 *	The size of the rectangle and the dots' radius are defined here.
	 *	This method is only called in the <em>main</em> method of this class 
	 *	in order to generate all SVG image files.
	 *	@param code Braille code for the character to be shown (letters and some punctuation)
	 *	@return XML text of the SVG representation
	 */
	private String getBrailleSVG(String code) {
		StringBuffer result = new StringBuffer(512);
		if (code != null) { // known character
			int x = 30; // 154;
			int y = 43; // 215;
			int r =  4; // 15;
			int str = 1;
			result.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
			result.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"");
			result.append(" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
			result.append("<!-- $Id -->\n"); // CVS resp. SVN version identification string
			result.append("<svg width=\"");
			result.append(String.valueOf(x));
			result.append("\" height=\"");
			result.append(String.valueOf(y));
			result.append("\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");
			int pos = 0;
			while (pos < 6) {
				int xp = pos % 2;
   				int yp = (pos / 2);
				result.append("<circle cx=\"");
				result.append(String.valueOf((x - 4 * r) / 3 * (1 + xp) + (2 * xp + 1) * r));
				result.append("\" cy=\"");
				result.append((y - 6 * r) / 4 * (1 + yp) + (2 * yp + 1) * r);
				result.append("\" r=\"");
		 		if (pos < code.length() && code.charAt(pos) == '1') {
 					result.append(String.valueOf(r));
 					result.append("\" fill=\"black\"");
				} else {
    	   			result.append(String.valueOf(r - str / 2));
	 				result.append("\" stroke=\"black\" stroke-width=\"");
 					result.append(String.valueOf(str));
 					result.append("\" fill=\"none\"");
	 			}
 				result.append(" />\n");
 				pos ++;
			} // while pos
			result.append("</svg>\n");
		} // known character
		return result.toString();
 	} // getBrailleSVG

	/**	Gets an HTML object reference to the SVG image file
	 *	showing the Braille representation of the specified character.
	 *	@param ch character to be shown
	 *	@return HTML object element referencing the SVG file
	*/
	private String getSVGObjectRef(char ch) {
		String code = getBrailleCode(Character.toUpperCase(ch));
		if (code == null) {
			code = getBrailleCode('?');
		}
		return "<object type=\"image/svg+xml\" data=\"braille/braille_"
				+ code 
				+ ".svg\"></object>\n";
	} // getSVGObjectRef

    /** Needed since method is <em>abstract</em> in {@link BaseSpeller}.
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
    } // spellTuple

    /** Returns Braille code images for any string.
     *  @param number a sequence of characters
     *  @return a series of HTML objects referencing litte SVG images with 6 dots
     */
    public String spellCardinal(String number) {
		StringBuffer result = new StringBuffer(1024);
    	int pos = 0;
    	while (pos < number.length()) {
			result.append(getSVGObjectRef(number.charAt(pos ++)));
    	} // while pos
		return result.toString();
    } // spellCardinal

    /** Commandline interface for number spelling.
     *	If no argument is given, a series of SVG files for the
     *	the Braille codes of all known characters is written.
     *	The files are named "braille_&lt;code&gt;.svg", where 
     *	&lt;code&gt; is a 6-digit string of zeroes and ones.
     *  @param args elements of the commandline separated by whitespace
     */
    public static void main(String args[]) {
        Logger log = Logger.getLogger(BrailleSpeller.class.getName());
        BrailleSpeller speller = new BrailleSpeller();
		if (args.length >= 1) { // show the SVG for all characters in the word
	        String word = args[0];
	        for (int i = 0; i < word.length(); i ++) {
    	    	System.out.println(speller.getBrailleSVG(speller.getBrailleCode(word.charAt(i))));
        	} // for i
        } else { // create SVG files for all known characters
        	char ch = ' ';
        	while (ch < 0xff) {
        		String code = speller.getBrailleCode(ch);
        		if (code != null) { // known character
        			try {
			        	PrintWriter out = new PrintWriter(new File("braille_" + code + ".svg"));
			        	out.print(speller.getBrailleSVG(code));
			        	out.close();
			        } catch (Exception exc) {
			        	log.error(exc.getMessage(), exc);
			        }
		        } // known character
	        	ch ++;
	        } // while ch
    	} // create SVG files
    } // main

} // BrailleSpeller
