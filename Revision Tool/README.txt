


##
##
##Documentation
##
##

A detailed breakdown of the tool, and how to use it, is found in the RevisionTool.docx file.
The other files are supplementary, and could contain information pertaining to various aspects of
the implementation itself. The RevisionToolWeights.docx file was a walkthrough through the other version
of the tool that I was working on earlier in the summer. The implementation using various weights.

##
##
##Template files
##
##

##
##Ranking File
##

The ranking file is used to define a ranking state for General revision.
The format of the file takes the following format:

Default value
Rank: State,State
Rank: State,State

Eg
4
0:01,00
1:11

Any state not specified in the configuration file will be assigned the default value for it's rank.

##
##Report Function File
##

The report function file is used to define how reports are applied to the Trust Graph. There are two main
sections -defaults- and -combinations-. State combinations made in the -combinations- header will have specific
values applied during the add report process.

Formula's can be used to modify existing Trust Graph values. A formula must be inputted in a specific format. Like that 
below:

f(v) = v + 2

The variable 'v' represents the current trust graph edge value.

The format of the file takes the following format:

#comment
#comment

-defaults-
increase value, decrease value

-combinations-
state,state,increase val, decrease val


Eg file:

-defaults-
2, 1
#f(v) = (1.5 * v) + 1, f(v) = (0.5 * v) - 1

-combinations-
00,01, 1, 0.75
00,11, 1, 0.5
10,01, 1,0.25
#10,11, f(v) = 2 * v, f(v) = (0.5 * v) - 1






