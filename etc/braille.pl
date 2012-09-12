 #!/usr/bin/perl
 #
 # braille.pl - Create Braille images
 # Copyright (C) 2005 Claus Faerber <claus@faerber.name>
 #
 # This program is free software; you can redistribute it and/or modify it
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
 # As a special exception, permission is granted to include the source code
 # of this program into a document and copy, distribute and/or modify that
 # document under the terms of the GNU Free Documentation License, Version
 # 1.2 or any later version published by the Free Software Foundation; with
 # no Invariant Sections, no Front-Cover Texts, and no Back-Cover Texts. 
 #
 # If you write modifications of your own for this software, it is your
 # choice whether to permit this exception to apply to your modifications.
 # If you do not wish that, delete this exception notice.
 #
 
 %data = (
   'A1' => '1',
   'B2' => '101',
   'Bracket' => '001111',
   'C3' => '11',
   'CapitalSign' => '000001',
   'Comma' => '001',
   'D4' => '1101',
   'E5' => '1001',
   'ExclamationPoint' => '00111',
   'F6' => '111',
   'G7' => '1111',
   'H8' => '1011',
   'Hyphen' => '000011',
   'I9'    => '0110',
   'J0'    => '0111',
   'K'     => '10001',
   'L'     => '101010',
   'M'     => '110010',
   'N'     => '110110',
   'NumberSign' => '010111',
   'O'     => '100110',
   'P'     => '111010',
   'Period'=> '001101',
   'Q'     => '111110',
   'QuestionMark' => '001011',
   'QuoteClose' => '000111',
   'QuoteOpen' => '001011',
   'R'   => '101110',
   'S'   => '011010',
   'Semicolon' => '001010',
   'T'   => '011110',
   'U'   => '100011',
   'V'   => '101011',
   'W'   => '011101',
   'X'   => '110011',
   'Y'   => '110111',
   'Z'   => '100111',
 );
 

 
 my $x = 154;
 my $y = 215;
 my $r = 15;
 my $str = 1;
 $x = 30;
 $y = 43;
 $r = 4;
 
 foreach (keys(%data)) {
   open STDOUT, ">Braille_$_.svg";
 
   print "<?xml version=\"1.0\" standalone=\"no\"?>\n";
   print "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";
   print "<svg width=\"$x\" height=\"$y\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n";
#   print "<rect x=\"0\" y=\"0\" width=\"$x\" height=\"$y\" fill=\"white\" stroke-width=\"1\" stroke=\"black\" />\n";
 
   for( $i=0; $i<6; $i++ )
   {
     my $xp = ($i % 2);
     my $yp = int($i/2);
   
     printf "<circle cx=\"%f\" cy=\"%f\"",
       ($x-4*$r)/3 * (1+$xp) + (2*$xp+1)*$r,
       ($y-6*$r)/4 * (1+$yp) + (2*$yp+1)*$r;
 
     if(int(substr($data{$_},$i,1))) 
     {
       printf " r=\"%f\"", $r;
       printf " fill=\"black\"";
     } else {
       printf " r=\"%f\"", $r-$str/2;
       printf " stroke=\"black\" stroke-width=\"%d\" fill=\"none\"", $str;
     }
     print " />\n";
   }
   print "</svg>";
 };
