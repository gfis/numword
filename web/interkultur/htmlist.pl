#!/usr/bin/perl

# Generate HTML lists for numbers, weekdays, months etc.
# @(#) $Id: rename.pl 113 2009-04-06 14:57:07Z gfis $
# 2025-09-10: Georg Fischer; *CH
#-------------------------------------------------------
use strict;
use warnings;
use integer;


my %classes = qw(
  c Zahlen
  g Grußformeln
  m Monate
  s Jahreszeiten
  w Wochentage
  );

my $class;
while (scalar(@ARGV) > 0 and ($ARGV[0] =~ m{\A[\-\+]})) {
    my $opt = shift(@ARGV);
    if (0) {
    } elsif ($opt  =~ m{([cgmsw])}) {
        $class =  $1;
    } elsif ($opt  =~ m{([i])}) {
        &gen_index(); 
        exit(0);
    } else {
        die "invalid option \"$opt\"\n";
    }
} # while $opt

my %langs; # enabled languages
my $class_name = $classes{$class};

&head();
&read_langs("roman,braille,morse,tlh,slo");
foreach my $iso (sort(keys(%langs))) {
  print <<"GFis";
<tr><td>$langs{$iso}</td>
GFis
  &lang_row($class, $iso);
  print <<"GFis";
</tr>
GFis
} # foreach $lang 
print <<"GFis";
</table>
<p>
Zur&uuml;ck zur <a href="index.html">&Uuml;bersicht</a>
</p>
GFis
&tail();
#--------
sub gen_index {
  $class_name = "Übersicht";
  &head();
  print << "GFis";
<table>
GFis
  foreach my $class (sort(keys(%classes))) {
    print <<"GFis";
    <tr><td><a href=\"$class.html\">$classes{$class}</a></td></tr>
GFis
  } # for $class
  print << "GFis";
</table> 
<p>
Kommentare, Fragen? -&gt; <a href="mailto:dr dot georg dot fischer at gmail ...">Dr. Georg Fischer</a>
</p>
GFis
  &tail();
} # gen_index

sub lang_row {
  my ($class, $iso) = @_;
  my @words = map {
    s/\A\d+\s+//; $_
    } split(/\r?\n/, `java -jar ../../dist/numword.jar -l $iso -$class`);
  if ($class eq "c") { # cardinals
    @words = splice(@words, 0, 13);
  }
  foreach my $word(@words) {
    print "<td>$word</td>";
  } # for $word
} # lang_row

sub read_langs {
  my ($exclist) = @_; # comma separated list of excluded language codes
  my %exc = map { ($_, 1) } split(/\,/, $exclist);
  %langs = ();
  open(LAL, "<", "list.tmp") || die "cannot read list.tmp";
  while(<LAL>) {
    s/\s+\Z//; # chompr
    my ($iso, $name) = split(/ +\- +/);
    if (!defined($exc{$iso})) {
      $langs{$iso} = $name;
      print "<!-- $iso: $name -->\n";
    }
  } # while <LAL>
  close(LAL);
} # read_langs

sub head { # print the HTML header
  print << "GFis";
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" [
]>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>$class_name</title>
<meta name="generator" content="https://github.com/gfis/numword/blob/master/web/interkultur/htmlist.pl" />
<meta name="author"    content="Georg Fischer" />
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8" />
<!-- <meta name="robots" content="noindex, nofollow" /> -->
<style>
body,table,p,td,th
        { font-family: Verdana,Arial,sans-serif; }
table   { border-collapse: collapse; }
td      { padding-right: 4px; }
tr,td,th{ vertical-align: top; }
th      { text-align: left; }
.arr    { text-align: right; background-color: white; color: black; }
.bor    { border-left  : 1px solid gray    ; padding-left: 8px; padding-right: 8px; 
          border-top   : 1px solid gray    ;
          border-right : 1px solid gray    ;                                
          border-bottom: 1px solid gray    ;  }
.refp   { background-color: lightgreen;    } /* refers to the partner */
.warn   { background-color: yellow;        } /* no reference to the partner and newer */
.comt   { background-color: peachpuff;     } /* lightpink; #fff0f0; a lightred like in history, for conclusion */
.err    { background-color: red; color: white; } /* for errors */
.refn   { background-color: greenyellow;   } /* no reference to the partner */
.more   { color:white  ; background-color: blue;      }
.dead   { color:white  ; background-color: gray;      }
.fini   { color:black  ; background-color: turquoise; }
.narrow { font-size    : smaller;  }
.prefor { font-family  : monospace; white-space: pre;
          font-size    : large; font-weight: bold; }
.same   { background-color:lightyellow }
</style>
</head>
<body>
<h2>$class_name</h2>
<table>
GFis
} # head

sub tail { # print the HTML trailer
  print << "GFis";
</body></html>
GFis
} # tail
