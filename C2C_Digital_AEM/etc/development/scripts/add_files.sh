#!/bin/sh
# This script is used to precisely add files to the local git staging area.
# It reads a file named add.list and - for every line that does not start with
# a pound (#) character, constructs a 'git add' command to add it to the local
# git staging area.
#
# Usage:
#
# PROJECT_ROOT=<path> ./etc/development/scripts/add_files.sh
#
# Example:
# $ pwd
# /c/development/validate/C2C_Digital_AEM
# PROJECT_ROOT=`pwd` ./etc/development/scripts/add_files.sh
#echo $PROJECT_ROOT

cat ${PROJECT_ROOT}/etc/development/scripts/add.list |
while read line
 do 
   echo $line | grep -q '^#'
#   if [ $? -eq 0 ]
   if [ $? -ne 0 ]
#   if [ `echo $line | grep -q '^#'` -eq 0 ]
    then
#      echo $line
      command="git add $line"
      echo $command
      `$command`
   fi
done
