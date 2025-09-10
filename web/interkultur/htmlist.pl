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

my $class = "c"; # cardinal number word
while (scalar(@ARGV) > 0 and ($ARGV[0] =~ m{\A[\-\+]})) {
    my $opt = shift(@ARGV);
    if (0) {
    } elsif ($opt  =~ m{c}) {
        $class =  shift(@ARGV);
    } else {
        die "invalid option \"$opt\"\n";
    }
} # while $opt

my %langs = (); # enabled languages
&read_langs("roman,braille,morse,tlh");

my $numword = "java -jar ../../dist/numword.jar";

#--------
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
      print "$iso: $name\n";
    }
  } # while <LAL>
  close(LAL);
} # read_langs

sub head { # print the HTML header
	print << "GFis";
<html>
<head>
</head>
<body>
GFis
} # head  

sub tail { # print the HTML trailer
	print << "GFis";
</body></html>
GFis
} # tail
