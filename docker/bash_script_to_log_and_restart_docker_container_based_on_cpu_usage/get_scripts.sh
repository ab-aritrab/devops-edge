#!/bin/bash
logs=/var/log/process_name.log
container_name=container_name

while :
do
  # Get a variable with the cpu usage for a specific container
  var=`docker stats --no-stream --format "{{.CPUPerc}}" $container_name`
  length=${#var}

  if (( $length==0 )); then
     echo "Container ${container_name} does not exist"
     echo "$(date +'%d-%m-%Y %H:%M') | Container $container_name does not exist" >> $logs
  else
    # CPU usage in number
    percent="${var[@]::-4}"

    echo "Actual cpu usage: ${percent}"

    # Save actual CPU usage in file
    echo "$(date +'%d-%m-%Y %H:%M') | ${percent}" >> $logs
  fi
  sleep 5