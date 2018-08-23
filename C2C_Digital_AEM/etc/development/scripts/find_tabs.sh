#!/bin/sh
#
# This script will produce a list of all files containing a horizontal TAB
# character.
#
# Usage:
#
#   ./find_tabs.sh [path]
#

if [ "x$1" != "x" ]
 then
    path=$1
else
    path="."
fi

#pattern='$\011'
#command="find $path -exec grep -m 1 -q $pattern {} \; -a -print"
#find $path -exec grep -m 1 -q $'\011' {} \; -a -print
#echo $command
#$command
#find $path -type f -name .git -prune -o -exec grep -m 1 -q $'\011' {} \; -a -print
find $path \( -name .git -o -name \.vlt -o -name \*\.png -o -name \*\.jpe\*g -o -name \*\.ico -o -name \*~ \) -prune -o -type f -exec grep -m 1 -q $'\011' {} \; -a -print | grep -v -E '(renditions/original|image|folderThumbnail|image/file)$'

